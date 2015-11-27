$('[data-ride="datatables"]').each(function() {
	var oTable = $(this).dataTable( {
		"processing": true,
		"ajax": {
			"url": "common/js/data/payment.json",
			"dataSrc": "aaData"
		},
		"dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
		"paginationType": "full_numbers",
		"autoWidth":true,
		//"pageLength": 10,
		//"serverSide":true,
		"columns": [
			{ "data": "no" },
			{ "data": "receivable" },
			{ "data": "Assigned" },
			{ "data": "undistributed" },
			{ "data": "collectionDate" },
			{ "data": null }
		],
		"columnDefs": [
			{
				"render": function(oObj, type, full, meta ) {
					return '<a href="#distribution" data-toggle="modal">分配</a>'+
					'<a href="#distribution" data-toggle="modal">查看</a>'
				},
				"sortable": false,
				"targets": 5
			}
		]

	} );
});
$("#distribution").on("show.bs.modal", function(){
	$(this).distribution();
});
(function($, window){
	$.fn.distribution = function(option){
		var opt = $.extend({}, $.fn.distribution.defaultOpt, option);
		var $this = $(this);
		var amount = $("#amount");
		var assigned = $("#assigned");
		var unallocated = $("#unallocated");

		var addNode = $("#addLine");
		addNode.click(function(){
			var trNode = $("<tr>");
			trNode.append($("<td>").append($this.find("select").eq(0).clone()));
			trNode.append($("<td>").append('<input type="text" class="form-control input-small">'))
			var deleteNode = $("<a href='javascript:void(0)'><i class='icon-remove'></i>删除</a>")
			deleteNode.off('click').click(function () {
				func.deleteLine($(this));
			});
			trNode.append($("<td>").append(deleteNode));

			trNode.appendTo($this.find("table tbody"));
		});


		$this.find("table tbody").on("keyup blur", ":text", function () {
			var _this = $(this);
			if(isNaN(_this.val())){
				_this.parent().addClass("has-error");
				return false;
			}else{
				_this.parent().removeClass("has-error");
			}
			var account = 0;
			$this.find("table tbody").find(":text").each(function () {
				var thisNode = $(this);
				if(thisNode.val() != ""){
					account += parseInt(thisNode.val());
				}
			});
			assigned.text(opt.amount - account);
			func.stateChange(account);

		});
		var func = {
			init : function () {
				amount.text(opt.amount);
				assigned.text(opt.amount - opt.unallocated);
				unallocated.text(opt.unallocated);
				func.stateChange(opt.unallocated)

				//$.ajax({
				//	url : "",
				//	//data : {"id" : id},
				//	dataType : "json",
				//	type:"get"
				//}).done(function(result){
				//	returnNode = $("<select>");
				//	if(result.success){
				//		$.each(result.data, function(i, n){
				//			var trNode = $("<tr>");
				//			trNode.append($("<td>").append($this.find("select").eq(0).clone()));
				//			trNode.append($("<td>").append('<input type="text" class="form-control input-small">'));
				//			var deleteNode = $("<a href='javascript:void(0)'><i class='icon-remove'></i>删除</a>")
				//			deleteNode.off('click').click(function () {
				//				func.deleteLine($(this));
				//			});
				//			trNode.append($("<td>").append(deleteNode));
				//		});
				//	}
				//}) ;
			},
			stateChange : function (account) {
				var poor = account - opt.amount;
				if(poor > 0){
					unallocated.addClass("bg-danger");
					assigned.addClass("bg-danger");
				}else if(poor < 0){
					assigned.addClass("bg-warning");
					assigned.removeClass("bg-danger");
					unallocated.removeClass("bg-danger");
				}else{
					unallocated.removeClass("bg-danger");
					assigned.removeClass("bg-danger").removeClass("bg-warning");
				}
			},
			findContract : function (id) {
				if($this.hasClass("hasSelect")){
					return;
				}else{
					$.ajax({
						url : "",
						//data : {"id" : id},
						dataType : "json",
						async : false,
						type:"get"
					}).done(function(result){
						var selectNode = $this.find("select").empty();
						if(result.success){
							selectNode.append($("<option>").val(0).text("请选择一个合同"));
							$.each(result.data, function(i, n){
									selectNode.append($("<option>").val(n.id).text(n.name));
							});
						}
						$this.addClass("hasSelect")
					}) ;
				}
			},
			deleteLine : function (obj) {
				obj.parents("tr").remove();
				var account = 0;
				$this.find("table tbody").find(":text").each(function () {
					var thisNode = $(this);
					if(thisNode.val() != ""){
						account += parseInt(thisNode.val());
					}
				});
				assigned.text(opt.amount - account);
				func.stateChange(account);
			}
		};

		func.init();
	}

	$.fn.distribution.defaultOpt = {
		amount:20000,// 收款金额
		unallocated : 5000,// 已分配金额
	};
})(jQuery);