package com.github.maojx0630.upright.mahjong;

import cn.hutool.core.date.DateUtil;
import com.github.maojx0630.upright.mahjong.calculate.GameCalculationService;
import com.github.maojx0630.upright.mahjong.param.GameDataParam;
import com.github.maojx0630.upright.mahjong.param.ScoreParam;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(annotationClass = Repository.class)
public class DemoApplication {

  public static void main(String[] args) {
    GameDataParam gameDataParam = new GameDataParam();
    gameDataParam.setDate(DateUtil.parseDate("2022/02/11"));
    gameDataParam.setHowMany(0);
    List<ScoreParam> list = new ArrayList<>();
    ScoreParam p1 = new ScoreParam();
    p1.setOrder(0);
    p1.setScore(50300);
    p1.setUserId(1495602348301524993L);
    ScoreParam p2 = new ScoreParam();
    p2.setOrder(1);
    p2.setScore(34100);
    p2.setUserId(1495600845725749250L);
    ScoreParam p3 = new ScoreParam();
    p3.setOrder(2);
    p3.setScore(16200);
    p3.setUserId(1495600845121769473L);
    ScoreParam p4 = new ScoreParam();
    p4.setOrder(3);
    p4.setScore(-600);
    p4.setUserId(1495600845088215041L);
    list.add(p1);
    list.add(p2);
    list.add(p3);
    list.add(p4);

    gameDataParam.setScores(list);

    ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
    run.getBean(GameCalculationService.class).calculation(gameDataParam);
    run.close();
  }
}
