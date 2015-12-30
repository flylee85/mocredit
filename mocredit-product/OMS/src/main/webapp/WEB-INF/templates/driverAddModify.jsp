<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>智慧物流后台管理平台</title>
<jsp:include page="common/common.jsp" />
<link rel="stylesheet" type="text/css" href="${path}/lib/bootstrap-fileinput/bootstrap-fileinput.css" />
<script type="text/javascript" src="${path}/lib/bootstrap-fileinput/bootstrap-fileinput.js" ></script>
<script type="text/javascript" src="${path}/lib/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="${path}/lib/jquery-validation/jquery.metadata.js"></script>
<script type="text/javascript" src="${path}/lib/jquery-validation/messages_zh.js"></script>
<script type="text/javascript" src="${path}/lib/md5.js"></script>
<script type="text/javascript" src="${path}/js/driver.js"></script>
</head>

<body>
	<jsp:include page="common/header.jsp" />

	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<jsp:include page="common/menu.jsp" />
			<div class="col-xs-12 col-sm-10">

				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<c:choose>
								<c:when test="${optype == '0'}">新增司机信息</c:when>
								<c:otherwise>修改司机信息</c:otherwise>
							</c:choose>
						</h3>
					</div>
					<div class="panel-body">

						<ul class="nav nav-tabs">
							<li class="active"><a href="#driverinfo" data-toggle="tab">司机信息</a></li>
							<li><a href="#carinfo" data-toggle="tab">车辆信息</a></li>
							<c:if test="${optype == '1'}"><li><a href="#passinfo" data-toggle="tab">修改密码</a></li></c:if>
						</ul>

						<div class="col-xs-12" style="height:10px;"></div>

						<div class="tab-content">
							<div class="tab-pane active" id="driverinfo">
								<form action="" id="driverForm" method="post" class="form-horizontal" enctype="multipart/form-data">
									<div class="col-xs-12">
										<div class="col-xs-2 text-center">
											<div class="form-group">
												<div class="col-md-12">
													<div class="fileinput fileinput-new" data-provides="fileinput">
														<div class="fileinput-new thumbnail">
															<c:choose>
																<c:when test="${empty driver_car.driver_photo}">
																	<img src="${path}/images/nohead.png">
																</c:when>
																<c:otherwise>
																	<img src="${driver_car.driver_photo}" style="max-width: 133px; max-height: 144px;">
																</c:otherwise>
															</c:choose>
														</div>
														<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 133px; max-height: 144px;"></div>
														<div>
															<span class="btn default btn-file"> 
																<span class="fileinput-new"> 上传图片 </span>
																<span class="fileinput-exists"> 修改 </span>
																<input type="file" id="file" name="file" onchange="checkPhoto(this,$('#deleteFile'))" />
																<input class="" type="hidden" id="oldPhoto" name="oldPhoto" value="${driver_car.photo}">
															</span>
															<a id="deleteFile"  href="#" class="btn default fileinput-exists" data-dismiss="fileinput"> 删除 </a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-xs-10">
											<div class="form-group">
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>司机姓名：</label>
												<div class="col-xs-4">
													<input type="text" name="name" id="driverName" value="${driver_car.name}" maxlength="8" class="form-control borderRadiusIE8"  onkeyup="$('#checkDriverNameTip').addClass('hidden')">
													<p id="checkDriverNameTip" class="help-block hidden"><font color="red"><b>请输入2-8个汉字</b></font></p>
												</div>
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>联系方式：</label>
												<div class="col-xs-4">
													<input type="text" name="tel" id="driverTel" value="${driver_car.tel}" class="form-control borderRadiusIE8 required"  onkeyup="$('#checkDriverTelTip').addClass('hidden')">
													<p id="checkDriverTelTip" class="help-block hidden"><font color="red"><b>请输入正确的电话或手机号码</b></font></p>
												</div>
											</div>
											<div class="form-group">
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>身份证号：</label>
												<div class="col-xs-4">
													<input type="text" id="idcard" name="idcard" value="${driver_car.idcard}" class="form-control borderRadiusIE8 required"  ${optype == '1' ? 'readonly' : ''} <c:if test="${optype == '0'}"> onblur="checkDriver('0',this)"</c:if> onkeyup="$('#checkDriverTip').addClass('hidden')">
													<p id="checkDriverTip" class="help-block hidden"><font color="red"><b>该司机已存在！</b></font></p>
													<p id="checkDriverIdTip" class="help-block hidden"><font color="red"><b>请输入正确的身份证号码</b></font></p>
												</div>
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车牌号：</label>
												<div class="col-xs-4">
													<input type="text" id="license" name="license" value="${driver_car.license}" autocomplete="off" onkeyup="searchCar(this.value)" class="form-control borderRadiusIE8 required">
													<p id="licenseTip" class="help-block hidden"><font color="red"><b>请选择车辆！</b></font></p>
													<div id="searchCarDiv" class="hidden" style="border: 1px solid #cccccc;background: #fff;"></div>
													<!-- height:200px;overflow:auto -->
													<input type="hidden" id="carid" name="carid" value="${driver_car.carid}">
												</div>
											</div>
											<div class="form-group text-center help-block">
													提示：可以根据车牌号查找车辆，如果车辆不存在请先添加车辆！
											</div>
										</div>
									</div>
									
									<c:choose>
										<c:when test="${optype == '0'}">
											<div class='col-xs-offset-4'>
												<button type="button" class="btn btn-success col-xs-2" onclick="editDriver('0')">完成</button>
												<div class="col-xs-1">&nbsp;</div>
												<button type="button" class="btn btn-default col-xs-2" onclick="goback('新增司机')">取消</button>
											</div>
										</c:when>
										<c:otherwise>
											<div class='col-xs-offset-4'>
												<button type="button" class="btn btn-success col-xs-2" onclick="goback('修改司机')">返回</button>
												<div class="col-xs-1">&nbsp;</div>
												<button type="button" class="btn btn-default col-xs-2" onclick="editDriver('1')">完成修改</button>
											</div>
										</c:otherwise>
									</c:choose>
									
									<input type="hidden" id="driver_id" name="id" value="${driver_car.id}">
									<input type="hidden" id="driver_exist_flag" name="driver_exist_flag" value="0">
								</form>
							</div>

							<div class="tab-pane" id="carinfo">
								<form action="" id="carForm" method="post" class="form-horizontal" enctype="multipart/form-data">
									<div class="col-xs-12">
										<font color="#fea11e">车辆信息：</font>
									</div>
									<div class="col-xs-12">
										<div class="col-xs-2 text-center">
											<div class="form-group">
												<div class="col-md-12">
													<div class="fileinput fileinput-new" data-provides="fileinput">
														<div class="fileinput-new thumbnail">
															<c:choose>
																<c:when test="${empty driver_car.car_photo}">
																	<img src="${path}/images/nohead.png">
																</c:when>
																<c:otherwise>
																	<img src="${driver_car.car_photo}" style="max-width: 133px; max-height: 144px;">
																</c:otherwise>
															</c:choose>
														</div>
														<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 133px; max-height: 144px;"></div>
														<div>
															<span class="btn default btn-file"> 
																<span class="fileinput-new"> 上传图片 </span>
																<span class="fileinput-exists"> 修改 </span> 
																<input type="file" id="file1" name="file1" onchange="checkPhoto(this,$('#deleteFile1'))" />
																<input class="" type="hidden" id="oldPhoto" name="oldPhoto" value="${driver_car.photo}">
															</span>
															<a id="deleteFile1"  href="#" class="btn default fileinput-exists" data-dismiss="fileinput"> 删除 </a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-xs-10">
											<div class="form-group">
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车牌号：</label>
												<div class="col-xs-4">
													<input type="text" name="license" id="carlicense" value="${driver_car.license}" onkeyup="$('#checkCarTip1').addClass('hidden')" class="form-control borderRadiusIE8 required" ${optype == '1' ? 'readonly' : ''} <c:if test="${optype == '0'}"> onblur="checkCar(this)"</c:if>>
													<p id="checkCarTip" class="help-block hidden"><font color="red"><b>该车辆已存在！</b></font></p>
													<p id="checkCarTip1" class="help-block hidden"><font color="red"><b>请输入正确的车牌号码</b></font></p>
												</div>
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车型：</label>
												<div class="col-xs-4">
													<select id="cartype1"  name="cartype1" >
														<option value="普通">普遍</option>
														<option value="平板">平板</option>
														<option value="高低平板">高低平板</option>
														<option value="厢式">厢式</option>
														<option value="封闭">封闭</option>
														<option value="开顶箱">开顶箱</option>
														<option value="自卸">自卸</option>
														<option value="集装箱">集装箱</option>
														<option value="罐式">罐式</option>
														<option value="高栏">高栏</option>
														<option value="满轮车">满轮车</option>
														<option value="框架板">框架板</option>
														<option value="高低高板">高低高板</option>
														<option value="抽拉板">抽拉板</option>
														<option value="簸箕板">簸箕板</option>
														<option value="笼子车">笼子车</option>
														<option value="叶片车">叶片车</option>
														<option value="其他">其他</option>
													</select>
													<select id="cartype2" name="cartype2" >
														<option value="半挂一拖二">半挂一拖二</option>
														<option value="半挂一拖三">半挂一拖三</option>
														<option value="半挂二拖二">半挂二拖二</option>
														<option value="半挂二拖三">半挂二拖三</option>
														<option value="前四后四半挂">前四后四半挂</option>
														<option value="前四后六">前四后六</option>
														<option value="前四后八">前四后八</option>
														<option value="前四后十">前四后十</option>
														<option value="后八轮">后八轮</option>
														<option value="五轮车">五轮车</option>
														<option value="单桥">单桥</option>
														<option value="双桥">双桥</option>
														<option value="四桥">四桥</option>
														<option value="七桥">七桥</option>
														<option value="八桥">八桥</option>
														<option value="九桥">九桥</option>
														<option value="全挂">全挂</option>
														<option value="其他">其他</option>
													</select>
													<input type="hidden" id="car_type" name="car_type" value="${driver_car.cartype}">
												</div>
											</div>
											<div class="form-group">
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>总载重(吨)：</label>
												<div class="col-xs-4">
													<input type="text" name="totalload" value="${driver_car.totalload}" class="form-control borderRadiusIE8 required number {range:[0,1000]}">
												</div>
												<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车长(米)：</label>
												<div class="col-xs-4">
													<input type="text" name="carlength" value="${driver_car.carlength}" class="form-control borderRadiusIE8 required number {range:[0,20]}">
												</div>
											</div>
											<div class="form-group">
												<label class="col-xs-2 control-label">车宽(米)：</label>
												<div class="col-xs-4">
													<input type="text" name="carwidth" value="${driver_car.carwidth}" class="form-control borderRadiusIE8 number">
												</div>
												<label class="col-xs-2 control-label">车高(米)：</label>
												<div class="col-xs-4">
													<input type="text" name="carheight" value="${driver_car.carheight}" class="form-control borderRadiusIE8 number">
												</div>
											</div>
											<div class="form-group">
												<label class="col-xs-2 control-label">容积(m³)：</label>
												<div class="col-xs-4">
													<input type="text" name="volume" value="${driver_car.volume}" class="form-control borderRadiusIE8 number">
												</div>
												<label class="col-xs-2 control-label">发动机号：</label>
												<div class="col-xs-4">
													<input type="text" name="engineno" value="${driver_car.engineno}" class="form-control borderRadiusIE8">
												</div>
											</div>
											<div class="form-group">
												<label class="col-xs-2 control-label">车架号码：</label>
												<div class="col-xs-4">
													<input type="text" name="frameno" value="${driver_car.frameno}" class="form-control borderRadiusIE8">
												</div>
												<label class="col-xs-2 control-label">保险号：</label>
												<div class="col-xs-4">
													<input type="text" name="insuranceno" value="${driver_car.insuranceno}" class="form-control borderRadiusIE8">
												</div>
											</div>
											<div class="form-group">
												<label class="col-xs-2 control-label">运营证书：</label>
												<div class="col-xs-4">
													<input type="text" name="operationno" value="${driver_car.operationno}" class="form-control borderRadiusIE8">
												</div>
												<label class="col-xs-2 control-label">所属公司：</label>
												<div class="col-xs-4">
													<input type="text" name="company" value="${driver_car.company}" class="form-control borderRadiusIE8">
												</div>
											</div>
											<c:if test="${optype == '0'}">
												<div class="form-group">
													<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>密码：</label>
													<div class="col-xs-4">
														<input type="password" id="password" name="password" value="" class="form-control borderRadiusIE8 required" onkeyup="checkpass()">
														<p id="passTip" class="help-block hidden">
															<font color="red"><b>密码和确认密码不一致！</b></font>
														</p>
													</div>
													<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>确认密码：</label>
													<div class="col-xs-4">
														<input type="password" id="confirmpass" name="confirmpass" value="" class="form-control borderRadiusIE8 required" onkeyup="checkpass()">
													</div>
												</div>
											</c:if>
										</div>
									</div>


									<div class="col-xs-12">
										<hr />
										<font color="#fea11e">车主信息：</font>
									</div>

									<div class="col-xs-12">
										<div class="col-xs-2 text-center">
											<c:if test="${optype == '1'}">
												<div id="modifyUploadDiv" class="form-group">
													<div class="col-md-12">
														<div class="fileinput fileinput-new" data-provides="fileinput">
															<div class="fileinput-new thumbnail">
																<c:choose>
																	<c:when test="${empty owner.photo}">
																		<img src="${path}/images/nohead.png">
																	</c:when>
																	<c:otherwise>
																		<img src="${owner.photo}" style="max-width: 133px; max-height: 144px;">
																	</c:otherwise>
																</c:choose>
															</div>
															<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 133px; max-height: 144px;"></div>
															<div>
																<span class="btn default btn-file"> 
																	<span class="fileinput-new"> 上传图片 </span>
																	<span class="fileinput-exists"> 修改 </span>
																	<input type="file" id="file2" name="file2"  onchange="checkPhoto(this,$('#deleteFile2'))"/>
																	<input class="" type="hidden" id="oldPhoto" name="oldPhoto" value="${owner.photo}">
																</span> 
																<a id="deleteFile2"  href="#" class="btn default fileinput-exists" data-dismiss="fileinput"> 删除 </a>
															</div>
														</div>
													</div>
												</div>
											</c:if>
											<div id="newUploadDiv" class="form-group ${optype == '1' ? 'hidden' : ''}">
												<div class="col-md-12">
													<div class="fileinput fileinput-new" data-provides="fileinput">
														<div class="fileinput-new thumbnail" id="newImgDiv">
															<img src="${path}/images/nohead.png">
														</div>
														<div class="fileinput-new thumbnail hidden" id="oldImgDiv">
															<img src="${path}/images/nohead.png">
														</div>
														<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 133px; max-height: 144px;"></div>
														<div id="photo_op_div">
															<span class="btn default btn-file"> 
																<span class="fileinput-new"> 上传图片 </span>
																<span class="fileinput-exists"> 修改 </span>
																<input type="file" id="file2" name="file2" onchange="checkPhoto(this,$('#deleteFile3'))"/>
																<input class="" type="hidden" id="oldPhoto" name="oldPhoto" value="${owner.photo}">
															</span>
															<a id="deleteFile3"  href="#" class="btn default fileinput-exists" data-dismiss="fileinput"> 删除 </a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-xs-10">
											<div class="form-group">
												<c:if test="${optype == '1'}">
													<label class="col-xs-2 control-label">选择操作：</label>
													<div class="col-xs-4">
														<select name="modifyType" class="form-control" onchange="modifyOwner(this)">
															<option value="0" selected>修改当前车主信息</option>
															<option value="1">变更该车辆的车主</option>
														</select>
													</div>
												</c:if>
												<label id="ownerLabel" class="col-xs-2 control-label ${optype == '1' ? 'hidden' : ''}">选择车主：</label>
												<div class="col-xs-4">
													<select id="ownerType" name="ownerType" class="form-control ${optype == '1' ? 'hidden' : ''}" onchange="changeOwner(this)">
														<option value="0" selected>新的车主</option>
														<option value="1">已有车主</option>
													</select>
												</div>
											</div>

											<c:if test="${optype == '1'}">
												<div id="modifyOwnerDiv">
													<div class="form-group">
														<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车主姓名：</label>
														<div class="col-xs-4">
															<input type="text" name="owner_name" value="${owner.name}" class="form-control borderRadiusIE8 required">
														</div>
														<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>联系方式：</label>
														<div class="col-xs-4">
															<input type="text" name="owner_tel" value="${owner.tel}" class="form-control borderRadiusIE8 required">
														</div>
													</div>
													<div class="form-group" id="modifyOwnerDiv2">
														<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>身份证号：</label>
														<div class="col-xs-4">
															<input type="text" name="owner_idcard" value="${owner.idcard}" class="form-control borderRadiusIE8 required" readonly>
														</div>
													</div>
												</div>
											</c:if>

											<div id="newOwnerDiv" class=" ${optype == '1' ? 'hidden' : ''}">
												<div class="form-group" id="newOwnerDiv1">
													<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车主姓名：</label>
													<div class="col-xs-4">
														<input type="text" name="owner_name_new" id="owner_name_new" value="" class="form-control borderRadiusIE8 required" onkeyup="$('#checkOwnerNameTip').addClass('hidden')">
														<p id="checkOwnerNameTip" class="help-block hidden"><font color="red"><b>请输入2-8个汉字</b></font></p>
													</div>
													<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>联系方式：</label>
													<div class="col-xs-4">
														<input type="text" name="owner_tel_new" id="owner_tel_new" value="" class="form-control borderRadiusIE8 required" onkeyup="$('#checkOwnerTelTip').addClass('hidden')">
														<p id="checkOwnerTelTip" class="help-block hidden"><font color="red"><b>请输入正确的电话或手机号码</b></font></p>
													</div>
												</div>
												<div class="form-group" id="newOwnerDiv2">
													<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>身份证号：</label>
													<div class="col-xs-4">
														<input type="text" name="owner_idcard_new" id="owner_idcard_new" value="" class="form-control borderRadiusIE8 required"  onkeyup="$('#checkOwnerIdTip').addClass('hidden')" onblur="checkDriver('1',this)">
														<p id="checkOwnerTip" class="help-block hidden"><font color="red"><b>该车主已存在！</b></font></p>
														<p id="checkOwnerIdTip" class="help-block hidden"><font color="red"><b>请输入正确的身份证号码</b></font></p>
													</div>
												</div>
												<div class="form-group hidden" id="oldOwnerDiv1">
													<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>车主姓名：</label>
													<div class="col-xs-4">
														<input type="text" id="owner_name_old" name="owner_name_old" value="" autocomplete="off" onkeyup="searchOwner(this.value);" class="form-control borderRadiusIE8 required">
														<p id="ownerTip" class="help-block hidden">
															<font color="red"><b>请选择车主！</b></font>
														</p>
														<div id="searchOwnerDiv" class="hidden" style="border: 1px solid #cccccc;background: #fff;"></div>
														<input type="hidden" id="owner_photo_old" name="owner_photo_old" value="">
													</div>
													<label class="col-xs-2 control-label">联系方式：</label>
													<div class="col-xs-4">
														<input type="text" id="owner_tel_old" name="owner_tel_old" value="" class="form-control borderRadiusIE8" readonly>
													</div>
												</div>
												<div class="form-group hidden" id="oldOwnerDiv2">
													<label class="col-xs-2 control-label">身份证号：</label>
													<div class="col-xs-4">
														<input type="text" id="owner_idcard_old" name="owner_idcard_old" value="" class="form-control borderRadiusIE8" readonly>
													</div>
												</div>
											</div>
										</div>
									</div>

									<c:choose>
										<c:when test="${optype == '0'}">
											<div class='col-xs-offset-4'>
												<button type="button" class="btn btn-success col-xs-2" onclick="editCar('0')">完成</button>
												<div class="col-xs-1">&nbsp;</div>
												<button type="button" class="btn btn-default col-xs-2" onclick="goback('新增')">取消</button>
											</div>
										</c:when>
										<c:otherwise>
											<div class='col-xs-offset-4'>
												<button type="button" class="btn btn-success col-xs-2" onclick="goback('修改')">返回</button>
												<div class="col-xs-1">&nbsp;</div>
												<button type="button" class="btn btn-default col-xs-2" onclick="editCar('1')">完成修改</button>
											</div>
										</c:otherwise>
									</c:choose>

									<input type="hidden" id="cari_id" name="id" value="${driver_car.carid}">
									<input type="hidden" id="car_exist_flag" name="car_exist_flag" value="0">
									<input type="hidden" id="owner_exist_flag" name="owner_exist_flag" value="0">
								</form>
							</div>

							<div class="tab-pane" id="passinfo">
								<form action="" id="passForm" method="post" class="form-horizontal" enctype="multipart/form-data">
									<div class="col-xs-12">
										<div class="form-group">
											<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>密码：</label>
											<div class="col-xs-3">
												<input type="password" name="password" value="" class="form-control borderRadiusIE8" onfocus="$('#passTip1').addClass('hidden')">
												<p id="passTip1" style="width: 300px" class="help-block hidden" ><font color="red"><b>请输入密码！</b></font></p>
												<p id="passTip3" style="width: 300px" class="help-block hidden"><font color="red"><b>密码和确认密码不一致！</b></font></p>
											</div>
											<label class="col-xs-2 control-label"><span class="span-red-bold">* </span>确认密码：</label>
											<div class="col-xs-3">
												<input type="password" name="confirmpass" value="" class="form-control borderRadiusIE8" onfocus="$('#passTip2').addClass('hidden')">
												<p id="passTip2" style="width: 300px" class="help-block hidden"><font color="red"><b>请输入确认密码！</b></font></p>
											</div>
										</div>								
									</div>
									
									<div class='col-xs-offset-2'>
										<button type="button" class="btn btn-success col-xs-2" onclick="window.history.go(-1)">返回</button>
										<div class="col-xs-1">&nbsp;</div>
										<button type="button" class="btn btn-default col-xs-2" onclick="modifyPass('0')">完成修改</button>
										<div class="col-xs-1">&nbsp;</div>
										<button type="button" class="btn btn-primary col-xs-3" onclick="modifyPass('1')">重置为系统密码</button>
									</div>
									
									<input type="hidden" id="pass_id" name="id" value="${driver_car.carid}">
								</form>
							</div>
						</div><!-- tab-content end -->


						</div><!-- panel-body end -->
					</div><!-- panel end -->


				</div><!-- col-sm-10 end -->

			</div><!-- row end -->
			<footer>
				<!--nothing-->
			</footer>
		</div><!-- container end -->

</body>
</html>
