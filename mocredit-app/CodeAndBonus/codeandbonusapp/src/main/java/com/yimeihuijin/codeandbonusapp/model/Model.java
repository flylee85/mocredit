package com.yimeihuijin.codeandbonusapp.model;

import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.utils.ThreadMananger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chanson on 2015/12/16.
 */
public abstract class Model {
    protected void execute(Runnable runnable){
        ThreadMananger.get().execute(runnable);
    }

    public static class PO{
        public String jData;
        public String termId;
        public String storId;
        public String timestamp;

        public PO(Object jData) {
            termId = DeviceModel.getInstance().getDevice().en;
            storId = DeviceModel.getInstance().getDevice().mcode;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date date = new Date(System.currentTimeMillis());
            timestamp = sdf.format(date);

            Gson gson = new Gson();
            this.jData = gson.toJson(jData);
        }
    }
}
