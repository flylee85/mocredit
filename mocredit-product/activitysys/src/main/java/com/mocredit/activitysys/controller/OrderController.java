package com.mocredit.activitysys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 发码批次-控制类
 * @author lishoukun
 * @date 2015/07/13
 */
@Controller
@RequestMapping("/order/")
public class OrderController{

//	@Autowired
//	private OrderService orderService;
//
//	/**
//	 * 跳转至发码批次管理页面
//	 * @return model and view
//	 */
//	@RequestMapping("/order")
//	public ModelAndView order(){
//		ModelAndView mav = new ModelAndView("order/order");
//		return mav;
//	}
//	/**
//	 * 查询发码批次列表，根据条件
//	 * @param  reqMap 请求参数
//	 * @return json array string
//	 */
//	@RequestMapping("/queryOrderList")
//	@ResponseBody
//	public String queryOrderList(@RequestParam Map<String, Object> reqMap){
//		//定义返回页面的对象
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			/*
//			 * 执行Service类中的查询方法
//			 * 如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
//			 */
//			List<Order> orderList = orderService.queryOrderList(reqMap);
//			responseData.setData(orderList);
//		}catch(Exception e){
//			//如果抛出异常，则将返回页面的对象设置为false
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		//返回页面数据
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
//	/**
//	  * 查询发码批次分页数据，根据条件和分页参数
//	  * @param reqMap 请求参数
//	  * @param page 页数
//	  * @param rows 每页条数
//	  * @return json object string
//	  */
//	 @RequestMapping("/queryOrderPage")
//	 @ResponseBody
//	 public String queryOrderPage(@RequestParam Map<String, Object> reqMap,Integer page, Integer pageSize){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Page<Order> pageMap = orderService.queryOrderPage(reqMap, page, pageSize);
//			responseData.setData(pageMap);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	 }
//	/**
//	 * 获取一条发码批次,根据主键
//	 * @param id 主键
//	 * @return json obj string
//	 */
//	@RequestMapping("/getOrderById")
//	@ResponseBody
//	public String getOrderById(@RequestParam String id){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Order order = orderService.getOrderById(id);
//			responseData.setData(order);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
//	/**
//	 * 获取一条发码批次,根据条件
//	 * @param reqMap 请求参数
//	 * @return json obj string
//	 */
//	@RequestMapping("/getOrder")
//	@ResponseBody
//	public String getOrder(@RequestParam Map<String, Object> reqMap){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Order order = orderService.getOrder(reqMap);
//			responseData.setData(order);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
//	/**
//	 * 保存发码批次
//	 * @param order 保存的对象
//	 * @return json obj string
//	 */
//	@RequestMapping("/saveOrder")
//	@ResponseBody
//	public String saveOrder(Order order){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Integer affectCount = 0;//影响行数
//			if("".equals(order.getId())||order.getId()==null){
//	//		//在这里添加设置id操作代码
//	//		String id = IDUtil.getID();
//	//		order.setId(id);
//	//		//获取当前session中的用户对象，用赋值到对象中
//	//		User currentUser = this.getUser();
//	//		if(currentUser!=null){
//	//			String userId = currentUser.getId();
//	//			//在这里添加赋值操作代码
//	//			order.setCreator(userId);
//	//		}
//				affectCount = orderService.addOrder(order);
//			}else{
//				affectCount = orderService.updateOrder(order);
//			}
//			responseData.setData(affectCount);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
//
//	/**
//	 * 删除发码批次,根据主键
//	 * @param id 主键
//	 * @return json obj string
//	 */
//	@RequestMapping("/deleteOrderById")
//	@ResponseBody
//	public String deleteOrderById(@RequestParam String id){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Integer affectCount = orderService.deleteOrderById(id);
//			responseData.setData(affectCount);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
//
//	/**
//	 * 批量删除发码批次,根据主键集合
//	 * @param ids 主键集合
//	 * @return json obj string
//	 */
//	@RequestMapping("/deleteOrdersByIds")
//	@ResponseBody
//	public String deleteOrdersByIds(@RequestParam String ids){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Integer affectCount = orderService.deleteOrdersByIds(ids);
//			responseData.setData(affectCount);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
//
//	/**
//	 * 删除发码批次,根据条件
//	 * @param reqMap   请求参数
//	 * @return json obj string
//	 */
//	@RequestMapping("/deleteOrder")
//	@ResponseBody
//	public String deleteOrder(@RequestParam Map<String, Object> reqMap){
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			Integer affectCount = orderService.deleteOrder(reqMap);
//			responseData.setData(affectCount);
//		}catch(Exception e){
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		return JacksonJsonMapper.objectToJson(responseData);
//	}
}
