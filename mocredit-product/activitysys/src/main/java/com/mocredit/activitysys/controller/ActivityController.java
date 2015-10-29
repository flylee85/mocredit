package com.mocredit.activitysys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.Activity;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.IDUtil;
import com.mocredit.base.util.PropertiesUtil;

/**
 *
 * 活动-控制类
 * 活动管理的入口类，所有活动管理相关的后台入口方法，都是在此类中
 * @author lishoukun
 * @date 2015/07/08
 */
@RestController
@RequestMapping("/activitysys/")
public class ActivityController{
	//引入活动Service类
	@Autowired
	private ActivityService activityService;

	/**
	 * 跳转至活动管理页面
	 * @return model and view
	 */
	@RequestMapping("/index")
	public ModelAndView activity(){
		//定义页面对象，用来跳转指定页面，下面一句代码的意思是：跳转到Web容器中的index.jsp页面
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	/**
	 * 查询活动列表，根据条件
	 * @param  reqMap 请求参数，包含请求时会用到的所有参数，比如活动名称，活动编码等
	 * @return json array string
	 */
	@RequestMapping("/queryActivityList")
	@ResponseBody
	public String queryActivityList(@RequestParam Map<String, Object> reqMap){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			/*
			 * 执行Service类中的查询方法
			 * 如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			 */
			List<Activity> activityList = activityService.queryActivityList(reqMap);

			responseData.setData(activityList);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}
	/**
	  * 查询活动分页数据，根据条件和分页参数
	  * @param reqMap 请求参数
	  * @param draw  查询标示，返回数据时将该值原封不动返回
	  * @param start 开始数
	  * @param length 查询数
	  * @return json object string
	  */
	 @RequestMapping("/queryActivityPage")
	 @ResponseBody
	 public String queryActivityPage(@RequestParam Map<String, Object> reqMap,Integer draw, Integer start, Integer length){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		//简单计算页数，当前页数=开始条数/搜索条数+1
		if(start==null){
			start = 0;
		}
		if(length==null){
			length = 10;
		}
		int currentPage = start/length+1;
		try{
			//根据参数查询分页信息对象
			PageInfo<Activity> pageMap = activityService.queryActivityPage(reqMap, draw,currentPage, length);
			//重构新的分页对象，为适应前端分页插件
			Map<String,Object> newMap = new HashMap<String,Object>();
			newMap.put("draw", draw);//查询标示,原值返回
			newMap.put("recordsTotal", pageMap.getTotal());//总数量
			newMap.put("recordsFiltered", pageMap.getTotal());//过滤后的总数量，暂未用到
			newMap.put("data", pageMap.getList());//数据列表
			String resultStr = JSON.toJSONString(newMap);//将新的分页对象返回页面
			//返回页面数据
			return resultStr;
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	 }

	 /**
	  * 查询活动管理相关选择门店分页数据，根据条件和分页参数
	  * @param reqMap 请求参数
	  * @param draw  查询标示，返回数据时将该值原封不动返回
	  * @param start 开始数
	  * @param length 查询数
	  * @return json object string
	  */
	 @RequestMapping("/getSelectedStores")
	 public String getSelectedStores(@RequestParam Map<String, Object> reqMap){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			responseData.setData(activityService.querySelectStores(reqMap));
			String resultStr =JSON.toJSONString(responseData);
			return resultStr;
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	 }
	/**
	 * 获取一条活动,根据主键
	 * @param id 主键
	 * @return json obj string
	 */
	@RequestMapping("/getActivityById")
	@ResponseBody
	public String getActivityById(@RequestParam String id){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			/*
			 * 根据id获取活动对象
			 * 如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			 */
			Activity activity = activityService.getActivityById(id);
			responseData.setData(activity);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}
	/**
	 * 获取一条活动,根据条件
	 * @param reqMap 请求参数
	 * @return json obj string
	 */
	@RequestMapping("/getActivity")
	@ResponseBody
	public String getActivity(@RequestParam Map<String, Object> reqMap){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			/*
			 * 根据参数获取活动对象
			 * 如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			 */
			Activity activity = activityService.getActivity(reqMap);
			responseData.setData(activity);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}
	/**
	 * 保存活动
	 * @param activity 保存的对象
	 * @return json obj string
	 */
	@RequestMapping("/saveActivity")
	@ResponseBody
	public String saveActivity(@RequestBody String body){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			//将前端传递过来的字符串数据解析为活动对象
			Activity activity = JSON.parseObject(body, Activity.class);
			//定义影响行数为0
			Integer affectCount = 0;
			//如果活动对象中id不存在或者为空，则执行添加操作
			if("".equals(activity.getId())||activity.getId()==null){
				//生成一个32位的UUID,并添加活动对象
				String id = IDUtil.getID();
				activity.setId(id);
				activity.setMaxNumber(1);
				affectCount = activityService.addActivity(activity);
			}
			//如果活动对象中id存在且不为空，则执行更新操作
			else{
				//更新活动对象
				affectCount = activityService.updateActivity(activity);
			}
			//如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			responseData.setData(affectCount);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}
	/**
	 * 保存活动
	 * @param activity 保存的对象
	 * @return json obj string
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public String updateStatus(@RequestBody String body){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			//将前端传递过来的字符串数据解析为活动对象
			Activity activity = JSON.parseObject(body, Activity.class);
			//定义影响行数为0
			Integer affectCount = 0;
			//更新活动对象
			affectCount = activityService.updateStatus(activity);
			//如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			responseData.setData(affectCount);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}
	/**
	 * 导入联系人(客户)
	 * @param selectExcel，前端传递到后台的文件流对象，在form表单中，name必须是selectExcel
	 * @param activityId,活动id
	 * @param remark,导入联系人备注
	 * @throws IOException
	 */
	@RequestMapping("/importCustomer")
	@ResponseBody
	public String importCustomer(@RequestParam MultipartFile selectExcel,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取前端传递过来的活动id，和导入联系人备注
		String activityId = request.getParameter("activityId");
		String remark = request.getParameter("remark");

		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		/*
		 * 处理文件,处理excel数据
		 */
		if(selectExcel.getSize()>0){
			//如果文件大小大于0，说明文件上传成功
			//调用导入联系人方法
			Map<String,Object> operMap = activityService.importCustomor(activityId,IDUtil.getID(),remark,selectExcel.getInputStream());
			//获取导入联系人方法执行结果
			if(Boolean.parseBoolean(String.valueOf(operMap.get("success")))){
				//如果导入结果为true，则只需要将导入消息设置为data就可以，因为返回页面的对象中，默认为true。
				responseData.setData(operMap.get("msg"));
			}else{
				//如果导入结果为false，则需要将返回页面的对象中设置为false，并且需要将导入消息设置为data就可以。
				responseData.setSuccess(false);
				responseData.setData(operMap.get("msg"));
			}
		}else{
			//如果文件大小为0，则将返回页面的对象设置为false
			responseData.setSuccess(false);
			responseData.setData("请上传格式正确的文件！");
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}

	/**
	 * 导入联系人Excel文件
	 * @param selectExcel，前端传递到后台的文件流对象，在form表单中，name必须是selectExcel
	 * @throws IOException
	 */
	@RequestMapping("/importCustomerFile")
	@ResponseBody
	public String importCustomerFile(@RequestParam MultipartFile selectExcel,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		//定义一个文件输出流
		FileOutputStream fileOut = null;
		try{
			//生成一个32位的UUID作为该次导入联系人的主键
			String importId = IDUtil.getID();
			//获取项目Web的基础路径，获取项目Web基础路径下的importxlstmp文件夹作为存放新上传文件的路径，如果该文件夹不存在，则创建该文件夹
			String basePath = request.getSession().getServletContext().getRealPath("/")+ "/";
			String allPath = basePath+"/"+PropertiesUtil.getValue("activity.importXlsPath");
			File pathFile = new File(allPath);
			if(!pathFile.exists()){
				pathFile.mkdirs();
			}
			//定义新上传文件需要保存的路径（包括文件名，使用方法开始时，定义的那个导入联系人主键作为文件名），并使用该路径实例化文件输出流
			File file = new File(allPath+"/" + importId + ".xls");
			fileOut = new FileOutputStream(file);
			//拷贝新上传的文件流到需要保存的路径（也就是输出到方法开始时，定义的那个文件输出流中）
			FileCopyUtils.copy(selectExcel.getInputStream(), fileOut);
			//如果程序执行到这里没有发生异常，则证明该操作成功执行,将方法开始时定义的导入联系人主键传递回前端
			responseData.setData(importId);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}finally{
			//关闭流
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}

	/**
	 * 解析联系人Excel文件
	 * @param activityId,活动id
	 * @param importId，导入联系人Id
	 * @param remark,导入联系人备注
	 */
	@RequestMapping("/parseCustomerFile")
	@ResponseBody
	public String parseCustomerFile(HttpServletRequest request,HttpServletResponse response){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		//定义一个文件输入流
		InputStream fis = null;
		try{
			//获取前端传来的数据，活动Id,导入联系人Id,导入联系人备注
			String activityId = request.getParameter("activityId");
			String importId = request.getParameter("importId");
			String remark = request.getParameter("remark");
			//获取项目Web的基础路径，获取项目Web基础路径下的importxlstmp文件夹路径，因为该文件夹是存放新上传文件的路径
			String basePath = request.getSession().getServletContext().getRealPath("/")+ "/";
			String allPath = basePath+"/"+PropertiesUtil.getValue("activity.importXlsPath");;
			//拼凑新上传文件保存的文件（包括文件名，使用文件夹路径+导入联系人Id+.xls这样的形式）
			File file = new File(allPath+"/" + importId + ".xls");
			//判断该新上传的文件是否存在
	    	if(file.exists()){
	    		//如果该新上传的文件存在,则解析该文件
	    		fis = new FileInputStream(file);
	    		Map<String,Object> operMap = activityService.importCustomor(activityId,importId,remark,fis);
	    		//解析完成后，将解析后的各项数据拼凑成新的Map,并设置到返回页面的数据中
	    		Map<String,Object> dataMap = new HashMap<String,Object>();
		    	dataMap.put("successNumber", operMap.get("successNumber"));
		    	dataMap.put("importNumber", operMap.get("importNumber"));
		    	dataMap.put("sysDate", DateUtil.getLongCurDate());
		    	responseData.setData(dataMap);
	    	}else{
	    		//如果该新上传的文件不存在，则将返回页面的对象设置为false,并设置errormsg为文件不存在
	    		responseData.setSuccess(false);
				responseData.setErrorMsg("文件不存在");
	    	}
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		} finally{
			//关闭流
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}

	/**
	 * 短信发码
	 * @param orderId 发码批次id，也就是导入联系人的id
	 * @return json obj string
	 */
	@RequestMapping("/sendCode")
	@ResponseBody
	public String sendCode(@RequestParam String orderId,HttpServletRequest req, HttpServletResponse resp){
		//定义返回页面的对象
//		ResponseData responseData = new AjaxResponseData();
//		try{
//			//获取Session，用来传递到Service对象中，执行方法过程中，保存进度到Session中
//			HttpSession session = req.getSession();
//			//调用Service对象执行发码操作
//			Map<String,Object> operMap = activityService.sendCode(orderId,session);
//			//判断执行发码操作的返回对象中，是否成功
//			if(Boolean.parseBoolean(String.valueOf(operMap.get("success")))){
//				//如果发码结果为true，则只需要将发码消息设置为data就可以，因为返回页面的对象中，默认为true。
//				responseData.setData(operMap.get("msg"));
//			}else{
//				//如果发码结果为false，则需要将返回页面的对象中设置为false，并且需要将发码消息设置为data就可以。
//				responseData.setSuccess(false);
//				responseData.setData(operMap.get("msg"));
//			}
//		}catch(Exception e){
//			//如果抛出异常，则将返回页面的对象设置为false
//			e.printStackTrace();
//			responseData.setSuccess(false);
//			responseData.setErrorMsg(e.getMessage(), e);
//		}
//		//返回页面数据
//		return JSON.toJSONString(responseData);
		return null;
	}
	/**
	   * 生成短信发码excel
	   * @param orderId 发码批次id，也就是导入联系人的id
	   * @throws IOException
	   */
	  @RequestMapping("/createSendSmsExcel")
	  @ResponseBody
	  public String createSendSmsExcel(@RequestParam String orderId,HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		  //定义返回页面的对象
//		  ResponseData responseData = new AjaxResponseData();
//		  try{
//			  //获取生成短信发码的模板地址
//			  String templateDir =  "/"+PropertiesUtil.getValue("activity.sendSmsTemplatePath");;
//			  //获取Session，用来传递到Service对象中，执行方法过程中，保存进度到Session中
//			  HttpSession session = req.getSession();
//			  //调用Service对象执行创建导出数据操作
//			  Map<String,Object> expertMap = activityService.createSendSmsExcel(orderId,session);
//			  //解析执行操作的返回对象中，是否成功
//			  boolean isSuccess = Boolean.parseBoolean(String.valueOf(expertMap.get("success")));
//			  if(isSuccess){
//				  //如果操作结果为true,则执行创建Excel操作
//				  //定义模版引擎
//				  XLSTransformer transformer = new XLSTransformer();
//				  try {
//					  //因为资源文件会被打包到jar中，必须要特殊处理，如下一行有效代码所示
//					  InputStream in = this.getClass().getResourceAsStream(templateDir); 
//					  //根据模板流和导出数据对象，生成新的excel对象
//					  HSSFWorkbook workBook = (HSSFWorkbook) transformer.transformXLS(in, expertMap);
//					  //获取项目Web的基础路径，获取项目Web基础路径下的exportxlstmp文件夹路径,如果没有该路径，则创建该路径
//					  String basePath = req.getSession().getServletContext().getRealPath("/")+ "/";  
//					  String allPath = basePath+"/"+PropertiesUtil.getValue("activity.exportXlsPath");
//					  File pathFile = new File(allPath);
//					  if(!pathFile.exists()){
//						  pathFile.mkdirs();
//					  }
//					  //拼凑新生成Excel文件需要保存的路径（包括文件名，使用文件夹路径+orderId+.xls这样的形式）
//					  File file = new File(allPath+"/" + orderId + ".xls");  
//					  //定义文件输出流，并根据Excel文件保存路径实例化该文件输出流,将新生成的excel对象写入到流中
//					  FileOutputStream fileOut = new FileOutputStream(file);  
//					  workBook.write(fileOut);  
//					  //关闭流
//					  in.close();  
//					  fileOut.close();  
//			      
//				  } catch (ParsePropertyException e) {
//					  e.printStackTrace();
//					  responseData.setSuccess(false);
//					  responseData.setErrorMsg(e.getMessage(), e);
//				  } catch (InvalidFormatException e) {
//					  e.printStackTrace();
//					  responseData.setSuccess(false);
//					  responseData.setErrorMsg(e.getMessage(), e);
//				  } catch (IOException e) {
//					  e.printStackTrace();
//					  responseData.setSuccess(false);
//					  responseData.setErrorMsg(e.getMessage(), e);
//				  }
//			  }else{
//				//如果操作结果为false，则将返回页面的对象设置为false
//		    	responseData.setSuccess(false);
//			    responseData.setErrorMsg(String.valueOf(expertMap.get("msg")));
//			  }
//		  }catch(Exception e){
//			  	//如果抛出异常，则将返回页面的对象设置为false
//			  	e.printStackTrace();
//				responseData.setSuccess(false);
//				responseData.setErrorMsg(e.getMessage(), e);
//		  }
//		  //返回页面数据
//		  return JSON.toJSONString(responseData);
		  return null;
	  }
	  /**
	   * 导出短信发码excel
	   * @param orderId 发码批次id，也就是导入联系人的id
	   * @throws IOException
	   */
	  @RequestMapping("/exportSendSmsExcel")
	  public void exportSendSmsExcel(@RequestParam String orderId,HttpServletRequest req, HttpServletResponse resp) throws IOException {
//	    //获取导出文件名称
//		String fileName = "导出短信发码.xls";
//	    try {
//	    	//获取项目Web的基础路径,拼凑新生成Excel文件需要保存的路径（包括文件名，使用文件夹路径+orderId+.xls这样的形式）
//	    	String basePath = req.getSession().getServletContext().getRealPath("/")+ "/";  
//			String filePath = basePath+"/"+PropertiesUtil.getValue("activity.exportXlsPath")+"/" + orderId + ".xls";
//    		File file = new File(filePath);
//    		//定义下载的输出流
//    		OutputStream os;
//    		//判断文件是否存在
//    		if(file.exists()){
//    			//如果文件存在,定义下载的文件输入流
//    			InputStream fis = new FileInputStream(file);
//    			resp.reset();// 清空response
//    			//设置response的Header
//    			resp.setStatus(HttpServletResponse.SC_OK);
//    			resp.setContentType("application/octet-stream");
//    			resp.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
//    			resp.addHeader("Content-Length", "" + file.length());
//    			//将文件输入流中的数据读取到输出流中
//    			os = resp.getOutputStream();
//    			int readLength = 0;
//    			byte[] readUnit=new byte[1024*1024];
//    			while ((readLength = fis.read(readUnit))!=-1) {
//    				os.write(readUnit,0, readLength);
//    				os.flush();
//    			} 
//    			//关闭流
//    			os.close();
//    			fis.close();
//    		}else{
//    			//如果文件不存在
//    			resp.setStatus(HttpServletResponse.SC_OK);
//    			resp.setContentType("application/octet-stream");
//    			OutputStream out = resp.getOutputStream();
//    			out.write("文件已删除或无权限缺失，无法下载！".getBytes("gb2312"));
//    			out.close();         	
//    		}	
//	    } catch (ParsePropertyException e) {
//	      e.printStackTrace();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
	  }
	  /**
		* 获取发码进度
		* @return json obj string
		*/
	  @RequestMapping("/getProcessNumber")
	  @ResponseBody
	  public String getProcessNumber(HttpServletRequest req, HttpServletResponse resp){
		  //定义返回页面的对象
		  ResponseData responseData = new AjaxResponseData();
		  //获取Session，发码和导出码的进度就是保存在Session中
		  HttpSession session = req.getSession();
		  //判断Session中sendCodeProcess属性是否为空
		  if(session.getAttribute("sendCodeProcess")!=null){
			  //如果Session中sendCodeProcess属性不为空，则返回该属性的值
			  responseData.setData(session.getAttribute("sendCodeProcess"));
		  }else{
			  //如果没有获取到相关的session值，则将返回页面的对象设置为false
			  responseData.setSuccess(false);
			  responseData.setErrorMsg("发生异常");
		  }
		  //返回页面数据
		  return JSON.toJSONString(responseData);
	  }

	/**
	 * 删除活动,根据主键
	 * @param id 主键
	 * @return json obj string
	 */
	@RequestMapping("/deleteActivityById")
	@ResponseBody
	public String deleteActivityById(@RequestParam String id){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			Integer affectCount = activityService.deleteActivityById(id);
			//如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			responseData.setData(affectCount);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}

	/**
	 * 批量删除活动,根据主键集合
	 * @param ids 主键集合
	 * @return json obj string
	 */
	@RequestMapping("/deleteActivitysByIds")
	@ResponseBody
	public String deleteActivitysByIds(@RequestParam String ids){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			Integer affectCount = activityService.deleteActivitysByIds(ids);
			//如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			responseData.setData(affectCount);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}

	/**
	 * 删除活动,根据条件
	 * @param reqMap   请求参数
	 * @return json obj string
	 */
	@RequestMapping("/deleteActivity")
	@ResponseBody
	public String deleteActivity(@RequestParam Map<String, Object> reqMap){
		//定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		try{
			Integer affectCount = activityService.deleteActivity(reqMap);
			//如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
			responseData.setData(affectCount);
		}catch(Exception e){
			//如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		//返回页面数据
		return JSON.toJSONString(responseData);
	}
	@RequestMapping("getCombs")
	public String getComb(){
		return "";
	}
	@RequestMapping("/test")
	public String test(){
		return activityService.extractedCode("c6efb2223be54cbe944a3f4afbdd0023", "批次"+new Random().nextInt(), 10)+"";
	
	}
	
}
