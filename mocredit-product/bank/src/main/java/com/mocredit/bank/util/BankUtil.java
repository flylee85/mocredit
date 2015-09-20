package com.mocredit.bank.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class BankUtil {
	private static Map<String, List<String>> BINS = new HashMap<>();
	private static long lastModifyTime = 0;
	/**
	 * 间隔两小时更新
	 */
	private static int interval = 1000 * 60 * 60 * 2;

	public static void main(String[] args) throws IOException {
		System.out.println(getBankByCard("404157123123123"));
	}

	private static void sysoBank() throws UnsupportedEncodingException, IOException {
		String bank = "民生银行";
		String path = "/bin.txt";
		InputStream resourceAsStream = BankUtil.class.getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream, "gbk"));
		StringBuffer sb = new StringBuffer();
		String readLine = null;
		while ((readLine = br.readLine()) != null) {
			String[] split = readLine.split(" ");
			if (split.length == 6 && bank.equals(split[0])) {
				sb.append(split[5]).append("|");
			}
		}
		System.out.println(sb.toString());
	}

	/**
	 * 根据卡号判断银行
	 * 
	 * @param cardNo
	 * @return 银行代码 citic/cmbc....
	 */
	public static String getBankByCard(String cardNo) {
		if (null == cardNo) {
			return null;
		}
		Map<String, List<String>> bins = getBINS();
		for (Entry<String, List<String>> bank : bins.entrySet()) {
			for (String bin : bank.getValue()) {
				if (cardNo.startsWith(bin)) {
					return bank.getKey();
				}
			}
		}
		return null;
	}

	private static Map<String, List<String>> getBINS() {
		if (checkModify()) {
			initBIN();
		}
		return BINS;
	}

	/**
	 * 
	 */
	private static synchronized void initBIN() {
		long now = System.currentTimeMillis();
		if (0 == lastModifyTime || now - lastModifyTime >= interval) {
			InputStream resourceAsStream = BankUtil.class.getResourceAsStream("/conf/bin.properties");
			if (null == resourceAsStream) {
				return;
			}
			Properties p = new Properties();
			try {
				p.load(resourceAsStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Set<Entry<Object, Object>> values = p.entrySet();
			for (Entry<Object, Object> entry : values) {
				String bank = entry.getKey().toString();
				String value = entry.getValue().toString();
				String[] bins = value.split("[|]");
				List<String> list = new ArrayList<>();
				for (String bin : bins) {
					list.add(bin);
				}
				BINS.put(bank, list);
			}
			lastModifyTime = now;
		}
	}

	private static boolean checkModify() {
		return 0 == lastModifyTime || System.currentTimeMillis() - lastModifyTime >= interval;
	}
}
