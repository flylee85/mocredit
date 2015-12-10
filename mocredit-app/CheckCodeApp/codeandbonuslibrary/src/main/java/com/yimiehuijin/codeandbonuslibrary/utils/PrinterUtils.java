package com.yimiehuijin.codeandbonuslibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.yimiehuijin.codeandbonuslibrary.App;

import java.io.ByteArrayOutputStream;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.Printer;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * Created by Chanson on 2015/11/24.
 */
public class PrinterUtils {
    private static Printer printer;
    public static final int rowSize = 384;
    public final static String printDivider = "---------------------------";
    public static final int smallSize = 24 * 2;
    public static final int mediumSize = 16 * 2;
    public static final int largeSize = 12 * 2;
    public static final int extralargeSize = 8 * 2;

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

    public static boolean printQR(String qr){
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
        printer.printQrCode(qr,320,Printer.Gravity.CENTER);

        print("\n");
        return true;
    }

    public static void printNormal(String data){
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
//                Toast.makeText(App.getInstance(), msg,
//                        Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
            }
        }
        print(data);
    }


    public static void printNormal(final String data,final PrinterListener listener) {
        if(printer == null){
            try {
                printer = WeiposImpl.as().openPrinter();
            } catch (Exception e) {
                listener.onFailure("找不到打印机");
            }
        }
        printer.setOnEventListener(new IPrint.OnEventListener() {

            @Override
            public void onEvent(int arg0, String arg1) {
                // TODO Auto-generated method stub
                String msg = getPrintErrorInfo(arg0, arg1);
                if (msg == null || msg.equals("")) {
                    listener.onSuccess();
                    print(data);
                    return;
                }
                listener.onFailure(msg);
//                Toast.makeText(App.getInstance(), msg,
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void print(String data){
        printer.printText(data, Printer.FontFamily.SONG,
                Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL,
                Printer.Gravity.LEFT);
        printer.printText("\n\n", Printer.FontFamily.SONG,
                Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL,
                Printer.Gravity.LEFT);
    }


    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null || str.trim().equals("")
                || str.trim().equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param String
     *            s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
     *
     * @param String
     *            s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static double getLength(String s) {
        if (s == null) {
            return 0;
        }
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        // 进位取整
        return Math.ceil(valueLength);
    }

    public static String getBlankBySize(int size) {
        String resultStr = "";
        for (int i = 0; i < size; i++) {
            resultStr += " ";
        }
        return resultStr;
    }

    // 将Drawable转化为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap → byte[]
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static interface PrinterListener{
        public void onSuccess();
        public void onFailure(String msg);
    }
}
