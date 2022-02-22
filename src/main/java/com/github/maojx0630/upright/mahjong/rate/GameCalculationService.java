package com.github.maojx0630.upright.mahjong.rate;

import com.github.maojx0630.upright.mahjong.model.GameData;
import com.github.maojx0630.upright.mahjong.param.GameDataParam;
import com.github.maojx0630.upright.mahjong.param.ScoreParam;
import com.github.maojx0630.upright.mahjong.utils.IdUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author 毛家兴
 * @since 2022/2/21 10:17
 */
@Service
public class GameCalculationService {

  public void calculation(GameDataParam gameDataParam) {
    // 对局
    GameData gameData = new GameData();
    gameData.setId(IdUtils.next());
    gameData.setHowMany(gameDataParam.getHowMany());
    gameData.setGameDate(gameDataParam.getDate());
    // 处理分数相关
    List<ScoreParam> scores = gameDataParam.getScores();
    scores.sort(Comparator.comparing(ScoreParam::getScore));
  }
}
