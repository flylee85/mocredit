package com.mocredit.security.entity;

/**
 * Created by IntelliJ IDEA.
 * User: wangweiguo
 * Date: 11-6-17
 * Time: 下午11:56
 * To change this template use File | Settings | File Templates.
 */
public class Captcha {
    private String code;
    private byte[] jpegBytes;

    public Captcha() {
    }

    public Captcha(String code, byte[] jpegBytes) {
        this.code = code;
        this.jpegBytes = jpegBytes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getJpegBytes() {
        return jpegBytes;
    }

    public void setJpegBytes(byte[] jpegBytes) {
        this.jpegBytes = jpegBytes;
    }
}
