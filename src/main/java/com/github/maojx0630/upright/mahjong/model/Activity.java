package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * (Activity)实体类
 *
 * @author makejava
 * @since 2022-02-24 13:11:15
 */
@Data
@TableName(value = "activity")
public class Activity {

  /** 主键 */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /** 名称 */
  @TableField("name")
  private String name;

  /** 开始时间 */
  @TableField("start_time")
  private Date startTime;

  /** 结束时间 */
  @TableField("end_time")
  private Integer endTime;

  /** 活动段位 */
  @TableField("lowest_table")
  private Integer lowestTable;

  /** 高段人数 */
  @TableField("people_number")
  private Integer peopleNumber;

  /** 高段段位 */
  @TableField("people_value")
  private Integer peopleValue;

  /** 高段 0称号 1段位 */
  @TableField("people_dan_title")
  private Integer peopleDanTitle;

  /** 参与人数 */
  @TableField("player_number")
  private Integer playerNumber;

  /** 参与段位 */
  @TableField("player_value")
  private Integer playerValue;

  /** 参与人 0称号 1段位 */
  @TableField("player_dan_title")
  private Integer playerDanTitle;

  /** 结算称号 */
  @TableField("settlement_title")
  private Integer settlementTitle;
}
