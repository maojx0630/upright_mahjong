package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 段位基础信息
 * @author 毛家兴
 * @since 2022/2/21 16:32
 */
@Data
@TableName("dan_info")
public class DanInfo {

  /** 段位顺序 */
  @TableId(value = "dan_value", type = IdType.INPUT)
  private Integer danValue;

  /** 段位名称 */
  @TableField("dan_name")
  private String danName;

  /** 称号 */
  @TableField("title")
  private String title;

  /** 升级后基础点数 */
  @TableField("basis_pt")
  private Integer basisPt;

  /** 吃1点数 */
  @TableField("first")
  private Integer first;

  /** 吃2点数 */
  @TableField("second")
  private Integer second;

  /** 吃3点数 */
  @TableField("third")
  private Integer third;

  /** 吃4点数 */
  @TableField("fourth")
  private Integer fourth;

  /** 是否降级 0 降级 1 不降级 */
  @TableField("demotion")
  private Integer demotion;

  /** 升级需要pt */
  @TableField("upgrade_pt")
  private Integer upgradePt;

  /** 平均rate */
  @TableField("average_rate")
  private Integer averageRate;
}
