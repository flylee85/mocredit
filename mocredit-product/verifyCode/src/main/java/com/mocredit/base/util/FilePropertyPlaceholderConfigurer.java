package com.mocredit.base.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class FilePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private Properties props;

	@Override
	public void setLocations(Resource[] locations) {
		for (int i = 0; i < locations.length; i++) {
			locations[i] = convertPath(locations[i]);
		}
		super.setLocations(locations);
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		props = super.mergeProperties();
		PropertiesUtil.init(props);
		return props;
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
