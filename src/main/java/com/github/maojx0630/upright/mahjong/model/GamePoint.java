package com.github.maojx0630.upright.mahjong.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("game_point")
public class GamePoint {

    @TableField("game_id")
    private Long gameId;

    @TableField("user_id")
    private Long userId;

    @TableField("points")
    private Integer points;

    @TableField("seq_order")
    private Integer seqOrder;
}