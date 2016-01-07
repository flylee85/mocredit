var oTable= $("#store").find('[data-ride="datatables"]').DataTable( {
	"ajax": {
		"url": "store/list",
		type:"post",
		data:function(d){
			 d.merchantId=$("#currentId").val();
			 var formArray = $("#searchArea").find("form").serializeArray();
			$.each(formArray,function(index){
					d[this['name']] = this['value'];
			});
			return d;
		 }
	},
	 "processing": true,
     "serverSide": true,
     "pageLength": 10,
     "pagingType": "full_numbers",
     "searching": false,
	"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
	"paginationType": "full_numbers",
	"autoWidth":true,
	//"pageLength": 5,
	//"serverSide":true,
	"columns": [
		{ "data": "name","className":"sName" },
		{ "data": "code" },
		{ "data": "merchantName" },
		{ "data": "createTime" },
		{ "data": null },
		{ "data": null }
	],
	"columnDefs": [
	    {
	    	"render":function(oObj, type, full, meta ){
	    		var str="";
	    		if( full["provinceName"]){
	    			str+=full["provinceName"]+"&nbsp;&nbsp;&nbsp;&nbsp;";
	    		}
	    		if( full["cityName"]){
	    			str+=full["cityName"]+"&nbsp;&nbsp;&nbsp;&nbsp;";
	    		}
	    		if( full["areaName"]){
	    			str+=full["areaName"]+"&nbsp;&nbsp;&nbsp;&nbsp;";
	    		}
	    		return str;
	    	},
	    	"sortable": false,
			"targets": 4
	    },
		{
			"render": function(oObj, type, full, meta ) {
				return '<a href="javascript:openUpdate(\''+full[ 'id' ]+'\',1)">编辑</a>'
				+ '<a href="#" onclick="javascript:doDelete(this)" data-id="'+full['id']+'">删除</a>'
				+ '<a href="terminal.html" class="changePage toTerminal" current-id="'+full[ 'id' ]+'">管理机具</a>'
				},
			"sortable": false,
			"targets": 5
		}
	]

} );
//datepicker
$(".datepicker").each(function () {
	$(this).datepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		language: 'zh-CN',
		todayHighlight: true,
		todayBtn:"linked"
	});
});
//搜索条件
$.get("area/getChildren/0",null,function(result){
	setArea($("#searchArea form .area select:first"),0,result.data);
},"json");
$("#searchArea form .area select").change(function(){
	var $select=$("#searchArea form .area select");
	console.log($select);
	var index=$select.index(this);
	$select.filter(":gt("+index+")").find("option:gt(0)").remove();
	var next=$select.eq(index+1);
	if(next.length>0){
		$.get("area/getChildren/"+this.value,null,function(result){
				setArea(next,0,result.data);
		},'json');
	}
})
$('.radio-custom > input[type=radio]').each(function () {
		var $this = $(this);
		if ($this.data('radio')) return;
		$this.radio($this.data());
	});
$("#searchBtn").click(function(){
	oTable.ajax.reload();
})
//var map = new BMap.Map("mapcontainer");    // 创建Map实例
//map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
//map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
//map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
//var marker=null;
////添加点击事件获取经纬度
//map.addEventListener("click", function(e){    
//	if(marker){
//		marker.setPosition(new BMap.Point(e.point.lng,e.point.lat));
//	}else{
//		addMarker(new BMap.Point(e.point.lng,e.point.lat));
//	}
//	map.addOverlay(marker);
//	$("#addStore form input[name=longitude]").val(e.point.lng);
//	$("#addStore form input[name=latitude]").val(e.point.lat);
//});
//验证
var form = $("#addStore").find("form").parsley();
$("#addStore").on('hidden.bs.modal', function (e) {
	form.reset();
	$("#addStore form :input[name=name]").attr("data-id","");
	$("#addStore form :input")
	 .not(':button, :submit, :reset')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
//	removeMarker();
});
$("#toAdd").click(function(){
	$.get("merchant/all",null,function(result){
		var _select=$("#addStore form .merchant select");
		_select.empty().append('<option value="" selected>请选择所属商家</option>')
		var merchantId=$("#currentId").val();
		for(var i in result.data){
			var merchant=result.data[i];
			_select.append('<option value="'+merchant.id+'" '+(merchantId.length>0&&merchantId==merchant.id?'selected':'')+'>'+merchant.name+'</option>');
		}
	},"json");
	$.get("area/getChildren/0",null,function(result){
		setArea($("#addStore form .area select:first"),0,result.data);
	},"json");
})
$("#addStore form .area select").change(function(){
	var $select=$("#addStore form .area select");
	var index=$select.index(this);
	$select.filter(":gt("+index+")").find("option:gt(0)").remove();
	var next=$select.eq(index+1);
	if(next.length>0){
		$.get("area/getChildren/"+this.value,null,function(result){
				setArea(next,0,result.data);
		},'json');
	}
})
//查看/编辑
function openUpdate(id, type){
	var modalNode = $("#addStore");
	modalNode.modal("show");
	var addEnterpriseForm = modalNode.find('form');
	$.get("store/view/"+id,null,function(result){
		if(result.success){
			var form=$("#addStore form");
			form.find("input[name=name]").attr("data-id",result.data.store.id).val(result.data.store.name);
			form.find("input[name=code]").val(result.data.store.code);
			form.find("input[name=linkman]").val(result.data.store.linkman);
			form.find("input[name=phone]").val(result.data.store.phone);
			form.find("input[name=address]").val(result.data.store.address);
			form.find("input[name=longitude]").val(result.data.store.longitude);
			form.find("input[name=latitude]").val(result.data.store.latitude);
			form.find("select[name=businessStatus]").val(result.data.store.businessStatus);
			//处理商户
			var _select=$("#addStore form .merchant select");
			_select.empty().append('<option value="" selected>请选择所属商家</option>')
			for(var i in result.data.merchants){
				var merchant=result.data.merchants[i];
				_select.append('<option value="'+merchant.id+'" '+(merchant.id==result.data.store.merchantId?'selected':'')+'>'+merchant.name+'</option>');
			}
//			//处理地图
//			if(result.data.store.longitude){
//				var point =new BMap.Point(result.data.store.longitude,result.data.store.latitude);
//				addMarker(point);
//				map.setCenter(point);
//			}
			//处理地区
			if(result.data.province){
				setArea(form.find("select[name=province]"),result.data.store.province,result.data.province,"省份");
			}
			if(result.data.city){
				setArea(form.find("select[name=city]"),result.data.store.city,result.data.city,"城市");
			}
			if(result.data.area){
				setArea(form.find("select[name=area]"),result.data.store.area,result.data.area,"地区");
			}
		}else{
			alert("失败，请重试");
		}
	},"json");
}
/**
 * 删除数据
 * @param obj
 */
function doDelete(obj){
	var id = $(obj).attr("data-id");
	var contentTitle = $(obj).parent().prevAll(".sName").text();
	$.confirmDailog({
		confirm : function(){
			$.get("store/del/"+id, null, function (msg) {
				if(msg.success){
					sendMsg(true, "删除成功");
					oTable.ajax.reload();
				}else{
					sendMsg(false, "删除失败，请重试");
				}
			},'json');
		},
		target : contentTitle
	});
}
/**
 * 添确认按钮
 */
$("#addItem").click(function(){
	if(!form.validate()){
		return false;
	}
	//获取表单基本元素对象
	var formArray = $("#addStore").find("form").serializeArray();
	var formObject = new Object();
	$.each(formArray,function(index){
		if(formObject[this['name']]){
			formObject[this['name']] = formObject[this['name']]+","+this['value'];
		}else{
			formObject[this['name']] = this['value'];
		}
	});
	var id=$("#addStore form input[name=name]").attr("data-id");
	if(id){
		formObject.id=id;
	}
	//提交
	$.ajax({
		type: "post",
		url: "store/save",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(formObject),
		dataType: "json",
		success: function (result) {
			if(result.success){
				sendMsg(true, "添加成功");
				$("#addStore").modal("hide");
				//刷新
				oTable.ajax.reload();
			}else{
				sendMsg(false,result.errorMsg);
			}
		},
		error: function (result) {
			alert('失败！失败原因：'+result.msg);
		}
	});
});

function addMarker(point){
	marker = new BMap.Marker(point);    
	map.addOverlay(marker); 
}
function removeMarker(){
	map.clearOverlays();
	marker=null;
	$("#addStore form input[name=longitude]").val("");
	$("#addStore form input[name=latitude]").val("");
}
function setArea($select,value,data){
	$select.find("option:gt(0)").remove();
	for(var i in data){
		$select.append('<option value="'+data[i].id+'" '+(value==data[i].id?'selected':'')+' >'+data[i].title+'</option>');
	}
}