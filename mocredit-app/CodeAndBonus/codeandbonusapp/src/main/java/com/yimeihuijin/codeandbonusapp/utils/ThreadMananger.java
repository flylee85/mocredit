package com.yimeihuijin.codeandbonusapp.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池，主要管理数据库操作的线程
 * Created by Chanson on 2015/12/17.
 */
public class ThreadMananger {
    private static final int MAX_THREAD_NUM = 3;

    private ExecutorService executorService;

    private static ThreadMananger instance;

    private ThreadMananger(){
        executorService = Executors.newFixedThreadPool(MAX_THREAD_NUM);
    }

    public static ThreadMananger get(){
        if(instance == null){
            synchronized (ThreadMananger.class){
                instance = new ThreadMananger();
            }
        }
        return instance;
    }

    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }

    public void stop(){
        executorService.shutdown();
    }

    public void stopNow(){
        executorService.shutdownNow();
    }
}
