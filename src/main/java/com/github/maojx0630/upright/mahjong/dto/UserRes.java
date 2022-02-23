package com.github.maojx0630.upright.mahjong.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 毛家兴
 * @since 2022/2/23 16:46
 */
@Data
public class UserRes {

  /** 选手id */
  private Long id;

  /** 选手名称 */
  private String name;

  /** 选手简称 */
  private String simpleName;

  /** 选手创建时间 */
  private Date createTime;

  /** 段位 */
  private Integer dan;

  /** r值 */
  private Double rate;

  /** 对局次数 */
  private Integer gameNumber;

  /** 平均顺位 */
  private Double averageSequence;

  /** 击飞率 */
  private String diaupChance;

  /** 连对率 */
  private String pairingRate;

  /** 通算得分 */
  private Double totalScore;

  /** 通算顺位分 */
  private Double totalSequenceScore;

  /** 段位顺序 */
  private Integer danValue;

  /** 段位名称 */
  private String danName;

  /** 称号 */
  private String title;

  /** 称号ID */
  private Integer titleId;

  /** 升级后基础点数 */
  private Integer basisPt;

  /** 吃1点数 */
  private Integer first;

  /** 吃2点数 */
  private Integer second;

  /** 吃3点数 */
  private Integer third;

  /** 吃4点数 */
  private Integer fourth;

  /** 是否降级 0 降级 1 不降级 */
  private Integer demotion;

  /** 升级需要pt */
  private Integer upgradePt;
}
