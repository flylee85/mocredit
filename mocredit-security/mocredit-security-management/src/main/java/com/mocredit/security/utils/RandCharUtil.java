package com.mocredit.security.utils;

/**
 * Created by IntelliJ IDEA.
 * User: wangweiguo
 * Date: 11-6-18
 * Time: 上午12:03
 * To change this template use File | Settings | File Templates.
 */
public class RandCharUtil {
    /**
     * 生成随机字符
     */
    public static String getRandChar() {
        int rand = (int) Math.round(Math.random() * 2);
//        int rand = 0;
        long itmp = 0;
        char ctmp = '\u0000';
        // 根据rand的值来决定来生成一个大写字母、小写字母和数字
        switch (rand) {
            // 生成大写字母
            case 1:
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            // 生成小写字母
            case 2:
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            // 生成数字
            default:
                itmp = Math.round(Math.random() * 7) + 2;
                return String.valueOf(itmp);
        }
    }

    public static String getRandStr(int size) {
        if (size <= 0) {
            size = 6;
        }
        String randStr = "";
        for (int i = 0; i < size; i++) {
            // 获得一个随机字符
            String tmp = RandCharUtil.getRandChar();
            randStr += tmp;
        }
        return randStr;
    }
}
