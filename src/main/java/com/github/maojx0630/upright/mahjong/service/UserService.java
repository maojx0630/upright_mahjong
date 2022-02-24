package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.dto.UserRes;
import com.github.maojx0630.upright.mahjong.mapper.UserMapper;
import com.github.maojx0630.upright.mahjong.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService extends ServiceImpl<UserMapper, User> {

  public List<UserRes> getUserResList(List<Long> collect) {
    return baseMapper.getUserResList(collect);
  }
}
