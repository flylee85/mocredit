	// 验证
	var form2 = $("#addActivityJifenForm").parsley();

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
	$('.backToList').click(function () {
		$('#content').load('activity.html');
	});

	/**
	 * 添加发码活动确认按钮
	 */
	$("#addActivityJifenSubmitButton").click(function(){
		if(!form2.validate()){
			return false;
		}
		if($(".choosedPop .selectStore span").length==0){
			sendMsg(false, "请选择门店");
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
		formObject.storeList = [];
		$("#addActivityJifenForm .choosedStore span").each(function () {
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
		formObject.enterpriseName=$("#addActivityJifenForm select[name=enterpriseId] option:selected").text();
		formObject.contractName=$("#addActivityJifenForm select[name=contractId] option:selected").text();
		//次数限制处理
		formObject.maxNumber="";
		$(".maxNumber input").each(function(){
			var $this=$(this);
			if($this.val()){
				formObject.maxNumber+=this.id+":"+$this.val()+";";
			}
		})
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
		var addActivityFamaForm = $("#addActivityJifenForm");
		$.get("activitysys/getActivityById",{id:activityId},function(result){
			if(result.success){

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
				addActivityFamaForm.find("input[name='integral']").val(dataObject.integral);
				addActivityFamaForm.find("select[name='maxType']").val(dataObject.maxType);
				addActivityFamaForm.find("select[name='maxType']").attr('data-val',dataObject.maxType);
				addActivityFamaForm.find("input[name='maxNumber']").val(dataObject.maxNumber);

				addActivityFamaForm.find("textarea[name='bins']").val(dataObject.bins);
				addActivityFamaForm.find("select[name='channel']").val(dataObject.channel);
				addActivityFamaForm.find("select[name='channel']").attr('data-val',dataObject.channel);
				addActivityFamaForm.find("input[name='exchangeType'][value="+dataObject.exchangeType+"]").attr("checked","checked");
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

				addActivityFamaForm.find("input[name='startTime']").val(dataObject.startTime);
				addActivityFamaForm.find("input[name='endTime']").val(dataObject.endTime);
				if(dataObject.startTime){
					$('#date2').datepicker('setStartDate', new Date(Date.parse(dataObject.startTime.replace(/-/g, "/"))));
				}
				if(dataObject.endTime){
					$('#date1').datepicker('setEndDate', new Date(Date.parse(dataObject.endTime.replace(/-/g, "/"))));
				}
				if( dataObject.selectDate){
					var selectDateArray = dataObject.selectDate.split(",");
					for(var i=0;i<selectDateArray.length;i++){
						var value = selectDateArray[i];
						$("#selectDateJifenCheckboxFormGroup").find("input[value='"+value+"']").prop('checked', true).next('i').addClass('checked');
					}
				}
				//处理次数限制
				if(dataObject.maxNumber){
					var max=dataObject.maxNumber.split(";");
					for(var i in max){
						var value=max[i].split(":");
						$("#"+value[0]).val(value[1]);
					}
				}
			}else{
				alert('失败！' + result.errorMsg);
			}
		},'json');

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
	openUpdateFamaActivity();
	loadDictionary();
