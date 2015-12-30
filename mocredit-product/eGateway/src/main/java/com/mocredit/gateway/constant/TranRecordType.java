package com.mocredit.gateway.constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytq on 2015/12/23.
 */
public class TranRecordType {
    public static List<String> tranTypes = new ArrayList<>();

    static {
        Field fields[] = CardRule.class.getDeclaredFields();
        for (Field field : fields) {
            tranTypes.add(field.getName());
        }
    }
}
