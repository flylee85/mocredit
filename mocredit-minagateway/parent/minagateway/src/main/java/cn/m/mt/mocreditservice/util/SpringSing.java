package cn.m.mt.mocreditservice.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringSing {
	public static ApplicationContext ctx = null;
	
	public static ApplicationContext getContext (){
		if(ctx == null)
			ctx = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
		return ctx;
	}
	
}
