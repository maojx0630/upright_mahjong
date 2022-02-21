package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 对局点数
 **/
@Data
@TableName("game_point")
public class GamePoint {

 /**
  * 对局id
  **/
  @TableField("game_id")
  private Long gameId;

  /**
   * 选手id
   **/
  @TableField("user_id")
  private Long userId;

  /**
   * 选手点数
   **/
  @TableField("points")
  private Integer points;

  /**
   * 选手顺位
   **/
  @TableField("seq_order")
  private Integer seqOrder;
}
