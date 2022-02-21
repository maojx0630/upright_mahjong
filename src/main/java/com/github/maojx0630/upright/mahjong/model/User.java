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
}
