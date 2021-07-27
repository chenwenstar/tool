package com.chenwen.excel;

import lombok.Data;

import java.util.Map;

/**
 * @author chen.jw
 * @date 2021/7/26 10:06
 */
@Data
public class ChoiceQuestion {
    private String title;
    private String answer;
    private Map<String,String> options;


}
