package com.mocredit.activitysys.service;

import java.util.List;
import java.util.Map;

public interface DataService {
Map<String, Object> getData(String field);
List<Object> getStores(Map<String, String> param);
}
