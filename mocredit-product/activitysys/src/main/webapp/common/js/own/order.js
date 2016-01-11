$(function () {

    var famaTable;

    function initFamaTab(page) {
        if ($.type(famaTable) != 'object') {
            famaTable = $('#fama').find('table[data-ride="datatables"]').DataTable({
                    "ajax": {
                        url: "order/queryCodeOrderPage?type=02",
                        data: function (formObject) {
                            //获取表单基本元素对象
                            var formArray = $("#queryOrderPage").serializeArray();
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
                        {"data": "enterpriseName", "name": "enterpriseName", "width": "70px"},
                        {"data": "shopName", "name": "shopName", "width": "70px"},
                        {"data": "storeName", "name": "storeName", "width": "70px"},
                        {"data": "activityName", "name": "activityName", "width": "90px"},
                        {"data": "verifyTime", "name": "verifyTime", "width": "90px"},
                        {"data": "status", "name": "status", "width": "70px"},
                        {"data": "code", "name": "code", "width": "70px"},
                        {"data": "amount", "name": "amount", "width": "70px"},
                        {"data": "mobile", "name": "mobile", "width": "70px"},
                        {"data": null, "width": "150px"}
                    ],
                    "columnDefs": [
                        {
                            "targets": 5,
                            "data": "status",
                            "sortable": false,
                            "render": function (data, type, full) {
                                var statusText = "";
                                if (data == "01") {
                                    statusText = "未验证";
                                }
                                if (data == "02") {
                                    statusText = "已验证";
                                }
                                if (data == "03") {
                                    statusText = "已废码";
                                }
                                return statusText;
                            }
                        },
                        {
                            "targets": 9,
                            "data": null,
                            "sortable": false,
                            "render": function (data, type, full) {
                                var op = '<a href="#"></a>';
                                if (full['status'] == "01") {
                                    op += '<a href="javascript:doUpdate(this,\'' + full['id'] + '\',\'' + full['code'] + '\')"data-id="' + full['id'] + '">废码</a>' +
                                        '<a href="javascript:delayCode(this,\'' + full['id'] + '\',\'' + full['endTime'] + '\')"data-id="' + full['id'] + '">延期</a>';
                                }
                                if (full['status'] != "03" && full['mobile']) {
                                    op += '<a href="javascript:reissue(this,\'' + full['codeId'] + '\',\'' + full['code'] + '\')"data-id="' + full['codeId'] + '">补发</a>';
                                }
                                return op;
                            }
                        }
                    ]
                }
            );
        }
    }

    initFamaTab(1);

    $("#searchFamaCond").click(function () {
        famaTable.ajax.reload();
    });
    $("#export").click(function () {
        var formArray = $("#queryOrderPage").serialize();
        window.location.href = "order/exportCodeOrder?type=02&exportType=CSV&" + formArray;
    });

    if ($('#activityCurrentType').val() == "2") {
        initFamaTab($('#activityCurrentPage').val());
        $("#tabToFama").parent().addClass('active').siblings().removeClass('active');
        $('#fama').addClass('active in').siblings().removeClass('active in');
    }


});

/**
 * 查看发码活动
 * @param activityId
 * @param type
 */
function openUpdateFamaActivity(activityId, type) {
    $("#activityCurrentId").val(activityId);
    $("#activityDetailType").val(type);
    $("#activityCurrentType").val(2);
    var page = $('#fama').find('a.current').text();
    $("#activityCurrentPage").val(page);

    $("#content").load('fama.html');

}

function delayCode(obj, id, time) {
    $("#expireTime").val(time);
    $("#delayTime").val("");
    $("#codeId").val(id);
    $("#delay").modal("show");
}
$("#delayConfirm").click(function () {
    var id = $("#codeId").val();
    var delayTime = $("#delayTime").val();
    $.get("order/delayOrderById", {
        "id": id,
        "delayTime": delayTime
    }, function (result) {
        if (result.success) {
            sendMsg(true, "延期成功");
            $('#fama').find('table').DataTable().ajax.reload();
        } else {
            sendMsg(false, result.errorMsg);
        }
    }, 'json');
    $("#delay").modal("hide");
})
function reissue(obj, id, code) {
    $.confirmUpdateDailog({
        confirm: function () {
            $.get("order/reissueByCodeId", {"id": id}, function (msg) {
                if (msg.success) {
                    sendMsg(true, "补发成功");
                    $('#fama').find('table').DataTable().ajax.reload();
                } else {
                    sendMsg(false, msg.errorMsg);
                }
            }, 'json');
        },
        target: code,
        content: "您确定要补发 <b>{target}</b> 吗？"
    });
}
/**
 * 更新订单状态数据
 * @param obj
 */
function doUpdate(obj, id, code) {
    $.confirmUpdateDailog({
        confirm: function () {
            $.get("order/abolishByCodeId", {"id": id}, function (msg) {
                if (msg.success) {
                    sendMsg(true, "废除成功");
                    $('#fama').find('table').DataTable().ajax.reload();
                } else {
                    sendMsg(false, msg.errorMsg);
                }
            }, 'json');
        },
        target: code,
        content: "您确定要废除 <b>{target}</b> 吗？"
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




