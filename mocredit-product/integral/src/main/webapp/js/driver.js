//页面初始化设置
$(document).ready(function() {
	$("#driverForm").validate({
		submitHandler:function(form){
			
			var flag = true;
			
			if (!new RegExp(/^[\u4e00-\u9fa5]{2,8}$/).test($("#driverName").val())) {
				$("#checkDriverNameTip").removeClass("hidden");
				flag = false;
			}
			
			if ($("#carid").val() == "") {
				$('#licenseTip').removeClass('hidden');
				flag = false;
			}
			
			//检查联系方式格式是否正确
			var isTelNum = new RegExp(/^\d{11}/);
			if (!isTelNum.test($("#driverTel").val())) {
				$("#checkDriverTelTip").removeClass("hidden");
				flag = false;
			}
			
			//检查身份证格式是否正确
			var idCarNo = new RegExp(/^\d{17}([0-9]|X)$|^(\d{15})$/);
			if (!idCarNo.test($("#idcard").val())) {
				$("#checkDriverIdTip").removeClass("hidden");
				flag = false;
			}
			
			// 如果货代用户名已存在
			if ($('#driver_exist_flag').val() == "1") {
				$('#checkDriverTip').removeClass('hidden');
				flag = false;
			}
			
			if (flag) {
				form.submit();
			} else {
				return false;
			}
		}
	});
	$("#carForm").validate({
		submitHandler:function(form){
			
			var flag = true;
			
			if (!new RegExp(/^[\u4e00-\u9fa5]{2,8}$/).test($("#owner_name_new").val())) {
				$("#checkOwnerNameTip").removeClass("hidden");
				flag = false;
			}
			
			if ($("#password").val() != $("#confirmpass").val()) {
				$('#passTip').removeClass('hidden');
				flag = false;
			}
			
			// 当新增和修改时，选择已有车主，如果没有选择车主
			if ( ($("#modifyType").val() == undefined && $("#ownerType").val() == "1" &&  $("#owner_idcard_old").val() == "") 
			   || ($("#modifyType").val() == "1" && $("#ownerType").val() == "1" &&  $("#owner_idcard_old").val() == "") )   {
				$('#ownerTip').removeClass('hidden');
				flag = false;
			}
			
			// 验证车牌号是否合法
			var carNo = new RegExp(/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/);
			if (!carNo.test($("#carlicense").val())) {
				$("#checkCarTip1").removeClass("hidden");
				flag = false;
			}
			
			// 如果车牌号已存在
			if ($('#car_exist_flag').val() == "1") {
				$('#checkCarTip').removeClass('hidden');
				flag = false;
			}

			// 当新增和修改时，选择新车主，如果车主已存在
			if ( ($("#modifyType").val() == undefined && $("#ownerType").val() == "0" &&  $("#owner_exist_flag").val() == "1") 
			   || ($("#modifyType").val() == "1" && $("#ownerType").val() == "0" &&  $("#owner_exist_flag").val() == "1") )   {
				$('#checkOwnerTip').removeClass('hidden');
				flag = false;
			}		

			//检查身份证格式是否正确
			var idCarNo = new RegExp(/^\d{17}([0-9]|X)$|^(\d{15})$/);
			if (!idCarNo.test($("#owner_idcard_new").val())) {
				$("#checkOwnerIdTip").removeClass("hidden");
				flag = false;
			}
			
			//检查联系方式格式是否正确
			var isTelNum = new RegExp(/^\d{11}/);
			if (!isTelNum.test($("#owner_tel_new").val())) {
				$("#checkOwnerTelTip").removeClass("hidden");
				flag = false;
			}
			if (flag) {
				form.submit();
			}else {
				return false;
			}
		}
	});	
	
	var car_type = $("#car_type").val();
	var cartype_arr = car_type.split(",");
	$("#cartype1").find("option[value='"+cartype_arr[0]+"']").attr("selected",true);
	$("#cartype2").find("option[value='"+cartype_arr[1]+"']").attr("selected",true);
});

// 检查密码和确认密码
function checkpass() {
	if ($("#password").val() == $("#confirmpass").val()) {
		$('#passTip').addClass('hidden');
	}
}

// 搜索司机信息
function searchDriver(currentPage) {
	$("#currentPage").val(currentPage);
	$("#searchForm").submit();
}

// 页面跳转，查看司机详情或者修改司机信息
function jump(driverId, opType, pageType) {
	$("#driverId").val(driverId);
	$("#pageType").val(pageType);
	$("#searchForm").attr("action", _path + "/driver/" + opType);
	$("#searchForm").submit();
}

// 全选司机
function checkAllDriver(boxObj) {
	if ($(boxObj).prop("checked") == true) {
		$("input[name='driverBox']").prop("checked", true);
	} else {
		$("input[name='driverBox']").removeAttr("checked");
	}
}

// 批量注销或者激活司机
function batchDriver(type) {
	$("#opType").val(type);
	var opType = "";
	if (type == "0") {
		opType = "激活";
	} else {
		opType = "注销";
	}

	var hasChecked = false;
	$("input[name='driverBox']").each(function() {
		if ($(this).prop("checked") == true) {
			hasChecked = true;
			return false;
		}
	});

	if (hasChecked == false) {
		alert("请选择要" + opType + "的司机！");
		return;
	}

	if (window.confirm("确定" + opType + "这些司机吗？")) {
		$.ajax({
			url : _path + "/driver/batch",
			type : "POST",
			datatype : "json",
			data : $('#searchForm').serialize(),
			success : function(data) {
				if (data.status == 0) {
					alert(data.msg);
					$("#searchForm").submit();
				} else {
					alert(data.msg);
				}
			},
			error : function(data) {
				alert(data.msg);
			}
		});
	}
}

// 注销司机信息
function cancelDriver(driverId, cancelFlag) {
	var opType = "";
	if (cancelFlag == "0") {
		opType = "激活";
	} else {
		opType = "注销";
	}
	if (window.confirm("确定" + opType + "该司机吗？")) {
		$.ajax({
			url : _path + "/driver/cancel",
			type : "POST",
			datatype : "json",
			data : {
				"driverId" : driverId,
				"cancelFlag" : cancelFlag
			},
			success : function(data) {
				if (data.status == 0) {
					alert(data.msg);
					$("#searchForm").submit();
				} else {
					alert(data.msg);
				}
			},
			error : function(data) {
				alert(data.msg);
			}
		});
	}
}

// 根据车牌号搜索车辆
function searchCar(keywords) {
	$('#licenseTip').addClass('hidden');
	$("#carid").val("");
	if ($.trim(keywords) != "") {
		$("#searchCarDiv").html("");
		$.ajax({
			url : _path + "/driver/searchCar",
			dataType : "json",
			type : "post",
			data : {
				"keywords" : $.trim(keywords)
			},
			success : function(data) {
				if (data.status == 0) {
					var items = data.items;
					if (items.length == 0) {
						$("#searchCarDiv").html("<font color='red'>没有查询到车辆信息!</b>");
						$("#searchCarDiv").removeClass("hidden");
						return;
					}
					var vhtml = "";
					for ( var i in items) {
						vhtml +="<li style='list-style:none'  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)' onclick=\"setval('"+ items[i].id + "','"+ items[i].license + "')\">"+  items[i].license + "，"+ items[i].name + "</li>";
					}
					if (items.length == 1) {
						setval( items[0].id, items[0].license);
					}
					$("#searchCarDiv").html(vhtml);
					$("#searchCarDiv").removeClass("hidden");
				} else {
					$("#searchCarDiv").html("<font color='red'>没有查询到车辆信息!</b>");
					$("#searchCarDiv").removeClass("hidden");
				}
			}
		});
	}
}

// 选择车辆时设值
function setval(id, val) {
	$('#licenseTip').addClass('hidden');
	$("#license").val(val);
	$("#carid").val(id);
	$("#searchCarDiv").addClass("hidden");
}

function mouseOver(obj){
	$(obj).css("background","#efefef");
	$(obj).css("cursor","pointer");
}

function mouseOut(obj){
	$(obj).css("background","#fff");
	$(obj).css("cursor","default");
}

// 司机信息，新增和修改页面填写完成后提交
function editDriver(optype) {
	if (optype == "0") {
		$("#driverForm").attr("action", _path + "/driver/add");
	} else {
		$("#driverForm").attr("action", _path + "/driver/modify");
	}
	$("#driverForm").submit();
}

// 车辆信息，新增和修改页面填写完成后提交
function editCar(optype) {
	if (optype == "0") {
		$("#carForm").attr("action", _path + "/driver/addcar");
	} else {
		$("#carForm").attr("action", _path + "/driver/modifycar");
	}
	$("#carForm").submit();
}

// 改变车主选择，是新的车主还是已有车主
function changeOwner(obj) {
	if ($(obj).val() == "0") {
		$("#newOwnerDiv1").removeClass("hidden");
		$("#newOwnerDiv2").removeClass("hidden");
		$("#oldOwnerDiv1").addClass("hidden");
		$("#oldOwnerDiv2").addClass("hidden");
		$("#newImgDiv").removeClass("hidden");
		$("#oldImgDiv").addClass("hidden");
		$("#photo_op_div").removeClass("hidden");
	} else {
		$("#oldOwnerDiv1").removeClass("hidden");
		$("#oldOwnerDiv2").removeClass("hidden");
		$("#newOwnerDiv1").addClass("hidden");
		$("#newOwnerDiv2").addClass("hidden");
		$("#newImgDiv").addClass("hidden");
		$("#oldImgDiv").removeClass("hidden");
		$("#photo_op_div").addClass("hidden");
	}
}

// 改变修改车辆时操作，是修改当前车主信息还是变更该车辆的车主
function modifyOwner(obj) {
	if ($(obj).val() == "0") {
		$("#modifyOwnerDiv").removeClass("hidden");
		$("#ownerLabel").addClass("hidden");
		$("#ownerType").addClass("hidden");
		$("#newOwnerDiv").addClass("hidden");
		$("#modifyUploadDiv").removeClass("hidden");
		$("#newUploadDiv").addClass("hidden");
	} else {
		$("#modifyOwnerDiv").addClass("hidden");
		$("#ownerLabel").removeClass("hidden");
		$("#ownerType").removeClass("hidden");
		$("#newOwnerDiv").removeClass("hidden");
		$("#modifyUploadDiv").addClass("hidden");
		$("#newUploadDiv").removeClass("hidden");
	}
}

// 根据姓名搜索车主
function searchOwner(keywords) {
	$('#ownerTip').addClass('hidden');
	$("#owner_tel_old").val("");
	$("#owner_idcard_old").val("");
	$("#owner_photo_old").val("");
	$("#oldImgDiv").html('<img src="'+_path+'/images/nohead.png">');
	if ($.trim(keywords) != "") {
		$("#searchOwnerDiv").html("");
		$.ajax({
			url : _path + "/driver/searchOwner",
			dataType : "json",
			type : "post",
			data : {
				"keywords" : $.trim(keywords)
			},
			success : function(data) {
				if (data.status == 0) {
					var items = data.items;
					if (items.length == 0) {
						$("#searchOwnerDiv").html("<font color='red'>没有查询到车主信息!</b>");
						$("#searchOwnerDiv").removeClass("hidden");
						return;
					}
					var vhtml = "";
					for ( var i in items) {
						vhtml +="<li style='list-style:none'  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)' onclick=\"setvalOwner('"+ items[i].name + "','"+ items[i].tel + "','"+ items[i].idcard + "','"+ items[i].photo + "')\">"+  items[i].name + "，"+ items[i].tel + "</li>";
					}
					$("#searchOwnerDiv").html(vhtml);
					$("#searchOwnerDiv").removeClass("hidden");
				} else {
					$("#searchOwnerDiv").html("<font color='red'>没有查询到车主信息!</b>");
					$("#searchOwnerDiv").removeClass("hidden");
				}
			}
		});
	}
}

// 选择车主时设值
function setvalOwner(name, tel, idcard, photo) {
	$('#ownerTip').addClass('hidden');
	$("#owner_name_old").val(name);
	$("#owner_tel_old").val(tel);
	$("#owner_idcard_old").val(idcard);
	$("#owner_photo_old").val(photo);
	$("#oldImgDiv").html('<img src="'+photo+'" style="max-width: 133px; max-height: 144px;">');
	$("#searchOwnerDiv").addClass("hidden");
}


//修改密码
function modifyPass(type) {
	var password = "";
	var typeName = "";
	if (type == "0") {
		var original_pass = $.trim($("input[name='password']").val());
		var confirmpass = $.trim($("input[name='confirmpass']").val());
		if (original_pass == "") {
			$('#passTip1').removeClass('hidden');
			return;
		} else if (confirmpass == "") {
			$('#passTip2').removeClass('hidden');
			return;
		} else if (original_pass != confirmpass) {
			$('#passTip3').removeClass('hidden');
			return;
		}
		
		$('#passTip1').addClass('hidden');
		$('#passTip2').addClass('hidden');
		$('#passTip3').addClass('hidden');

		password = hex_md5(original_pass);
		typeName = "修改密码";
	} else {
		typeName = "重置为系统密码";
	}

	if (window.confirm("确定" + typeName + "吗？")) {
		$.ajax({
			url : _path + "/driver/password",
			type : "POST",
			datatype : "json",
			data : {
				"id" : $("#pass_id").val(),
				"password" : password,
				"opType" : type
			},
			success : function(data) {
				if (data.status == 0) {
					alert(data.msg);
					$("input[name='password']").val("");
					$("input[name='confirmpass']").val("");
				} else {
					alert(data.msg);
				}
			},
			error : function(data) {
				alert(data.msg);
			}
		});
	}	
}

// 检查司机或者车主是否存在
function checkDriver(type, obj) {
	if ($.trim($(obj).val()) == "") {
		return;
	}
	$.ajax({
		url : _path + "/driver/checkdriver",
		type : "POST",
		datatype : "json",
		data : {
			"type" : type,
			"idcard" : $.trim($(obj).val())
		},
		success : function(data) {
			if (data.count == 0) {
				if (type == "0") {
					$('#checkDriverTip').addClass('hidden');
					$('#driver_exist_flag').val('0');
				} else {
					$('#checkOwnerTip').addClass('hidden');
					$('#owner_exist_flag').val('0');
				}
			} else {
				if (type == "0") {
					$('#checkDriverTip').removeClass('hidden');
					$('#driver_exist_flag').val('1');
				} else {
					$('#checkOwnerTip').removeClass('hidden');
					$('#owner_exist_flag').val('1');
				}
			}
		},
		error : function(data) {

		}
	});
}

//检查车辆和车主是否存在
function checkCar(obj) {
	if ($.trim($(obj).val()) == "") {
		return;
	}
	$.ajax({
		url : _path + "/driver/checkcar",
		type : "POST",
		datatype : "json",
		data : {
			"license" : $.trim($(obj).val())
		},
		success : function(data) {
			if (data.count == 0) {
				$('#checkCarTip').addClass('hidden');
				$('#car_exist_flag').val('0');
			} else {
				$('#checkCarTip').removeClass('hidden');
				$('#car_exist_flag').val('1');
			}
		},
		error : function(data) {

		}
	});
}