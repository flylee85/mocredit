package com.mocredit.base.util;

import java.net.URLDecoder;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class FilePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	public void setLocations(Resource[] locations) {
		for (int i = 0; i < locations.length; i++) {
			locations[i] = convertPath(locations[i]);
		}
		super.setLocations(locations);
	}

	@Override
	public void setLocation(Resource location) {
		super.setLocation(convertPath(location));
	}

	private Resource convertPath(Resource resource) {
		if (resource.exists()) {
			return resource;
		}
		String path = getClass().getResource("/").getPath();
		try {
			path = URLDecoder.decode(path, "utf-8");
			String fileName = resource.getFile().getPath();
			return new FileSystemResource(path + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
