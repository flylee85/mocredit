package com.yimeihuijin.codeandbonusapp.model;


import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.MagneticReader;
import cn.weipass.pos.sdk.Printer;
import cn.weipass.pos.sdk.Scanner;
import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * Created by Chanson on 2015/12/16.
 *
 * 获取机具信息，提供打印，扫码，刷卡等接口
 */
public class DeviceModel {

    private Device device;

    private static DeviceModel instance;

    private Card card;

    private MagneticReader magneticReader;

    private Scanner scanner;

    private Printer printer;

    public final static String PRINT_DIVIDER = "---------------------------";
    public final static String SIGN_INFO = "持卡人签名：\n\n" + "本人已确认以上交易，统一将其计入本账户\n"
            + PRINT_DIVIDER + "\n";

    private DeviceModel(){
    }

    public static DeviceModel getInstance(){
        if(instance == null){
            synchronized (DeviceModel.class){
                instance = new DeviceModel();
            }
        }
        return instance;
    }

    /**
     * 初始化机具
     * @param context
     * @param presenter 机具初始化结果的接收方
     */
    public  void initialize(final Context context,final IDeviceInitPresenter presenter){
        WeiposImpl.as().init(context, new Weipos.OnInitListener() {
            @Override
            public void onInitOk() {
                device = new Gson().fromJson(WeiposImpl.as().getDeviceInfo(),Device.class);
                if(device != null){
                    device.cleanEN();
                    magneticReader = WeiposImpl.as().openMagneticReader(); //获取磁卡reader
                    presenter.initOk();
                }else{
                    onError("设备的信息异常");
                }
            }

            @Override
            public void onError(String s) {
                presenter.initError(s);
            }
        });
    }

    public Device getDevice(){
        return device;
    }

    /**
     * 根据打印机状态码返回打印机异常信息
     * @param what
     * @param info
     * @return
     */
    public static String getPrintErrorInfo(int what, String info) {
        String message = "";
        switch (what) {
            case IPrint.EVENT_CONNECT_FAILD:
                message = "连接打印机失败";
                break;
            case IPrint.EVENT_CONNECTED:
                // Log.e("subscribe_msg", "连接打印机成功");
                break;
            case IPrint.EVENT_PAPER_JAM:
                message = "打印机卡纸";
                break;
            case IPrint.EVENT_UNKNOW:
                message = "打印机未知错误";
                break;
            case IPrint.EVENT_OK:
                // 回调函数中不能做UI操作，所以可以使用runOnUiThread函数来包装一下代码块
                // Log.e("subscribe_msg", "打印机正常");
                break;
            case IPrint.EVENT_NO_PAPER:
                message = "打印机缺纸";
                break;
            case IPrint.EVENT_HIGH_TEMP:
                message = "打印机高温";
                break;
        }

        return message;
    }

    /**
     * 打印二维码
     * @param qr
     * @return
     */
    public  boolean printQR(String qr){
        if(printer == null){
            try {
                printer = WeiposImpl.as().openPrinter();
                printer.setOnEventListener(new IPrint.OnEventListener() {

                    @Override
                    public void onEvent(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        String msg = getPrintErrorInfo(arg0, arg1);
                        if (msg == null || msg.equals("")) {
                            return;
                        }

                        Toast.makeText(App.getInstance(), msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                return false;
            }
        }
        if(qr == null){
            return true;
        }
        printer.printQrCode(qr,320, Printer.Gravity.CENTER);

        print("\n");
        return true;
    }

    public  void print(String data){
        printer.printText(data, Printer.FontFamily.SONG,
                Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL,
                Printer.Gravity.LEFT);
        printer.printText("\n\n", Printer.FontFamily.SONG,
                Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL,
                Printer.Gravity.LEFT);
    }

    /**
     * 打印字符串
     * @param data
     */
    public  void printNormal(String data){
        if(printer == null){
            try {
                printer = WeiposImpl.as().openPrinter();
                printer.setOnEventListener(new IPrint.OnEventListener() {
                    @Override
                    public void onEvent(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        String msg = getPrintErrorInfo(arg0, arg1);
                        if (msg == null || msg.equals("")) {
                            return;
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
        print(data);
    }

    public void scan(Scanner.OnResultListener listener){
        if(scanner == null){
            try {
                scanner = WeiposImpl.as().openScanner();
            }catch (Exception e){

            }
        }
        if(scanner != null){
            scanner.scan(Scanner.TYPE_QR,listener);
        }
    }

    public Card getCard(){
        if(magneticReader == null){
            magneticReader = WeiposImpl.as().openMagneticReader();
        }
        if(magneticReader == null){
            return null;
        }
        String cardinfo = magneticReader.getCardDecodeData();
        if(cardinfo == null){
            return null;
        }else {
            String[] cardinfoArray = cardinfo.split("=");
            if (cardinfoArray != null && cardinfoArray.length > 1) {
                Card card = new Card();
                card.cardno = cardinfoArray[0];
                card.cardbin = cardinfoArray[0].substring(0, 6);
                if (cardinfoArray[1].length() > 3) {
                    card.expdate = cardinfoArray[1].substring(0, 4);
                } else {
                    card.expdate = cardinfoArray[1];
                }
                this.card = card;
                this.card.cardno = "6226011030776167";
                this.card.expdate = "0819";
                this.card.cardbin = "622601";
                return card;
            }
        }
        return null;
    }

    public void destroy(){
        WeiposImpl.as().destroy();
    }

    public interface  IDeviceInitPresenter{
        public void initOk();
        public void initError(String msg);
    }

    public class Card{
        public String cardno;
        public String cardbin;
        public String expdate;
    }

    public class Device{
        public String mname;
        public String mcode;
        public String en;
        public String loginType;
        public String name;

        /**
         * 机具号默认有空格，该方法去掉机具号的空格
         */
        public void cleanEN(){
            if(en != null){
                en = en.replaceAll("\\s*", "");
            }
        }

        public String getDevMD5(){
            return StringUtils.MD5(en);
        }
    }
}
