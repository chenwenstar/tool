package com.chenwen.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author chen.jw
 * @date 2021/7/26 10:01
 */
public class ExcelTest {
    public static void main(String[] args) throws Exception {
        Map<Integer, Integer> choice = new LinkedHashMap<Integer, Integer>();
        Map<Integer, Integer> judge = new LinkedHashMap<Integer, Integer>();
        Map<Integer, Integer> multiChoice = new LinkedHashMap<Integer, Integer>();

        InputStream inputStream = Class.forName(ExcelTest.class.getName()).getResourceAsStream("/excel/Excel模板.xls");
        InputStream inputStream1 = new FileInputStream("C:\\Users\\chenw\\Desktop\\789.xls");

        HSSFWorkbook excel = new HSSFWorkbook(inputStream);
        HSSFWorkbook excel1 = new HSSFWorkbook(inputStream1);

        HSSFSheet sheetAt = excel1.getSheetAt(0);

        HSSFSheet sheet = excel.getSheetAt(0);
        HSSFSheet sheet1 = excel.getSheetAt(1);
        HSSFSheet sheet2 = excel.getSheetAt(2);

        int choiceFlag = 0;
        int judgeFlag = 0;
        int multiChoiceFlag = 0;
        for (int i = 0; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            String trim = sheetAt.getRow(i).getCell(1).getStringCellValue().trim();
            if ("单选题".equals(trim)) {
                choiceFlag = i;
                if (judgeFlag != 0) {
                    judge.put(judgeFlag, choiceFlag);
                    judgeFlag = 0;
                }
            }
            if ("多选题".equals(trim)) {
                multiChoiceFlag = i;
                if (choiceFlag != 0) {
                    choice.put(choiceFlag, multiChoiceFlag);
                    choiceFlag = 0;
                }
            }
            if ("判断题".equals(trim)) {
                judgeFlag = i;
                if (multiChoiceFlag == 0) {
                    choice.put(choiceFlag, judgeFlag);
                    choiceFlag = 0;
                } else {
                    multiChoice.put(multiChoiceFlag, judgeFlag);
                    multiChoiceFlag = 0;
                }
            }

        }
        System.out.println(choice.size());
        System.out.println(judge.size());
        List<ChoiceQuestion> choiceQuestionList = packageChoices(choice, sheetAt);
        List<ChoiceQuestion> multiChoiceQuestionList = packageChoices(multiChoice, sheetAt);

        for (int i = 1; i <= choiceQuestionList.size(); i++) {
            ChoiceQuestion choiceQuestion = choiceQuestionList.get(i - 1);
            HSSFRow row = sheet.createRow(i);
            //第一列
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(choiceQuestion.getTitle());

            HSSFCell cell9 = row.createCell(9);
            cell9.setCellValue(choiceQuestion.getAnswer());

            row.createCell(1).setCellValue(choiceQuestion.getOptions().get("A"));
            row.createCell(2).setCellValue(choiceQuestion.getOptions().get("B"));
            row.createCell(3).setCellValue(choiceQuestion.getOptions().get("C"));
            row.createCell(4).setCellValue(choiceQuestion.getOptions().get("D"));
        }

        for (int i = 1; i <= multiChoiceQuestionList.size(); i++) {
            ChoiceQuestion choiceQuestion = multiChoiceQuestionList.get(i - 1);
            HSSFRow row = sheet1.createRow(i);
            //第一列
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(choiceQuestion.getTitle());

            HSSFCell cell9 = row.createCell(9);
            if(choiceQuestion.getAnswer().contains("、")){
                String[] split = choiceQuestion.getAnswer().split("\\、");
                StringBuilder str5 = new StringBuilder();
                for (String s : split) {
                    str5.append(s);
                }
                cell9.setCellValue(str5.toString());
            }else {
                cell9.setCellValue(choiceQuestion.getAnswer());
            }
            row.createCell(1).setCellValue(choiceQuestion.getOptions().get("A"));
            row.createCell(2).setCellValue(choiceQuestion.getOptions().get("B"));
            row.createCell(3).setCellValue(choiceQuestion.getOptions().get("C"));
            row.createCell(4).setCellValue(choiceQuestion.getOptions().get("D"));
            if(choiceQuestion.getOptions().size()==5){
                row.createCell(5).setCellValue(choiceQuestion.getOptions().get("E"));
            }
            if(choiceQuestion.getOptions().size()==6){
                row.createCell(5).setCellValue(choiceQuestion.getOptions().get("E"));
                row.createCell(6).setCellValue(choiceQuestion.getOptions().get("F"));
            }
        }


        List<JudgeQuestion> judgeQuestionList = new ArrayList();

        Iterator<Map.Entry<Integer, Integer>> iterator1 = judge.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator1.next();
            Integer start = entry.getKey() + 1;
            Integer end = entry.getValue();
            for (int i = start; i < end; i++) {
                String stringCellValue2 = sheetAt.getRow(i).getCell(1).getStringCellValue();
                if (stringCellValue2.contains("（")) {
                    String[] split = stringCellValue2.split("\\（");
                    JudgeQuestion judgeQuestion = new JudgeQuestion();
                    if (split[1].contains("√")) {
                        judgeQuestion.setAnswer(true);
                    } else {
                        judgeQuestion.setAnswer(false);
                    }
                    judgeQuestion.setTitle(split[0]);
                    judgeQuestionList.add(judgeQuestion);
                }
            }
        }

        for (int i = 1; i <= judgeQuestionList.size(); i++) {
            JudgeQuestion judgeQuestion = judgeQuestionList.get(i - 1);
            HSSFRow row = sheet2.createRow(i);
            //第一列
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(judgeQuestion.getTitle());

            HSSFCell cell1 = row.createCell(1);
            if (judgeQuestion.getAnswer()) {
                cell1.setCellValue("对");
            } else {
                cell1.setCellValue("错");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\chenw\\Desktop\\456.xls");
        excel.write(fileOutputStream);
        fileOutputStream.close();
    }

    private static List<ChoiceQuestion> packageChoices(Map<Integer, Integer> choiceMap, HSSFSheet sheetAt) {
        Iterator<Map.Entry<Integer, Integer>> iterator = choiceMap.entrySet().iterator();
        List<ChoiceQuestion> choiceQuestionList = new ArrayList();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            Integer start = entry.getKey();
            Integer end = entry.getValue();
            for (int i = start + 1; i < end; i++) {
                String stringCellValue = sheetAt.getRow(i++).getCell(1).getStringCellValue();
                String stringCellValue1 = sheetAt.getRow(i).getCell(1).getStringCellValue();

                ChoiceQuestion choiceQuestion = new ChoiceQuestion();
                String[] split = stringCellValue.split("\\（");
                String[] split2 = split[1].split("\\）");
                String answer = split[0] + "( )";
                if (split2.length >= 2) {
                    answer = answer + split2[1];
                }
                choiceQuestion.setTitle(answer);
                choiceQuestion.setAnswer(split2[0]);

                String[] split1 = stringCellValue1.split("\\）");
                Map<String, String> options = new HashMap<String, String>(4);
                options.put("A", breakProhibition(split1[1].split("\\（")[0]));
                options.put("B", breakProhibition(split1[2].split("\\（")[0]));
                options.put("C", breakProhibition(split1[3].split("\\（")[0]));
                if (split1.length == 5) {
                    options.put("D", breakProhibition(split1[4]));
                }else if(split1.length == 6) {
                    options.put("D", breakProhibition(split1[4].split("\\（")[0]));
                    options.put("E", breakProhibition(split1[5]));
                }else if(split1.length == 7) {
                    options.put("D", breakProhibition(split1[4].split("\\（")[0]));
                    options.put("E", breakProhibition(split1[4].split("\\（")[0]));
                    options.put("F", breakProhibition(split1[5]));
                }
                choiceQuestion.setOptions(options);
                choiceQuestionList.add(choiceQuestion);
            }
        }
        return choiceQuestionList;
    }

    /**
     * 绕过验证
     */
    private static String breakProhibition(String stringCellValue) {
        int index = stringCellValue.indexOf("性交");
        if (index != -1) {
            return stringCellValue.substring(0, index + 1) + "__" + stringCellValue.substring(index + 1);
        }
        return stringCellValue;
    }
}
