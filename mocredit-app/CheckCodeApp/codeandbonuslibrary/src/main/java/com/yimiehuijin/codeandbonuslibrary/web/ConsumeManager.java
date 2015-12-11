package com.yimiehuijin.codeandbonuslibrary.web;

import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;

import java.util.LinkedList;

/**
 * Created by Chanson on 2015/11/25.
 */
public class ConsumeManager{
    private LinkedList<BonusConsumePostData> consumeQueue;
    private static ConsumeManager instance;
    private ConsumeManager(){
        consumeQueue = new LinkedList<BonusConsumePostData>();
    }

    public static<T> ConsumeManager getInstance(){
        if(instance == null){
            synchronized (ConsumeManager.class){
                instance = new ConsumeManager();
            }
        }
        return instance;
    }

    public boolean in(BonusConsumePostData data){
        synchronized (consumeQueue) {
            return consumeQueue.offer(data);
        }
    }

    public BonusConsumePostData out(){
        return consumeQueue.poll();
    }

    public int size(){
        return consumeQueue.size();
    }
}
