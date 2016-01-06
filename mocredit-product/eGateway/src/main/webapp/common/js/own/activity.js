$(function () {
    var jifenTable;

    function initJifenTab(page) {
        if ($.type(jifenTable) != 'object') {
            jifenTable = $('#jifen').find('table[data-ride="datatables"]').DataTable({
                "ajax": {
                    url: "queryActivityPage",
                    type: "post",
                },
                "processing": true,
                "serverSide": true,
                "pageLength": 10,
                "pagingType": "full_numbers",
                "searchDelay": 500,
                "displayStart": parseInt(page) * 10 - 10,
                "autoWidth": true,
                "dom": "<'row'<'col col-lg-6'l><'col col-lg-6'f>r>t<'row'<'col col-lg-6'i><'col col-lg-6'p>>",
                "aaSorting": [[4, "desc"]],//默认排序
                "columns": [
                    {"data": "name", "name": "name", "width": "200px"},
                    {"data": "code", "name": "code", "width": "90px", "sortable": false},
                    {"data": "enterpriseName", "name": "enterpriseName", "width": "120px", "sortable": false},
                    {"data": "merchantId", "name": "merchantId", "className": "name", "width": "100px"},
                    {"data": "merchantName", "name": "merchantName", "className": "name", "width": "120px"},
                    //{"data": "rule", "name": "rule", "width": "180px"},
                    //{"data": null, "width": "100px"},
                    {"data": null, "width": "150px"}
                ],
                "columnDefs": [
                  /*  {
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
                    },*/
                    {
                        "targets": 5,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            return '<a href="javascript:openUpdateJifenActivity(\'' + data['id'] + '\',0)" >查看</a>' +
                                '<a href="javascript:openUpdateJifenActivity(\'' + data['id'] + '\',1)" >编辑</a>';
                        }
                    }
                ]
            });
        }
    }

    initJifenTab(1);
    // 活动启用停用
    $(".tab-content").on("click", ".switch :checkbox", function () {
        var isCheck = $(this).prop("checked");
        if (isCheck) {
            changeActivityStatus($(this).attr("data-id"), "01");
        } else {
            changeActivityStatus($(this).attr("data-id"), "02");
        }
    });
    /**
     * 活动停用/启用
     * @param activityId
     * @param status
     */
    function changeActivityStatus(activityId, status) {
        //提交
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: "updateActivityById",
            data: JSON.stringify({id: activityId, status: status}),
            success: function (result, textStuts) {
                if (result.success) {
                    var msg = "停用成功";
                    if (status == "01") {
                        msg = "启用成功";
                    }
                    sendMsg(true, msg);
                } else {
                    sendMsg(false, result.errorMsg);
                    //sendMsg(false, '失败！失败原因：'+result.errorMsg);
                }
            }
        });
    }

    // 添加积分活动按钮
    $('#openAddOffact').click(function () {
        $("#activityCurrentId").val("");
        $("#activityCurrentType").val(1);
        $("#activityDetailType").val(1);
        $("#activityCurrentPage").val(1);
        $("#content").load('offactivity.html');
        /*$("#addActivityJifen").modal('show');
         $("#addActivityJifenForm").find("input, select, textarea,button").prop("disabled",false);
         $("#addActivityJifenForm")[0].reset();
         $("#addActivityJifenForm input[name='id']").val("");
         $("#addActivityJifenForm").find(".chooseShop").text("选择门店").addClass("popEnter").removeClass('popFloat');
         $("#addActivityJifen").find('.look').hide();
         $("#addActivityJifen").find('.add').show();*/


    });


    ///**
    // * 初始化积分表格
    // */
    //$('#tabToJifen').on('show.bs.tab', function (e) {
    //    initJifenTab(1);
    //});
    ///**
    // * 初始化发码表格
    // */
    //$('#tabToFama').on('show.bs.tab', function (e) {
    //    initFamaTab(1);
    //});
    //
    //if ($('#activityCurrentType').val() == "2") {
    //    initFamaTab($('#activityCurrentPage').val());
    //    $("#tabToFama").parent().addClass('active').siblings().removeClass('active');
    //    $('#fama').addClass('active in').siblings().removeClass('active in');
    //} else if ($('#activityCurrentType').val() == "1") {
    //    initJifenTab($('#activityCurrentPage').val());
    //}
});
function openUpdateJifenActivity(activityId, type) {
    $("#activityCurrentId").val(activityId);
    $("#activityDetailType").val(type);
    $("#activityCurrentType").val(1);
    var page = $('#jifen').find('a.current').text();
    $("#activityCurrentPage").val(page);

    $("#content").load('offactivity.html');

}

