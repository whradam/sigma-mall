package com.whradam.sigmamall.common.utils;

/**
 * 通用参数验证工具
 * 用于controller参数验证
 */

public class UtilsParamsValidator {
    /**
     * 判断是否是空String
     * @param str 需要判空的字符串
     * @return true 表示是空
     */
    public static boolean isEmptyString(String str){return str==null||str.trim().equals("");}





}
