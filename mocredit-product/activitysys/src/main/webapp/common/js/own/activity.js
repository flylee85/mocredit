$(function(){

	var jifenTable, famaTable;

	function initJifenTab(page){
		if($.type(jifenTable) != 'object'){
			jifenTable = $('#jifen').find('table[data-ride="datatables"]').DataTable( {
				"ajax": {
					url : "activitysys/queryActivityPage?type=01",
					type: "post",
				},
				"processing": true,
				"serverSide":true,
				"pageLength": 10,
				"pagingType": "full_numbers",
				"searchDelay": 500,
				"displayStart": parseInt(page) * 10 - 10,
				"autoWidth":true,
				"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
				"aaSorting":[[4, "desc"]],//默认排序
				"columns": [
					{ "data": "code","name":"code", "width" : "100px" },
					{ "data": "storeCount","name":"storeCount", "width" : "90px", "sortable":false },
					{ "data": "orderCount","name":"orderCount", "width" : "90px", "sortable":false },
					{ "data": "name", "name":"name","className" : "name", "width" : "180px"  },
					{ "data": "createtime","name":"createtime", "width" : "130px" },
					{ "data": null, "name": "endTime", "width" : "150px" },
					{ "data": null, "width" : "100px" },
					{ "data": null, "width" : "150px" }
				],
				"columnDefs": [
					{
						"targets": 5,
						"data": null,
						"render": function(data, type, full ) {
							var entTime = data['endTime'];
							return entTime.substring(0, 10);
						}
					},
					{
						"targets": 6,
						"data": null,
						"sortable": false,
						"render": function(data, type, full ) {
							var html = '<div class="switch demo3">';
							if(data[ 'status' ] == "01"){
								html 	+=	'<input type="checkbox" checked value="'+data[ 'status' ]+'" data-id="'+data['id']+'">';
							}else{
								html 	+=	'<input type="checkbox" value="'+data[ 'status' ]+'" data-id="'+data['id']+'">';
							}
							html 	+=	'<label><i data-on="启用" data-off="停用"></i></label>';
							html 	+=	'</div>';
							return html;
						}
					},
					{
						"targets": 7,
						"data": null,
						"sortable": false,
						"render": function ( data, type, full ) {
							return '<a href="javascript:openUpdateJifenActivity(\''+data[ 'id' ]+'\',0)" >查看</a>'+
								'<a href="javascript:openUpdateJifenActivity(\''+data[ 'id' ]+'\',1)" >编辑</a>'+
								'<a href="#" onclick="javascript:doDelete(this)" data-id="'+data['id']+'">删除</a>';
						}
					},
				]
			});
		}
	}

	function initFamaTab(page){
		if($.type(famaTable) != 'object') {
			famaTable = $('#fama').find('table[data-ride="datatables"]').DataTable({
				"ajax": {
					url: "activitysys/queryActivityPage?type=02",
					type: "post",
				},
				"processing": true,
				"serverSide": true,
				"autoWidth": true,
				"searchDelay": 500,
				"displayStart": parseInt(page) * 10 - 10,
				"pagingType": "full_numbers",
				"pageLength": 10,
				"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
				"aaSorting":[[4, "desc"]],//默认排序
				"columns": [
					{"data": "code", "name":"code","width": "70px"},
					{"data": "storeCount", "name":"storeCount","width": "90px", "sortable": false},
					{"data": "orderCount", "name":"storeCount", "width": "90px", "sortable": false},
					{"data": "name", "name":"name","className": "name", "width": "200px"},
					{"data": "createtime","name":"createtime", "width": "180px"},
					{"data": null, "name":"endTime","width": "130px"},
					{"data": null, "width": "70px"},
					{"data": null, "width": "100px"},
					{"data": null, "width": "170px"}
				],
				"columnDefs": [
					{"searchable": false, "targets": [1, 2]},
					{
						"targets": 5,
						"data": null,
						"render": function(data, type, full ) {
							var entTime = data['endTime'];
							return entTime.substring(0, 10);
						}
					},
					{
						"targets": 6,
						"data": null,
						"sortable": false,
						"render": function (data, type, full) {
							var html = '<div class="switch demo3">';
							if (data['status'] == "01") {
								html += '<input type="checkbox" checked value="' + data['status'] + '" data-id="' + data['id'] + '">';
							} else {
								html += '<input type="checkbox" value="' + data['status'] + '" data-id="' + data['id'] + '">';
							}
							html += '<label><i data-on="启用" data-off="停用"></i></label>';
							html += '</div>';
							return html;
						}
					}, {
						"targets": 7,
						"data": null,
						"sortable": false,
						"render": function (data, type, full) {
							return '<a href="javascript:openFama(\'' + data['id'] + '\',\'' + data['sendSmsType'] + '\')" >发码</a>';
						}
					}, {
						"targets": 8,
						"data": null,
						"sortable": false,
						"render": function (data, type, full) {
							return '<a href="javascript:openUpdateFamaActivity(\'' + full['id'] + '\',0)" >查看</a>' +
								'<a href="javascript:openUpdateFamaActivity(\'' + full['id'] + '\',1)" >编辑</a>' +
								'<a href="#" onclick="javascript:doDelete(this)" data-id="' + full['id'] + '">删除</a>';
						}
					}
				]
			});
		}
	}
	/**
	 * 初始化积分表格
	 */
	$('#tabToJifen').on('show.bs.tab', function (e) {
		initJifenTab(1);
	});
	/**
	 * 初始化发码表格
	 */
	$('#tabToFama').on('show.bs.tab', function (e) {
		initFamaTab(1);
	});

	if($('#activityCurrentType').val() == "2"){
		initFamaTab($('#activityCurrentPage').val());
		$("#tabToFama").parent().addClass('active').siblings().removeClass('active');
		$('#fama').addClass('active in').siblings().removeClass('active in');
	}else if($('#activityCurrentType').val() == "1"){
		initJifenTab($('#activityCurrentPage').val());
	}
	// 添加积分活动按钮
	$('#openAddJifen').click(function () {
		$("#activityCurrentId").val("");
		$("#activityCurrentType").val(1);
		$("#activityDetailType").val(1);
		$("#activityCurrentPage").val(1);
		$("#content").load('jifen.html');
		/*$("#addActivityJifen").modal('show');
		$("#addActivityJifenForm").find("input, select, textarea,button").prop("disabled",false);
		$("#addActivityJifenForm")[0].reset();
		$("#addActivityJifenForm input[name='id']").val("");
		$("#addActivityJifenForm").find(".chooseShop").text("选择门店").addClass("popEnter").removeClass('popFloat');
		$("#addActivityJifen").find('.look').hide();
		$("#addActivityJifen").find('.add').show();*/


	});

	// 添加发码活动按钮
	$('#openAddFama').click(function () {
		$("#activityCurrentId").val("");
		$("#activityCurrentType").val(2);
		$("#activityDetailType").val(1);
		$("#activityCurrentPage").val(1);
		$("#content").load('fama.html');
		/*$("#addActivityFama").modal('show');
		$("#addActivityFamaForm").find("input, select, textarea,button").prop("disabled",false);
		$("#addActivityFamaForm")[0].reset();
		$("#addActivityFamaForm input[name='id']").val("");
		$("#addActivityFamaForm").find(".chooseShop").text("选择门店").addClass("popEnter").removeClass('popFloat');
		$("#addActivityFama").find('.look').hide();
		$("#addActivityFama").find('.add').show();*/
	});


	// 查询发码进度
	function intervalUpdate() {
		$.ajax({
			'url': 'activitysys/getProcessNumber',
			'timeout':9999999,
		}).done(function(msg){
			if(msg.result){// 失败

			}
			$(".progress-fama").css("width", msg.data); // 进度百分比
			clearInterval(timer);
		});
	}

	// 重新发码
	$("#famaState").on("click", "#reFama", function(){
		$(this).text("正在重新发码").addClass("disabled");
		$(".progress-fama").replace("progress-bar-danger", "progress-bar-info").addClass("progress-bar-striped active").css("width:1%");
		intervalUpdate();
		timer = setInterval(intervalUpdate(), 5000);

	});


	// 活动启用停用
	$(".tab-content").on("click", ".switch :checkbox", function(){
		var isCheck = $(this).prop("checked");
		if(isCheck){
			changeActivityStatus($(this).attr("data-id"),"01");
		}else{
			changeActivityStatus($(this).attr("data-id"),"02");
		}
	});


	var jiexiTimer, famaTimer,famaTimer1,famaTimer2;
	var $input = $("#importExcel");
	$input.fileinput({
		uploadUrl: "activitysys/importCustomerFile", // server upload action
		language: 'zh',
		uploadAsync: true,
		showUpload: false, // hide upload button
		showRemove: false, // hide remove button
		showPreview: false,
		//allowedFileExtensions : ['xls','xlsx'],
		msgInvalidFileExtension: "{name}" + "{extensions}" ,
		minFileCount: 1,
		maxFileCount: 5,
		showCaption: false
	}).on("fileerror", function(event, data){
		console.log(data.id);
		console.log(data.index);
		console.log(data.file);
		console.log(data.reader);
		console.log(data.files);
	}).on("filebatchselected", function(event, files) {
		for(var i=0;i<files.length;i++){
			var file = files[i];
			var fileName = file.name;
			var index1 = fileName.lastIndexOf(".");  
			var index2= fileName.length;
			var suffix=fileName.substring(index1,index2);
			if(suffix!='.xls'&&suffix!='.xlsx'){
				sendMsg(false, "请选择正确的Excel格式文件");
				//清除进度条
				$('#kv-upload-progress').empty();
				return false;
			}
		}
		$(".progressWrap").append('<label class="famaTip" >正在上传 <i>......</i></label>');
		$('#timaModal').find('.close').prop('disabled', true);
		$('#remark').prop("disabled", true);
		$input.fileinput("upload");

	}).on('filebatchuploadcomplete', function(event, data) {

		$input.fileinput("disable");
		$(".all").animate({
			marginTop : "40px"
		},500);
		var either = showSucTip({
			progressed : "20%",
			progressedText : "上传成功",
			title : "正在解析",
			left : '99px',
			time : 1
		});
		var progressSum = 1;
		jiexiTimer = setInterval(function () {
			progressSum = progressSum + 0.2;
			either.css("width", progressSum + '%');
			if(progressSum >= 79){
				clearInterval(jiexiTimer);
			}
		}, 200);
	}).on("filebatchuploadsuccess", function(event, data, previewId, index) {
		var response   = data.response;
		var sendSmsType = $("#timaModal").find("input[name='sendSmsType']").val();
		var proWrap = $("#kv-upload-progress");
		proWrap.attr('data-id', response.data);
		$.ajax({
			url : 'activitysys/parseCustomerFile',
			type: 'post',
			data : {'activityId':$("#timaModal").find("input[name='activityId']").val(),'remark':$("#remark").val(),'importId':response.data},
			dataType : 'json',
			timeout:600000,
			beforeSend: function () {

			},
			complete : function () {
				$input.fileinput("enable");
				$('#remark').prop("disabled", false);
			},
			success : function (msg) {
				$('#timaModal').find('.close').prop('disabled', false);

				var data = msg.data;
				if(msg.success){
					clearInterval(jiexiTimer);
					var proNode = $("#kv-upload-progress .progress");
					proNode.find(".progress-bar-info").removeClass('progress-bar-striped').css("width", "80%");
					$('.progressWrap').children('.famaTip').html('联系人导入成功');
					
					var famaBtn = '<button class="btn btn-info btn-small" onclick="sendCode(\''+response.data+'\', this)" type="button">短信发码</button>';
					var daochuBtn = '<button class="btn btn-info btn-small" onclick="exportCode(\''+response.data+'\', this)" type="button">导出短信</button>';
					var remark = "";
					if($("#remark").val() != ""){
						remark = '<div class="row"><span class="col-lg-10" style="margin-top: -14px; font-size: 11px;">描述: '+$("#remark").val()+'</span></div>'
					}else{
						remark = "";
					}
					var successStr = temp.success.replace('{time}', data.sysDate).replace('{sucSum}', data.successNumber).replace('{sum}', data.importNumber).replace('{failSum}', parseInt(data.importNumber)-parseInt(data.successNumber)).replace("{remark}", remark);;
					if(sendSmsType=='02'){
						successStr = successStr.replace('{operaBtn}', famaBtn);
					}else if(sendSmsType=='01'){
						successStr = successStr.replace('{operaBtn}', daochuBtn);
					}
					var mainStr = temp.main.replace('{complate}', successStr);
					$("#famaState").prepend(mainStr);
				}else{

				}
			}
		});
	});

	// 关闭发码时重置
	$("#timaModal").on('hide.bs.modal', function () {
		$input.fileinput('reset');
		$("#kv-upload-progress").empty().removeAttr('data-id');
		$(".progressWrap").find('.all').remove();
		$(".progressWrap").find('.famaTip').remove();

	})
	
});
	// 添加多段进度条
	function showSucTip (option){
		var proNode = $("#kv-upload-progress .progress");
		proNode.find('.progress-bar').eq(2).remove();
		proNode.find('.popover').eq(1).remove();
		proNode.find(".progress-bar").last().css("width", option.progressed);

		var popStr = $('<div role="tooltip" class="popover fade bottom in">');
		popStr.append('<div class="arrow" style="left: 50%;"></div><div class="popover-content">'+option.progressedText+'</div>');

		var progressStr = $('<div class="progress-bar progress-bar-striped active progress-fama" role="progressbar" ></div>');
		if(option.time == 1){
			progressStr.addClass('progress-bar-info').css('max-width', '80%');
		}else if(option.time == 2){
			progressStr.addClass('progress-bar-primary').css('max-width', '60%');
		}

		proNode.append(progressStr).append(popStr);

		$('.progressWrap').children('.famaTip').html(option.title+' <i>......</i>');
		setTimeout(function(){
			popStr.fadeIn(300).css({
				left: option.left,
				top: "20px"
			});

		}, 1000);
		return progressStr;
	}

	// 发码列表模板
	var temp = {
	main : '<section class="panel">'+
	'<header class="panel-heading">状态</header>'+
	'<section class="panel-content">'+
	'{complate}'+
	'</section>'+
	'</section>',
	success : '<article class="media famaSuc">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-successLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'{operaBtn}'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">联系人导入成功</a>'+
	'<div class="row">'+
	'<small class="block col-lg-3">总数：<span class="badge bg-info">{sum}</span></small>'+
	'<small class="block col-lg-3">成功：<span class="badge bg-success">{sucSum}</span></small>'+
	'<small class="block col-lg-3">失败：<span class="badge bg-danger">{failSum}</span></small>'+
	'</div>'+
	'{remark}'+
	'</div>'+
	'</article>',
	success2 : '<article class="media famaSuc">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-successLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'<button class="btn btn-info btn-small" onclick="sendCode(\'{id}\', this)" type="button">短信发码</button>'+
	'<button class="btn btn-info btn-small" onclick="exportCode(\'{orderId}\', this)" type="button">导出短信</button>'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">联系人导入成功</a>'+
	'<div class="row">'+
	'<small class="block col-lg-3">总数：<span class="badge bg-info">{sum}</span></small>'+
	'<small class="block col-lg-3">成功：<span class="badge bg-success">{sucSum}</span></small>'+
	'<small class="block col-lg-3">失败：<span class="badge bg-danger">{failSum}</span></small>'+
	'</div>'+
	'{remark}'+
	'</div>'+
	'</article>',
	successDown : '<article class="media famaSuc">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-successLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'<button class="btn btn-info btn-small" onclick="exportCode(\'{id}\', this)"  type="button">导出短信</button>'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">联系人导入成功</a>'+
	'<div class="row">'+
	'<small class="block col-lg-3">总数：<span class="badge bg-info">{sum}</span></small>'+
	'<small class="block col-lg-3">成功：<span class="badge bg-success">{sucSum}</span></small>'+
	'<small class="block col-lg-3">失败：<span class="badge bg-danger">{failSum}</span></small>'+
	'</div>'+
	'{remark}'+
	'</div>'+
	'</article>',
	successDownEnd : '<article class="media famaSuc">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-successLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'<button class="btn btn-info btn-small" onclick="exportCode(\'{id}\', this)"  type="button" disabled="disabled" >已导出</button>'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">联系人导入成功</a>'+
	'<div class="row">'+
	'<small class="block col-lg-3">总数：<span class="badge bg-info">{sum}</span></small>'+
	'<small class="block col-lg-3">成功：<span class="badge bg-success">{sucSum}</span></small>'+
	'<small class="block col-lg-3">失败：<span class="badge bg-danger">{failSum}</span></small>'+
	'</div>'+
	'{remark}'+
	'</div>'+
	'</article>',
	successSend : '<article class="media famaSuc">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-successLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'<button class="btn btn-info btn-small" onclick="sendCode(\'{id}\', this)" type="button">短信发码</button>'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">联系人导入成功</a>'+
	'<div class="row">'+
	'<small class="block col-lg-3">总数：<span class="badge bg-info">{sum}</span></small>'+
	'<small class="block col-lg-3">成功：<span class="badge bg-success">{sucSum}</span></small>'+
	'<small class="block col-lg-3">失败：<span class="badge bg-danger">{failSum}</span></small>'+
	'</div>'+
	'{remark}'+
	'</div>'+
	'</article>',
	successSendEnd : '<article class="media famaSuc">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-successLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'<button class="btn btn-info btn-small" onclick="sendCode(\'{id}\', this)" type="button" disabled="disabled">已发送</button>'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">联系人导入成功</a>'+
	'<div class="row">'+
	'<small class="block col-lg-3">总数：<span class="badge bg-info">{sum}</span></small>'+
	'<small class="block col-lg-3">成功：<span class="badge bg-success">{sucSum}</span></small>'+
	'<small class="block col-lg-3">失败：<span class="badge bg-danger">{failSum}</span></small>'+
	'</div>'+
	'{remark}'+
	'</div>'+
	'</article>',
	error : '<article class="media famaError">'+
	'<div class="pull-left">'+
	'<span class="">'+
	'<i class="icon-smile icon-warningLarger"></i>'+
	'</span>'+
	'</div>'+
	'<div class="media-body">'+
	'<div class="pull-right media-mini text-right text-muted">'+
	'<strong class="h6">{time}</strong>'+
	'<div class="resetExcel">'+
	'{reBtn}'+
	'</div>'+
	'</div>'+
	'<a class="h4" href="#">{title}</a>'+
	'<div class="row">'+
	'<a href="javascript:void(0);"  class="errorDetail" data-content="{errorMsg}" data-title="失败原因">查看失败详情</a>'+
	'</div>'+
	'</div>'+
	'</article>',
};

	/**
	 * 短信发码
	 * @param orderId
	 */
	function sendCode(orderId, obj){
		var proWrap = $("#kv-upload-progress");
		if(proWrap.attr('data-id') == orderId){
			$("#importExcel").fileinput("disable");
		}else{
			$("#famaState").find('button').filter(function(){
				return $(this).attr('onclick').indexOf(proWrap.attr('data-id')) < 0;
			}).addClass("disabled");
		}
		var $this = $(obj);
		$this.text("正在发码").addClass("disabled");
		$('#timaModal').find('.close').prop('disabled', true)
		var either;

		var proNode = $("#kv-upload-progress .progress");
		var progressWrap = $('.progressWrap');

		if(proWrap.attr('data-id') == orderId){
			either = showSucTip({
				progressed: "20%",
				progressedText: "解析成功",
				title: "正在发码",
				time: 2,
				left: '222px'
			});
			var progressSum = 1;
			famaTimer = setInterval(function () {
				progressSum = progressSum + 0.05;
				either.css("width", progressSum + '%');
				if (progressSum >= 59) {
					clearInterval(famaTimer);
				}
			}, 100);
		}else {
			var proStr = $('<div class="progress"></div>');
			either = $('<div role="progressbar" class="progress-bar progress-bar-striped progress-bar-primary"></div>')
			var famaTip = '<label class="famaTip" >正在发码 <i>......</i></label>';
			progressWrap.find('.all').remove();
			progressWrap.append($('<div class="all" data-id="'+orderId+'">').append(proStr.append(either)).append(famaTip));
			if(proNode.length > 0){
				progressWrap.find('.all').css('marginTop', '40px');
			}
			var progressSum = 1;
			famaTimer = setInterval(function () {
				progressSum = progressSum + 0.1;
				either.css("width", progressSum + '%');
				if (progressSum >= 99) {
					clearInterval(famaTimer);
				}
			}, 100);

		}
		var famaSucStr = $this.parents('section.panel-content').find('article.famaSuc');
		var famaErrorStr = $this.parents('section.panel-content').find('article.famaError');
		$.ajax({
			type: "get",
			url : "activitysys/sendCode",
			dataType:'json',
			timeout : 300000,
			data:  {orderId:orderId},
			beforeSend: function () {

			},
			complete : function () {
				if(proWrap.attr('data-id') == orderId){
					$("#importExcel").fileinput("enable");
					clearInterval(famaTimer);
				}else{
					clearInterval(famaTimer1);
				}

			},
			success: function(result){
				$('#timaModal').find('.close').prop('disabled', false);
				either.css("width",'100%').removeClass('progress-bar-striped');

				var tipsText = "发码成功";
				if(result.success){
					tipsText = "发码成功";
					$this.text("短信发码");
					famaSucStr.show().find('.h4').text('发码成功');
					famaErrorStr.remove();
					//发码成功后隐藏发码按钮
					//famaSucStr.find('button').text("短信发码");
					famaSucStr.find('button').text("已发码").prop('disabled', true);
				}else{
					either.addClass('progress-bar-danger');
					tipsText = "发码失败";
					var time = $this.parents('.pull-right').find('.h6').text();
					var rebtn = '<button class="btn btn-info btn-small" type="button" onclick="sendCode(\''+orderId+'\', this)">重新发码</button>';
					var errorStr = temp.error.replace('{time}', time).replace('{errorMsg}', result.errorMsg).replace('{reBtn}', rebtn).replace('{title}', "发码失败");
					famaSucStr.hide();
					if(famaErrorStr.length <= 0){
						$this.parents('section.panel-content').append(errorStr);
					}else{
						famaErrorStr.find('button').text("重新发码");
					}
					$('.errorDetail').popover();
				}
				//$("#famaState").find('button').removeClass("disabled");
				if(proWrap.attr('data-id') == orderId){
					$this.removeClass("disabled");
				}else{
					$("#famaState").find('button').filter(function(){
						return $(this).attr('onclick').indexOf(proWrap.attr('data-id')) < 0;
					}).removeClass("disabled");
				}
				if($('div[data-id="'+orderId+'"]').is('#kv-upload-progress')){
					$('.progressWrap').children('.famaTip').html(tipsText);
				}else if($('div[data-id="'+orderId+'"]').is('.all')){
					$('.all').find('.famaTip').html(tipsText);
				}
			}
		});
	}
	// 重新选择已选择门店
	function updateChooseStore (){
		$('#shopModal').find(".selectStore").find('span').each(function (i, n) {
			var storeId = $(this).attr('data-store');
			var shopId = $(this).attr('data-id');
			$('#shopModal').find("tbody").find(":checkbox[data-id='"+shopId+"'][data-store='"+storeId+"']").prop("checked", true).parents("tr").addClass('selected');
		});
	}
	/**
	 * 删除数据
	 * @param obj
	 */
	function doDelete(obj){
		var id = $(obj).attr("data-id");
		var contentTitle = $(obj).parent().prevAll(".name").text();
		$.confirmDailog({
			confirm : function(){
				$.get("activitysys/deleteActivityById", {"id" : id}, function (msg) {
					if(msg.success){
						sendMsg(true, "删除成功");
						if($('#jifen').hasClass('active')){
							$('#jifen').find('table').DataTable().ajax.reload();
						}else{
							$('#fama').find('table').DataTable().ajax.reload();
						}
					}else{
						alert('删除失败，请重试 ' + msg.errorMsg);
						//sendMsg(false, "删除失败，请重试");
					}
				}, 'json');
			},
			target : contentTitle
		});
	}

	/**
	 * 查看发码活动
	 * @param activityId
	 * @param type
	 */
	function openUpdateFamaActivity(activityId, type){
		$("#activityCurrentId").val(activityId);
		$("#activityDetailType").val(type);
		$("#activityCurrentType").val(2);
		var page = $('#fama').find('a.current').text();
		$("#activityCurrentPage").val(page);

		$("#content").load('fama.html');

	}
	/**
	 * 查看积分活动
	 * @param activityId
	 * @param type
	 */
	function openUpdateJifenActivity(activityId, type){
		$("#activityCurrentId").val(activityId);
		$("#activityDetailType").val(type);
		$("#activityCurrentType").val(1);
		var page = $('#jifen').find('a.current').text();
		$("#activityCurrentPage").val(page);

		$("#content").load('jifen.html');

	}

	/**
	 * 活动停用/启用
	 * @param activityId
	 * @param status
	 */
	function changeActivityStatus(activityId, status){
		//提交
        $.ajax({
			type: "POST",
			dataType: "json",
			contentType: "application/json; charset=utf-8",
        	url: "activitysys/saveActivity",
        	data: JSON.stringify({id:activityId,status:status}),
        	success: function (result, textStuts) {
				if(result.success){
					var msg = "停用成功";
					if(status == "01"){
						msg = "启用成功";
					}
					sendMsg(true, msg);
				}else{
					sendMsg(false, result.errorMsg);
					//sendMsg(false, '失败！失败原因：'+result.errorMsg);
				}
        	}
        });
	}

	/**
	 * 打开活动发码窗口
	 */
	function openFama(activityId,sendSmsType){
		$("#timaModal").find("input[name='activityId']").val(activityId);
		$("#timaModal").find("input[name='sendSmsType']").val(sendSmsType);
		$("#timaModal").modal('show');
		$.get("order/queryOrderList",{activityId:activityId},function(result){
			var resultObject = JSON.parse(result);
			if(resultObject.success){
				var resultArray = resultObject.data;
				$("#famaState").empty();
				$.each(resultArray,function(i,v){ 
					var successSum = v.importNumber, errorSum= 0, sum = v.importNumber, time = v.createtime, remark = v.remark,status = v.status;
					var successTemp;
					if(sendSmsType=='01'){
						if(status=='04'){
							successTemp = temp.successDownEnd;
						}else{
							successTemp = temp.successDown;
						}
					}else if(sendSmsType=='02'){
						if(status=='04'){
							successTemp = temp.successSendEnd;
						}else{
							successTemp = temp.successSend;
						}
					}else{
						successTemp = temp.success2;
					}
					var remarkStr = "";
					if(remark != ""){
						remarkStr = '<div class="row"><span class="col-lg-10" style="margin-top: -14px; font-size: 11px;">描述: '+remark+'</span></div>'
					}else{
						remarkStr = "";
					}
					var a = successTemp.replace("{time}", time).replace("{sum}", sum).replace("{sucSum}", successSum).replace("{failSum}", errorSum).replace("{id}", v.id).replace("{orderId}", v.id).replace("{remark}", remarkStr);;

					var b = temp.main.replace("{complate}", a);
			    	$("#famaState").append(b);
				}); 
			}
		});
	}

	/**
	 * 导出发码
	 * @param orderId
	 */
	function exportCode(orderId, obj){
		var proWrap = $("#kv-upload-progress");
		if(proWrap.attr('data-id') == orderId){
			$("#importExcel").fileinput("disable");
		}else{
			$("#famaState").find('button').filter(function(){
				return $(this).attr('onclick').indexOf(proWrap.attr('data-id')) < 0;
			}).addClass("disabled");
		}
		var $this = $(obj);
		$this.text("正在导出").addClass("disabled");
		$('#timaModal').find('.close').prop('disabled', true)
		var either;

		var proNode = $("#kv-upload-progress .progress");
		var progressWrap = $('.progressWrap');

		if(proWrap.attr('data-id') == orderId){
			either = showSucTip({
				progressed: "20%",
				progressedText: "解析成功",
				title: "正在导出",
				time: 2,
				left: '222px'
			});
			var progressSum = 1;
			famaTimer = setInterval(function () {
				progressSum = progressSum + 0.05;
				either.css("width", progressSum + '%');
				if (progressSum >= 59) {
					clearInterval(famaTimer);
				}
			}, 100);
		}else {
			var proStr = $('<div class="progress"></div>');
			either = $('<div role="progressbar" class="progress-bar progress-bar-striped progress-bar-primary"></div>')
			var famaTip = '<label class="famaTip" >正在导出 <i>......</i></label>';
			progressWrap.find('.all').remove();
			progressWrap.append($('<div class="all" data-id="'+orderId+'">').append(proStr.append(either)).append(famaTip));
			if(proNode.length > 0){
				progressWrap.find('.all').css('marginTop', '40px');
			}
			var progressSum = 1;
			famaTimer1 = setInterval(function () {
				progressSum = progressSum + 0.1;
				either.css("width", progressSum + '%');
				if (progressSum >= 99) {
					clearInterval(famaTimer);
				}
			}, 100);

		}
		var famaSucStr = $this.parents('section.panel-content').find('article.famaSuc');
		var famaErrorStr = $this.parents('section.panel-content').find('article.famaError');
		$.ajax({
			type: "get",
			url : "activitysys/createSendSmsExcel",
			dataType:'json',
			timeout : 300000,
			data:  {orderId:orderId},
			beforeSend: function () {

			},
			complete : function () {
				if(proWrap.attr('data-id') == orderId){
					$("#importExcel").fileinput("enable");
					clearInterval(famaTimer);
				}else{
					clearInterval(famaTimer1);
				}
			},
			success: function(result){
				$('#timaModal').find('.close').prop('disabled', false);
				either.css("width",'100%').removeClass('progress-bar-striped');

				var tipsText = "导出成功";
				if(result.success){
					tipsText = "导出成功";
					$this.text("导出短信");
					famaSucStr.show().find('.h4').text('导出成功');
					famaSucStr.find('button').text("导出短信");
					famaErrorStr.remove();
					//导出成功后隐藏导出按钮
					famaSucStr.find('button').text("已导出").prop('disabled', true);
					window.location.href = 'activitysys/exportSendSmsExcel?orderId='+orderId;
				}else{
					either.addClass('progress-bar-danger');
					tipsText = "导出失败";
					var time = $this.parents('.pull-right').find('.h6').text();

					var rebtn = '<button class="btn btn-info btn-small" type="button" onclick="exportCode(\''+orderId+'\', this)">重新导出</button>';
					var errorStr = temp.error.replace('{time}', time).replace('{errorMsg}', result.errorMsg).replace('{reBtn}', rebtn).replace('{title}', "导出失败");

					famaSucStr.hide();
					if(famaErrorStr.length <= 0){
						$this.parents('section.panel-content').append(errorStr);
					}else{
						famaErrorStr.find('button').text("重新导出");
					}
					$('.errorDetail').popover();
				}
				//$("#famaState").find('button').removeClass("disabled");
				if(proWrap.attr('data-id') == orderId){
					$this.removeClass("disabled");
				}else{
					$("#famaState").find('button').filter(function(){
						return $(this).attr('onclick').indexOf(proWrap.attr('data-id')) < 0;
					}).removeClass("disabled");
				}
				if($('div[data-id="'+orderId+'"]').is('#kv-upload-progress')){
					$('.progressWrap').children('.famaTip').html(tipsText);
				}else if($('div[data-id="'+orderId+'"]').is('.all')){
					$('.all').find('.famaTip').html(tipsText);
				}
			}
		});
	}
	
	/**
	 * 加载字典数据
	 */
	function loadDictionary(){
		$.get("code/queryCodeListForCombo?field=CONTRACT,INTEGRAL_ACTIVITY,ENTERPRISE", function(data){
			if(data.success){
				$.each(data.data, function(i, n){
					var thisSelect = $("select[code='"+i+"']");
					$.each(n, function(j, o){
						var optionNode = $("<option>").attr("value", o.value).text(o.text);
						thisSelect.append(optionNode);
					});
					thisSelect.val(thisSelect.attr('data-val'));
				});

				$("#addActivityJifen").addClass("hasDic");
			}
		}, "json");
	}

	function cishuLimite (obj) {
		if($(obj).val() == ""){
			$(obj).parent().next().find(':text').val("").prop("disabled", true).removeAttr('data-parsley-required').removeClass('parsley-error').addClass('parsley-success').next('.parsley-errors-list').empty();
		}else{
			$(obj).parent().next().find(':text').prop("disabled", false).attr('data-parsley-required', 'true');
		}
	}