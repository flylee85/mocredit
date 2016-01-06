package cn.mocredit.gateway.service;

public interface ControllerService {
    String qiandao(String h, String t);
    String qiandaohuizhi(String h, String t);
    String jiesuan(String h, String t);
    String huodongliebiao(String h, String t);
    String xintiao(String h, String t);
    String yanma(String h, String t);
    String xiaofei(String h, String t);
    String xiaofeichongzheng(String h, String t);
    String chexiaochongzheng(String h, String t);
    String chexiao(String h, String t);
    String jiamiceshi(String h, String t);
    String syncdevcode(String json);
    String resetDevpassword(String json);
    String yanmachexiao(String h,String t);
    String shoudantongbu(String h,String t);
}
