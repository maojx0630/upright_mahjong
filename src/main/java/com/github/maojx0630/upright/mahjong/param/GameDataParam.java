package com.github.maojx0630.upright.mahjong.param;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 毛家兴
 * @since 2022/2/22 16:43
 */
@Data
public class GameDataParam {

  private Date date;

  private Integer howMany;

  private List<ScoreParam> scores;
}
