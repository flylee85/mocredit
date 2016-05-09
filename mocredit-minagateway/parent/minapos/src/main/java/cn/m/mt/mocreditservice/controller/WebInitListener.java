package cn.m.mt.mocreditservice.controller;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.mina.core.service.IoAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import cn.m.mt.mocreditservice.util.SpringContextUtils;

public class WebInitListener implements ServletContextListener {
	private static Logger log = LoggerFactory.getLogger(WebInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ctx = SpringContextUtils.getApplicationContext();
		IoAcceptor acceptor = (IoAcceptor) ctx.getBean("ioAcceptor");
		log.info("Calculator server has started up.... Local Address: " + acceptor.getLocalAddress().toString()
				+ " Default Local Address: " + acceptor.getDefaultLocalAddress().toString());
		acceptor.unbind();
		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
