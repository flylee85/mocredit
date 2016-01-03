package cn.mocredit.gateway.posp.config;

import java.net.SocketAddress;

import org.apache.mina.integration.beans.InetSocketAddressEditor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.stereotype.Component;

@Component
public class PospEditorRegistrar implements PropertyEditorRegistrar {

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(SocketAddress.class, new InetSocketAddressEditor());
	}
}