package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 对局信息 */
@Data
@TableName("game_data")
public class GameData {

  /** id */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /** 当天的第几局 */
  @TableField("how_many")
  private Integer howMany;

  /** 对局时间 */
  @TableField("game_date")
  private Date gameDate;
}
