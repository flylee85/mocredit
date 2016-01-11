// datepicker
$(".datepicker").each(function () {
	var $this=$(this);
	$this.datepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		language: 'zh-CN',
		todayHighlight: true
	});
});
$('#date1').datepicker().on('hide', function (e) {
	$('#date2').datepicker('setStartDate', e.date);
});
$('#date2').datepicker().on('hide', function (e) {
	$('#date1').datepicker('setEndDate', e.date);
});
var oTable = $('[data-ride="datatables"]').DataTable( {
	"ajax": {
		"url": "contract/list",
		 type: "post",
		 data:function(d){
			 d.enterpriseId=$("#currentId").val();
		 }
	},
	"processing": true,
    "serverSide": true,
    "pageLength": 10,
    "pagingType": "full_numbers",
    "searchDelay": 500,
	"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
	"autoWidth":true,
	//"pageLength": 5,
	//"serverSide":true,
	"columns": [
		{ "data": "code" },
		{ "data": "name" ,"className":"htName"},
		{ "data": "enterpriseName" },
		{ "data": "signature" },
		{ "data": null }
	],
	"columnDefs": [
		{
			"render": function(oObj, type, full, meta ) {
				return '<a href="javascript:;" onclick="openUpdate(\''+full["id"]+'\')" data-toggle="modal">编辑</a>'
					+ '<a href="javascript:;" onclick="doDelete(this)" data-id="'+full["id"]+'">删除</a>'
					+ '<a href="merchant.html" class="changePage toContract" current-id="'+full[ 'id' ]+'">商户</a>';
			},
			"sortable": false,
			"targets": 4
		}
	]
} );
$("#contractAdd").click(function(){
	$.get("merchant/all",null,function(result){
		var merchantDiv=$(" #addContract form .merchants");
		for(var index in result.data){
			var merchant=result.data[index];
			merchantDiv.append('<input type="checkbox" name="merchants" value="'+merchant.id+'" '+(merchant.selected?'checked':'')+'>'+merchant.name);
		}
	},'json');
})
//获取门店信息
$.get("enterprise/view/"+$("#currentId").val(),null,function(result){
	var form=$("#addContract form"); 
	if(result.success){
		form.find("input[name=enterpriseName]").val(result.data.name);
	}
},'json')
//验证
var form = $("#addContract").find("form").parsley();
$("#addContract").on('hidden.bs.modal', function (e) {
	form.reset();
	$("#addContract form :input[name=name]").attr("data-id","");
	$("#addContract form :input")
	 .not(':button, :submit, :reset,input[name=enterpriseName]')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
	$("#addContract .merchants").empty();
});
/**
 * 删除数据
 * @param obj
 */
function doDelete(obj){
	var id = $(obj).attr("data-id");
	var contentTitle = $(obj).parent().prevAll(".htName").text();
	$.confirmDailog({
		confirm : function(){
			$.get("contract/del/"+id, null, function (msg) {
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
	// 查看/编辑
function openUpdate(id){
	var modalNode = $("#addContract");
	modalNode.modal("show");
	var addContractForm = modalNode.find('form');
	$.get("contract/view/"+id,null,function(result){
		if(result.success){
			var form=$("#addContract form");
			form.find("input[name=name]").attr("data-id",result.data.contract.id).val(result.data.contract.name);
			form.find("input[name=code]").val(result.data.contract.code);
			form.find("input[name=startTime]").val(result.data.contract.startTime);
			form.find("input[name=endTime]").val(result.data.contract.endTime);
			form.find("input[name=signature]").val(result.data.contract.signature);
			for(var index in result.data.merchants){
				var merchant=result.data.merchants[index];
				for(var i in result.data.contract.merchantList){
					var selectedMerchnat=result.data.contract.merchantList[i];
					if(merchant.id==selectedMerchnat.merchantId){
						merchant.selected=true;
						break;
					}
				}
			}
			var merchantDiv=$(".merchants",form);
			for(var index in result.data.merchants){
				merchantDiv.append('<input type="checkbox" name="merchants" value="'+result.data.merchants[index].id+'" '+(result.data.merchants[index].selected?'checked':'')+'>'+result.data.merchants[index].name);
			}
		}else{
			alert("失败，请重试");
		}
	},"json");
}
$(".add .backToList").click(function(){
	$("#addContract").modal("hide");
})
/**
 * 添确认按钮
 */
$("#addItem").click(function(){
	if(!form.validate()){
		return false;
	}
	//获取表单基本元素对象
	var formArray = $("#addContract").find("form").serializeArray();
	var formObject = new Object();
	$.each(formArray,function(index){
		if(formObject[this['name']]){
			formObject[this['name']] = formObject[this['name']]+","+this['value'];
		}else{
			formObject[this['name']] = this['value'];
		}
	});
	var id=$("#addContract form input[name=name]").attr("data-id");
	if(id){
		formObject.id=id;
	}
	console.log(11);
	formObject.enterpriseId=$("#currentId").val();
	var merchantList=[];
	$("#addContract form input[name=merchants]:checked").each(function(){
		var $this=$(this);
		var merchant=new Object();
		merchant.merchantId=$this.val();
		merchantList.push(merchant);
	})
	formObject.merchantList=merchantList;
	//提交
	$.ajax({
		type: "post",
		url: "contract/save",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(formObject),
		dataType: "json",
		success: function (result) {
			if(result.success){
				sendMsg(true, "添加成功");
				$("#addContract").modal("hide");
				//刷新
				oTable.ajax.reload();
			}else{
				sendMsg(false, result.errorMsg);
			}
		},
		error: function (result) {
			alert('失败！失败原因：'+result.msg);
		}
	});
});
