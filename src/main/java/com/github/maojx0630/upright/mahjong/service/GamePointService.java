package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.mapper.GamePointMapper;
import com.github.maojx0630.upright.mahjong.model.GamePoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GamePointService extends ServiceImpl<GamePointMapper, GamePoint> {}
