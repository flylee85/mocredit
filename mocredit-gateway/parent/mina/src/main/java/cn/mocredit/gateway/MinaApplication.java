package cn.mocredit.gateway;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import cn.mocredit.gateway.posp.mina.TextLineCodecFactory;

@SpringBootApplication
@PropertySource({"classpath:do_not_modify.properties"})
public class MinaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MinaApplication.class, args);
	}

	@Bean
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();
	}

	@Bean
	public ProtocolCodecFilter protocolCodecFilter(TextLineCodecFactory f) {
		return new ProtocolCodecFilter(f);
	}

	@Bean
	public CustomEditorConfigurer pospCustomEditorConfigurer(PropertyEditorRegistrar[] array) {
		CustomEditorConfigurer ret = new CustomEditorConfigurer();
		ret.setPropertyEditorRegistrars(array);
		return ret;
	}

	@Bean
	public DefaultIoFilterChainBuilder filterChainBuilder(Map<String, IoFilter> filters) {
		DefaultIoFilterChainBuilder ret = new DefaultIoFilterChainBuilder();
		ret.setFilters(filters);
		return ret;
	}

	@Bean
	public NioSocketAcceptor pospMinaIoAcceptor(IoHandler handler, IoFilterChainBuilder builder, 
			@Value("${mina.defaultLocalAddress}") InetSocketAddress localAddress) {
		NioSocketAcceptor ret = new NioSocketAcceptor();
		ret.setHandler(handler);
		ret.setFilterChainBuilder(builder);
		ret.setDefaultLocalAddress(localAddress);
		return ret;
	}

}
