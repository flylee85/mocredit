package com.yimiehuijin.codeandbonuslibrary.data;

public class WeiposDeviceInfo {
	public String mname;
	public String mcode;
	public String en;
	public String loginType;
	public String name;
	
	public void cleanEN(){
		if(en != null){
			en = en.replaceAll("\\s*", "");
		}
	}
}
