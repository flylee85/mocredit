package cn.m.mt.mocreditservice.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ResourceUtil {
	
	public static String HESSIAN_URL=null;
	public static String ZX_PRINTTITLE=null;
	static {
		Properties properties = new Properties();
		try {
			properties.load(ResourceUtil.class.getResourceAsStream("/system.properties"));
		} catch (Exception e) {
			//System.out.println("读取配置文件失败");
		}
		
		HESSIAN_URL = properties.getProperty("system.hessian.url");
		ZX_PRINTTITLE = properties.getProperty("system.zx.printtitle");
	}

}
