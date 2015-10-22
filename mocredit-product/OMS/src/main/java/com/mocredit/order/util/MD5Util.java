package com.mocredit.order.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: cxw
 * Date: 11-3-17
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public class MD5Util {
    public static String Md5(String plain) {
        return DigestUtils.md5Hex(plain);
    }

    public static void main(String args[]) {
        System.out.println(MD5Util.Md5("123"));
    }

}
