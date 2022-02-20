package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class GamePoints {

    @TableField("create_date")
    private Long gameId;

    @TableField("user_id")
    private Long userId;

    @TableField("points")
    private Integer points;

    @TableField("create_date")
    private Integer order;
}