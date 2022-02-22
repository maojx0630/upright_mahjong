package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 选手信息 */
@Data
@TableName("user")
public class User {

  /** 选手id */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /** 选手名称 */
  @TableField("name")
  private String name;

  /** 选手简称 */
  @TableField("simple_name")
  private String simpleName;

  /** 选手创建时间 */
  @TableField("create_time")
  private Date createTime;

  /** 段位 */
  @TableField("dan")
  private Integer dan;

  /** r值 */
  @TableField("rate")
  private String rate;

  /** 对局次数 */
  @TableField("game_number")
  private Integer gameNumber;

  /** 平均顺位 */
  @TableField("average_sequence")
  private Double averageSequence;

  /** 击飞率 */
  @TableField("diaup_chance")
  private String diaupChance;

  /** 连对率 */
  @TableField("pairing_rate")
  private String pairingRate;

  /** 通算得分 */
  @TableField("total_score")
  private Double totalScore;

  /** 通算顺位分 */
  @TableField("total_sequence_score")
  private Double totalSequenceScore;
}
