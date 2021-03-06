package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.mapper.GameDataMapper;
import com.github.maojx0630.upright.mahjong.model.GameData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GameDataService extends ServiceImpl<GameDataMapper, GameData> {}
