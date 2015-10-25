package com.mocredit.activitysys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.mocredit.activitysys.model.Data;
import com.mocredit.activitysys.service.DataService;

@Service
public class DataServiceImpl implements DataService {
	private static Map<String, Object> STORES=new HashMap<>();
	static{
		Map<String, String>map1=new HashMap<>();
		map1.put("id", "we234werwer");
		map1.put("shopId", "3232525235");
		map1.put("shopName", "商户1");
		map1.put("storeName", "门店1");
		map1.put("area", "成都");
		Map<String, String>map2=new HashMap<>();
		map2.put("id", "234sdfs");
		map2.put("shopId", "4563436");
		map2.put("shopName", "商户2");
		map2.put("storeName", "门店2");
		map2.put("area", "成都");
		Map<String, String>map3=new HashMap<>();
		map3.put("id", "34532533");
		map3.put("shopId", "dfgeretr");
		map3.put("shopName", "商户3");
		map3.put("storeName", "门店3");
		map3.put("area", "成都");
		
		Map<String, String>map4=new HashMap<>();
		map4.put("id", "ertertret345");
		map4.put("shopId", "dfgeretr");
		map4.put("shopName", "商户4");
		map4.put("storeName", "门店4");
		map4.put("area", "成都");Map<String, String>map5=new HashMap<>();
		map5.put("id", "345ertert");
		map5.put("shopId", "dfgeretr");
		map5.put("shopName", "商户5");
		map5.put("storeName", "门店5");
		map5.put("area", "成都");Map<String, String>map6=new HashMap<>();
		map6.put("id", "34532533");
		map6.put("shopId", "dfgeretr");
		map6.put("shopName", "商户6");
		map6.put("storeName", "门店6");
		map6.put("area", "成都");Map<String, String>map7=new HashMap<>();
		map7.put("id", "27145e");
		map7.put("shopId", "dfgeretr");
		map7.put("shopName", "商户7");
		map7.put("storeName", "门店7");
		map7.put("area", "成都");Map<String, String>map8=new HashMap<>();
		map8.put("id", "4564df");
		map8.put("shopId", "dfgeretr");
		map8.put("shopName", "商户8");
		map8.put("storeName", "门店8");
		map8.put("area", "成都");Map<String, String>map9=new HashMap<>();
		map9.put("id", "w4tsrtesrt");
		map9.put("shopId", "dfgeretr");
		map9.put("shopName", "商户9");
		map9.put("storeName", "门店9");
		map9.put("area", "成都");Map<String, String>map10=new HashMap<>();
		map10.put("id", "6ergsrg");
		map10.put("shopId", "dfgeretr");
		map10.put("shopName", "商户10");
		map10.put("storeName", "门店10");
		map10.put("area", "成都");Map<String, String>map11=new HashMap<>();
		map11.put("id", "456drgsfg");
		map11.put("shopId", "dfgeretr");
		map11.put("shopName", "商户11");
		map11.put("storeName", "门店11");
		map11.put("area", "成都");Map<String, String>map12=new HashMap<>();
		map12.put("id", "34tdfgsfg");
		map12.put("shopId", "dfgeretr");
		map12.put("shopName", "商户12");
		map12.put("storeName", "门店12");
		map12.put("area", "成都");
		STORES.put(map1.get("id"),map1);
		STORES.put(map2.get("id"),map2);
		STORES.put(map3.get("id"),map3);
		STORES.put(map4.get("id"),map4);
		STORES.put(map5.get("id"),map5);
		STORES.put(map6.get("id"),map6);
		STORES.put(map7.get("id"),map7);
		STORES.put(map8.get("id"),map8);
		STORES.put(map9.get("id"),map9);
		STORES.put(map10.get("id"),map10);
		STORES.put(map11.get("id"),map11);
		STORES.put(map12.get("id"),map12);
	}
	@Override
	public Map<String, Object> getData(String field) {
		String[] fields = field.split(",");
		Map<String, Object> map = new HashMap<>();
		for (String f : fields) {
			String[] fieldAndQuery = f.split("[|]");
			Map<String, Object> queryMap = null;
			if (fieldAndQuery.length > 1) {
				queryMap = new HashMap<>();
				try {
					String[] querys = fieldAndQuery[1].split("&");
					for (String query : querys) {
						String[] nameAndValue = query.split("=");
						queryMap.put(nameAndValue[0], nameAndValue[1]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put(fieldAndQuery[0], handle(fieldAndQuery[0], queryMap));
		}
		return map;
	}

	private List<Object> handle(String field, Map<String, Object> param) {
		switch (field) {
		case "ENTERPRISE":
			List<Object> enterprises = new ArrayList<>();
			enterprises.add(new Data("wer234234", "中信银行"));
			enterprises.add(new Data("23sfsef", "民生银行"));
			enterprises.add(new Data("2sfddsf", "costa"));
			return enterprises;
		case "CONTRACT":

			List<Object> contracts = new ArrayList<>();
			contracts.add(new Data("123124124", "合同1"));
			contracts.add(new Data("we2342143", "合同2"));
			contracts.add(new Data("34612safsdf", "合同3"));
			return contracts;
		case "STORE":
			List<Object> stores = new ArrayList<>();
			String ids = param.get("id").toString();
			String[] idarray = ids.split("_");
			for(String id:idarray){
				stores.add(STORES.get(id));
			}
			return stores;
		default:
			break;
		}
		return null;
	}

	@Override
	public List<Object> getStores(Map<String, String> param) {
		List<Object> list=new ArrayList<>();
		for(Entry<String, Object> entry:STORES.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}
}
