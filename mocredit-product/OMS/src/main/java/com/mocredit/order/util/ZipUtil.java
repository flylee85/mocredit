package com.mocredit.order.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * zip压缩 User: jiangxin Date: 14-7-17 Time: 下午3:16
 */
public class ZipUtil {
	private static Log logger = LogFactory.getLog(ZipUtil.class);

	public static void write(File path, File zipFile, String filterFileName)
			throws IOException {
		try {
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(
					zipFile));
			zip.setEncoding("GBK");
			write(path, path, zip, filterFileName);
			zip.close();
		} catch (IOException e) {
			logger.error("ZipUtil  write is error...", e);
		}
	}

	private static void write(File base, File path, ZipOutputStream zip,
			String filterFileName) throws IOException {
		URI rel = base.toURI().relativize(path.toURI());
		if (path.isDirectory()) {
			ZipEntry entry = new ZipEntry(rel.getPath());
			entry.setUnixMode(755);
			zip.putNextEntry(entry);
			zip.closeEntry();
			File[] files = path.listFiles();
			for (File file : files) {
				if (file.getName().contains(filterFileName)) {
					continue;
				}
				write(base, file, zip, filterFileName);
			}
		} else {
			ZipEntry entry = new ZipEntry(rel.getPath());
			entry.setUnixMode(644);
			zip.putNextEntry(entry);
			FileInputStream is = new FileInputStream(path);
			zip.write(IOUtils.toByteArray(is));
			is.close();
			zip.closeEntry();
		}
	}

}
