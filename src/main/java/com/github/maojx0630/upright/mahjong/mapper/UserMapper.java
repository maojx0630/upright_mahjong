package com.github.maojx0630.upright.mahjong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.maojx0630.upright.mahjong.dto.UserRes;
import com.github.maojx0630.upright.mahjong.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

	List<UserRes> getUserResList(List<Long> collect);
}
