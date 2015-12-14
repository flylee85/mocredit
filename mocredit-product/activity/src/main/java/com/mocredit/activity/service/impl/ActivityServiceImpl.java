package com.mocredit.activity.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.Activity;
import com.mocredit.activity.model.ActivityStore;
import com.mocredit.activity.model.Batch;
import com.mocredit.activity.model.BatchCode;
import com.mocredit.activity.model.BatchCodeVO;
import com.mocredit.activity.model.BatchVO;
import com.mocredit.activity.model.Mms;
import com.mocredit.activity.model.Mmsframe;
import com.mocredit.activity.model.SelectStoreVO;
import com.mocredit.activity.persitence.ActivityMapper;
import com.mocredit.activity.persitence.ActivityStoreMapper;
import com.mocredit.activity.persitence.BatchCodeMapper;
import com.mocredit.activity.persitence.BatchMapper;
import com.mocredit.activity.persitence.MmsframeMapper;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.activity.service.SendMMSPackage;
import com.mocredit.activity.utils.ResourceUtil;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.ExcelUtil;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.IDUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.base.util.ValidatorUtil;
import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.model.Store;
import com.mocredit.manage.model.Terminal;
import com.mocredit.manage.persitence.ContractMapper;
import com.mocredit.manage.persitence.EnterpriseMapper;
import com.mocredit.manage.persitence.StoreMapper;
import com.mocredit.manage.persitence.TerminalMapper;
import com.mocredit.sys.service.OptLogService;

/**
 * 
 * 活动-Service映射层 活动管理的Service类，所有活动管理相关的后台处理方法，都是在此类中
 * 
 * @author lishoukun
 * @date 2015/07/08
 */
@Service
public class ActivityServiceImpl implements ActivityService {
	// 日志对象
	private static Logger logger = Logger.getLogger(ActivityServiceImpl.class);
	@Autowired
	private ActivityMapper activityMapper;// 活动dao对象
	@Autowired
	private ActivityStoreMapper activityStoreMapper; // 活动关联门店dao对象
	@Autowired
	private BatchMapper batchMapper;// 发码批次dao对象
	@Autowired
	private BatchCodeMapper batchCodeMapper;// 发码批次码dao对象
	@Autowired
	private EnterpriseMapper enterpriseMapper;// 企业操作
	@Autowired
	private ContractMapper contractMapper;// 合同操作对象
	@Autowired
	private OptLogService optLogService;// 操作日志service对象
	@Autowired
	private StoreMapper storeMapper; // 门店mapper
	@Autowired
	private TerminalMapper terminalMapper;// 机具mapper
	@Autowired
	private MmsframeMapper mmsframeMapper;
	@Autowired
	private SendMMSPackage sendMMSPackage;
	private boolean importFlag = true;

	/**
	 * 获取一条活动，根据主键
	 * 
	 * @param id
	 * @return一
	 */
	public Activity getActivityById(String id) {
		// 获取活动对象
		Activity activityObj = activityMapper.getActivityById(id);
		// 根据活动Id,获取关联门店信息，并将相关数据设置到活动对象
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("activityId", id);
		List<ActivityStore> storeList = activityStoreMapper.queryActivityStoreList(queryMap);
		activityObj.setStoreList(storeList);
		// activityObj.setStoreCount(storeList.size());
		// //根据活动Id,查询订单列表
		// List<Batch> orderList = batchMapper.queryBatchList(queryMap);
		// activityObj.setOrderList(orderList);
		// activityObj.setOrderCount(orderList.size());
		return activityObj;
	}

	/**
	 * 获取一条活动，随机获取
	 * 
	 * @return
	 */
	public Activity getActivityByRand() {
		Activity activityObj = activityMapper.getActivityByRand();
		return activityObj;
	}

	/**
	 * 获取一条活动，根据条件
	 * 
	 * @param activityMap
	 * @return
	 */
	public Activity getActivity(Map<String, Object> activityMap) {
		List<Activity> activityList = activityMapper.queryActivityList(activityMap);
		if (activityList.size() > 0) {
			return activityList.get(0);
		}
		return null;
	}

	/**
	 * 查询活动列表，根据条件
	 * 
	 * @param activityMap
	 * @return
	 */
	public List<Activity> queryActivityList(Map<String, Object> activityMap) {
		List<Activity> activityList = activityMapper.queryActivityList(activityMap);
		return activityList;
	}

	/**
	 * 获取活动总数量，根据条件
	 * 
	 * @param activityMap
	 * @return
	 */
	public Integer getActivityTotal(Map<String, Object> activityMap) {
		Integer activityTotal = activityMapper.getActivityTotal(activityMap);
		return activityTotal;
	}

	/**
	 * 查询活动分页信息，根据条件
	 * 
	 * @param activityMap
	 *            请求参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public PageInfo<Activity> queryActivityPage(Map<String, Object> activityMap, Integer draw, Integer pageNum,
			Integer pageSize) {
		if (draw != null && draw != 1) {
			String searchContent = String.valueOf(activityMap.get("search[value]"));
			if (searchContent != null && !"".equals(searchContent)) {
				activityMap.put("searchInfoContent", searchContent);
			}
			String orderContent = String.valueOf(
					activityMap.get("columns[" + String.valueOf(activityMap.get("order[0][column]")) + "][name]"));
			String orderDir = String.valueOf(activityMap.get("order[0][dir]"));
			if (orderContent != null && !"".equals(orderContent) && orderDir != null && !"".equals(orderDir)) {
				activityMap.put("orderInfoContent", orderContent);
				activityMap.put("orderInfoDir", orderDir);
			}
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Activity> activityList = activityMapper.queryActivityList(activityMap);
		PageInfo<Activity> page = new PageInfo<Activity>(activityList);
		// 获取分页对象
		// 拼装存放活动id的list
		List<String> idList = new ArrayList<String>();
		for (Activity act : activityList) {
			idList.add(act.getId());
		}
		// 根据活动id的list查询列表
		// Map<String,Object> queryMap = new HashMap<String,Object>();
		// if(idList.size()>0){
		// queryMap.put("activityIdList", idList);
		//// int storeCount =
		// activityStoreMapper.getActivityStoreCount(queryMap);
		//
		// //将查询到的关联信息，添加到活动对象中
		// for(Activity act:activityList){
		// for(ActivityStore as : storeList){
		// if(act.getId().equals(as.getActivityId())){
		// act.getStoreList().add(as);
		// }
		// }
		// act.setStoreCount(act.getStoreList().size());
		// }
		// //根据活动id的list查询订单列表
		// List<Batch> orderList = batchMapper.queryBatchList(queryMap);
		// //将查询到的关联信息，添加到活动对象中
		// for(Activity act:activityList){
		// for(Batch o : orderList){
		// if(act.getId().equals(o.getActivityId())){
		// act.getOrderList().add(o);
		// }
		// }
		// act.setOrderCount(act.getOrderList().size());
		// }
		// }
		return page;
	}

	/**
	 * 查询活动管理相关选择门店分页信息，根据条件
	 * 
	 * @param activityMap
	 *            请求参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public PageInfo<SelectStoreVO> querySelectStorePage(Map<String, Object> activityMap, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SelectStoreVO> stores = activityMapper.querySelectStore(activityMap);
		return new PageInfo<>(stores);
	}

	@Override
	public List<ActivityStore> querySelectStores(Map<String, Object> activityMap) {
		if (null == activityMap.get("activityId")) {
			return null;
		}
		return activityStoreMapper.queryActivityStoreList(activityMap);
	}

	@Override
	public List<ActivityStore> queryStoresForSelect(Map<String, String> param) {
		return activityStoreMapper.selectForChoose(param);
	}

	@Override
	public void sendMMSPackage(String activityId) {
		Activity activity = activityMapper.getActivityById(activityId);
		Enterprise enterprise = new Enterprise();
		enterprise.setId(activity.getEnterpriseId());
		Enterprise ent = enterpriseMapper.selectOne(enterprise);
		Mms mms = mmsframeMapper.getMmsByActivityId(Integer.parseInt(activityId));
		Mmsframe mmsfram = new Mmsframe();
		List<Mmsframe> list = mmsframeMapper.getMmsframeListByMMSId(mms.getId());
		if(list!=null&&!list.isEmpty()){
			StringBuilder packageXML = new StringBuilder("<mms>");
			packageXML.append("<subject>").append(activity.getSubject()).append("</subject>");
			packageXML.append("<pages>");
			for(int i=0;i<list.size();i++){
				mmsfram =list.get(i);
				packageXML.append("<page dur=\"50\">");
				if(mmsfram.getPic()!=null&&!mmsfram.getPic().isEmpty()){
					packageXML.append("<img type=\""+mmsfram.getPictype()+"\">");
					packageXML.append(mmsfram.getPic());
					packageXML.append("</img>");
				}
				if(mmsfram.getText()!=null&&!mmsfram.getText().isEmpty()){
					String txtContent = mmsfram.getText();
					txtContent = txtContent.replace("$phone", "[[param01]]").replace("$name", "[[param02]]").replace("$pwd", "[[param03]]").replace("$f1", "[[param04]]").replace("$f2", "[[param05]]").replace("$f3", "[[param06]]");
					packageXML.append("<text>");
					packageXML.append(txtContent);
					packageXML.append("</text>");
				}
				packageXML.append("</page>");
				if (mms.getCode_no() - 1 == i + 1) {
					packageXML.append("<page dur=\"50\"><img type=\"image/jpeg\">/9j/4AAQSkZJRgABAgAAZABkAAD/7AARRHVja3kAAQAEAAAAPAAA/+4AJkFkb2JlAGTAAAAAAQMAFQQDBgoNAAABiQAAAaoAAAHaAAAB+//bAIQABgQEBAUEBgUFBgkGBQYJCwgGBggLDAoKCwoKDBAMDAwMDAwQDA4PEA8ODBMTFBQTExwbGxscHx8fHx8fHx8fHwEHBwcNDA0YEBAYGhURFRofHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8f/8IAEQgABQAFAwERAAIRAQMRAf/EAG4AAQAAAAAAAAAAAAAAAAAAAAcBAQAAAAAAAAAAAAAAAAAAAAEQAQAAAAAAAAAAAAAAAAAAAAARAQAAAAAAAAAAAAAAAAAAAAASAQAAAAAAAAAAAAAAAAAAAAATAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhEDEQAAAUgP/9oACAEBAAEFAn//2gAIAQIAAQUCf//aAAgBAwABBQJ//9oACAECAgY/An//2gAIAQMCBj8Cf//aAAgBAQEGPwJ//9oACAEBAwE/IX//2gAIAQIDAT8hf//aAAgBAwMBPyF//9oADAMBAAIRAxEAABB//9oACAEBAwE/EH//2gAIAQIDAT8Qf//aAAgBAwMBPxB//9k=</img></page>");
				}
			}
			packageXML.append("</pages>");
			packageXML.append("</mms>");
//			log.info("packageXML====="+packageXML.toString());
			String packageId ="000";
//			String token = ResourceUtil.MMSTOKENMAP.get(ent.getMmschannle());
			String token = "7102566630630741";
			packageId = sendMMSPackage.sendMMSPackage(packageXML.toString(),token);
			 if (!packageId.startsWith("ERROR") && !"0".equals(packageId)){
				 mms.setMmspackageid(Integer.parseInt(packageId));
				 mmsframeMapper.updateMms(mms);
             }else{
            	 logger.warn("上传彩信模板包失败，服务器返回："+packageId);
             }
		}
	}
	
	/**
	 * 添加活动
	 * 
	 * @param activity
	 * @return
	 */
	@Transactional()
	public Integer addActivity(Activity activity) {
		// 设置新添加活动的状态为01，创建时间为当前时间
		activity.setStatus("01");
		activity.setCreatetime(new Date());
		// 查询已存在活动名称
		if (activity.getName() != null) {
			Map<String, Object> existQueryMap = new HashMap<String, Object>();
			existQueryMap.put("name", activity.getName());
			Integer existCount = activityMapper.getActivityTotal(existQueryMap);
			if (existCount > 0) {
				throw new BusinessException("已存在重复的活动名称");
			}
		}
		// 查询已存在编码
		if (activity.getCode() != null) {
			Map<String, Object> existQueryMap = new HashMap<String, Object>();
			existQueryMap.put("code", activity.getCode());
			Integer existCount = activityMapper.getActivityTotal(existQueryMap);
			if (existCount > 0) {
				throw new BusinessException("已存在重复的活动编码");
			}
		}
		// 查询已存在外部编码
		if (activity.getOutCode() != null) {
			Map<String, Object> existQueryMap = new HashMap<String, Object>();
			existQueryMap.put("outCode", activity.getOutCode());
			Integer existCount = activityMapper.getActivityTotal(existQueryMap);
			if (existCount > 0) {
				throw new BusinessException("已存在重复的外部编码");
			}
		}
		// 添加活动
		Integer affectCount = activityMapper.addActivity(activity);
		// 添加活动关联的门店信息
		List<ActivityStore> storeList = activity.getStoreList();
		for (ActivityStore as : storeList) {
			as.setActivityId(activity.getId());
			activityStoreMapper.addActivityStore(as);
		}
		/*
		 * 积分同步活动
		 */
		if (!importFlag) {
			return affectCount;
		}
		if ("01".equals(activity.getType())) {
			// 获取验码系统中-修改活动，启动活动，停止活动的ＵＲＬ，并定义一个请求这些地址时，所需要的参数Map,将活动Id和活动名称都放在这个Map中
			String changeActivityUrl = PropertiesUtil.getValue("integral.activityImport");
			Map<String, Object> httpPostMap = new HashMap<String, Object>();
			httpPostMap.put("activityId", activity.getId());// 活动Id
			httpPostMap.put("activityName", activity.getName());// 活动Id
			// 定义一个修改内容描述
			StringBuffer changeDescribe = new StringBuffer();
			// 活动启用或停止
			if ("02".equals(activity.getStatus())) {
				// 验码模块-活动停止
				httpPostMap.put("status", "02");
				changeDescribe.append("活动状态：停止；");
			} else if ("01".equals(activity.getStatus())) {
				// 验码模块-活动启用
				httpPostMap.put("status", "01");
				changeDescribe.append("活动状态：启用；");
			}

			// 开始时间，结束时间
			httpPostMap.put("startTime", DateUtil.dateToStr(activity.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 开始时间
			httpPostMap.put("endTime", DateUtil.dateToStr(activity.getEndTime(), "yyyy-MM-dd HH:mm:ss"));// 结束时间
			changeDescribe.append("开始时间：" + httpPostMap.get("startTime") + ";");
			changeDescribe.append("结束时间：" + httpPostMap.get("endTime") + ";");

			// 活动指定日期修改，该处临时这样判断
			httpPostMap.put("selectDate", activity.getSelectDate());// 指定日期
			changeDescribe.append("指定日期：" + activity.getSelectDate() + ";");

			// 活动积分修改
			httpPostMap.put("integral", activity.getIntegral());
			changeDescribe.append("积分：" + activity.getIntegral() + ";");

			// 活动最大类型修改
			httpPostMap.put("maxType", "1");
			changeDescribe.append("最大类型：" + activity.getMaxType() + ";");

			// 活动使用次数
			httpPostMap.put("maxNumber", "1");
			changeDescribe.append("最大次数：" + activity.getMaxNumber() + ";");

			// 活动使用次数
			httpPostMap.put("productCode", activity.getOutCode().toString());
			changeDescribe.append("外部编码：" + activity.getOutCode() + ";");

			// BIN码
			httpPostMap.put("bins", activity.getBins());
			changeDescribe.append("BIN码列表：" + activity.getBins() + ";");

			// 积分通道
			httpPostMap.put("channel", activity.getChannel());
			changeDescribe.append("积分通道：" + activity.getChannel() + ";");

			// 兑换类型
			httpPostMap.put("exchangeType", activity.getExchangeType());
			changeDescribe.append("兑换类型：" + activity.getExchangeType() + ";");

			// 将活动的门店关联信息添加到修改描述中和调用接口的请求参数中
			List<Store> selectAllofActivity = storeMapper.selectAllofActivity(activity.getId());

			changeDescribe.append("门店信息：[");
			for (Store as : selectAllofActivity) {
				changeDescribe.append("{门店名称：" + as.getName() + ";");
				changeDescribe.append("{门店编码：" + as.getCode() + ";");
				changeDescribe.append("门店id：" + as.getId() + ";}");
				// 机具
				Terminal terminal = new Terminal();
				terminal.setStoreId(as.getId());
				List<Terminal> terminals = terminalMapper.selectAllEncode(terminal);
				as.setTerminals(terminals);
			}
			changeDescribe.append("]");

			httpPostMap.put("storeList", selectAllofActivity);

			// 将修改信息发送至验码系统
			httpPostMap.put("operCode", "1");
			// System.out.println(JSON.toJSONString(httpPostMap));
			String returnJson = HttpUtil.doRestfulByHttpConnection(changeActivityUrl, JSON.toJSONString(httpPostMap));
			Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
			boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
			if (!isSuccess) {
				throw new BusinessException("向积分核销系统同步信息失败");
			}
			// 保存送信息到验码系统的日志
			optLogService.addOptLog("活动Id:" + activity.getId(), "", "积分核销接口修改活动信息-----" + changeDescribe.toString());
		}
		// 保存日志
		optLogService.addOptLog(activity.getId(), "", "活动添加-----" + activity.toDescribeString());
		return affectCount;
	}

	/**
	 * 更新活动
	 * 
	 * @param activity
	 *            活动对象
	 * @return
	 */
	@Transactional()
	public Integer updateActivity(Activity activity) {
		// 获取原活动对象
		Activity oldActivity = activityMapper.getActivityById(activity.getId());
		// 查询已存在活动名称
		if (activity.getName() != null && !activity.getName().equals(oldActivity.getName())) {
			Map<String, Object> existQueryMap = new HashMap<String, Object>();
			existQueryMap.put("name", activity.getName());
			Integer existCount = activityMapper.getActivityTotal(existQueryMap);
			if (existCount > 0) {
				throw new BusinessException("已存在重复的活动名称");
			}
		}
		// 查询已存在编码
		if (activity.getCode() != null && !activity.getCode().equals(oldActivity.getCode())) {
			Map<String, Object> existQueryMap = new HashMap<String, Object>();
			existQueryMap.put("code", activity.getCode());
			Integer existCount = activityMapper.getActivityTotal(existQueryMap);
			if (existCount > 0) {
				throw new BusinessException("已存在重复的活动编码");
			}
		}
		// 查询已存在外部编码
		if (activity.getOutCode() != null && !activity.getOutCode().equals(oldActivity.getOutCode())) {
			Map<String, Object> existQueryMap = new HashMap<String, Object>();
			existQueryMap.put("outCode", activity.getOutCode());
			Integer existCount = activityMapper.getActivityTotal(existQueryMap);
			if (existCount > 0) {
				throw new BusinessException("已存在重复的外部编码");
			}
		}
		// 更新活动
		Integer affectCount = activityMapper.updateActivity(activity);
		// 删除原有的活动门店关联
		Map<String, Object> delMap = new HashMap<String, Object>();
		delMap.put("activityId", activity.getId());
		activityStoreMapper.deleteActivityStore(delMap);
		// 添加新的活动门店关联
		List<ActivityStore> storeList = activity.getStoreList();
		for (ActivityStore as : storeList) {
			as.setActivityId(activity.getId());
			activityStoreMapper.addActivityStore(as);
		}
		/*
		 * // * 同步接口 //
		 */
		synOthers(activity, oldActivity);

		// 修改活动日志
		optLogService.addOptLog(activity.getId(), "", "活动修改-----" + activity.toDescribeString());

		return affectCount;
	}

	/**
	 * 活动同步接口
	 * 
	 * @param activity
	 * @param oldActivity
	 * @param storeList
	 */
	private void synOthers(Activity activity, Activity oldActivity) {
		if (!importFlag) {
			return;
		}
		// 积分同步
		if ("01".equals(activity.getType())) {
			// 获取验码系统中-修改活动，启动活动，停止活动的ＵＲＬ，并定义一个请求这些地址时，所需要的参数Map,将活动Id和活动名称都放在这个Map中
			String changeActivityUrl = PropertiesUtil.getValue("integral.activityImport");
			Map<String, Object> httpPostMap = new HashMap<String, Object>();
			httpPostMap.put("activityId", activity.getId());// 活动Id
			httpPostMap.put("activityName", activity.getName());// 活动Id
			// 定义一个修改内容描述
			StringBuffer changeDescribe = new StringBuffer();
			// 活动启用或停止
			if (oldActivity.getStatus() != null && activity.getStatus() != null
					&& !oldActivity.getStatus().equals(activity.getStatus())) {
				if ("02".equals(activity.getStatus())) {
					// 验码模块-活动停止
					httpPostMap.put("status", "02");
					changeDescribe.append("活动状态：停止；");

					// 调用验码模块-停止活动接口
					httpPostMap.put("operCode", "3");
					String returnJson = HttpUtil.doRestfulByHttpConnection(changeActivityUrl,
							JSON.toJSONString(httpPostMap));
					Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
					boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
					if (!isSuccess) {
						throw new BusinessException("向积分核销系统停止活动失败");
					}
					// 保存送信息到验码系统的日志
					optLogService.addOptLog("活动Id:" + activity.getId(), "",
							"积分核销接口停止活动-----" + changeDescribe.toString());
				} else if ("01".equals(activity.getStatus())) {
					// 验码模块-活动启用
					httpPostMap.put("status", "01");
					changeDescribe.append("活动状态：启用；");

					// 调用验码模块--启用活动接口
					httpPostMap.put("operCode", "4");
					String returnJson = HttpUtil.doRestfulByHttpConnection(changeActivityUrl,
							JSON.toJSONString(httpPostMap));
					Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
					boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
					if (!isSuccess) {
						throw new BusinessException("向积分核销系统启用活动失败");
					}
					// 保存送信息到验码系统的日志
					optLogService.addOptLog("活动Id:" + activity.getId(), "",
							"积分核销接口启用活动-----" + changeDescribe.toString());
				}
			} else {
				// 活动开始时间或者结束时间修改
				httpPostMap.put("startTime", DateUtil.dateToStr(activity.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 开始时间
				httpPostMap.put("endTime", DateUtil.dateToStr(activity.getEndTime(), "yyyy-MM-dd HH:mm:ss"));// 结束时间
				changeDescribe.append("开始时间：" + httpPostMap.get("startTime") + ";");
				changeDescribe.append("结束时间：" + httpPostMap.get("endTime") + ";");

				// 活动指定日期修改，该处临时这样判断
				httpPostMap.put("selectDate", activity.getSelectDate());// 指定日期
				changeDescribe.append("指定日期：" + activity.getSelectDate() + ";");

				// 活动积分修改
				httpPostMap.put("integral", activity.getIntegral().toString());
				changeDescribe.append("积分：" + activity.getIntegral() + ";");

				// 活动最大类型修改
				httpPostMap.put("maxType", "1");
				changeDescribe.append("最大类型：" + activity.getMaxType() + ";");

				// 活动使用次数
				httpPostMap.put("maxNumber", "1");
				changeDescribe.append("最大次数：" + activity.getMaxNumber() + ";");

				// 活动状态 活动修改时的状态与原活动一致
				httpPostMap.put("status", oldActivity.getStatus());
				changeDescribe.append("状态：" + oldActivity.getStatus() + ";");

				// 外部编码
				httpPostMap.put("productCode", activity.getOutCode().toString());
				changeDescribe.append("外部编码：" + activity.getOutCode() + ";");

				// BIN码
				httpPostMap.put("bins", activity.getBins());
				changeDescribe.append("BIN码：" + activity.getBins() + ";");

				// 积分通道
				httpPostMap.put("channel", activity.getChannel());
				changeDescribe.append("积分通道：" + activity.getChannel() + ";");

				// 兑换类型
				httpPostMap.put("exchangeType", activity.getExchangeType());
				changeDescribe.append("兑换类型：" + activity.getExchangeType() + ";");

				// 将活动的门店关联信息添加到修改描述中和调用接口的请求参数中
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("activityId", activity.getId());
				List<Store> storeList = storeMapper.selectAllofActivity(activity.getId());
				changeDescribe.append("门店信息：[");
				for (Store as : storeList) {
					changeDescribe.append("{门店名称：" + as.getName() + ";");
					changeDescribe.append("{门店编码：" + as.getCode() + ";");
					changeDescribe.append("门店id：" + as.getId() + ";}");
					// 机具
					Terminal terminal = new Terminal();
					terminal.setStoreId(as.getId());
					List<Terminal> terminals = terminalMapper.selectAllEncode(terminal);
					as.setTerminals(terminals);
				}
				changeDescribe.append("]");
				httpPostMap.put("storeList", storeList);

				// 将修改信息发送至验码系统
				httpPostMap.put("operCode", "2");
				// System.out.println(JSON.toJSONString(httpPostMap));
				String returnJson = HttpUtil.doRestfulByHttpConnection(changeActivityUrl,
						JSON.toJSONString(httpPostMap));
				Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
				boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
				if (!isSuccess) {
					throw new BusinessException("向积分核销系统同步信息失败");
				}
				// 保存送信息到验码系统的日志
				optLogService.addOptLog("活动Id:" + activity.getId(), "",
						"积分核销接口修改活动信息-----" + changeDescribe.toString());
			}
		}
		// 验码同步
		else if ("02".equals(activity.getType())) {
			// 获取验码系统中-修改活动，启动活动，停止活动的ＵＲＬ，并定义一个请求这些地址时，所需要的参数Map,将活动Id和活动名称都放在这个Map中
			String changeActivityUrl = PropertiesUtil.getValue("verifyCode.changeActivity");
			String stopActivityUrl = PropertiesUtil.getValue("verifyCode.stopActivity");
			String startActivityUrl = PropertiesUtil.getValue("verifyCode.startActivity");
			Map<String, Object> httpPostMap = new HashMap<String, Object>();
			httpPostMap.put("activityId", activity.getId());// 活动Id
			httpPostMap.put("activityName", activity.getName());// 活动Id
			// 定义一个修改内容描述
			StringBuffer changeDescribe = new StringBuffer();
			// 活动启用或停止
			if (oldActivity.getStatus() != null && activity.getStatus() != null
					&& !oldActivity.getStatus().equals(activity.getStatus())) {
				if ("02".equals(activity.getStatus())) {
					// 验码模块-活动停止
					httpPostMap.put("status", "02");
					changeDescribe.append("活动状态：停止；");

					// 调用验码模块-停止活动接口
					httpPostMap.put("operType", "CANCEL");
					String returnJson = HttpUtil.doRestfulByHttpConnection(stopActivityUrl,
							JSON.toJSONString(httpPostMap));
					Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
					boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
					if (!isSuccess) {
						throw new BusinessException("向验码系统停止活动失败");
					}
					// 保存送信息到验码系统的日志
					optLogService.addOptLog("活动Id:" + activity.getId(), "",
							"验码接口停止活动-----" + changeDescribe.toString());
				} else if ("01".equals(activity.getStatus())) {
					// 验码模块-活动启用
					httpPostMap.put("status", "01");
					changeDescribe.append("活动状态：启用；");

					// 调用验码模块--启用活动接口
					httpPostMap.put("operType", "START_USING");
					String returnJson = HttpUtil.doRestfulByHttpConnection(startActivityUrl,
							JSON.toJSONString(httpPostMap));
					Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
					boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
					if (!isSuccess) {
						throw new BusinessException("向验码系统启用活动失败");
					}
					// 保存送信息到验码系统的日志
					optLogService.addOptLog("活动Id:" + activity.getId(), "",
							"验码接口启用活动-----" + changeDescribe.toString());
				}
			} else {
				// 活动指定日期修改，该处临时这样判断
				if (oldActivity.getSelectDate() != null && activity.getSelectDate() != null
						&& !oldActivity.getSelectDate().equals(activity.getSelectDate())) {
					httpPostMap.put("selectDate", activity.getSelectDate());// 指定日期
					changeDescribe.append("指定日期：" + activity.getSelectDate() + ";");
				}

				// 活动发行商修改
				if (oldActivity.getEnterpriseId() != null && activity.getEnterpriseId() != null
						&& !oldActivity.getEnterpriseId().equals(activity.getEnterpriseId())) {
					httpPostMap.put("enterpriseId", activity.getEnterpriseId());
					changeDescribe.append("活动发行商：" + activity.getEnterpriseId() + ";");
				}

				// 活动合同修改
				if (oldActivity.getContractId() != null && activity.getContractId() != null
						&& !oldActivity.getContractId().equals(activity.getContractId())) {
					httpPostMap.put("contractId", activity.getContractId());
					changeDescribe.append("合同：" + activity.getContractId() + ";");
				}

				// 活动价格修改
				if (oldActivity.getAmount() != null && activity.getAmount() != null
						&& !oldActivity.getAmount().equals(activity.getAmount())) {
					httpPostMap.put("amount", activity.getAmount().toString());
					changeDescribe.append("价格：" + activity.getAmount() + ";");
				}

				// 活动使用次数
				if (oldActivity.getMaxNumber() != null && activity.getMaxNumber() != null
						&& !oldActivity.getMaxNumber().equals(activity.getMaxNumber())) {
					httpPostMap.put("maxNumber", activity.getMaxNumber().toString());
					changeDescribe.append("使用次数：" + activity.getMaxNumber() + ";");
				}

				// 外部编码
				if (oldActivity.getOutCode() != null && activity.getOutCode() != null
						&& !oldActivity.getOutCode().equals(activity.getOutCode())) {
					httpPostMap.put("outCode", activity.getOutCode().toString());
					changeDescribe.append("外部编码：" + activity.getOutCode() + ";");
				}
				// 活动编码
				if (oldActivity.getCode() != null && activity.getCode() != null
						&& !oldActivity.getCode().equals(activity.getCode())) {
					httpPostMap.put("activityCode", activity.getCode().toString());
					changeDescribe.append("活动编码：" + activity.getCode() + ";");
				}
				// 企业编码
				Enterprise param = new Enterprise();
				param.setId(activity.getEnterpriseId());
				Enterprise enterprise = enterpriseMapper.selectOne(param);
				httpPostMap.put("enterpriseCode", enterprise.getCode());
				changeDescribe.append("企业编码：" + enterprise.getCode() + ";");

				// 将活动的门店关联信息添加到修改描述中和调用接口的请求参数中
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("activityId", activity.getId());
				List<ActivityStore> storeList = activityStoreMapper.queryActivityStoreList(queryMap);
				httpPostMap.put("actActivityStores", storeList);
				changeDescribe.append("门店信息：[");
				for (ActivityStore as : storeList) {
					changeDescribe.append("{门店名称：" + as.getStoreName() + ";");
					changeDescribe.append("{门店编码：" + as.getStoreCode() + ";");
					changeDescribe.append("门店id：" + as.getStoreId() + ";}");
				}
				changeDescribe.append("]");

				// 将修改信息发送至验码系统
				httpPostMap.put("operType", "UPDATE");
				System.out.println(JSON.toJSONString(httpPostMap));
				String returnJson = HttpUtil.doRestfulByHttpConnection(changeActivityUrl,
						JSON.toJSONString(httpPostMap));
				Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
				boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
				if (!isSuccess) {
					throw new BusinessException("向验码系统同步信息失败");
				}
				// 保存送信息到验码系统的日志
				optLogService.addOptLog("活动Id:" + activity.getId(), "", "验码接口修改活动信息-----" + changeDescribe.toString());
			}
		}
	}

	/**
	 * 导入联系人，通过Excel数据 activityId 活动Id orderId 导入联系人批次Id remark 导入联系人批次备注
	 * InputStream in 导入的流
	 * 
	 * @return 影响行数
	 */
	@Transactional()
	public Map<String, Object> importCustomor(String activityId, String orderId, String remark, InputStream in) {
		// 定义一个返回Map
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 解析excel流,并生成list
		List<List<Object>> excelList = ExcelUtil.excel2List(in);

		// 获取标题和字段在excel中的对应关系，并存放在一个Map中
		List<Object> titleList = excelList.get(0);
		Map<String, Integer> titleColumnMap = new HashMap<String, Integer>();
		for (int i = 0; i < titleList.size(); i++) {
			String titleStr = String.valueOf(titleList.get(i));
			if ("手机号".equals(titleStr)) {
				titleColumnMap.put("mobile", i);
			} else if ("姓名".equals(titleStr)) {
				titleColumnMap.put("name", i);
			}
		}
		// 遍历每一条数据，根据上一段代码中获取到的对应关系，获取手机号，并验证是否为正确的手机格式，如果是非法格式，则返回错误
		for (int i = 1; i < excelList.size(); i++) {
			List<Object> oneRowList = excelList.get(i);
			String mobile = String.valueOf(oneRowList.get(titleColumnMap.get("mobile")));
			// 如果不是正确的手机格式，则返回错误信息
			if (!ValidatorUtil.isMobile(mobile)) {
				resultMap.put("success", false);
				resultMap.put("msg", "第" + (i + 1) + "行发生错误，错误原因：不是正确的手机号格式");
				return resultMap;
			}
		}

		/*
		 * 遍历每一条数据，执行批量添加方法
		 */
		// 获取一次添加数量
		Integer batchInsertCount = Integer.valueOf(PropertiesUtil.getValue("activity.batchInsertCount"));
		Integer successNumber = 0;
		// 定义一个空的列表，用于临时存放联系人数据，当列表数量达到一定数量时，就批量添加数据。
		List<BatchCode> batchOrderCodeList = new ArrayList<BatchCode>();
		// 遍历列表，将列表中的数据拼装后放入到临时列表中
		for (int i = 1; i < excelList.size(); i++) {
			List<Object> oneRowList = excelList.get(i);
			String mobile = String.valueOf(oneRowList.get(titleColumnMap.get("mobile")));
			String name = String.valueOf(oneRowList.get(titleColumnMap.get("name")));

			BatchCode oc = new BatchCode();
			oc.setCustomerName(name);
			oc.setCustomerMobile(mobile);
			oc.setId(IDUtil.getID());
			oc.setBatchId(orderId);
			oc.setStatus("01");// 01：已导入，未提码

			batchOrderCodeList.add(oc);

			// 当该数量达到一定的数量时，批量添加数据，批量添加完成后，将临时存储数据的列表置空
			if (i != 0 && i % batchInsertCount == 0) {
				// 批量添加批次码数据
				successNumber += batchCodeMapper.batchAddBatchCode(batchOrderCodeList);
				batchOrderCodeList = new ArrayList<BatchCode>();
			}
		}
		// 添加剩余的批次码数据
		successNumber += batchCodeMapper.batchAddBatchCode(batchOrderCodeList);

		// 保存导入联系人批次数据
		Batch od = new Batch();
		od.setId(orderId);
		od.setActivityId(activityId);
		od.setBatch("");
		od.setImportNumber(successNumber);// 导入联系人数量
		od.setImportSuccessNumber(successNumber);// 导入成功数量
		od.setRemark(remark);// 备注
		od.setStatus("01");// 导入状态
		od.setCreatetime(new Date());
		batchMapper.addBatch(od);

		// 保存日志
		StringBuffer optInfo = new StringBuffer();
		optInfo.append("批次：" + od.getBatch() + ";导入数量:" + od.getImportNumber() + ";导入成功数量:"
				+ od.getImportSuccessNumber() + ";备注：" + od.getRemark());
		optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + orderId, "", "导入联系人-----" + optInfo.toString());

		// 返回数据
		resultMap.put("success", true);
		resultMap.put("msg", "上传成功" + successNumber + "条");
		resultMap.put("successNumber", successNumber);// 成功数
		resultMap.put("importNumber", successNumber);// 导入数量
		return resultMap;
	}

	/**
	 * 码库接口-库存查询
	 * 
	 * @param activityId,活动Id
	 * @param orderId,发码批次Id
	 * @return
	 */
	private Map<String, Object> getCodeCountStock(String activityId, String orderId) {
		// 获取库存查询URL
		String countStockUrl = PropertiesUtil.getValue("code.countStock");
		// 拼凑库存查询参数Map
		Map<String, Object> httpPostMap1 = new HashMap<String, Object>();
		httpPostMap1.put("rule_code", "b");// 编码规则，待定？
		httpPostMap1.put("system_code", "activity");// 系统编码，用来标示查询的来源系统，或者来源设备
		// 使用Http工具进行连接请求，并解析返回值
		String stockJson = HttpUtil.doPostByHttpConnection(countStockUrl, httpPostMap1);
		Map<String, Object> stockMap = JSON.parseObject(stockJson, Map.class);
		logger.debug("库存查询，请求参数rule_code：" + httpPostMap1.get("rule_code") + ";system_code："
				+ httpPostMap1.get("system_code") + "返回结果：" + stockJson);

		// 保存码库查询日志
		StringBuffer optInfo = new StringBuffer();
		optInfo.append("请求参数rule_code：" + httpPostMap1.get("rule_code") + ";");
		optInfo.append("请求参数system_code：" + httpPostMap1.get("system_code") + ";");
		optInfo.append("请求回应is_success：" + stockMap.get("is_success") + ";");
		boolean isSuccess = Boolean.parseBoolean(String.valueOf(stockMap.get("is_success")));
		optInfo.append("请求回应is_success：" + isSuccess + ";");
		if (isSuccess) {
			optInfo.append("请求回应number：" + stockMap.get("number") + ";");
		} else {
			optInfo.append("请求回应error_msg：" + stockMap.get("error_msg") + ";");
		}
		optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + orderId, "", "码库接口库存查询-----" + optInfo.toString());
		return stockMap;
	}

	/**
	 * 码库接口-提码
	 * 
	 * @param activityId,活动Id
	 * @param orderId,发码批次Id
	 * @param number
	 *            提码数量
	 * @return
	 */
	private Map<String, Object> getCodeCodes(String activityId, String orderId, Integer number) {
		// 定义返回对象
		Map<String, Object> codesMap = new HashMap<String, Object>();
		// 获取提码URL
		String getCodesUrl = PropertiesUtil.getValue("code.getCodes");
		// 拼凑库存查询参数Map，定义一个总的数据列表
		Map<String, Object> httpPostMap2 = new HashMap<String, Object>();
		List<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();
		httpPostMap2.put("rule_code", "b");// 编码规则，待定？
		httpPostMap2.put("system_code", "activity");// 系统编码，用来标示查询的来源系统，或者来源设备
		/*
		 * 循环提码，使用Http工具进行连接请求，循环每5000条取一次，并解析返回值
		 */
		int allNumber = number;
		int oneNumber = Integer.valueOf(PropertiesUtil.getValue("activity.oneGetCodeCount"));// 获取一次取码的数量
		oneNumber = oneNumber > number ? number : oneNumber;
		while (allNumber >= oneNumber) {
			// 设置请求数量
			httpPostMap2.put("number", String.valueOf(oneNumber));// 请求数量
			// 使用Http工具进行连接请求，并解析返回值
			String oneCodesJson = HttpUtil.doPostByHttpConnection(getCodesUrl, httpPostMap2);// 获取码信息
			Map<String, Object> oneCodesMap = JSON.parseObject(oneCodesJson, Map.class);
			boolean oneIsSuccess = Boolean.parseBoolean(String.valueOf(oneCodesMap.get("is_success")));
			logger.debug("取码，请求参数rule_code：" + httpPostMap2.get("rule_code") + ";system_code："
					+ httpPostMap2.get("system_code") + ";number：" + oneNumber + ";返回结果：" + oneCodesJson);
			// 判断是否请求成功，如果请求成功，则将数据列表添加到总的数据列表
			if (oneIsSuccess) {
				List<Map<String, Object>> oneCodeList = (List<Map<String, Object>>) oneCodesMap.get("data");
				codeList.addAll(oneCodeList);
			} else {
				// 如果请求失败，则抛出异常，回滚事物
				throw new BusinessException("提码失败！码库提码出错");
			}
			// 该次提码后，计算剩余的数量
			allNumber = allNumber - oneNumber;
		}
		// /*
		// * 最后一次提码，使用Http工具进行连接请求
		// */
		// // 设置请求数量
		// httpPostMap2.put("number", String.valueOf(allNumber));// 请求数量
		// // 使用Http工具进行连接请求，并解析返回值
		// String lastCodesJson = HttpUtil.doPostByHttpConnection(getCodesUrl,
		// httpPostMap2);// 获取码信息
		// Map<String, Object> lastCodesMap = JSON.parseObject(lastCodesJson,
		// Map.class);
		// logger.debug("取码，请求参数rule_code：" + httpPostMap2.get("rule_code") +
		// ";system_code："
		// + httpPostMap2.get("system_code") + ";number：" + allNumber + ";返回结果："
		// + lastCodesJson);
		// boolean lastIsSuccess =
		// Boolean.parseBoolean(String.valueOf(lastCodesMap.get("is_success")));
		// // 判断是否请求成功，如果请求成功，则将数据列表添加到总的数据列表
		// if (lastIsSuccess) {
		// List<Map<String, Object>> lastCodeList = (List<Map<String, Object>>)
		// lastCodesMap.get("data");
		// codeList.addAll(lastCodeList);
		// } else {
		// // 如果请求失败，则抛出异常，回滚事物
		// throw new BusinessException("提码失败！码库提码出错");
		// }
		// 前面的代码没有出现错误，将要返回的Map中isSuccess设置为true,将总的列表存放在Map中
		codesMap.put("is_success", true);
		codesMap.put("data", codeList);
		// 保存码库提码的日志
		StringBuffer optInfo = new StringBuffer();
		optInfo.append("请求参数rule_code：" + httpPostMap2.get("rule_code") + ";");
		optInfo.append("请求参数system_code：" + httpPostMap2.get("system_code") + ";");
		optInfo.append("请求参数number：" + number + ";");
		optInfo.append("请求回应is_success：true;");
		optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + orderId, "", "码库接口提码-----" + optInfo.toString());
		// 返回数据
		return codesMap;
	}

	/**
	 * 验码接口-送码
	 * 
	 * @param act,活动对象
	 * @param order,活动发码批次对象
	 * @param ocList,活动发码批次下所有的码对象列表
	 * @param codeList
	 *            提码获取到的码列表
	 * @return
	 */
	private Map<String, Object> carryVerifyCode(Activity act, Batch order, List<BatchCode> ocList,
			List<Map<String, Object>> codeList) {
		// 定义发码批次对象，设置上一些基本信息
		BatchVO orderVO = new BatchVO();
		orderVO.setActivityId(act.getId());// 活动Id
		orderVO.setActivityName(act.getName());// 活动名称
		orderVO.setOperType("IMPORT");// 操作编码
		orderVO.setTicketTitle(act.getReceiptTitle());// 活动小票标题
		orderVO.setTicketContent(act.getReceiptPrint());// 活动小票内容
		orderVO.setPosSuccessMsg(act.getPosSuccessMsg());// pos验证成功短信
		orderVO.setSuccessSmsMsg(act.getSuccessSmsMsg());// 验证成功提示
		// 获取并设置关联门店数据
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("activityId", act.getId());
		List<ActivityStore> storeList = activityStoreMapper.queryActivityStoreList(queryMap);
		orderVO.setActActivityStores(storeList);
		// 解析并设置码数据
		List<BatchCodeVO> carryList = new ArrayList<BatchCodeVO>();// 定义送码的列表
		// 定义json数据
		// JSONObject carryJson = new JSONObject();
		// carryJson.put("activityId", act.getId());
		// carryJson.put("activityName", act.getName());
		// carryJson.put("operType", "IMPORT");
		// JSONArray carryJsonArray = new JSONArray();
		// 遍历活动发码批次下所有的码对象列表
		for (int i = 0; i < ocList.size(); i++) {
			BatchCode oc = ocList.get(i);
			Map<String, Object> ocMap = codeList.get(i);

			// Map<String,Object> codeMap = new HashMap<String,Object>();
			// codeMap.put("", ocMap.get("id"));
			// codeMap.put("code", ocMap.get("code"));
			// codeMap.put("activityId", act.getId());
			// codeMap.put("activityName", act.getName());
			// codeMap.put("amount", act.getAmount());
			// codeMap.put("codeSerialNumber", ocMap.get("id"));
			// codeMap.put("createTime", DateUtil.getLongCurDate());
			// codeMap.put("customMobile", oc.getCustomerMobile());
			// codeMap.put("issueEnterpriseId", act.getEnterpriseId());
			// codeMap.put("issueEnterpriseName", "");
			// codeMap.put("contractId",act.getContractId());
			// codeMap.put("maxNum", act.getMaxNumber());
			// codeMap.put("orderId", order.getId());
			// codeMap.put("releaseTime", DateUtil.dateToStr(new
			// Date(),"yyyy-MM-dd HH:mm:ss"));
			// codeMap.put("startTime",
			// DateUtil.dateToStr(act.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
			// codeMap.put("endTime",
			// DateUtil.dateToStr(act.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
			// codeMap.put("selectDate", act.getSelectDate());

			// 组件新的活动批次码对象
			BatchCodeVO codeVO = new BatchCodeVO();
			codeVO.setCodeSerialNumber(String.valueOf(ocMap.get("id")));// 码库提码的id
			codeVO.setCode(String.valueOf(ocMap.get("code")));// 码库提码的码值
			codeVO.setActivityId(act.getId());// 活动Id
			codeVO.setActivityName(act.getName());// 活动名称
			codeVO.setAmount(String.valueOf(act.getAmount()));// 活动价格
			codeVO.setCreateTime(DateUtil.getLongCurDate());// 创建时间，也就是当前时间
			codeVO.setCustomMobile(oc.getCustomerMobile());// 联系人手机号
			codeVO.setCustomName(oc.getCustomerName());// 联系人名称
			codeVO.setIssueEnterpriseId(act.getEnterpriseId());// 所属活动发行方Id
			codeVO.setIssueEnterpriseName(act.getEnterpriseName());// 所属活动发行方名称
			codeVO.setContractId(act.getContractId());// 合同
			codeVO.setMaxNum(String.valueOf(act.getMaxNumber()));// 最大次数
			codeVO.setOrderId(order.getId());// 发码批次Id
			codeVO.setReleaseTime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));// 发布时间，当然时间
			codeVO.setStartTime(DateUtil.dateToStr(act.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 活动开始时间
			codeVO.setEndTime(DateUtil.dateToStr(act.getEndTime(), "yyyy-MM-dd HH:mm:ss"));// 活动结束时间
			codeVO.setSelectDate(act.getSelectDate());// 活动指定日期

			// OrderCodeVO codeVO = new OrderCodeVO();
			// codeVO.setCodeSerialNumber(IDUtil.getID());
			// codeVO.setCode(String.valueOf("12233243"));
			// codeVO.setActivityId("sasdasdasdasdfd");
			// codeVO.setActivityName("sasdasdasdasdfd");
			// codeVO.setAmount(String.valueOf(20));
			// codeVO.setCreateTime(DateUtil.getLongCurDate());
			// codeVO.setCustomMobile("15610109082");
			// codeVO.setCustomName("李某");
			// codeVO.setIssueEnterpriseId("dasdasfsfsdfds");
			// codeVO.setIssueEnterpriseName("");
			// codeVO.setContractId("dasdasfsfsdfds");
			// codeVO.setMaxNum(String.valueOf(45));
			// codeVO.setOrderId("dasdasfsfsdfds");
			// codeVO.setReleaseTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd
			// HH:mm:ss"));
			// codeVO.setStartTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd
			// HH:mm:ss"));
			// codeVO.setEndTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd
			// HH:mm:ss"));
			// codeVO.setSelectDate("234");

			// 将新组建的码对象添加到列表中
			carryList.add(codeVO);

			// 定义json数据
			// JSONObject codeJson = new JSONObject();
			// codeJson.put("code", ocMap.get("code"));
			// codeJson.put("activityId", act.getId());
			// codeJson.put("activityName", act.getName());
			// codeJson.put("amount", act.getAmount());
			// codeJson.put("codeSerialNumber", ocMap.get("id"));
			// codeJson.put("createTime", DateUtil.getLongCurDate());
			// codeJson.put("customMobile", oc.getCustomerMobile());
			// codeJson.put("issueEnterpriseId", act.getEnterpriseId());
			// codeJson.put("issueEnterpriseName", "");
			// codeJson.put("contractId",act.getContractId());
			// codeJson.put("maxNum", act.getMaxNumber());
			// codeJson.put("orderId", order.getId());
			// codeJson.put("releaseTime", DateUtil.dateToStr(new
			// Date(),"yyyy-MM-dd HH:mm:ss"));
			// codeJson.put("startTime",
			// DateUtil.dateToStr(act.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
			// codeJson.put("endTime",
			// DateUtil.dateToStr(act.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
			// codeJson.put("selectDate", act.getSelectDate());
			// carryJsonArray.add(codeJson);
		}
		// 将列表添加到码批次对象中
		orderVO.setActivityCodeList(carryList);
		// carryJson.put("activityCodeList", carryJsonArray);
		// 获取验码系统--送码ＵＲＬ
		String carryCodeUrl = PropertiesUtil.getValue("verifyCode.carryCode");
		// 将要送码的数据JSON化
		String carryMapJson = JSON.toJSONString(orderVO);
		// String carryMapJson = JacksonJsonMapper.objectToJson(carryMap);
		// String carryMapJson= JSON.toJSONString(orderVO);
		// String carryMapJson = carryJson.toJSONString();
		logger.debug("送码，请求参数：" + carryMapJson);
		// 调用Http工具，执行送码操作，并解析返回值
		String carryCodeJson = HttpUtil.doRestfulByHttpConnection(carryCodeUrl, carryMapJson);// 送码
		Map<String, Object> carryCodeMap = JSON.parseObject(carryCodeJson, Map.class);
		logger.debug("送码，返回结果：" + carryCodeJson);
		// 保存验码模块送码的日志
		StringBuffer optInfo = new StringBuffer();
		optInfo.append("请求参数码数量：" + carryList.size() + ";");
		boolean isSuccess = Boolean.parseBoolean(String.valueOf(carryCodeMap.get("success")));
		optInfo.append("请求回应success：" + isSuccess + ";");
		optLogService.addOptLog("活动Id:" + act.getId() + ",批次Id:" + order.getId(), "",
				"验码接口送码-----" + optInfo.toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 将返回对象的success设置为true,并返回数据对象
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 导出发送短信Excel,包含提码、送码、生成码excel三步
	 * 
	 * @param orderId,发码批次Id，导入联系人批次Id
	 * @param session,session对象，用于传递会话中的session对象，将发码过程中的进度值保存到session对象中。
	 * @describe 方法体中诸如logger.info("内容")的代码为添加日志，诸如session.setAttribute("xxx",20
	 *           )的代码是向session会话中添加进度
	 * @return
	 */
	@Transactional()
	public Map<String, Object> createSendSmsExcel(String orderId, HttpSession session) {
		// 定义一个返回数据的Map和一个数据列表
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();
		// 获取该条导入联系人批次信息
		Batch od = batchMapper.getBatchById(orderId);
		Activity activity = activityMapper.getActivityById(od.getActivityId());
		/*
		 * 码库接口-库存查询
		 */
		logger.info("码库接口----库存查询开始");
		// 调用库存查询方法，获取库存查询返回的数据
		Map<String, Object> stockMap = getCodeCountStock(activity.getId(), orderId);
		boolean isCountStockSuccess = Boolean.parseBoolean(String.valueOf(stockMap.get("is_success")));
		logger.info("码库接口----库存查询结束");
		session.setAttribute("sendCodeProcess", 10);
		// 判断此次库存查询请求是否成功
		if (isCountStockSuccess) {
			// 如果此次库存查询成功，则再获取库存数量
			Integer number = Integer.valueOf(String.valueOf(stockMap.get("number")));
			// 如果库存不足，则返回错误信息
			if (number < od.getImportNumber()) {
				resultMap.put("success", false);
				resultMap.put("msg", "提码失败！码库现有的码数量不足，只有" + number + "条码。");
				return resultMap;
			}
			// 如果库存足够，则进行提码操作
			else {
				/*
				 * 码库接口-提码
				 */
				logger.info("码库接口----提码开始-----提码数量：" + od.getImportNumber());
				// 调用提码方法，进行提码操作
				Map<String, Object> codesMap = getCodeCodes(activity.getId(), orderId, od.getImportNumber());
				boolean pickIsSuccess = Boolean.parseBoolean(String.valueOf(codesMap.get("is_success")));
				logger.info("码库接口----提码结束-----提码数量：" + od.getImportNumber());
				session.setAttribute("sendCodeProcess", 30);

				// 判断提码请求是否成功，如果请求成功则进行送码操作
				if (pickIsSuccess) {
					/*
					 * 验码接口--送码
					 */
					// 从提码对象中拿出码列表，解析这些码，保存到活动批次码表中，并且组织送码数据格式
					List<Map<String, Object>> codeList = (List<Map<String, Object>>) codesMap.get("data");
					logger.info("送码----获取所有批次码开始-----码数量：" + od.getImportNumber());
					// 查询已导入的联系人列表
					Map<String, Object> getAllMap = new HashMap<String, Object>();
					getAllMap.put("orderId", od.getId());
					List<BatchCode> ocList = batchCodeMapper.queryBatchCodeList(getAllMap);
					logger.info("送码----获取所有批次码结束-----码数量：" + od.getImportNumber());
					logger.info("送码----送码开始-----码数量：" + od.getImportNumber());
					// 调用送码方法，进行送码操作
					Map<String, Object> carryCodeMap = carryVerifyCode(activity, od, ocList, codeList);
					boolean carryIsSuccess = Boolean.parseBoolean(String.valueOf(carryCodeMap.get("success")));
					logger.info("送码----送码结束-----码数量：" + od.getImportNumber());
					session.setAttribute("sendCodeProcess", 50);

					// 判断送码请求是否成功，如果请求成功，则进行发码操作，并更新该批次下所有码的状态
					if (carryIsSuccess) {
						/*
						 * 组装发码短信数据
						 */
						logger.info("导出码----发码与更新批次码状态开始-----码数量：" + ocList.size());
						// 删除该批次所有数据
						Map<String, Object> delAllMap = new HashMap<String, Object>();
						delAllMap.put("orderId", od.getId());
						batchCodeMapper.deleteBatchCode(delAllMap);

						// 获取短信模板、价格，活动截止日期等信息
						// String noticeSmsMsg =
						// activity.getNoticeSmsMsg();//短信模版内容

						// 获取一次批量添加数据数量
						Integer batchInsertCount = Integer
								.valueOf(PropertiesUtil.getValue("activity.batchInsertCount"));
						// 定义一个空列表，用来临时存放组装后的数据，当该列表达到一定数量后，执行批量添加操作，然后将该列表置空
						List<BatchCode> batchOrderCodeList = new ArrayList<BatchCode>();
						// 遍历已导入的联系人列表
						for (int i = 0; i < ocList.size(); i++) {
							// 获取单条已导入联系人
							BatchCode oc = ocList.get(i);
							// 获取单条码信息
							Map<String, Object> ocMap = codeList.get(i);
							// 获取码和码id
							// String codeId = String.valueOf(ocMap.get("id"));
							// String code = String.valueOf(ocMap.get("code"));

							// 创建新的数据Map,将手机号和联系人名称都放入这个Map中
							Map<String, Object> recordMap = new HashMap<String, Object>();
							// 设置短信手机号
							recordMap.put("mobile", oc.getCustomerMobile());
							recordMap.put("name", oc.getCustomerName());

							// 拼凑短信内容，并设置短信内容
							// Map<String,Object> mappingMap = new
							// HashMap<String,Object>();
							// mappingMap.put("activityName",activity.getName());
							// mappingMap.put("activityCode",activity.getCode());
							// mappingMap.put("outCode",activity.getOutCode());
							// mappingMap.put("enterpriseCode",activity.getEnterpriseId());
							// mappingMap.put("enterpriseName",activity.getEnterpriseName());
							// mappingMap.put("maxNum",activity.getMaxNumber());
							// mappingMap.put("startTime",DateUtil.dateToStr(activity.getStartTime(),"yyyy-MM-dd
							// HH:mm:ss"));
							// mappingMap.put("endTime",DateUtil.dateToStr(activity.getEndTime(),"yyyy-MM-dd
							// HH:mm:ss"));
							// mappingMap.put("selectDate",activity.getSelectDate());
							// mappingMap.put("codeSerialNumber",String.valueOf(ocMap.get("id")));
							// mappingMap.put("code",String.valueOf(ocMap.get("code")));
							// mappingMap.put("amount",activity.getAmount());
							// mappingMap.put("customMobile",oc.getCustomerMobile());
							// mappingMap.put("customName",oc.getCustomerName());
							//
							// String content =
							// TextUtil.strVarMapping(noticeSmsMsg,mappingMap);

							// 设置内容
							recordMap.put("content", "");
							// 在数据列表中添加该条心建的码Map
							recordList.add(recordMap);

							// 更新批次码表，添加码、码id等信息
							oc.setCodeId(String.valueOf(ocMap.get("id")));
							oc.setCode(String.valueOf(ocMap.get("code")));
							// 直接修改状态为已送码，未发码
							oc.setStatus("04");

							// 添加拼凑后的数据
							batchOrderCodeList.add(oc);
							// 当临时列表数量达到一定数值后，执行批量插入操作，然后置空该临时列表
							if (i != 0 && i % batchInsertCount == 0) {
								// 批量添加批次码数据
								batchCodeMapper.batchAddBatchCode(batchOrderCodeList);
								batchOrderCodeList = new ArrayList<BatchCode>();
							}
						}
						// 添加剩余的批次码数据
						batchCodeMapper.batchAddBatchCode(batchOrderCodeList);
						logger.info("导出码----发码与更新批次码状态结束-----码数量：" + ocList.size());
						session.setAttribute("sendCodeProcess", 95);

						// 更新发码批次的状态
						od.setStatus("04");
						batchMapper.updateBatch(od);

						// 记录创建短信Excel数据、保存发码等两个步骤的日志
						StringBuffer optInfo1 = new StringBuffer();
						optInfo1.append("发送数量：" + ocList.size() + ";");
						optLogService.addOptLog("活动Id:" + activity.getId() + ",批次Id:" + orderId, "",
								"导出短信并保存发码记录-----" + optInfo1.toString());
						session.setAttribute("sendCodeProcess", 100);
					} else {
						// 如果向验码模块送码请求失败的话，则抛出异常，回滚事物
						throw new BusinessException("验码失败！验码模块送码出错");
					}

				} else {
					// 如果向码库模块提码请求失败的话，则抛出异常，回滚事物
					throw new BusinessException("提码失败！码库提码出错");
				}
			}
		} else {
			// 如果向库存查询模块库存查询请求失败的话，则抛出异常，回滚事物
			throw new BusinessException("提码失败！码库库存查询出错");
		}
		// 返回数据
		resultMap.put("records", recordList);
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 删除活动,根据主键
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteActivityById(String id) {
		Integer affectCount = activityMapper.deleteActivityById(id);
		return affectCount;
	}

	/**
	 * 批量删除活动,根据主键集合
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteActivitysByIds(String ids) {
		Integer affectCount = 0;
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			affectCount += activityMapper.deleteActivityById(id);
		}
		return affectCount;
	}

	/**
	 * 删除活动,根据条件
	 * 
	 * @param activityMap
	 * @return
	 */
	public Integer deleteActivity(Map<String, Object> activityMap) {
		Integer affectCount = activityMapper.deleteActivity(activityMap);
		return affectCount;
	}

	@Override
	@Transactional
	public String extractedCode(String activityId, String batchName, int count) {
		if (null == activityId || activityId.isEmpty() || null == batchName || batchName.isEmpty()) {
			throw new BusinessException("参数不正确");
		}
		/* 生成批次 */
		Batch batch = new Batch();
		batch.setId(IDUtil.getID());
		batch.setActivityId(activityId);
		batch.setBatch(batchName);
		batch.setStatus("01");// 已提码
		batch.setCreatetime(new Date());
		batchMapper.addBatch(batch);

		// /*
		// * 码库接口-库存查询
		// */
		// logger.info("码库接口----库存查询开始");
		// // 调用库存查询方法，获取库存查询返回的数据
		// Map<String, Object> stockMap = getCodeCountStock(activityId,
		// batch.getId());
		// boolean isCountStockSuccess =
		// Boolean.parseBoolean(String.valueOf(stockMap.get("is_success")));
		// logger.info("码库接口----库存查询结束");
		// // 判断此次库存查询请求是否成功
		// if (isCountStockSuccess) {
		// // 如果此次库存查询成功，则再获取库存数量
		// Integer number =
		// Integer.valueOf(String.valueOf(stockMap.get("number")));
		// // 如果库存不足，则返回错误信息
		// if (number < count) {
		// throw new BusinessException("提码失败！码库现有的码数量不足，只有" + number + "条码。");
		// }
		// // 如果库存足够，则进行提码操作
		// else {
		/*
		 * 码库接口-提码
		 */
		logger.info("码库接口----提码开始-----提码数量：" + count);
		// 调用提码方法，进行提码操作
		Map<String, Object> codesMap = getCodeCodes(activityId, batch.getId(), count);
		boolean pickIsSuccess = Boolean.parseBoolean(String.valueOf(codesMap.get("is_success")));
		List<Map<String, Object>> codeList = (List<Map<String, Object>>) codesMap.get("data");
		logger.info("码库接口----提码结束-----提码数量：" + count);
		if (pickIsSuccess) {
			Activity activity = activityMapper.getActivityById(activityId);
			List<BatchCode> batchOrderCodeList = new ArrayList<>();
			// 遍历列表，将列表中的数据拼装后放入到临时列表中
			for (Map<String, Object> codeMap : codeList) {
				BatchCode oc = new BatchCode();
				oc.setId(IDUtil.getID());
				oc.setBatchId(batch.getId());
				oc.setCodeId(String.valueOf(codeMap.get("id")));
				oc.setCode(String.valueOf(codeMap.get("code")));
				oc.setEndTime(activity.getEndTime());
				oc.setStatus("01");// 01：已提码

				batchOrderCodeList.add(oc);
			}
			batchCodeMapper.batchAddBatchCode(batchOrderCodeList);
			/* 修改批次导出数量 */
			Batch updateBatch = new Batch();
			updateBatch.setId(batch.getId());
			updateBatch.setPickSuccessNumber(batchOrderCodeList.size());
			updateBatch.setPickFailNumber(0);
			batchMapper.updateBatch(updateBatch);
		}
		// }
		// }

		return batch.getId();
	}

	@Override
	public Integer updateStatus(Activity activity) {
		// 获取原活动对象
		Activity oldActivity = activityMapper.getActivityById(activity.getId());
		activity.setType(oldActivity.getType());
		// 更新活动
		Integer affectCount = activityMapper.updateActivity(activity);
		/*
		 * // * 同步接口 //
		 */
		synOthers(activity, oldActivity);

		// 修改活动日志
		optLogService.addOptLog(activity.getId(), "", "活动修改-----" + activity.toDescribeString());

		return affectCount;
	}

	@Override
	public Map<String, Object> getComb(String activityId) {
		Map<String, Object> map = new HashMap<>();
		if (activityId == null || activityId.equals("")) {
			map.put("ENTERPRISE", enterpriseMapper.selectAll(null));
			map.put("CONTRACT", contractMapper.selectAll(null));
		} else {
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getActivitiesForDevice(List<String> activityId, String snCode) {
		List<Map<String, Object>> activitys = activityMapper.selectForDevice(activityId);
		Map<String, Object> storeInfo = activityMapper.selectStoreInfoForDevice(snCode);
		if (null != activitys && null != storeInfo) {
			for (Map<String, Object> map : activitys) {
				map.put("sTime", DateUtil.dateToStr((Date) map.get("sTime"), "yyyy-MM-dd HH:mm:ss"));
				map.put("eTime", DateUtil.dateToStr((Date) map.get("eTime"), "yyyy-MM-dd HH:mm:ss"));
				map.putAll(storeInfo);
			}
		}
		return activitys;
	}
}
