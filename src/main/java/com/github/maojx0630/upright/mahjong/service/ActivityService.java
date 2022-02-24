package com.github.maojx0630.upright.mahjong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.upright.mahjong.mapper.ActivityMapper;
import com.github.maojx0630.upright.mahjong.model.Activity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (Activity)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 13:11:15
 */
@Service
@Transactional(readOnly = true)
public class ActivityService extends ServiceImpl<ActivityMapper, Activity> {

}
