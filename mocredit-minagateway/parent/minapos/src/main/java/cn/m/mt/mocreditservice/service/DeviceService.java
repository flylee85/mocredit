package cn.m.mt.mocreditservice.service;

import cn.m.mt.po.Device;

public interface DeviceService {

	Device save(Device device);

	Object update(Object entity);

	Device getDeviceByTerminalId(String terminalId);
	
	Device getDeviceByDevcode(String devcode);
}
