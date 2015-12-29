package com.yimeihuijin.codeandbonusapp.utils;

import de.greenrobot.event.EventBus;

/**
 * 使用EventBus的进行通信
 * Created by Chanson on 2015/12/17.
 */
public class BusProvider {
    private static EventBus bus;

    public static final EventBus get(){
        if(bus == null){
            synchronized (EventBus.class){
                bus = EventBus.getDefault();
            }
        }
        return bus;
    }
}
