package com.yimiehuijin.codeandbonuslibrary.web;

/**
 * Created by Chanson on 2015/12/9.
 */
public abstract class PosAction {

    protected PosActionListener listener;

    public PosAction(PosActionListener listener){
        this.listener = listener;
    }

    public abstract  void start();

    public void end(String msg){

    }

    public interface PosActionListener{
        public void succees();
        public void fail();
    }
}
