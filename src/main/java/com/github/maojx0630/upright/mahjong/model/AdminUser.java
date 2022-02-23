package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 管理员 */
@Data
@TableName("admin_user")
public class AdminUser {

  /** id */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /** 名称 */
  @TableField("name")
  private String name;

  /** 创建时间 */
  @TableField("create_date")
  private Date createDate;
}
