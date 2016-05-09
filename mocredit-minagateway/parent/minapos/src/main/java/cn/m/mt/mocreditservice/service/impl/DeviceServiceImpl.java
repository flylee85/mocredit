package cn.m.mt.mocreditservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m.mt.dao.DeviceDao;
import cn.m.mt.mocreditservice.service.DeviceService;
import cn.m.mt.po.Device;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;

	@Override
	public Device save(Device device) {
		return deviceDao.save(device);
	}

	@Override
	public Object update(Object entity) {
		return deviceDao.update(entity);
	}

	@Override
	public Device getDeviceByTerminalId(String terminalId) {
		List<Device> list = deviceDao.findByProperty("terminalid", terminalId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Device getDeviceByDevcode(String devcode){
		List<Device> list = deviceDao.findByProperty("devcode", devcode);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

}
