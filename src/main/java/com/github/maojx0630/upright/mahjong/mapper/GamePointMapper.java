package com.github.maojx0630.upright.mahjong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.maojx0630.upright.mahjong.model.GamePoint;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamePointMapper extends BaseMapper<GamePoint> {

  @Select(
      "select a.* FROM game_point a LEFT JOIN game_data b on a.game_id=b.id where user_id=#{userId} ORDER BY b.game_date desc,b.how_many desc")
  List<GamePoint> getGamePoints(Long userId);
}
