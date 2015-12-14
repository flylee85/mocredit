	// 验证
	var form = $("#addActivityJifenForm").parsley({

	});

	// datepicker
	$(".datepicker").each(function () {
		$(this).datepicker({
			format: "yyyy-mm-dd",
			container: $(this).parent(),
			autoclose: true,
			language: 'zh-CN',
			todayHighlight: true
		});
	});
	$('#date3').datepicker().on('hide', function (e) {
		$('#date4').datepicker('setStartDate', e.date);
	});
	$('#date4').datepicker().on('hide', function (e) {
		$('#date3').datepicker('setEndDate', e.date);
	});

	$('.checkbox-custom > input[type=checkbox]').each(function () {
		var $this = $(this);
		if ($this.data('checkbox')) return;
		$this.checkbox($this.data());
	});
	$('.radio-custom > input[type=radio]').each(function () {
		var $this = $(this);
		if ($this.data('radio')) return;
		$this.radio($this.data());
	});

	$('.backToList').click(function () {
		$('#content').load('activity.html');
	});

	/**
	 * 添加积分活动确认按钮
	 */
	$("#addActivityJifenSubmitButton").click(function(){
		if(!form.validate()){
			return false;
		}
		//获取表单基本元素对象
		var formArray = $("#addActivityJifenForm").serializeArray();
		var formObject = new Object();
		$.each(formArray,function(index){
			if(formObject[this['name']]){
				formObject[this['name']] = formObject[this['name']]+","+this['value'];
			}else{
				formObject[this['name']] = this['value'];
			}
		});
		//获取表单特殊元素---选择日期
		var selectDate;
		var checkboxArray = $("#selectDateJifenCheckboxFormGroup input:checked");
		checkboxArray.each(function(i){
			if(selectDate==null||selectDate==''){
				selectDate = $(this).val();
			}else{
				selectDate += ','+$(this).val();
			}
		});
		formObject.selectDate = selectDate;
		//整理开始日期和结束日期
		formObject.startTime = formObject.startTime+' 00:00:00';
		formObject.endTime = formObject.endTime+' 23:59:59';
		formObject.storeList = [];
		$("#addActivityJifenForm").find('.choosedStore').find('span').each(function () {
			var $this = $(this);
			var obj = new Object();
			obj.storeName = $this.text();
			obj.storeId = $this.attr('data-store') + "";
			obj.storeCode = $this.attr('data-storeCode') + "";
			obj.shopId = $this.attr('data-id') + "";
			formObject.storeList.push(obj)
		});
		//提交
		$.ajax({
			type: "post",
			url: "activity/saveActivity",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formObject),
			dataType: "json",
			success: function (result) {
				if(result.success){
					sendMsg(true, "成功")
					$('#activityCurrentPage').val(1);
					$('#content').load('activity.html');
				}else{
					sendMsg(false, result.errorMsg);
				}
			},
			error: function (result) {
				sendMsg(false, result.errorMsg);
			}
		});
	});

	// 加载企业信息
	$("#addActivityJifen").on("show.bs.modal", function () {
		if (!$("#addActivityJifen").hasClass("hasDic")) {
			loadDictionary();
		}
	});

	// 打开门店页面
	$(".chooseShop").hover(function () {
		if ($(this).hasClass('popFloat')) {
			$(this).next('.choosedPop').show().css("marginLeft", -$(this).next('.choosedPop').width() / 2 + 'px');
		}
	}, function () {
		$(this).next('.choosedPop').hide();
	}).click(function () {
		var $this = $(this);
		if ($this.hasClass("popEnter")) {
			$('#shopWrap').load('mendian.html', function () {
				var mbody = $("#shopModal .modal-body");

				var selectStore = $this.next('.choosedPop').find('.selectStore');
				if (selectStore.length > 0) {
					$("#shopModal .selectStore").remove();
					selectStore.clone().appendTo(mbody);
				}
				$("#shopModal").modal('show');
			});
		}
	});
	/**
	 * 查看积分活动
	 */
	function openUpdateJifenActivity() {
		var activityId = $("#activityCurrentId").val();
		if(activityId == ""){
			return false;
		}
		var type = $("#activityDetailType").val();

		var addActivityJifenForm = $("#addActivityJifenForm");

		$.get("activity/getActivityById",{id:activityId},function(result){
			if(result.success){
				var dataObject = result.data;
				addActivityJifenForm.find("input[name='id']").val(dataObject.id);
				addActivityJifenForm.find("input[name='name']").val(dataObject.name);
				addActivityJifenForm.find("input[name='code']").val(dataObject.code);
				addActivityJifenForm.find("input[name='outCode']").val(dataObject.outCode);
				addActivityJifenForm.find("select[name='enterpriseId']").val(dataObject.enterpriseId);
				addActivityJifenForm.find("select[name='enterpriseId']").attr('data-val',dataObject.enterpriseId);
				addActivityJifenForm.find("select[name='contractId']").val(dataObject.contractId);
				addActivityJifenForm.find("select[name='contractId']").attr('data-val',dataObject.contractId);
				addActivityJifenForm.find("select[name='integralActivity']").val(dataObject.integralActivity);
				addActivityJifenForm.find("select[name='integralActivity']").attr('data-val',dataObject.integralActivity);
				addActivityJifenForm.find("input[name='integral']").val(dataObject.integral.replace('.0', ""));

				addActivityJifenForm.find("input[name='receiptTitle']").val(dataObject.receiptTitle);
				addActivityJifenForm.find("textarea[name='receiptPrint']").val(dataObject.receiptPrint);
				addActivityJifenForm.find("input[name='posSuccessMsg']").val(dataObject.posSuccessMsg);
				addActivityJifenForm.find("input[name='successSmsMsg']").val(dataObject.successSmsMsg);


				addActivityJifenForm.find("input[name='startTime']").val(dataObject.startTime.substring(0,10));
				addActivityJifenForm.find("input[name='endTime']").val(dataObject.endTime.substring(0,10));
				var selectDateArray = dataObject.selectDate.split(",");
				for(var i=0;i<selectDateArray.length;i++){
					var value = selectDateArray[i];
					$("#selectDateJifenCheckboxFormGroup").find(":checkbox[value='"+value+"']").prop('checked', true).next('i').addClass('checked');
				}
				addActivityJifenForm.find("select[name='maxType']").val(dataObject.maxType).trigger('change', this);
				addActivityJifenForm.find("input[name='maxNumber']").val(dataObject.maxNumber);
				if(dataObject.storeList.length != 0){
					addActivityJifenForm.find(".chooseShop").addClass('popFloat').text("已选择 " + dataObject.storeList.length + " 家门店");
				}else{
					addActivityJifenForm.find(".chooseShop").removeClass('popFloat').text("已选择 0 家门店")
				}
				var str = '<div class="row selectStore clearfix">';

				$.each(dataObject.storeList, function (i, n) {
					str += '<span data-store="'+ n.storeId +'" data-storeCode="'+ n.storeCode +'" data-id="'+ n.shopId+'" class="label bg-info">'+ n.storeName+'<i class="icon-remove"></i></span>';
				});
				str += '</div>'
				addActivityJifenForm.find(".choosedStore").empty().append(str);

			}else{
				alert('失败！');
			}
		}, 'json');
		$('.look').children('.toModify').off('click').click(function () {
			$('div.add').show();
			$('div.look').hide();
			$(".chooseShop").addClass('popEnter');
			$("input, select, textarea").prop("disabled", false);
		});
		if (type == "0") {
			$(".chooseShop").removeClass('popEnter');
			$("input, select, textarea").prop("disabled", true);
			$('div.add').hide();
			$('div.look').show();
		} else {
			$(".chooseShop").addClass('popEnter');
			$("input, select, textarea").prop("disabled", false);
			$('div.add').show();
			$('div.look').hide();
		}
	}

	/**
	 * 加载字典数据
	 */
	function loadDictionary() {
		$.get("code/queryCodeListForCombo?field=CONTRACT,INTEGRAL_ACTIVITY,ENTERPRISE", function (data) {
			if (data.success) {
				$.each(data.data, function (i, n) {
					var thisSelect = $("select[code='" + i + "']");
					$.each(n, function (j, o) {
						var optionNode = $("<option>").attr("value", o.value).text(o.text);
						thisSelect.append(optionNode);
					});
					if($.type(thisSelect.attr('data-val')) != "undefined"){
						thisSelect.val(thisSelect.attr('data-val'));
					}
				});

				$("#addActivityJifen").addClass("hasDic");
			}
		}, "json");
	}

	function cishuLimite(obj) {
		console.log($(obj).val());
		if ($(obj).val() == "") {
			$("#maxTypeText").hide().find(":text").val("").removeAttr('data-parsley-required').removeClass('parsley-error').next('.parsley-errors-list').empty();;
			//$(obj).parent().next().find(':text').val("").prop("disabled", true).removeAttr('data-parsley-required').removeClass('parsley-error').addClass('parsley-success').next('.parsley-errors-list').empty();
		} else {
			$("#maxTypeText").show().find(':text').attr('data-parsley-required', 'true');
			//$(obj).parent().next().find(':text').prop("disabled", false).attr('data-parsley-required', 'true');
		}
	}
	openUpdateJifenActivity();
	loadDictionary();
