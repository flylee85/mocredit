<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发码管理</title>
    <link rel="stylesheet" href="${path}/activitysys/common/js/file-input/fileinput.css">
    <link rel="stylesheet" href="${path}/activitysys/common/css/switchery/style.css">
    <link rel="stylesheet" href="${path}/activitysys/common/css/bootstrap.css">
    <link rel="stylesheet" href="${path}/activitysys/common/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/activitysys/common/css/style.css">
    <link rel="stylesheet" href="${path}/activitysys/common/css/plugin.css">
    <script src="${path}/activitysys/common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${path}/activitysys/common/js/file-input/fileinput.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script src="${path}/activitysys/common/js/ie/respond.min.js"></script>
    <script src="${path}/activitysys/common/js/ie/html5.js"></script>
    <![endif]-->
</head>
<body>
<!-- .modal 发送提示 -->
<div class="modal fade operaState in " data-keyboard="false" id="operaState">
    <div class="modal-dialog modal-sm" style="margin-left:-150px;">
        <div class="modal-content">
            <div class="modal-body pop-div-content">
                <p><i class="icon-success"></i></p>

                <p class="tipP">
                    <label id="promptMsg"></label>
                </p>
            </div>
        </div>
    </div>
</div>
<section class="main padder">
    <div class="clearfix">
        <h4><i class="icon-list"></i> 发码列表</h4>
    </div>
    <section class="panel">
        <div class="tab-content">
            <div id="tima" class="tab-pane fade active in">
                <form id="uploadAndSendForm" action="importCustomer" method="post"
                      class="form-horizontal form-inline"
                      enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-lg-3">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="merger" name="downloadChannel" value="01">
                                    <i class="icon-circle-blank"></i>
                                    合并重复号码，并只发一个码
                                </label>
                            </div>
                        </label>
                        <label class="col-lg-3">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="notMerger" name="downloadChannel" value="02">
                                    <i class="icon-circle-blank"></i>
                                    不合并重复号码，发送多个码
                                </label>
                            </div>
                        </label>
                    </div>
                    <div class="form-group">
                        <div class="input-group col-2">
                            <a href="downloadTemplate" id="" class="btn btn-primary ">
                                下载模板
                            </a>
                        </div>
                        <div class="input-group col-4 text-left">
                            <label class="col-lg-3 control-label" for="name">批次名称</label>

                            <div class="col-lg-9">
                                <input type="text" tabindex="1" id="name"
                                       class="form-control input-small parsley-validated"
                                       data-parsley-required="true" placeholder="批次名称" data-parsley-maxlength="30"
                                       name="name">
                            </div>
                        </div>
                        <div class="input-group col-4">
                            <label class="col-lg-3 control-label" for="importExcel">导入联系人</label>

                            <div class="col-lg-9">
                                <input type="hidden" name="actId" id="actId" value="${actId}"/>
                                <input id="importExcel" name="selectExcel" type="file" class="file-input">
                            </div>
                        </div>
                        <div class="input-group col-2">
                            <input type="submit" class="btn btn-primary" value="上传并发送"/>
                        </div>
                    </div>

                </form>
                <div class="row">
                    <div class="col-lg-12">
                        <section class="panel">
                            <div class="pull-out">
                                <table class="table table-striped m-b-none" data-ride="datatables">
                                    <thead>
                                    <tr>
                                        <th>批次号</th>
                                        <th>名称</th>
                                        <th>数量</th>
                                        <th>短信发送</th>
                                        <th>导入时间</th>
                                        <th>状态</th>
                                        <th>发码类型</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>


<!-- /.modal 发送提示 -->
<script src="${path}/activitysys/common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${path}/activitysys/common/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${path}/activitysys/common/js/index.js" type="text/javascript"></script>
<!-- 验证 -->
<script src="${path}/activitysys/common/js/parsley/parsley.js" type="text/javascript"></script>
<!-- checkbox -->
<script src="${path}/activitysys/common/js/fuelux/fuelux.js" type="text/javascript"></script>

<!-- datatables -->
<script src="${path}/activitysys/common/js/datatables/jquery.dataTables10.min.js" type="text/javascript"></script>
<script>
    if ("${success}" == "true") {
        sendMsg(true, "上传并发送成功");
        window.location.href = "${path}/activitysys/sendCode/sendcode?id=${actId}"
    }
    if ("${success}" == "false" && "${msg}" != "") {
        sendMsg(false, "${msg}");
        window.location.href = "${path}/activitysys/sendCode/sendcode?id=${actId}"
    }
</script>
<script>
    var famaTable;
    var page = 1
    $(document).ready(function () {
        if ($.type(famaTable) != 'object') {
            famaTable = $('#tima').find('table[data-ride="datatables"]').DataTable({
                "ajax": {
                    url: "${path}/activitysys/sendCode/queryPickCodePage?actId=${actId}",
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
                "aaSorting": [[6, "desc"]],//默认排序
                "columns": [
                    {"data": "id", "name": "id", "width": "100px"},
                    {"data": "batch", "name": "batch", "width": "90px", "sortable": false},
                    {"data": "importNumber", "name": "pickNumber", "width": "90px", "sortable": false},
                    {"data": "importSuccessNumber", "name": "sendSuccessNumber", "className": "name", "width": "100px"},
                    {"data": "createtime", "name": "createtime", "width": "180px"},
                    {"data": "status", "name": "status", "width": "170px"},
                    {"data": "sendSmsType", "name": "sendSmsType", "width": "100px"},
                    {"data": null, "width": "170px"}
                ],
                "columnDefs": [
                    {"searchable": false, "targets": [2]},
                    {
                        "targets": 5,
                        "data": "status",
                        "sortable": false,
                        "render": function (data, type, full) {
// batch 00：已删除 01：已提码，未导入联系人 02：已导入联系人，待送码 03：已送码，待发码 04：已发码
                            var statusText;
                            if (data == "01") {
                                statusText = "已提码，未导入联系人";
                            }
                            if (data == "02") {
                                statusText = "已导入联系人，待送码";
                            }
                            if (data == "03") {
                                statusText = "已送码，待发码";
                            }
                            if (data == "04") {
                                statusText = "已发码";
                            }
                            return statusText;
                        }
                    },
                    {
                        "targets": 6,
                        "data": "sendSmsType",
                        "sortable": false,
                        "render": function (data, type, full) {
                            var sendSmsTypeText;
                            if (data == "02") {
                                sendSmsTypeText = "短信";
                            }
                            return sendSmsTypeText;
                        }
                    },
                    {
                        "targets": 7,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            return '<a href="javascript:sendCode(\'' + data['id'] + '\')">发码</a>' +
                                    '<a href="${path}/activitysys/codedetail.html?' + data['id'] + '-${actId}' + '" target="_blank">详情</a>' +
                                    '<a href="javascript:delBatch(\'' + data['id'] + '\')">删除</a>';
                        }
                    }
                ]
            });
        }
    })
</script>
<script>
    function sendCode(id) {
        $.get("${path}/activitysys/sendCode/sendCodeByBatchId", {
            "actId": "${actId}",
            "batchId": id
        }, function (result) {
            if (result.success) {
                sendMsg(true, "发送短信成功");
                window.location.href = "${path}/activitysys/sendCode/sendcode?id=${actId}"
            } else {
                sendMsg(false, result.errorMsg);
                window.location.href = "${path}/activitysys/sendCode/sendcode?id=${actId}"
            }
        }, 'json');
    }
    function delBatch(id) {
        $.get("${path}/activitysys/sendCode/delBatchById", {"batchId": id}, function (result) {
            if (result.success) {
                sendMsg(true, "删除批次成功");
                window.location.href = "${path}/activitysys/sendCode/sendcode?id=${actId}"
            } else {
                sendMsg(false, result.errorMsg);
                window.location.href = "${path}/activitysys/sendCode/sendcode?id=${actId}"
            }
        }, 'json');
    }
</script>
</body>
</html>
                