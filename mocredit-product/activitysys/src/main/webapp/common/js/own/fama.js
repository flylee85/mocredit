

// 验证
	var form2 = $("#addActivityFamaForm").parsley();

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
	$('#date1').datepicker().on('hide', function (e) {
		$('#date2').datepicker('setStartDate', e.date);
	});
	$('#date2').datepicker().on('hide', function (e) {
		$('#date1').datepicker('setEndDate', e.date);
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

	$("#channelSend").click(function () {
		$(this).parents(".parentNode").addClass("active");
		$('#duanxin').attr('data-parsley-required', true);
	});
	$("#channelDownload").click(function () {
		$("#channelSend").parents(".parentNode").removeClass("active");
		$('#duanxin').removeAttr('data-parsley-required').parent().next('.parsley-errors-list').empty();
		$("#duanxin").add("#weixin").prop("checked", false).next().removeClass("checked");
		$("#duanxinFama").add("#weixinFama").hide().find("textarea").val("");
		
		$("#caixin").prop("checked", false).next().removeClass("checked");
		$('#subject').removeAttr('data-parsley-required');
		$('#input-text').removeAttr('data-parsley-required');
		$('#frame-select').removeAttr('data-parsley-required');
		$('#code-select').removeAttr('data-parsley-required');
		$("#caixinFama").hide();
		$('#caixin').removeAttr('data-parsley-required')
	});

	$("#duanxin").click(function () {
		if ($(this).prop("checked")) {
			$("#duanxinFama").show().find('textarea').attr('data-parsley-required', true);
		} else {
			$("#duanxinFama").hide().find('textarea').removeAttr('data-parsley-required').next('.parsley-errors-list').empty();
		}
	});
	$("#caixin").click(function () {
		if ($(this).prop("checked")) {
			$("#caixinFama").show();
			$('#subject').attr('data-parsley-required',true);
			$('#input-text').attr('data-parsley-required',true);
			$('#frame-select').attr('data-parsley-required',true);
			$('#code-select').attr('data-parsley-required',true);
		} else {
			$("#caixinFama").hide();
			$('#subject').removeAttr('data-parsley-required');
			$('#input-text').removeAttr('data-parsley-required');
			$('#frame-select').removeAttr('data-parsley-required');
			$('#code-select').removeAttr('data-parsley-required');
		}
	});
	$("#weixin").click(function () {
		if ($(this).prop("checked")) {
			$("#weixinFama").show();
		} else {
			$("#weixinFama").hide();
		}
	});
	$('.backToList').click(function () {
		$('#content').load('activity.html');
	});




	/**
	 * 添加发码活动确认按钮
	 */
	$("#addActivityFamaSubmitButton").click(function(){
		if(!form2.validate()){
			return false;
		}
		if($(".choosedPop .selectStore span").length==0){
			sendMsg(false, "请选择门店");
			return false;
		}
		//获取表单基本元素对象
		var formArray = $("#addActivityFamaForm").serializeArray();
		var formObject = new Object();
		$.each(formArray,function(index){
			if(formObject[this['name']]){
				formObject[this['name']] = formObject[this['name']]+","+this['value'];
			}else{
				formObject[this['name']] = this['value'];
			}
		});
		//获取表单特殊元素--发码方式,通知短信内容
		var sendSmsType="";
		var noticeSmsMsg;
		if($("#channelDownload").prop('checked')){
			sendSmsType += '01,';
		}
		if($("#duanxin").prop('checked')){
			sendSmsType += '02,';
			noticeSmsMsg = $("#duanxinFama textarea").val();
		}
		if($("#caixin").prop('checked')){
			sendSmsType += '03,';
			formObject.mmsJson = $("#endJson").attr('data-json');
			formObject.codeno = $("#code-select").val();
			formObject.subject = $("#subject").val();
		}
		if(sendSmsType.length>0){
			sendSmsType=sendSmsType.substr(0,sendSmsType.length-1);
		}
		formObject.sendSmsType = sendSmsType;
		formObject.noticeSmsMsg = noticeSmsMsg;
		formObject.storeList = [];
		$("#addActivityFamaForm .choosedStore span").each(function () {
			var $this = $(this);
			var obj = new Object();
			obj.storeName =  $this.attr('data-storeName');
			obj.shopName =  $this.attr('data-shopName');
			obj.storeId = $this.attr('data-id');
			obj.shopId = $this.attr('data-shopId');
			formObject.storeList.push(obj)
		});
		//获取表单特殊元素---选择日期
		var selectDate;
		var checkboxArray = $("#selectDateFamaCheckboxFormGroup input:checked");
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
		formObject.enterpriseName=$("#addActivityFamaForm select[name=enterpriseId] option:selected").text();
		formObject.contractName=$("#addActivityFamaForm select[name=contractId] option:selected").text();
		//提交
		$.ajax({
			type: "post",
			url: "activitysys/saveActivity",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formObject),
			dataType: "json",
			success: function (result) {
				if(result.success){
					sendMsg(true, "成功");
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
	$("#addActivityFama").on("show.bs.modal", function () {
		if (!$("#addActivityFama").hasClass("hasDic")) {
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
				$("#shopModal").modal('show');
			});
		}
	});
	/**
	 * 查看发码活动
	 */
	function openUpdateFamaActivity() {
		var activityId = $("#activityCurrentId").val();
		if(activityId == ""){
			return false;
		}
		var type = $("#activityDetailType").val();

		var addActivityFamaForm = $("#addActivityFamaForm");
		$.ajax({
	        url : 'activitysys/getActivityById',
	        type : 'get',
	        dataType : 'json',
	        async : false,
	        data : {id:activityId},
	        success :  function(result){
	        	var dataObject = result.data;
				addActivityFamaForm.find("input[name='id']").val(dataObject.id);
				addActivityFamaForm.find("input[name='name']").val(dataObject.name);
				addActivityFamaForm.find("input[name='code']").val(dataObject.code);
				addActivityFamaForm.find("input[name='outCode']").val(dataObject.outCode);
				addActivityFamaForm.find("select[name='enterpriseId']").val(dataObject.enterpriseId);
				addActivityFamaForm.find("select[name='enterpriseId']").attr('data-val',dataObject.enterpriseId);
				addActivityFamaForm.find("select[name='contractId']").val(dataObject.contractId);
				addActivityFamaForm.find("select[name='contractId']").attr('data-val',dataObject.contractId);
				addActivityFamaForm.find("input[name='amount']").val(dataObject.amount);
				addActivityFamaForm.find("input[name='receiptTitle']").val(dataObject.receiptTitle);
				addActivityFamaForm.find("textarea[name='receiptPrint']").val(dataObject.receiptPrint);
				addActivityFamaForm.find("input[name='posSuccessMsg']").val(dataObject.posSuccessMsg);
				addActivityFamaForm.find("input[name='successSmsMsg']").val(dataObject.successSmsMsg);
				addActivityFamaForm.find("input[name='endJson']").attr('data-json', dataObject.mmsJson);
				if(dataObject.storeCount != 0){
					addActivityFamaForm.find(".chooseShop").addClass('popFloat').text("已选择 " + dataObject.storeCount + " 家门店");
				}else{
					addActivityFamaForm.find(".chooseShop").removeClass('popFloat').text("已选择 0 家门店")
				}
				var str = '<div class="row selectStore clearfix">';

				$.each(dataObject.storeList, function (i, n) {
					str += '<span data-id="'+ n.storeId +'" data-storeName="'+n.storeName+'" data-shopId="'+ n.shopId+'" data-shopName='+n.shopName+' class="label bg-info">'+ n.storeName+'<i class="icon-remove"></i></span>';
				});
				str += '</div>'
				addActivityFamaForm.find(".choosedStore").empty().append(str);
				if(dataObject.sendSmsType=='01'){
					//隐藏直接发送区域
//					$("#channelSend").parents(".parentNode").removeClass("active");
//			    	$("#duanxin").add("#weixin").prop("checked", false).next().removeClass("checked");
//			    	$("#duanxinFama").add("#weixinFama").hide().find("textarea").val("");
					//赋值
					$("#channelDownload").prop('checked', true).next('i').addClass('checked');
				}else if(dataObject.sendSmsType=='02'){
					//显示直接发送区域
					$('.parentNode').addClass('active');
					$("#channelSend").prop('checked', true).next('i').addClass('checked');
					$("#duanxin").prop('checked', true).next('i').addClass('checked');
					$("#duanxinFama").show();
					//赋值
					$("#duanxinFama textarea").val(dataObject.noticeSmsMsg);
				}else if(dataObject.sendSmsType=='03'){
					//显示直接发送区域
					$('.parentNode').addClass('active');
					$("#channelSend").prop('checked', true).next('i').addClass('checked');
					$("#caixin").prop('checked', true).next('i').addClass('checked');
					$("#caixinFama").show();
				}else if(dataObject.sendSmsType=='02,03'){
					//显示直接发送区域
					$('.parentNode').addClass('active');
					$("#channelSend").prop('checked', true).next('i').addClass('checked');
					$("#duanxin").prop('checked', true).next('i').addClass('checked');
					$("#duanxinFama").show();
					$("#duanxinFama textarea").val(dataObject.noticeSmsMsg);//赋值
					
					$("#caixin").prop('checked', true).next('i').addClass('checked');
					$("#caixinFama").show();
				}
				
				//兑换渠道
				var exchangeChannel=dataObject.exchangeChannel;
				console.log(dataObject.exchangeChannel);
				if(exchangeChannel){
					var exchangeChannels= exchangeChannel.split(",");
					for(var i in exchangeChannels){
						console.log($("input[name=exchangeChannel][value="+exchangeChannels[i]+"]"));
						$("input[name=exchangeChannel][value="+exchangeChannels[i]+"]").siblings("i").click();
					}
				}
				addActivityFamaForm.find("input[name='startTime']").val(dataObject.startTime);
				addActivityFamaForm.find("input[name='endTime']").val(dataObject.endTime);
				if( dataObject.selectDate){
					var selectDateArray = dataObject.selectDate.split(",");
					for(var i=0;i<selectDateArray.length;i++){
						var value = selectDateArray[i];
						$("#selectDateFamaCheckboxFormGroup").find("input[value='"+value+"']").prop('checked', true).next('i').addClass('checked');
					}
				}
//				$.caixinFama();
	        }
	    });

//		$.get("activitysys/getActivityById",{id:activityId},function(result){
//			if(result.success){
//
//				
//			}else{
//				alert('失败！' + result.errorMsg);
//			}
//		},'json');

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
		$.get("activitysys/getComb", function (data) {
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

				$("#addActivityFama").addClass("hasDic");
			}
		}, "json");
	}

	function cishuLimite(obj) {
		if ($(obj).val() == "") {
			$(obj).parent().next().find(':text').val("").prop("disabled", true).removeAttr('data-parsley-required').removeClass('parsley-error').addClass('parsley-success').next('.parsley-errors-list').empty();
		} else {
			$(obj).parent().next().find(':text').prop("disabled", false).attr('data-parsley-required', 'true');
		}
	}
	function addmark(value){
		$("#duanxinFama textarea").val($("#duanxinFama textarea").val()+value);
	}
	openUpdateFamaActivity();
	loadDictionary();


