var isLinked=$("#currentId").val();
var oTable= $("#store").find('[data-ride="datatables"]').DataTable( {
	"ajax": {
		"url": "terminal/list",
		data:function(d){
			if(isLinked){
				d.storeId=$("#currentId").val();
			}
			return d;
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
		{ "data": "snCode","className":"snCode" },
		{ "data": "storeName" },
		{ "data": "storeCode" },
		{ "data": "info" },
		{ "data": "createTime" },
		{ "data": null }
	],
	"columnDefs": [
		{
			"render": function(oObj, type, full, meta ) {
				var ret ="";
				if(isLinked){
					ret+='<a href="javascript:openUpdate(\''+full[ 'id' ]+'\',1)">编辑</a>';
				}else{
					ret+='<a href="terminal.html" class="changePage" current-id="'+full['storeId']+'">跳转到门店</a>';
				}
				ret+= '<a href="javascript:;" onclick="javascript:doDelete(this)" data-id="'+full['id']+'">删除</a>';
				if(full['gateway']=='01'){
					ret+='<a href="javascript:;" onclick="javascript:resetPwd(this)" data-id="'+full['id']+'">重置秘钥</a>';
				}
				return ret
				},
			"sortable": false,
			"targets": 5
		}
	]

} );

if(isLinked){
	//加载企业信息
	$("#addTerminal").on("show.bs.modal", function () {
		if (!$("#addTerminal").hasClass("hasDic")) {
			loadDictionary();
		}
	});
	/**
	 * 加载字典数据
	 */
	function loadDictionary() {
		$.get("terminal/getComb", function (data) {
			if (data.success) {
				$.each(data.data, function (i, n) {
					var thisSelect = $("select[code='" + i + "']");
					$.each(n, function (j, o) {
						var optionNode = $("<option>").attr("value", o.id).text(o.name);
						thisSelect.append(optionNode);
					});
					if($.type(thisSelect.attr('data-val')) != "undefined"){
						thisSelect.val(thisSelect.attr('data-val'));
					}
				});
	
				$("#addTerminal").addClass("hasDic");
			}
		}, "json");
	}
	//获取门店信息
	$.get("terminal/getStoreInfo/"+$("#currentId").val(),null,function(result){
		var form=$("#addTerminal form"); 
		if(result.success){
			form.find("input:first").val(result.data.merchantName);
			form.find("input:eq(1)").val(result.data.storeName);
			form.find("input:eq(2)").val(result.data.storeCode);
		}
	},'json')
	//验证
	var form = $("#addTerminal").find("form").parsley();
	$("#addTerminal").on('hidden.bs.modal', function (e) {
		form.reset();
		$("#addTerminal form :input[name=snCode]").attr("data-id","").val("");
		$("#addTerminal form :input[name=info]").val("");
        $("#tcode").val("");
	});
}else{
	$("#toAdd").remove();
}
//查看/编辑
function openUpdate(id, type){
	var modalNode = $("#addTerminal");
	modalNode.modal("show");
	$.get("terminal/view/"+id,null,function(result){
		if(result.success){
			var form=$("#addTerminal form");
			var terminal = result.data.terminal;
			form.find("input[name=snCode]").attr("data-id",terminal.id).val(terminal.snCode);
			$("#tcode").val(terminal.terminalCode);
			form.find("select[name=supplierId] option[value="+terminal.supplierId+"]").attr("selected","selected");
			form.find("select[name=type] option[value="+terminal.type+"]").attr("selected","selected");
			form.find("select[name=gateway] option[value="+terminal.gateway+"]").attr("selected","selected");
		}else{
			alert("失败，请重试");
		}
	},"json");
}
function resetPwd(obj){
	var contentTitle = $(obj).parent().prevAll(".snCode").text();
	var id = $(obj).attr("data-id");
	$.confirmDailog({
		confirm : function(){
			$.get("terminal/resetPwd/"+id, null, function (msg) {
				if(msg.success){
					sendMsg(true, "重置成功");
					oTable.ajax.reload();
				}else{
					sendMsg(false, "重置失败:"+msg.errorMsg);
				}
			},'json');
		},
		title:"重置确认",
		target:contentTitle,
		content:"你确定要重置<b>{target}</b>秘钥吗？"
	});
}
/**
 * 删除数据
 * @param obj
 */
function doDelete(obj){
	var id = $(obj).attr("data-id");
	var contentTitle = $(obj).parent().prevAll(".snCode").text();
	$.confirmDailog({
		confirm : function(){
			$.get("terminal/del/"+id, null, function (msg) {
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
	var formArray = $("#addTerminal").find("form").serializeArray();
	var formObject = new Object();
	$.each(formArray,function(index){
		if(formObject[this['name']]){
			formObject[this['name']] = formObject[this['name']]+","+this['value'];
		}else{
			formObject[this['name']] = this['value'];
		}
	});
	var id=$("#addTerminal form input[name=snCode]").attr("data-id");
	formObject.storeId=$("#currentId").val();
	if(id){
		formObject.id=id;
	}
	//提交
	$.ajax({
		type: "post",
		url: "terminal/save",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(formObject),
		dataType: "json",
		success: function (result) {
			if(result.success){
				sendMsg(true, formObject.id?"编辑成功":"添加成功");
				$("#addTerminal").modal("hide");
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