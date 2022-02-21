package com.github.maojx0630.upright.mahjong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.maojx0630.upright.mahjong.model.GamePoint;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePointMapper extends BaseMapper<GamePoint> {

  @Select("select count(distinct game_id) from game_point where user_id=#{id}")
  int getGameNum(Long id);
}
