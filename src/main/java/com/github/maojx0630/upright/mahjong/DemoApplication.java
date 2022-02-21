package com.github.maojx0630.upright.mahjong;

import lombok.SneakyThrows;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(annotationClass = Repository.class)
public class DemoApplication {

  @SneakyThrows
  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
//    UserService userService = run.getBean(UserService.class);
//    List<User> users = userService.list();
//    GamePointService pointService = run.getBean(GamePointService.class);
//    for (User user : users) {
//      List<GamePoint> list =
//          pointService.list(
//              Wrappers.<GamePoint>lambdaQuery().eq(GamePoint::getUserId, user.getId()));
//      Integer diaup = 0;
//      for (GamePoint point : list) {
//        if (point.getSeqOrder() <= 2) {
//          diaup++;
//        }
//      }
//      //      String s1 = NumberUtil.decimalFormat("#.000", NumberUtil.div(sequence + "",
//      // list.size() + ""));
//      String s2 = NumberUtil.decimalFormat("#.##%", NumberUtil.div(diaup + "", list.size() + ""));
//      userService.update(
//          Wrappers.<User>lambdaUpdate()
//              .set(User::getPairingRate, s2)
//              .eq(User::getId, user.getId()));
//    }
    run.close();
  }


}
/**
 * 分数计算方法：（当前点数－返点）÷1000+顺位马+头名赏返点也叫做基准点。
 * 顺位马也叫做顺位调整分，通常以X－Y代表。例如30－10，即第一名+30，第二名+10，第三名-10，第四名-30。
 * 头名赏计算方法：（返点－原点）÷1000×人数。只有第一名可以获得头名赏。
 * 算出分数后，小数部分的处理方式因规则而异，方式有：保留，四舍五入，五舍六入，进位，去尾。
 * 计算完成后，如果各家分数相加之和不为0，则调整第一名的分数，使各家分数相加之和等于0。
 * 注：牌局全部结束后，如果场上还有剩余的立直棒，由第一名收取。
 * 点数相同时，排名从前到后编排为：东一局的东家>南家>西家>北家。
 * 举例：四人麻将，原点25000，返点30000，顺位马20－10，小数部分保留。
 */
