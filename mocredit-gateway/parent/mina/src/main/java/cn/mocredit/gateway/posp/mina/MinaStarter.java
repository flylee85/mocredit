package cn.mocredit.gateway.posp.mina;

import java.io.IOException;

import org.apache.mina.core.service.IoAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Component
public class MinaStarter implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IoAcceptor acceptor;

	public void run(String... args) {
		logger.info("Mina server starting ...");
		acceptor.unbind();
		try {
			acceptor.bind();
		} catch (IOException e) {
			logger.error(getStackTrace(e));
			return;
		}
		logger.info("Mina server has started up.... Local Address: " 
				+ acceptor.getLocalAddress().toString() 
				+ " Default Local Address: " 
				+ acceptor.getDefaultLocalAddress().toString());
	}
}