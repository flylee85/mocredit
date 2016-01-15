$(function () {

    var jifenTable, famaTable;

    function initJifenTab(page) {
        if ($.type(jifenTable) != 'object') {
            jifenTable = $('#jifen').find('table[data-ride="datatables"]').DataTable({
                "ajax": {
                    url: "order/queryOrderPage?type=01",
                    data: function (formObject) {
                        //获取表单基本元素对象
                        var formArray = $("#queryJifenOrderPage").serializeArray();
                        $.each(formArray, function (index) {
                            if (formObject[this['name']]) {
                                formObject[this['name']] = formObject[this['name']] + "," + this['value'];
                            } else {
                                formObject[this['name']] = this['value'];
                            }
                        });
                        //获取表单状态
                        var statuses;
                        var checkboxArray = $("#queryOrderPage input:checked");
                        checkboxArray.each(function (i) {
                            if (statuses == null || statuses == '') {
                                statuses = $(this).val();
                            } else {
                                statuses += ',' + $(this).val();
                            }
                        });
                        formObject.statuses = statuses;
                        return formObject;
                    },
                    type: "post",
                },
                "processing": true,
                "serverSide": true,
                "autoWidth": true,
                "searching": false,
                "searchDelay": 500,
                "ordering": false,
                "lengthChange": false,
                "displayStart": parseInt(page) * 10 - 10,
                "pagingType": "full_numbers",
                "pageLength": 10,
                "dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
                "aaSorting": [[4, "desc"]],//默认排序
                "columns": [
                    {"data": "orderId", "name": "orderId", "width": "90px"},
                    {"data": "enterpriseName", "name": "enterpriseName", "width": "70px"},
                    {"data": "shopName", "name": "shopName", "width": "70px"},
                    {"data": "storeName", "name": "storeName", "width": "70px"},
                    {"data": "activityName", "name": "activityName", "width": "90px"},
                    {"data": "orderTime", "name": "orderTime", "width": "90px"},
                    {"data": "status", "name": "status", "width": "70px"},
                    {"data": "amt", "name": "amt", "width": "70px"},
                    {"data": "enCode", "name": "enCode", "width": "70px"},
                    {"data": "cardNo", "name": "cardNo", "width": "70px"},
                    {"data": "msg", "name": "msg", "width": "70px"}
                ], "columnDefs": [
                    {
                        "targets": 6,
                        "data": "status",
                        "sortable": false,
                        "render": function (data, type, full) {
                            /**
                             *PAYMENT("01", "积分消费"),
                             PAYMENT_REVOKE("02", "消费撤销"),
                             PAYMENT_REVERSAL("03", "积分冲正"),
                             PAYMENT_REVERSAL_REVOKE("04", "冲正撤销"),
                             CONFIRM_INFO("05", "积分查询");
                             */
                            var statusText = "";
                            if (data == "01") {
                                statusText = "积分消费";
                            }
                            if (data == "02") {
                                statusText = "消费撤销";
                            }
                            if (data == "03") {
                                statusText = "积分冲正";
                            }
                            if (data == "04") {
                                statusText = "冲正撤销";
                            }
                            return statusText;
                        }
                    }, {
                        "targets": 5,
                        "data": "orderTime",
                        "sortable": false,
                        "render": function (data, type, full) {
                            return new Date(parseInt(data)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                        }
                    }
                ]
            });
        }
    }


    function initFamaTab(page) {
        if ($.type(famaTable) != 'object') {
            famaTable = $('#fama').find('table[data-ride="datatables"]').DataTable({
                    "ajax": {
                        url: "order/queryOrderPage?type=02",
                        data: function (formObject) {
                            //获取表单基本元素对象
                            var formArray = $("#queryOrderPage").serializeArray();
                            //var formObject = new Object();
                            $.each(formArray, function (index) {
                                if (formObject[this['name']]) {
                                    formObject[this['name']] = formObject[this['name']] + "," + this['value'];
                                } else {
                                    formObject[this['name']] = this['value'];
                                }
                            });
                            //获取表单状态
                            var statuses;
                            var checkboxArray = $("#queryOrderPage input:checked");
                            checkboxArray.each(function (i) {
                                if (statuses == null || statuses == '') {
                                    statuses = $(this).val();
                                } else {
                                    statuses += ',' + $(this).val();
                                }
                            });
                            formObject.statuses = statuses;
                            return formObject;
                        },
                        type: "post",
                    },
                    "processing": true,
                    "serverSide": true,
                    "autoWidth": true,
                    "searching": false,
                    "searchDelay": 500,
                    "ordering": false,
                    "lengthChange": false,
                    "displayStart": parseInt(page) * 10 - 10,
                    "pagingType": "full_numbers",
                    "pageLength": 10,
                    "dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
                    "aaSorting": [[4, "desc"]],//默认排序
                    "columns": [
                        {"data": "orderId", "name": "orderId", "width": "90px"},
                        {"data": "enterpriseName", "name": "enterpriseName", "width": "70px"},
                        {"data": "shopName", "name": "shopName", "width": "70px"},
                        {"data": "storeName", "name": "storeName", "width": "70px"},
                        {"data": "activityName", "name": "activityName", "width": "90px"},
                        {"data": "orderTime", "name": "orderTime", "width": "90px"},
                        {"data": "status", "name": "status", "width": "70px"},
                        {"data": "code", "name": "code", "width": "70px"},
                        {"data": "enCode", "name": "enCode", "width": "70px"},
                        {"data": "mobile", "name": "mobile", "width": "70px"},
                        {"data": "msg", "name": "msg", "width": "70px"},
                        {"data": null, "width": "70px"}
                    ],
                    "columnDefs": [
                        {
                            "targets": 5,
                            "data": "orderTime",
                            "sortable": false,
                            "render": function (data, type, full) {
                                return new Date(parseInt(data)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                            }
                        }, {
                            "targets": 6,
                            "data": "status",
                            "sortable": false,
                            "render": function (data, type, full) {
                                var statusText = "";
                                if (data == "0") {
                                    statusText = "消费";
                                }
                                if (data == "2") {
                                    statusText = "撤销";
                                }
                                return statusText;
                            }
                        },
                        {
                            "targets": 11,
                            "data": null,
                            "sortable": false,
                            "render": function (data, type, full) {
                                if (full['status'] == "2") {
                                    return '<a href="#"></a>';
                                }
                                if (full['status'] == "0" && full['verifyStatus'] == "1") {
                                    return '<a href="javascript:doUpdate(this,\'' + full['orderId'] + '\',\'' + full['enCode'] + '\')"data-id="' + full['orderId'] + '">撤消</a>';
                                }
                                return "";
                            }
                        }
                    ]
                }
            );
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

    $("#searchFamaCond").click(function () {
        famaTable.ajax.reload();
    });
    $("#searchJiFenCond").click(function () {
        jifenTable.ajax.reload();
    });
    $("#exportJifen").click(function () {
        var formArray = $("#queryJifenOrderPage").serialize();
        //提交
        $.ajax({
            type: "POST",
            url: "order/exportCount",
            data: "type=01&exportType=CSV&" + formArray,
            success: function (result, textStuts) {
                if (result.success) {
                    window.location.href = "order/export?type=01&exportType=CSV&" + formArray;
                    sendMsg(true, msg);
                } else {
                    sendMsg(false, result.errorMsg);
                }
            }
        });
    });
    $("#export").click(function () {
        var formArray = $("#queryOrderPage").serialize();
        //提交
        $.ajax({
            type: "POST",
            url: "order/exportCount",
            data: "type=02&exportType=CSV&" + formArray,
            success: function (result, textStuts) {
                if (result.success) {
                    window.location.href = "order/export?type=02&exportType=CSV&" + formArray;
                    sendMsg(true, msg);
                } else {
                    sendMsg(false, result.errorMsg);
                }
            }
        });

    });
    if ($('#activityCurrentType').val() == "2") {
        initFamaTab($('#activityCurrentPage').val());
        $("#tabToFama").parent().addClass('active').siblings().removeClass('active');
        $('#fama').addClass('active in').siblings().removeClass('active in');
    } else if ($('#activityCurrentType').val() == "1") {
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
});
/**
 * 更新订单状态数据
 * @param obj
 */
function doUpdate(obj, id, enCode) {
    var contentTitle = $(obj).parent().prevAll(".name").text();
    $.confirmUpdateDailog({
        confirm: function () {
            $.get("order/updateOrderByOrderId", {"orderId": id, "enCode": enCode}, function (msg) {
                if (msg.success) {
                    sendMsg(true, "撤消成功");
                    if ($('#jifen').hasClass('active')) {
                        $('#jifen').find('table').DataTable().ajax.reload();
                    } else {
                        $('#fama').find('table').DataTable().ajax.reload();
                    }
                } else {
                    sendMsg(false, msg.errorMsg);
                }
            }, 'json');
        },
        target: id
    });
}

$.extend({
    confirmUpdateDailog: function (options) {
        var opt = $.extend({}, $.confirmUpdateDailog.default, options);
        var confirmNode = $(".confirmDialog");
        var content = opt.content.replace("{target}", opt.target);
        if (confirmNode.length == 0) {
            var str = opt.str.replace("{title}", opt.title).replace("{content}", content);
            $("#content").append(str);
            confirmNode = $(".confirmDialog");
            var btn = $("<button type='button'>").addClass("btn btn-primary").text("确认");
            confirmNode.modal("show");
            btn.appendTo(confirmNode.find(".modal-footer"));
            btn.off().click(function () {
                confirmNode.modal("hide");
                opt.confirm.call(this);
            });
        } else {
            confirmNode.modal("show");
            confirmNode.find(".modal-title").text(opt.title);
            confirmNode.find(".modal-body p").html(content);
            confirmNode.find(".modal-footer").find("button.btn-primary").off().click(function () {
                confirmNode.modal("hide");
                opt.confirm.call(this);
            });
        }
    },
    loading: function (options) {
        var opt = $.extend({
            text: "正在请求"
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
$.confirmUpdateDailog.default = {
    str: '<div class="modal fade confirmDialog" data-backdrop="static">\
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
    title: "确认",
    content: "您确定要处理 <b>{target}</b> 吗？",
    target: "",
    confirm: null
}

