package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class GameData {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("create_date")
    private Date createDate;
}