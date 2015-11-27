package com.mocredit.integral.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ytq on 2015/11/19.
 */
public class Bank {
    final static Map<String, String> BANK_MAP = new HashMap<>();

    static {
        BANK_MAP.put("1", "中信银行");
        BANK_MAP.put("2", "民生银行");
        BANK_MAP.put("3", "交通银行");
        BANK_MAP.put("4", "招商银行");
        BANK_MAP.put("5", "天津银行北京分行");
        BANK_MAP.put("6", "中国银行");
        BANK_MAP.put("7", "天津银行总行");
        BANK_MAP.put("8", "北京银行");
        BANK_MAP.put("9", "农行蛋糕券兑换");
        BANK_MAP.put("10", "光大银行");

    }

    public static String getBankNameByBankId(String bankId) {
        return BANK_MAP.get(bankId);
    }


}
