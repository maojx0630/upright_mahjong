package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.mapper.DanTitleMapper;
import com.github.maojx0630.upright.mahjong.model.DanTitle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 毛家兴
 * @since 2022/2/24 15:57
 */
@Service
@Transactional(readOnly = true)
public class DanTitleService extends ServiceImpl<DanTitleMapper, DanTitle> {}

