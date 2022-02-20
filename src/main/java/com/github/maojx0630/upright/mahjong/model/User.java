package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("simple_name")
    private String simpleName;

    @TableField("create_time")
    private Date createTime;
}