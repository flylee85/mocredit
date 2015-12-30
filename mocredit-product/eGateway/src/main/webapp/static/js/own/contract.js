var oTable;

$('[data-ride="datatables"]').each(function() {
	oTable = $(this).dataTable( {
		"processing": true,
		"ajax": {
			"url": "common/js/data/contract.json",
			"dataSrc": "aaData",
			"data": {
				"enterpriseId": $("#enterpriseConId").val()
			}
		},
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"pageLength": 5,
		//"serverSide":true,
		"columns": [
			{ "data": "no" },
			{ "data": "contractName" },
			{ "data": "enterprise" },
			{ "data": "contractSigntory" },
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, type, full, meta ) {
					return '<a href="#addContract" >下载</a>'
						+ '<a href="#contractAccount" data-toggle="modal">结算</a>'
						+ '<a href="#">删除</a>';
				},
				"sortable": false,
				"targets": 4
			}
		]

	} );
});
 
var temp = {
    	main : '<section class="panel">'+
			        '<header class="panel-heading">状态</header>'+
				    '<section class="panel-content">'+
				    	'{complate}'+
				    '</section>'+
	          	'</section>',
    	success : '<article class="media">'+
			        '<div class="pull-left">'+
			        '<span class="">'+
			         '<i class="icon-smile icon-successLarger"></i>'+
			        '</span>'+
			      '</div>'+
			      '<div class="media-body">'+
			        '<div class="pull-right media-mini text-center text-muted">'+
	                  '<strong class="h4">{time}</strong><br>'+
	                  '<small class="label bg-light">{date}</small>'+
	                '</div>'+
			        '<h3 style="margin-top: 5px;">合同上传成功</h3>'+
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
			              '<button class="btn btn-info btn-small" id="reUpload">{reBtn}</button>'+
			            '</div>'+
			        '</div>'+
			        '<a class="h4" href="#">{title}</a>'+
			        '<div class="row">'+
			            '<a href="javascript:void(0);" id="errorDetail" class="errorDetail" data-content="{errorMsg}" data-title="失败原因">查看失败详情</a>'+
			        '</div>'+
			      '</div>'+
			    '</article>',
     };
     var $input = $("#importContract");
     $input.fileinput({
    	    uploadUrl: "http://localhost/file-upload-batch/2", // server upload action
    	    language: 'zh',
    	    uploadAsync: false,
    	    showUpload: false, // hide upload button
    	    showRemove: false, // hide remove button
    	    showPreview: false,
    	    minFileCount: 1,
    	    maxFileCount: 5,
    	    showCaption: false
    	}).on("filebatchselected", function(event, files) {
    	    // trigger upload method immediately after files are selected
    	    $input.fileinput("upload");
    	}).on("filebatchuploadcomplete", function(event, files) {
    		var time = "12:22:33", date = "7月16日";
    		var a = temp.success.replace("{time}", time).replace("{date}", date);
    		var b = temp.main.replace("{complate}", a);
    		$("#uploadState").append(b);
    	});
     
	$("#errorDetail").popover();
	
	
	// 合同结算--编辑
	$("button.toEdit").click(function(){
		var $this = $(this);
		$this.text("确认并保存");
		var editNodes = $this.parents("table").find("td.clickEdit");
		editNodes.each(function(){
			var inputNode = $("<input type='text'>").val($(this).text());
			$(this).empty().append(inputNode);
		});
	});
	// 合同结算--确认并保存
	$("button.opera").click(function(){
		var $this = $(this);
		var $thisParent = $this.parents("div.accordion-group");
		var $thisLabel = $thisParent.find("a.accordion-toggle").find("span.label");
		if($this.parents("div.accordion-group").hasClass("undisposed")){
			$this.text("编    辑");
			var editNodes = $this.parents("table").find("td.clickEdit");
			editNodes.each(function(){
				var inputVal = $(this).find("input").val();
				$(this).empty().text(inputVal);
			});
			$thisParent.removeClass("undisposed");
			$thisLabel.removeClass("bg-danger").addClass("bg-success").text("已处理");
			
		}else{
			$this.text("确认并保存");
			var editNodes = $this.parents("table").find("td.clickEdit");
			editNodes.each(function(){
				var inputNode = $("<input type='text'>").val($(this).text());
				$(this).empty().append(inputNode);
			});
			$thisParent.addClass("undisposed");
		}
		
	});
	$("#contractAccount").on("show.bs.modal", function () {
		var strMain = '<div class="accordion-group ">'+
			'<div class="accordion-heading">'+
			'<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne" aria-expanded="true">'+
			'{data-date}'+
			'<span class="label bg-danger pull-right">未处理</span>'+
			'</a>'+
			'</div>'+
			'<div id="collapseOne" class="accordion-body collapse in" style="" aria-expanded="true">'+
			'<section class="panel">'+
			'<div class="pull-out">'+
			'<table class="table table-striped m-b-none text-small">'+
			'<thead><tr><th width="150px">活动名称</th><th>码价格</th><th>发码数</th>'+
			'<th>码验证次数</th><th width="100px">计算应收</th><th width="100px">确认应收</th>'+
			'<th width="120px">码验证次数误差</th></tr></thead><tbody>'+
			'{data-trs}'+
			'<tr><th>合计</th><td colspan="5">{data-account}</td><td>'+
			'<button type="button" class="btn btn-success btn-mini opera">确认并保存</button>'+
			'</td></tr></tbody></table></div></section></div></div>';
		$.ajax({
			type:"get",
			dataType:"json",
			url:"",
			data:"",
			success: function (result) {
				if(result.success){
					var data = result.data;
					$("#accordion").empty();
					$.each(data, function (j, k) {
						strMain.replace("{data-date}", k.date);
						var strAccount = '<div class="row">'+
							'<div class="col-lg-3">验证次数<span class="badge bg-info">data.validCount</span></div>'+
							'<div class="col-lg-3">计算应收<span class="badge bg-info">data.receivable</span></div>'+
							'<div class="col-lg-3">确认应收<span class="badge bg-info">data.confirm</span></div>'+
							'<div class="col-lg-3">误差<span class="badge bg-info">data.deviation</span></div></div>';
						var strTr = "";
						$.each(data.trs, function (i, n) {
							strTr += '<tr>'+
								'<td>n.name</td>'+
								'<td>n.jiage</td>'+
								'<td>n.famashu</td>'+
								'<td>n.yanyanzhengcishu</td>'+
								'<td class="clickEdit"><input value="'+ n.receivable+'"></td>'+
								'<td class="clickEdit"><input value="'+ n.confirm+'"></td>'+
								'<td class="clickEdit"><input value="'+ n.deviation+'"></td>'+
								'</tr>';

						});
						var str = strMain.replace("{data-trs}", strTr).replace("{data-account}", strAccount);
						$("#accordion").append(str);
					});
				}
			}
		});
	});
/*	$("#contractAccount").find("td.clickEdit").click(function(){
		var $this = $(this);
		if($this.has("input").length > 0){
			return false;
		}
		var inputNode = $("<input type='text'>").val($this.text());
		$this.empty().append(inputNode);
		inputNode.focus().off("blur").blur(function(){
			console.log("11111111");
			$this.text($(this).val());
			$(this).remove();
		});
	});*/