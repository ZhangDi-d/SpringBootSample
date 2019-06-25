package com.ryze.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xueLai on 2019/6/25.
 */
public class RegexUtil {
    //密码必须是包含大写字母、小写字母、数字、特殊符号（不是字母，数字，下划线，汉字的字符）的8位以上组合
    public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,16}$";
    public static void main(String[] args){
        System.out.println(testPattern("TW!b&2*Z"));
    }

    public static boolean testPattern(String  password){
        return password.matches(PW_PATTERN);
    }
}
