package cn.m.mt.mocreditservice;

import java.io.IOException;

import org.apache.mina.core.service.IoAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.m.mt.mocreditservice.util.SpringSing;

public class MinaTimeServer {
	private static Logger log = LoggerFactory.getLogger(MinaTimeServer.class);

	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = SpringSing.getContext();
		IoAcceptor acceptor = (IoAcceptor) ctx.getBean("ioAcceptor");
		log.info("Calculator server has started up.... Local Address: "
				+ acceptor.getLocalAddress().toString()
				+ " Default Local Address: "
				+ acceptor.getDefaultLocalAddress().toString());
		acceptor.unbind( );
		acceptor.bind();
	}
}