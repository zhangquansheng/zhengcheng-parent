package com.yhq.sensitive.util;

import com.yhq.sensitive.enums.SensitiveDefaultLengthEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 数据脱敏的工具类
 * @author yhq
 * @date 2021年9月6日 14点02分
 **/
public class SensitiveInfoUtils {

    /**
     * [中文姓名] 只显示第一(showLength)个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName, int showLength) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        if(StringUtils.length(fullName) <= showLength){
            return fullName;
        }
        final String name = StringUtils.left(fullName, showLength);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * 密码脱敏，只显示 6 个  *
     * @param password 密码
     * @return 脱敏后的数据
     */
    public static String password(final String password, int length) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        return getSensitiveInfo(length);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String idCardNum(final String idCard, int begin, int end) {
        if (StringUtils.isBlank(idCard)) {
            return "";
        }
        return StringUtils.left(idCard, begin).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(idCard, end), StringUtils.length(idCard), "*"),
                "******"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     */
    public static String fixedPhone(final String num, int end) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, end), StringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138****1234>
     */
    public static String mobilePhone(final String mobile, int begin, int end) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        /*return StringUtils.left(mobile, begin).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(mobile, end), StringUtils.length(mobile), "*"),
                        "***"));*/
        return StringUtils.left(mobile, begin).concat(
                StringUtils.leftPad(StringUtils.right(mobile, end), StringUtils.length(mobile) - begin, "*"));

    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param sensitiveBeginSize 敏感信息的长度
     */
    public static String address(final String address, final int sensitiveBeginSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        final int length = StringUtils.length(address);

        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveBeginSize), length, "*");

    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public static String email(final String email, int begin) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        final int index = StringUtils.indexOf(email, "@");
        if (index <= begin) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, begin), index, "*")
                    .concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     */
    public static String bankCard(final String cardNum, int begin , int end) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        if(getSensitive(cardNum,begin,end)){
            begin = SensitiveDefaultLengthEnum.BANKCARD.getBegin();
            end = SensitiveDefaultLengthEnum.BANKCARD.getEnd();
        }
        return StringUtils.left(cardNum, begin).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(cardNum, end), StringUtils.length(cardNum), "*"),
                getSensitiveInfo(begin)));
    }

    /**
     * 根据正则脱敏
     * @param context 内容
     * @param pattern 正则
     * @param replaceChar 替换后的字符
     * @return 脱敏后的数据
     */
    public static String patternReplace(final String context,String pattern, String replaceChar) {
        return context.replaceAll(pattern, replaceChar);
    }

    /**
     * 获取总的长度
     * @param begin 开始显示的长度
     * @param end 结尾显示的长度
     * @return 总要显示的长度
     */
    private static int getAllLength(int begin ,int end){
        return begin + end;
    }

    /**
     * 判断前端传来的长度，是否比 当前字符的长度大
     * @param address 地址
     * @param sensitiveBeginSize 开始长度
     * @param sensitiveEndSize  结尾长度
     * @return
     */
    private static boolean getSensitive(final String address, final int sensitiveBeginSize, final int sensitiveEndSize){
        int showLength = getAllLength(sensitiveBeginSize ,sensitiveEndSize);
        int length = StringUtils.length(address);
        if(showLength> length){
            return false;
        }
        return true;
    }

    /**
     * 显示多少个 *
     * @param length 显示的长度
     * @return 返回组装后的长度
     */
    private static String getSensitiveInfo(int length){
        return StringUtils.repeat("*", length);
    }

}
