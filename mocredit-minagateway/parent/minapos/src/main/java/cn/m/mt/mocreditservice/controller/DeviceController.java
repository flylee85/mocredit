package cn.m.mt.mocreditservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.m.mt.mocreditservice.service.DeviceService;
import cn.m.mt.po.Device;

@Controller
@RequestMapping("/")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = "syncDevice")
	@ResponseBody
	public String syncDevice(int oper, String id, String enCode) {
		Device device = new Device();
		device.setTerminalid(id);
		device.setDevcode(enCode);
		try {
			Device temp1 = deviceService.getDeviceByTerminalId(id);
			Device temp2 = deviceService.getDeviceByDevcode(enCode);
			if (temp1 == null && temp2 == null) {
				setDefaultValue(device);
				deviceService.save(device);
			} else if (temp1 == null && temp2 != null) {
				device.setId(temp2.getId());
				deviceService.update(device);
			} else if (temp1 != null && temp2 == null) {
				device.setId(temp1.getId());
				deviceService.update(device);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "0";
	}

	private void setDefaultValue(Device device) {
		device.setBatchno("000001");
		device.setDeskey("3131313131313131");
		device.setMackey("3131313131313131");
		device.setStatus(0);
		device.setLoginnum(0);
	}
}