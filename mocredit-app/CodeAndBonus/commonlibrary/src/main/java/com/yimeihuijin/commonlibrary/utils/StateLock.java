package com.yimeihuijin.commonlibrary.utils;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by Chanson on 2016/1/27.
 */
public class StateLock {
    private static StateLock instance;
    public static final int LOCKED = 0x001;
    public static final int UNLOCKED = 0x002;

    private SparseArrayCompat<Integer> lock = new SparseArrayCompat<Integer>();
    private int[] lockIds;
    private static Boolean globalLock = false;

    public static StateLock get(){
        if(instance == null) {
            synchronized (StateLock.class) {
                instance = new StateLock();
            }
        }
        return instance;
    }

    public void setIds(int... ids){
        if(lockIds != null){
            throw new RuntimeException("the setIds(int... ids) only can be called once");
        }
        lockIds = ids;
    }

    public void lock(int id){
        synchronized (lock) {
            lock.put(id, LOCKED);
        }
    }

    public void unlock(int id){
        synchronized (lock) {
            lock.put(id, UNLOCKED);
        }
    }

    public boolean isLocked(int id){
        return lock.get(id) == LOCKED;
    }

    public static void lock(){
        synchronized (globalLock){
            globalLock = true;
            System.out.println("lqs lock");
        }
    }

    public static void unlock(){
        synchronized (globalLock){
            globalLock = false;
            System.out.println("lqs unlock");
        }
    }

    public static boolean isGlobalLocked(){
        return globalLock;
    }
}
