package com.github.maojx0630.upright.mahjong.common.advice;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * get请求下 将时间戳转为date类型
 *
 * @author 毛家兴
 * @since 2021-03-16 16:49
 */
public class DateConverter implements Converter<String, Date> {
  @Override
  public Date convert(@Nullable String value) {
    if (StrUtil.isNotBlank(value)) {
      try {
        return new Date(Long.parseLong(value));
      } catch (Exception ignored) {
      }
    }
    return null;
  }
}
