package com.yimiehuijin.tempbonusconsume.data;

import java.io.Serializable;

/**
 * Created by Chanson on 2015/11/26.
 */
public class ConsumeResponse implements Serializable{
    public String state; //ok/error
    public String errorCode;
    public String errorMes;
    public String printInfo;
    public String qr;
}
