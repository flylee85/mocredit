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
		map1.put("id", "124124124wrqwr");
		map1.put("shopId", "2353251123");
		map1.put("shopName", "商户1");
		map1.put("storeName", "门店1");
		map1.put("area", "成都");
		Map<String, String>map2=new HashMap<>();
		map2.put("id", "qweqeqweew");
		map2.put("shopId", "2353251123");
		map2.put("shopName", "商户1");
		map2.put("storeName", "门店2");
		map2.put("area", "成都");
		STORES.put(map1.get("id"),map1);
		STORES.put(map2.get("id"),map2);
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
