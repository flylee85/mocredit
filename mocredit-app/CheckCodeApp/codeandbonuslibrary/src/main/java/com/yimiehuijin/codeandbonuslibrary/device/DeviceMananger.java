package com.yimiehuijin.codeandbonuslibrary.device;

/**
 * Created by Chanson on 2015/12/1.
 */
public class DeviceMananger {

    private static DeviceMananger instance;

    private DeviceMananger(){

    }

    public static DeviceMananger getInstance(){
        if(instance == null){
            synchronized (DeviceMananger.class){
                instance = new DeviceMananger();
            }
        }
        return instance;
    }
}
