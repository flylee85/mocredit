package com.mocredit.base.util;

import java.util.UUID;

/**
 *
 * UUID 工具
 * Created by YHL on 15/7/14 00:58.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class UUIDUtils {

    /**
     * 获取32位UUID
     * @return
     */
    public static String UUID32(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    public  static String UUID36(){
        return  UUID.randomUUID().toString();
    }

}
