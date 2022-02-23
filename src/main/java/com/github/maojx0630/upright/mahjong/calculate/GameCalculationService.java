package com.github.maojx0630.upright.mahjong.calculate;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.maojx0630.upright.mahjong.model.DanInfo;
import com.github.maojx0630.upright.mahjong.model.GameData;
import com.github.maojx0630.upright.mahjong.model.GamePoint;
import com.github.maojx0630.upright.mahjong.model.User;
import com.github.maojx0630.upright.mahjong.param.GameDataParam;
import com.github.maojx0630.upright.mahjong.param.ScoreParam;
import com.github.maojx0630.upright.mahjong.service.DanInfoService;
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

  private static final Map<Integer, DanInfo> DAN_MAP = new HashMap<>();

  private final UserService userService;

  public GameCalculationService(UserService userService, DanInfoService danInfoService) {
    this.userService = userService;
    for (DanInfo danInfo : danInfoService.list()) {
      DAN_MAP.put(danInfo.getDanValue(), danInfo);
    }
  }

  @Transactional
  public synchronized void calculation(GameDataParam gameDataParam) {
    List<User> users =
        userService.list(
            Wrappers.<User>lambdaQuery()
                .in(
                    User::getId,
                    gameDataParam.getScores().stream()
                        .map(ScoreParam::getUserId)
                        .collect(Collectors.toList())));
    if (users.size() != 4) {
      throw new RuntimeException("123");
    }
    double countRate = 0;
    for (User user : users) {
      countRate = NumberUtil.add(countRate, user.getRate().doubleValue());
    }
    double averageRate = NumberUtil.div(countRate, 4);
    Map<Long, User> userMap = getUserMap(users);
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

  // 计算对战后的pt
  private List<Integer> calculatePt(List<GamePoint> gamePoints, Map<Long, User> userMap) {

    return null;
  }

  // 战后数据
  private void afterGame(GamePoint gamePoint, Map<Long, User> userMap) {
    User user = userMap.get(gamePoint.getUserId());
    gamePoint.setAfterRate(NumberUtil.add(user.getRate(), gamePoint.getChangeRate()));
    gamePoint.setAfterScore(NumberUtil.add(user.getTotalScore(), gamePoint.getChangeScore()));
  }

  // 计算R值
  private void calculateChangeRate(
      GamePoint gamePoint, Map<Long, User> userMap, double averageRate) {
    User user = userMap.get(gamePoint.getUserId());
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

  private Map<Long, User> getUserMap(List<User> list) {
    Map<Long, User> map = new HashMap<>();
    for (User user : list) {
      map.put(user.getId(), user);
    }
    return map;
  }
}
