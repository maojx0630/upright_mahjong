package com.github.maojx0630.upright.mahjong;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.upright.mahjong.model.GameData;
import com.github.maojx0630.upright.mahjong.model.GamePoint;
import com.github.maojx0630.upright.mahjong.utils.IdUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(annotationClass = Repository.class)
public class DemoApplication {

    public static void main(String[] args) throws Exception {
//        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        File[] ls = FileUtil.ls("C:\\Users\\Administrator\\Desktop\\xlsx");
        for (File file : ls) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if (StrUtil.startWithAny(sheetName, "1", "2")) {
                    if (sheetName.length() != 6) {
                        sheetName = StrUtil.sub(sheetName, 0, 6);
                    }
                    if (ReUtil.contains("^[1-9]\\d*$", sheetName)) {
                        System.out.println(sheetName);
                        mainFun(sheet);
                    }

                }
            }
        }
//        run.close();
    }

    public static void mainFun(XSSFSheet sheet) {
        Map<String, Map<Integer, Integer>> points = getPoints(sheet);
        Map<String, Map<Integer, Integer>> order = getOrder(sheet);
        List<TestUser> names = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, Integer>> entry : points.entrySet()) {
            TestUser testUser = conversionStr(entry.getKey());
            names.add(testUser);
        }
        int size = points.get(names.get(0).getAll()).size();//半庄数量
        //封装每个半庄id
        List<GameData> gameDataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            GameData gameData = new GameData();
            gameData.setId(IdUtils.next());
            gameData.setCreateDate(DateUtil.parse("20" + sheet.getSheetName(),"yyyyMMdd"));
            gameDataList.add(gameData);
        }
        //封装半庄的点数
        List<GamePoint> gamePoints = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            TestUser testUser = names.get(i);
            for (int j = 0; j < size; j++) {
                Map<Integer, Integer> orderMap = order.get(testUser.getAll());
                Map<Integer, Integer> pointMap = points.get(testUser.getAll());
                for (Map.Entry<Integer, Integer> orderEntry : orderMap.entrySet()) {
                    Integer key = orderEntry.getKey();//第几个半庄
                    Integer userOrder = orderEntry.getValue();//第几顺位
                    Integer userPoint = pointMap.get(key);
                    GamePoint gamePoint=new GamePoint();
                    gamePoint.setGameId(gameDataList.get(j).getId());
                    gamePoint.setUserId((long)j);
                    gamePoint.setPoints(userPoint);
                    gamePoint.setSeqOrder(userOrder);
                    gamePoints.add(gamePoint);
                }
            }
        }
        List<TestUser> newUser=new ArrayList<>();
        for (TestUser testUser : names) {
            newUser.add(conversionUser(testUser));
        }
        // TODO

    }

    private static TestUser conversionStr(String name) {
        TestUser testUser = new TestUser();
        String key = name.trim();
        testUser.setAll(key);
        key = StrUtil.replace(key, "（", "");
        List<String> split = StrUtil.split(key, "）");
        testUser.setSimple(split.get(0).trim());
        testUser.setName(split.get(1).trim());
        return testUser;
    }

    private static TestUser conversionUser(TestUser testUser) {
        if (testUser.getName().equals("浓稠果汁")) {
            return conversionStr("（⑨）病娇⑨");
        } else if (testUser.getName().equals("zhao ying")) {
            return conversionStr("（z）zhao");
        } else if (testUser.getName().equals("なのです")) {
            return conversionStr("（荒）荒");
        }
        return testUser;
    }


    private static Map<String, Map<Integer, Integer>> getOrder(XSSFSheet sheet) {
        int startIndex = getStartIndex(sheet, getStartIndex(sheet, 0));
        int numberColumns = getNumberColumns(sheet.getRow(startIndex - 1));//半庄数量
        return packageData(sheet, startIndex, numberColumns);
    }

    private static Map<String, Map<Integer, Integer>> getPoints(XSSFSheet sheet) {

        int startIndex = getStartIndex(sheet, 0);//第几行到第几行为选手名字
        int numberColumns = getNumberColumns(sheet.getRow(startIndex - 1));//半庄数量
        return packageData(sheet, startIndex, numberColumns);
    }

    private static Map<String, Map<Integer, Integer>> packageData(XSSFSheet sheet, int startIndex, int numberColumns) {
        Map<String, Map<Integer, Integer>> mapMap = new HashMap<>();
        for (int i = startIndex; i < getEndIndex(sheet, startIndex); i++) {
            XSSFRow row = sheet.getRow(i);
            String userName = row.getCell(0).getStringCellValue();
            Map<Integer, Integer> map = new HashMap<>();//记录半庄点数
            for (int j = 0; j < numberColumns; j++) {
                XSSFCell cell = row.getCell(j + 1);
                String stringCellValue = cell.getRawValue();
                if (StrUtil.isNotBlank(stringCellValue)) {
                    try {
                        Integer integer = Integer.valueOf(stringCellValue);
                        map.put(j, integer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mapMap.put(userName, map);
        }
        return mapMap;
    }

    private static int getNumberColumns(XSSFRow row) {
        int count = 0;
        int i = 0;
        while (true) {
            XSSFCell cell = row.getCell(i++);
            if (Objects.isNull(cell)) {
                return count;
            }

            String stringCellValue = cell.getStringCellValue();
            if (StrUtil.isBlank(stringCellValue) || StrUtil.startWith(stringCellValue, "平均")) {
                return count;
            }
            if (StrUtil.startWith(stringCellValue, "半庄")) {
                count++;
            }


        }
    }

    public static int getStartIndex(XSSFSheet sheet, int startIndex) {
        while (true) {
            XSSFRow row = sheet.getRow(startIndex++);
            String stringCellValue = row.getCell(0).getStringCellValue();
            if (StrUtil.isNotBlank(stringCellValue)) {
                if (StrUtil.startWith(stringCellValue, "选手")) {
                    return startIndex;
                }
            }
        }
    }

    public static int getEndIndex(XSSFSheet sheet, int startIndex) {
        int i = startIndex;
        while (true) {
            XSSFRow row = sheet.getRow(i++);
            String stringCellValue = row.getCell(0).getStringCellValue();
            if (StrUtil.isBlank(stringCellValue)) {
                return i - 1;
            }
        }
    }

}
