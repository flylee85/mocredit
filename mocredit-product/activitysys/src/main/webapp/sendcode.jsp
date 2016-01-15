<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发码管理</title>
    <link rel="stylesheet" href="common/js/file-input/fileinput.css">
    <link rel="stylesheet" href="common/css/switchery/style.css">
    <link rel="stylesheet" href="common/css/bootstrap.css">
    <link rel="stylesheet" href="common/css/font-awesome.min.css">
    <link rel="stylesheet" href="common/css/style.css">
    <link rel="stylesheet" href="common/css/plugin.css">
    <script src="common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="common/js/file-input/fileinput.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script src="common/js/ie/respond.min.js"></script>
    <script src="common/js/ie/html5.js"></script>
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
                <form id="uploadAndSendForm" action="sendCode/importCustomer" method="post"
                      class="form-horizontal form-inline"
                      enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-lg-3">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="merger" checked="checked" name="downloadChannel" value="01">
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
                            <a href="sendCode/downloadTemplate" id="" class="btn btn-primary ">
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
                        <input type="hidden" name="type" id="type" value="02"/>
                        <input type="hidden" name="types" id="types" value="${type}"/>

                        <div class="input-group col-2">
                            <input type="button" onclick="upload()" class="btn btn-primary" value="导入联系人"/>
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
                                        <%--<th>批次号</th>--%>
                                        <th>名称</th>
                                        <th>导入数量</th>
                                        <th>发送数量</th>
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
<div id="send" class="modal fade">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                        class="icon-remove"></i></button>
                <h4 class="modal-title myModalLabel" align="center">短信发码</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="form-group">
                        <label class="col-lg-2">
                            <input type="hidden" id="sendBatchId" name="sendBatchId" value="">
                        </label>
                        <label class="col-lg-4">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="bpSend" name="sendType"
                                           value="01">
                                    <i class="icon-circle-blank"></i>
                                    断点续发
                                </label>
                            </div>
                        </label>
                        <label class="col-lg-4">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="allSend" name="sendType" value="02">
                                    <i class="icon-circle-blank"></i>
                                    全部发送
                                </label>
                            </div>
                        </label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row add">
                    <button type="button" class="btn btn-default backToList" data-dismiss="modal">取 消</button>
                    <button id="sendConfirm" type="button" class="btn btn-primary">确 认</button>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div id="sendMMS" class="modal fade">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i
                        class="icon-remove"></i></button>
                <h4 class="modal-title myModalLabel" align="center">彩信发码</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="form-group">
                        <label class="col-lg-2">
                            <input type="hidden" id="sendMMSBatchId" name="sendMMSBatchId" value="">
                        </label>
                        <label class="col-lg-4">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="bpSendMMS" name="sendType"
                                           value="01">
                                    <i class="icon-circle-blank"></i>
                                    断点续发
                                </label>
                            </div>
                        </label>
                        <label class="col-lg-4">
                            <div class="radio">
                                <label class="radio-custom">
                                    <input type="radio" id="allSendMMS" name="sendType" value="02">
                                    <i class="icon-circle-blank"></i>
                                    全部发送
                                </label>
                            </div>
                        </label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row add">
                    <button type="button" class="btn btn-default backToList" data-dismiss="modal">取 消</button>
                    <button id="sendMMSConfirm" type="button" class="btn btn-primary">确 认</button>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>

<!-- /.modal 发送提示 -->
<script src="common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="common/js/bootstrap.min.js" type="text/javascript"></script>
<script src="common/js/index.js" type="text/javascript"></script>
<!-- 验证 -->
<script src="common/js/parsley/parsley.js" type="text/javascript"></script>
<!-- checkbox -->
<script src="common/js/fuelux/fuelux.js" type="text/javascript"></script>

<!-- datatables -->
<script src="common/js/datatables/jquery.dataTables10.min.js" type="text/javascript"></script>
<script>
    if ("${success}" == "true") {
        sendMsg(true, "上传成功");
        window.location.href = "sendCode/sendcode?id=${actId}&type=${type}"
    }
    if ("${success}" == "false" && "${msg}" != "") {
        sendMsg(false, "${msg}");
        window.location.href = "sendCode/sendcode?id=${actId}&type=${type}"
    }
    <%--if ("${type}" == "02") {--%>
    <%--$("#sms").removeAttr("style");--%>
    <%--}--%>
    <%--if ("${type}" == "03") {--%>
    <%--$("#mms").removeAttr("style");--%>
    <%--}--%>
    <%--if ("${type}".indexOf("02") >= 0 && "${type}".indexOf("03") >= 0) {--%>
    <%--$("#smms").removeAttr("style");--%>
    <%--}--%>
</script>
<script>
    var famaTable;
    var page = 1
    $(document).ready(function () {
        if ($.type(famaTable) != 'object') {
            famaTable = $('#tima').find('table[data-ride="datatables"]').DataTable({
                "ajax": {
                    url: "sendCode/queryPickCodePage?actId=${actId}",
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
                    {"data": "batch", "name": "batch", "width": "90px", "sortable": false},
                    {"data": "importNumber", "name": "importNumber", "width": "90px", "sortable": false},
                    {
                        "data": "sendNumber",
                        "name": "sendNumber",
                        "className": "name",
                        "width": "100px"
                    },
                    {"data": "createtime", "name": "createtime", "width": "180px"},
                    {"data": "status", "name": "status", "width": "170px"},
                    {"data": "sendSmsType", "name": "sendSmsType", "width": "100px"},
                    {"data": null, "width": "170px"}
                ],
                "columnDefs": [
                    {"searchable": false, "targets": [2]},
                    {
                        "targets": 4,
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
                            if (data == "05") {
                                statusText = "部分已发码";
                            }
                            return statusText;
                        }
                    },
                    {
                        "targets": 5,
                        "data": "sendSmsType",
                        "sortable": false,
                        "render": function (data, type, full) {
                            var sendSmsTypeText;
                            if (data == "02") {
                                sendSmsTypeText = "短信";
                            }
                            if (data == "03") {
                                sendSmsTypeText = "彩信";
                            }
                            if (data.indexOf("02") >= 0 && data.indexOf("03") >= 0) {
                                sendSmsTypeText = "短信,彩信";
                            }
                            return sendSmsTypeText;
                        }
                    },
                    {
                        "targets": 6,
                        "data": null,
                        "sortable": false,
                        "render": function (data, type, full) {
                            if (full['sendSmsType'] == "03") {
                                if (full['status'] == "05") {
                                    return '<a href="javascript:sendCodeMMS(\'' + data['id'] + '\')">彩信发码</a>' +
                                            '<a href="codedetail.html?' + data['id'] + '-${actId}' + '-' + full['sendSmsType'] + '" target="_blank">详情</a>' /*+
                                     '<a href="javascript:delBatch(\'' + data['id'] + '\')">删除</a>'*/;
                                } else {
                                    return '<a href="javascript:sendCodeMMSAll(\'' + data['id'] + '\')">彩信发码</a>' +
                                            '<a href="codedetail.html?' + data['id'] + '-${actId}' + '-' + full['sendSmsType'] + '" target="_blank">详情</a>'
                                }
                            }
                            if (full['sendSmsType'] == "02") {
                                if (full['status'] == "05") {
                                    return '<a href="javascript:sendCode(\'' + data['id'] + '\')">短信发码</a>' +
                                            '<a href="codedetail.html?' + data['id'] + '-${actId}' + '-' + full['sendSmsType'] + '" target="_blank">详情</a>' /*+
                                     '<a href="javascript:delBatch(\'' + data['id'] + '\')">删除</a>'*/;
                                } else {
                                    return '<a href="javascript:sendCodeAll(\'' + data['id'] + '\')">短信发码</a>' +
                                            '<a href="codedetail.html?' + data['id'] + '-${actId}' + '-' + full['sendSmsType'] + '" target="_blank">详情</a>'
                                }

                            }
                            if (full['sendSmsType'].indexOf("03") >= 0 && full['sendSmsType'].indexOf("02") >= 0) {
                                if (full['status'] == "05") {
                                    return '<a href="javascript:sendCode(\'' + data['id'] + '\')">短信发码</a>' +
                                            '<a href="javascript:sendCodeMMS(\'' + data['id'] + '\')">彩信发码</a>' +
                                            '<a href="codedetail.html?' + data['id'] + '-${actId}' + '-' + full['sendSmsType'] + '" target="_blank">详情</a>' /*+
                                     '<a href="javascript:delBatch(\'' + data['id'] + '\')">删除</a>'*/;
                                } else {
                                    return '<a href="javascript:sendCodeAll(\'' + data['id'] + '\')">短信发码</a>' +
                                            '<a href="javascript:sendCodeMMSAll(\'' + data['id'] + '\')">彩信发码</a>' +
                                            '<a href="codedetail.html?' + data['id'] + '-${actId}' + '-' + full['sendSmsType'] + '" target="_blank">详情</a>'
                                }
                            }
                        }
                    }
                ]
            });
        }
    })
</script>
<script>
    function sms() {
        $("#type").val("02");
        $("form").submit();
    }
    function mms() {
        $("#type").val("03");
        $("form").submit();
    }
    function sms() {
        $("#type").val("02");
        $("form").submit();
    }
    function upload() {
        $("form").submit();
    }
    $("#send").on("show.bs.modal", function () {
//        $("#allSend").attr("checked", false).next("i").removeClass("checked");
//        $("#bpSend").attr("checked", true).next("i").addClass("checked");
        $("#bpSend").next("i").click();
    });
    $("#sendMMS").on("show.bs.modal", function () {
//        $("#allSendMMS").attr("checked", false).next("i").removeClass("checked");
//        $("#bpSendMMS").attr("checked", true).next("i").addClass("checked");
        $("#bpSendMMS").next("i").click();
    });
    $("#sendMMSConfirm").click(function () {
        var id = $("#sendMMSBatchId").val();
        var sendType = $('#sendMMS input[name="sendType"]:checked ').val();
        $.get("sendCode/sendCodeByBatchId", {
            "actId": "${actId}",
            "batchId": id,
            "type": "03",
            "sendType": sendType
        }, function (result) {
            if (result.success) {
                sendMsg(true, "发送彩信成功");
                window.location.href = "sendCode/sendcode?id=${actId}"
            } else {
                sendMsg(false, result.errorMsg);
                window.location.href = "sendCode/sendcode?id=${actId}"
            }
        }, 'json');
        $("#sendMMS").modal("hide");
    })
    $("#sendConfirm").click(function () {
        var id = $("#sendBatchId").val();
        var sendType = $('#send input[name="sendType"]:checked ').val();
        $.get("sendCode/sendCodeByBatchId", {
            "actId": "${actId}",
            "batchId": id,
            "type": "02",
            "sendType": sendType
        }, function (result) {
            if (result.success) {
                sendMsg(true, "发送短信成功");
                window.location.href = "sendCode/sendcode?id=${actId}"
            } else {
                sendMsg(false, result.errorMsg);
                window.location.href = "sendCode/sendcode?id=${actId}"
            }
        }, 'json');
        $("#send").modal("hide");
    })
</script>
<script>
    function sendCode(id) {
        $("#sendBatchId").val(id);
        $("#send").modal("show");

    }
    function sendCodeMMS(id) {
        $("#sendMMSBatchId").val(id);
        $("#sendMMS").modal("show");
    }

    function sendCodeAll(id) {
        $.get("sendCode/sendCodeByBatchId", {
            "actId": "${actId}",
            "batchId": id,
            "type": "02",
            "sendType": "02"
        }, function (result) {
            if (result.success) {
                sendMsg(true, "发送短信成功");
                window.location.href = "sendCode/sendcode?id=${actId}"
            } else {
                sendMsg(false, result.errorMsg);
                window.location.href = "sendCode/sendcode?id=${actId}"
            }
        }, 'json');

    }
    function sendCodeMMSAll(id) {
        $.get("sendCode/sendCodeByBatchId", {
            "actId": "${actId}",
            "batchId": id,
            "type": "03",
            "sendType": "02"
        }, function (result) {
            if (result.success) {
                sendMsg(true, "发送彩信成功");
                window.location.href = "sendCode/sendcode?id=${actId}"
            } else {
                sendMsg(false, result.errorMsg);
                window.location.href = "sendCode/sendcode?id=${actId}"
            }
        }, 'json');
    }
    /*   function delBatch(id) {
     $.get("sendCode/delBatchById", {"batchId": id}, function (result) {
     if (result.success) {
     sendMsg(true, "删除批次成功");
     window.location.href = "sendCode/sendcode?id=${actId}"
     } else {
     sendMsg(false, result.errorMsg);
     window.location.href = "sendCode/sendcode?id=${actId}"
     }
     }, 'json');
     }*/
</script>
</body>
</html>
                