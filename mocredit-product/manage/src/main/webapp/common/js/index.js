$("body").on("click", "a.changePage", function(event){
	event.preventDefault();
	if($(this).attr("current-id")){
		$('#currentId').val($(this).attr("current-id"));
	}else{
		$('#currentId').val("");
	}
	$("#content").load($(this).attr("href"),null,function(){
		if($('#currentId').val().length==0){
			$("ul.breadcrumb .null-hide").hide();
		}
	});
	if($(this).parents("li.dropdown-submenu").length > 0){
		$(this).parents("li.dropdown-submenu").addClass("active").siblings().removeClass("active");
	}
});

/**
 * ajax全局设置
 */
$.ajaxSetup({

	timeout: 6000,
	beforeSend : function () {
		$.loading();
	},
	complete :  function(){
		$('#loading').remove();
	},
	cache: false,
	error: function (XMLHttpRequest, textStatus, errorThrown) {
		if(textStatus == "timeout"){
			sendMsg(false, '超时！请求无响应');
		}else{
			sendMsg(false, '错误未知');
		}

	}
});


$.extend({
	confirmDailog : function(options){
		var opt = $.extend({}, $.confirmDailog.default, options);
		var confirmNode = $(".confirmDialog");
		var content = opt.content.replace("{target}", opt.target);
		if(confirmNode.length == 0){
			var str = opt.str.replace("{title}", opt.title).replace("{content}", content);
			$("#content").append(str);
			confirmNode = $(".confirmDialog");
			var btn = $("<button type='button'>").addClass("btn btn-primary").text("确认");
			confirmNode.modal("show");
			btn.appendTo(confirmNode.find(".modal-footer"));
			btn.off().click(function(){
				confirmNode.modal("hide");
				opt.confirm.call(this);
			});
		}else{
			confirmNode.modal("show");
			confirmNode.find(".modal-title").text(opt.title);
			confirmNode.find(".modal-body p").html(content);
			confirmNode.find(".modal-footer").find("button.btn-primary").off().click(function(){
				confirmNode.modal("hide");
				opt.confirm.call(this);
			});
		}
	},
	loading : function (options) {
		var opt = $.extend({
			text : "正在请求"
		}, options || {});
		var loading = $('<div id="loading">');
		loading.append($('<span>').text(opt.text));
		var removeBtn = $('<button type="button" class="close"><i class="icon-remove"></i></button>')
		removeBtn.off().click(function () {
			loading.remove();
		});
		$('body').append(loading.append(removeBtn));

	}
});

$.confirmDailog.default = {
	str : '<div class="modal fade confirmDialog" data-backdrop="static">\
			<div class="modal-dialog modal-sm">\
				<div class="modal-content">\
					<div class="modal-header">\
						<button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span></button>\
						<h4 class="modal-title">{title}</h4>\
					</div>\
					<div class="modal-body">\
						<p>{content}</p>\
					</div>\
					<div class="modal-footer">\
						<button data-dismiss="modal" class="btn btn-default" type="button">取消</button>\
					</div>\
					</div>\
				</div>\
			</div>',
	title : "确认",
	content : "您确定要删除 <b>{target}</b> 吗？",
	target : "",
	confirm : null
}


/**
 * 发送消息
 * @param isOk 是否成功
 * @param msg
 * @param callback
 * @returns
 */
function sendMsg(isOk,msg,callback){
	if(isOk){
		$("#operaState .modal-body i").removeClass("icon-fail").addClass("icon-success");
	}else{
		$("#operaState .modal-body i").removeClass("icon-success").addClass("icon-fail");
	}
	$("#operaState").find("#promptMsg").parent().html('<label id="promptMsg"></label>');
	var msgArray = msg.split("！");
	if(msgArray.length>1){
		for(var i=0;i<msgArray.length;i++){
			if(i==0){
				$("#operaState").find("#promptMsg").html(msgArray[0]+"！\n");
			}else if(i==msgArray.length-1){
				$("#operaState").find("#promptMsg").parent().append('<label>'+msgArray[i]+'</label>');
			}else{
				$("#operaState").find("#promptMsg").parent().append('<label>'+msgArray[i]+'！</label>');
			}
		}
	}else{
		$("#operaState").find("#promptMsg").html(msg);
	}

	$("#operaState").modal("show");
	if(callback!=null){
		window.setTimeout(callback, 1000);
		window.setTimeout('$("#operaState").modal("hide")', 1000);
	}else{
		window.setTimeout('$("#operaState").modal("hide")', 1000);
	}
}