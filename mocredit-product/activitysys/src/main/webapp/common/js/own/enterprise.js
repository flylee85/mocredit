var oTable = $('table[data-ride="datatables"]').DataTable( {
		"processing": true,
		"ajax": {
			"url": "common/js/data/enterprise.json",
			"dataSrc": "aaData"
		},
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"pageLength": 10,
		//"serverSide":true,
		"columns": [
			{ "data": "qiyeName", "className":"qiyeName" },
			{ "data": "createTime" },
			{ "data": "contractCount" },
			{ "data": "activityCount" },
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, type, full ) {
					return '<a href="javascript:openUpdateEnterprise(\''+full[ 'id' ]+'\',0)">查看</a>'
						+ '<a href="javascript:openUpdateEnterprise(\''+full[ 'id' ]+'\',1)">编辑</a>'
						+ '<a href="#" onclick="javascript:doDelete(this)" data-id="'+full['id']+'">删除</a>'
						+ '<a href="contract.html" class="changePage toContract" data-id="'+full[ 'id' ]+'">合同</a>'
				},
				"sortable": false,
				"targets": 4
			}
		]

});
// 跳转到合同
$(".toContract").click(function(){
	var $this = $(this);
	$("#enterpriseConId").val($this.attr("data-id"));

});

// 验证
var form = $("#addEnterprise").find("form").parsley();
$("#addEnterprise").on('hidden.bs.modal', function (e) {
	form.reset();
});

// 查看/编辑
function openUpdateEnterprise(id, type){
	var modalNode = $("#addEnterprise");
	modalNode.modal("show");
	var addEnterpriseForm = modalNode.find('form');
	modalNode.find('.look').children('button').off('click').click(function () {
		modalNode.find('div.add').show();
		modalNode.find('div.look').hide();
		addEnterpriseForm.find("input, select, textarea").prop("disabled",false);
	});
	if(type == "0"){
		addEnterpriseForm.find("input, select, textarea").prop("disabled",true);
		modalNode.find('div.add').hide();
		modalNode.find('div.look').show();
	}else{
		addEnterpriseForm.find("input, select, textarea").prop("disabled",false);
		modalNode.find('div.add').show();
		modalNode.find('div.look').hide();
	}
	if(type == "0"){
		$("#addEnterprise").find("input").prop("disabled",true);
	}else{
		$("#addEnterprise").find("input").prop("disabled",false);

	}
	$.get("",{id:id},function(result){
		if(result.success){
			$("#addEnterprise").find("form").parsley().reset();



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
	var contentTitle = $(obj).parent().prevAll(".qiyeName").text();
	$.confirmDailog({
		confirm : function(){
			//$.get("", {"id" : id}, function (msg) {
			//	if(msg.success){
			//		sendMsg(true, "删除成功");
			//	}else{
			//		sendMsg(false, "删除失败，请重试");
			//	}
			//});
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
	var formArray = $("#addEnterprise").find("form").serialize();

	//提交
	$.ajax({
		type: "post",
		url: "activity/saveActivity",
		contentType: "application/json; charset=utf-8",
		data: formArray,
		dataType: "json",
		success: function (result) {
			sendMsg(true, "添加成功")
			$("#addEnterprise").modal("hide");
			//刷新
			oTable._fnAjaxUpdate();
		},
		error: function (result) {
			alert('失败！失败原因：'+result.msg);
		}
	});
});