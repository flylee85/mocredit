package com.yimiehuijin.bonusconusmeapp.modules.setting;

import android.view.View;

import com.yimiehuijin.bonusconusmeapp.R;
import com.yimiehuijin.bonusconusmeapp.base.IBaseActivity;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;
import com.yimiehuijin.codeandbonuslibrary.utils.PrinterUtils;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;
import com.yimiehuijin.codeandbonuslibrary.web.ConsumeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chanson on 2015/11/25.
 */
public class SettingActivity  extends IBaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initAactivity() {

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.setting_settle:
                showProgressDialog("正在生成清单...");
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        printInfo();
                    }
                }.start();
                break;
            case R.id.setting_back:
                finish();
                break;
        }
    }

    private void printInfo(){
        List<String> listConsume = App.getInstance().getDBHelper().getTradelist(ConsumeUtil.FLAG_CONSUME);
        List<String> listRevoke = App.getInstance().getDBHelper().getTradelist(ConsumeUtil.FLAG_REVOKE);
        if(listConsume == null){

            listConsume = new ArrayList<String>();
        }
        if(listRevoke == null){
            listRevoke = new ArrayList<String>();
        }
        float consumeTotal = 0;
        float revokeTotal = 0;
        for(String b:listConsume){
            consumeTotal += StringUtils.getFloat(b,0);
        }
        for(String b:listRevoke){
            revokeTotal += StringUtils.getFloat(b,0);
        }
        String printInfo = "\n"+ PrinterUtils.printDivider+
                "商户名称："+App.getInstance().deviceInfo.mcode+"\n"+
                "日期："+StringUtils.getCurrentDate()+"\n"+
                "时间："+StringUtils.getCurrentTime()+"\n"+ PrinterUtils.printDivider+
                "消费笔数："+listConsume.size()+"\n"+
                "消费金额："+consumeTotal+"\n"+
                "撤销总笔数："+listRevoke.size()+"\n"+
                "撤销总金额："+revokeTotal+"\n"+PrinterUtils.printDivider+"\n";
        PrinterUtils.print(printInfo);
        dismissProgressDialog();
    }
}
