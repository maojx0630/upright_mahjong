package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 对局点数 */
@Data
@TableName("game_point")
public class GamePoint {

  /** 对局id */
  @TableField("game_id")
  private Long gameId;

  /** 选手id */
  @TableField("user_id")
  private Long userId;

  /** 选手点数 */
  @TableField("points")
  private Integer points;

  /** 选手顺位 */
  @TableField("seq_order")
  private Integer seqOrder;

  /** 战后R值 */
  @TableField("after_rate")
  private Double afterRate;

  /** 战后分数 */
  @TableField("after_score")
  private Double afterScore;

  /** R值变化 */
  @TableField("change_rate")
  private Double changeRate;

  /** 分数变化 */
  @TableField("change_score")
  private Double changeScore;
}
