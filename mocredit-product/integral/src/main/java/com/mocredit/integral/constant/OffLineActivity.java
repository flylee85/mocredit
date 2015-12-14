package com.mocredit.integral.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytq on 2015/11/24.
 */
public final class OffLineActivity {
    final static Map<String, List<String>> SHOP_BANK_MAP = new HashMap<>();
    final static Map<String, String> OFFLINE_ACTIVITY_MAP = new HashMap<>();

    static {
        OFFLINE_ACTIVITY_MAP.put("ZSYH11", "招商银行");
        OFFLINE_ACTIVITY_MAP.put("ZSYH12", "招商银行");
        OFFLINE_ACTIVITY_MAP.put("TJYH11", "天津银行");
        OFFLINE_ACTIVITY_MAP.put("TJYH22", "天津银行");
        OFFLINE_ACTIVITY_MAP.put("ZGYH01", "中国银行");
        OFFLINE_ACTIVITY_MAP.put("BOB001", "北京银行");
        OFFLINE_ACTIVITY_MAP.put("BOB002", "北京银行");
        OFFLINE_ACTIVITY_MAP.put("BOB003", "北京银行");
        OFFLINE_ACTIVITY_MAP.put("BOB004", "北京银行");
        OFFLINE_ACTIVITY_MAP.put("CEB001", "光大银行");
        OFFLINE_ACTIVITY_MAP.put("CEB002", "光大银行");
    }

    public static List<String> getBankIdsByBankShopId(String shopId) {
        return SHOP_BANK_MAP.get(shopId);
    }

    public static boolean isOffLineActivity(String activityId) {
        return OFFLINE_ACTIVITY_MAP.containsKey(activityId);
    }
}
