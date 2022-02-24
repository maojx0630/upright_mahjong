package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.mapper.DanInfoMapper;
import com.github.maojx0630.upright.mahjong.model.DanInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 毛家兴
 * @since 2022/2/21 16:41
 */
@Service
@Transactional(readOnly = true)
public class DanInfoService extends ServiceImpl<DanInfoMapper, DanInfo> {}
