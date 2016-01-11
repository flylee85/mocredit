var oTable = $('table[data-ride="datatables"]').DataTable( {
		"ajax": {
			"url": "enterprise/list",
			 type: "post",
			 data:function(data){
				 var query = $("#queryEnterprise").serializeArray();
				 for(var i in query){
					 if(query[i].value){
						 data[query[i].name]=query[i].value;
					 }
				 }
				 return data;
			 }
		},
		 "processing": true,
         "serverSide": true,
         "pageLength": 10,
         "pagingType": "full_numbers",
         "searching":false,
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"serverSide":true,
		"columns": [
			{ "data": "name", "className":"qiyeName" },
			{ "data": "createTime" },
			{ "data": "contractCount" },
			{ "data": null},
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, type, full ) {
					return '<a href="javascript:openUpdateEnterprise(\''+full[ 'id' ]+'\',1)">编辑</a>'
						+ '<a href="#" onclick="javascript:doDelete(this)" data-id="'+full['id']+'">删除</a>'
						+ '<a href="contract.html" class="changePage toContract" current-id="'+full[ 'id' ]+'">合同</a>'
				},
				"sortable": false,
				"targets": 4
			},
			{
				"render": function(oObj, type, full ) {
					return 0==full['activityCount']?"0":'<a href="activity.html#'+full[ 'id' ]+'" target="_blank">'+full['activityCount']+'</a>';
				},
				"sortable": false,
				"targets": 3
			}
		]

});
$("#searchBtn").click(function(){
	oTable.ajax.reload();
})
$(".datepicker").each(function () {
	var $this=$(this);
	$this.datepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		language: 'zh-CN',
		todayHighlight: true,
		todayBtn:"linked"
	});
});
// 验证
var form = $("#addEnterprise").find("form").parsley();
$("#addEnterprise").on('hidden.bs.modal', function (e) {
	form.reset();
	$("#addEnterprise form :input[name=name]").attr("data-id","");
	$("#addEnterprise form :input")
	 .not(':button, :submit, :reset')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
});

// 查看/编辑
function openUpdateEnterprise(id, type){
	var modalNode = $("#addEnterprise");
	modalNode.modal("show");
	var addEnterpriseForm = modalNode.find('form');
	if(type == "0"){
		addEnterpriseForm.find("input, select, textarea").prop("disabled",true);
		modalNode.find('div.add').hide();
		modalNode.find('div.look').show();
	}else{
		addEnterpriseForm.find("input, select, textarea").prop("disabled",false);
		modalNode.find('div.add').show();
		modalNode.find('div.look').hide();
	}
	$.get("enterprise/view/"+id,null,function(result){
		if(result.success){
			var form=$("#addEnterprise form");
			form.find("input[name=name]").attr("data-id",result.data.id).val(result.data.name);
			form.find("input[name=linkman]").val(result.data.linkman);
			form.find("input[name=linkphone]").val(result.data.linkphone);
			form.find("input[name=linkaddress]").val(result.data.linkaddress);
			
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
			$.get("enterprise/del/"+id, null, function (msg) {
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
	var formArray = $("#addEnterprise").find("form").serializeArray();
	var formObject = new Object();
	$.each(formArray,function(index){
		if(formObject[this['name']]){
			formObject[this['name']] = formObject[this['name']]+","+this['value'];
		}else{
			formObject[this['name']] = this['value'];
		}
	});
	var id=$("#addEnterprise form input[name=name]").attr("data-id");
	if(id){
		formObject.id=id;
	}
	//提交
	$.ajax({
		type: "post",
		url: "enterprise/save",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(formObject),
		dataType: "json",
		success: function (result) {
			sendMsg(true, "添加成功")
			$("#addEnterprise").modal("hide");
			//刷新
			oTable.ajax.reload();
		},
		error: function (result) {
			alert('失败！失败原因：'+result.msg);
		}
	});
});
$(".backToList").click(function(){
	$("#addEnterprise").modal("hide");
})
$("#addEnterprise .toModify").click(function(){
	$("#addEnterprise .look").hide();
	$("#addEnterprise .add").show();
	$("#addEnterprise input").attr("disabled",false);
	
})