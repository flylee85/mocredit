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
$("#addActivityJifenSubmitButton").click(function () {
    if (!form2.validate()) {
        return false;
    }
    //获取表单基本元素对象
    var formObject = $("#addActivityJifenForm").serialize();
    var rule = "";
    if ($("#CardDayMax").val()) {
        rule += "CardDayMax:" + $("#CardDayMax").val() + ";";
    }
    if ($("#CardWeekMax").val()) {
        rule += "CardWeekMax:" + $("#CardWeekMax").val() + ";";
    }
    if ($("#CardMonthMax").val()) {
        rule += "CardMonthMax:" + $("#CardMonthMax").val() + ";";
    }
    if ($("#CardYearMax").val()) {
        rule += "CardYearMax:" + $("#CardYearMax").val() + ";";
    }
    if ($("#CardTotalMax").val()) {
        rule += "CardTotalMax:" + $("#CardTotalMax").val() + ";";
    }
    //提交
    $.ajax({
        type: "post",
        url: "saveActivity",
        //contentType: "application/json; charset=utf-8",
        data: formObject + "&rule=" + rule,
        dataType: "json",
        success: function (result) {
            if (result.success) {
                sendMsg(true, "成功");
                $('#content').load('activity.html');
            } else {
                sendMsg(false, result.errorMsg);
            }
        },
        error: function (result) {
            sendMsg(false, result.errorMsg);
        }
    });
});
/**
 * 查看发码活动
 */
function openUpdateFamaActivity() {
    var activityId = $("#activityCurrentId").val();
    if (activityId == "") {
        return false;
    }
    var type = $("#activityDetailType").val();
    var addActivityFamaForm = $("#addActivityJifenForm");
    $.get("getActivityById", {id: activityId}, function (result) {
        if (result.success) {
            var dataObject = result.data;
            addActivityFamaForm.find("input[name='id']").val(dataObject.id);
            addActivityFamaForm.find("input[name='name']").val(dataObject.name);
            addActivityFamaForm.find("input[name='code']").val(dataObject.code);
            addActivityFamaForm.find("input[name='enterpriseName']").val(dataObject.enterpriseName);
            addActivityFamaForm.find("input[name='merchantId']").val(dataObject.merchantId);
            addActivityFamaForm.find("input[name='merchantName']").val(dataObject.merchantName);
            addActivityFamaForm.find("input[name='CardDayMax']").val(dataObject.cardDayMax);
            addActivityFamaForm.find("input[name='CardWeekMax']").val(dataObject.cardWeekMax);
            addActivityFamaForm.find("input[name='CardMonthMax']").val(dataObject.cardMonthMax);
            addActivityFamaForm.find("input[name='CardYearMax']").val(dataObject.cardYearMax);
            addActivityFamaForm.find("input[name='CardTotalMax']").val(dataObject.cardTotalMax);
        } else {
            alert('失败！' + result.errorMsg);
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
openUpdateFamaActivity();
