package com.yimeihuijin.commonlibrary.web;

import com.google.gson.Gson;

/**
 * Created by Chanson on 2015/12/18.
 */
public class GsonUtil {
    private static Gson gson;

    public static <T>T get(String res,Class<T> cls){
        if(res == null){
            return null;
        }
        if(gson == null){
            synchronized (Gson.class){
                gson = new Gson();
            }
        }
        try{
            return gson.fromJson(res,cls);
        }catch (Exception e){
            return null;
        }
    }
}
