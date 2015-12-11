package com.yimiehuijin.codeandbonuslibrary.data;

/**
 * Created by Chanson on 2015/11/23.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.App;

public class PostData {
    public String jData;
    public String termId;
    public String storId;
    public String timestamp;

    public PostData(Object jData) {
        termId = App.getInstance().deviceInfo.en;
        storId = App.getInstance().deviceInfo.mcode;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date date = new Date(System.currentTimeMillis());
        timestamp = sdf.format(date);

        Gson gson = new Gson();
        this.jData = gson.toJson(jData);
    }
}

