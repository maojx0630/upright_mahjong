package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.mapper.UserMapper;
import com.github.maojx0630.upright.mahjong.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {}
