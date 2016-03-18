if($("#currentId").val()!=""){
	$(".addMerchant").hide();
}
oTable= $("#shangjia").find('[data-ride="datatables"]').DataTable( {
	"ajax": {
		"url": "merchant/list",
		type:"post",
		data:function(d){
			 d.contractId=$("#currentId").val();
		 }
	},
	 "processing": true,
     "serverSide": true,
     "pageLength": 10,
     "pagingType": "full_numbers",
     "searchDelay": 500,
	"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
	"paginationType": "full_numbers",
	"autoWidth":true,
	//"pageLength": 5,
	//"serverSide":true,
	"columns": [
		{ "data": "name","className":"mName" },
		{ "data": "createTime" },
		{ "data": "linkphone" },
		{ "data": "areaCount" },
		{ "data": "storeCount" },
		{ "data": null},
		{ "data": null},
		{ "data": null},
		{ "data": null},
		{ "data": null }
	],
	"columnDefs": [
		{
			"render": function(oObj, type, full, meta ) {
				var storeInfo=full['storeInfo'];
				if(storeInfo){
					return storeInfo['businessStatus1']?storeInfo['businessStatus1']:0;
				}
				return 0;
			},
			"sortable": false,
			"targets": 5
		},
		{
			"render": function(oObj, type, full, meta ) {
				var storeInfo=full['storeInfo'];
				if(storeInfo){
					return storeInfo['businessStatus2']?storeInfo['businessStatus2']:0;
				}
				return 0;
				},
			"sortable": false,
			"targets": 6
		},
		{
			"render": function(oObj, type, full, meta ) {
				var storeInfo=full['storeInfo'];
				if(storeInfo){
					return storeInfo['businessStatus3']?storeInfo['businessStatus3']:0;
				}
				return 0;
				},
			"sortable": false,
			"targets": 7
		},
		{
			"render": function(oObj, type, full, meta ) {
				var storeInfo=full['storeInfo'];
				if(storeInfo){
					return storeInfo['businessStatus4']?storeInfo['businessStatus4']:0;
				}
				return 0;
				},
			"sortable": false,
			"targets": 8
		},
		{
			"render": function(oObj, type, full, meta ) {
				return '<a href="javascript:openUpdate(\''+full[ 'id' ]+'\',1)">编辑</a>'
				+ '<a href="#" onclick="javascript:doDelete(this)" data-id="'+full['id']+'">删除</a>'
				+ '<a href="store.html" class="changePage toContract" current-id="'+full[ 'id' ]+'">门店</a>'
				},
			"sortable": false,
			"targets": 9
		}
	]

} );
//验证
var form = $("#addShangjia").find("form").parsley();
$("#addShangjia").on('hidden.bs.modal', function (e) {
	form.reset();
	$("#addShangjia form :input[name=name]").attr("data-id","");
	$("#addShangjia form :input")
	 .not(':button, :submit, :reset')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
});
//查看/编辑
function openUpdate(id, type){
	var modalNode = $("#addShangjia");
	modalNode.modal("show");
	var addEnterpriseForm = modalNode.find('form');
	$.get("merchant/view/"+id,null,function(result){
		if(result.success){
			var form=$("#addShangjia form");
			form.find("input[name=name]").attr("data-id",result.data.id).val(result.data.name);
			form.find("#mcode").val(result.data.code);
			form.find("input[name=linkman]").val(result.data.linkman);
			form.find("input[name=linkphone]").val(result.data.linkphone);
			form.find("input[name=address]").val(result.data.address);
			form.find("input[name=descr]").val(result.data.descr);
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
	var contentTitle = $(obj).parent().prevAll(".mName").text();
	$.confirmDailog({
		confirm : function(){
			$.get("merchant/del/"+id, null, function (msg) {
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
	var formArray = $("#addShangjia").find("form").serializeArray();
	var formObject = new Object();
	$.each(formArray,function(index){
		if(formObject[this['name']]){
			formObject[this['name']] = formObject[this['name']]+","+this['value'];
		}else{
			formObject[this['name']] = this['value'];
		}
	});
	var id=$("#addShangjia form input[name=name]").attr("data-id");
	if(id){
		formObject.id=id;
	}
	//提交
	$.ajax({
		type: "post",
		url: "merchant/save",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(formObject),
		dataType: "json",
		success: function (result) {
			if(result.success){
				sendMsg(true, formObject.id?"编辑成功":"添加成功");
				$("#addShangjia").modal("hide");
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
