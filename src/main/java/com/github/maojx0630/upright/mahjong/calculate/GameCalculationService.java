package com.github.maojx0630.upright.mahjong.calculate;

import cn.hutool.core.util.NumberUtil;
import com.github.maojx0630.upright.mahjong.common.exception.StateEnum;
import com.github.maojx0630.upright.mahjong.dto.UserRes;
import com.github.maojx0630.upright.mahjong.model.DanInfo;
import com.github.maojx0630.upright.mahjong.model.DanTitle;
import com.github.maojx0630.upright.mahjong.model.GameData;
import com.github.maojx0630.upright.mahjong.model.GamePoint;
import com.github.maojx0630.upright.mahjong.param.GameDataParam;
import com.github.maojx0630.upright.mahjong.param.ScoreParam;
import com.github.maojx0630.upright.mahjong.service.DanInfoService;
import com.github.maojx0630.upright.mahjong.service.DanTitleService;
import com.github.maojx0630.upright.mahjong.service.UserService;
import com.github.maojx0630.upright.mahjong.utils.IdUtils;
import com.github.maojx0630.upright.mahjong.utils.MapBuild;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 毛家兴
 * @since 2022/2/21 10:17
 */
@Service
public class GameCalculationService {

  private static final Map<Integer, Integer> RATE_BASIC =
      MapBuild.<Integer, Integer>createKv().put(1, 30).put(2, 10).put(3, -10).put(4, -30).build();
  private static final Map<Integer, Integer> SCORE_BASIC =
      MapBuild.<Integer, Integer>createKv().put(1, 10).put(2, -20).put(3, -40).put(4, -50).build();

  private static final Map<Integer, DanTitle> DAN_TITLE_MAP = new HashMap<>();
  private static final Map<Integer, DanInfo> DAN_VALUE_MAP = new HashMap<>();

  private final UserService userService;

  public GameCalculationService(
      UserService userService, DanInfoService danInfoService, DanTitleService danTitleService) {
    this.userService = userService;
    for (DanInfo danInfo : danInfoService.list()) {
      DAN_VALUE_MAP.put(danInfo.getDanValue(), danInfo);
    }
    for (DanTitle danTitle : danTitleService.list()) {
      DAN_TITLE_MAP.put(danTitle.getTitleValue(), danTitle);
    }
  }

  @Transactional
  public synchronized void calculation(GameDataParam gameDataParam) {
    List<UserRes> users =
        userService.getUserResList(
            gameDataParam.getScores().stream()
                .map(ScoreParam::getUserId)
                .collect(Collectors.toList()));
    if (users.size() != 4) {
      throw StateEnum.user_number_error.create();
    }
    double countRate = 0;
    for (UserRes user : users) {
      countRate = NumberUtil.add(countRate, user.getRate().doubleValue());
    }
    double averageRate = NumberUtil.div(countRate, 4);
    Map<Long, UserRes> userMap = getUserMap(users);
    // 对局
    GameData gameData = new GameData();
    gameData.setId(IdUtils.next());
    gameData.setHowMany(gameDataParam.getHowMany());
    gameData.setGameDate(gameDataParam.getDate());
    // 处理分数相关
    List<ScoreParam> scores = gameDataParam.getScores();
    scores.sort(Comparator.comparing(ScoreParam::getOrder));
    List<GamePoint> gamePoints = new ArrayList<>();
    for (int i = 0; i < scores.size(); i++) {
      ScoreParam score = scores.get(i);
      GamePoint gamePoint = new GamePoint();
      gamePoint.setGameId(gameData.getId());
      gamePoint.setUserId(score.getUserId());
      gamePoint.setPoints(score.getScore());
      gamePoint.setSeqOrder(i + 1);
      calculateChangeScore(gamePoint); // 填充分数变化
      calculateChangeRate(gamePoint, userMap, averageRate); // 填充R值变化
      afterGame(gamePoint, userMap);
      gamePoints.add(gamePoint);
    }
    // 计算分数
  }
  // 四段以上r达到1550的，对战般场时是按照上级桌结算的
  // 计算对战后的pt
  private List<Integer> calculatePt(List<GamePoint> gamePoints, Map<Long, UserRes> userMap) {
    if (userMap.values().stream().map(UserRes::getDanValue).collect(Collectors.toSet()).size()
        == 1) { // 同一个桌 直接结算pt不用任何操作
      for (GamePoint gamePoint : gamePoints) {
        UserRes userRes = userMap.get(gamePoint.getUserId());
        Integer danValue = userRes.getDanValue();
        Integer pt =
            getSeqPt(
                gamePoint.getSeqOrder(),
                DAN_VALUE_MAP.get(danValue),
                DAN_TITLE_MAP.get(userRes.getTitleValue()));
        userRes.setUpgradePt(pt);
      }
    } else {
      if (specialCase()) { // 混桌 除最低级别正常加分 其他按照低一级别加分 如果
      }
    }
    return null;
  }
  // 三段特例（常驻）：3名三段教练与般桌教练对战按照上级桌结算pt
  // 3名特上桌教练与1名三段教练对战，特上桌教练可以按照特上桌结算pt
  // 3名凤凰桌教练与1名六段教练对战，凤凰桌教练可以按照凤凰桌结算pt
  private boolean specialCase() {
    return false;
  }

  private Integer getSeqPt(Integer seq, DanInfo danInfo, DanTitle danTitle) {
    switch (seq) {
      case 1:
        return danTitle.getTitleFirst();
      case 2:
        return danTitle.getTitleSecond();
      case 3:
        return danTitle.getTitleThird();
      case 4:
        return danInfo.getFourth();
    }
    throw StateEnum.ranking_not_correct.create().append("[").append(seq + "").append("]");
  }

  // 战后数据
  private void afterGame(GamePoint gamePoint, Map<Long, UserRes> userMap) {
    UserRes user = userMap.get(gamePoint.getUserId());
    gamePoint.setAfterRate(NumberUtil.add(user.getRate(), gamePoint.getChangeRate()));
    gamePoint.setAfterScore(NumberUtil.add(user.getTotalScore(), gamePoint.getChangeScore()));
  }

  // 计算R值
  private void calculateChangeRate(
      GamePoint gamePoint, Map<Long, UserRes> userMap, double averageRate) {
    UserRes user = userMap.get(gamePoint.getUserId());
    int gameNumber = user.getGameNumber() + 1;
    Integer integer = RATE_BASIC.get(gamePoint.getSeqOrder());
    double auxiliary = 0.2D; // 对局数辅正
    if (gameNumber < 200) {
      auxiliary = NumberUtil.sub(1D, NumberUtil.mul((double) gameNumber, 0.004D));
    }
    // 辅正值
    double div = NumberUtil.div(NumberUtil.sub(averageRate, user.getRate().doubleValue()), 40D);
    // 对局结果+辅正值
    double auxiliaryValue = NumberUtil.add(integer.doubleValue(), div);
    gamePoint.setChangeRate(NumberUtil.mul(auxiliary, auxiliaryValue));
  }

  // 计算分数变化
  private void calculateChangeScore(GamePoint gamePoint) {
    gamePoint.setChangeScore(
        NumberUtil.add(
            NumberUtil.div(gamePoint.getPoints().doubleValue(), 1000),
            SCORE_BASIC.get(gamePoint.getSeqOrder()).doubleValue()));
  }

  private Map<Long, UserRes> getUserMap(List<UserRes> list) {
    Map<Long, UserRes> map = new HashMap<>();
    for (UserRes user : list) {
      map.put(user.getId(), user);
    }
    return map;
  }
}
