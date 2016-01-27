<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>亿美汇金权限系统</title>
    <jsp:include page="common/common.jsp"></jsp:include>
    <script src="${pageContext.request.contextPath}/static/lib/md5.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap/css/signin.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/plugin.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js" type="text/javascript"></script>
    <style>.error {
        color: red;
    }</style>
</head>

<body>
<!-- .modal 发送提示 -->
<div class="modal fade operaState in " data-keyboard="false" id="operaState">
    <div class="modal-dialog modal-sm">
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
<jsp:include page="common/header.jsp"/>
<div class="container">
    <form class="form-signin" action="${path}/login" role="form" method="post">
        <h2 class="form-signin-heading" align="center">
            亿美汇金权限系统
        </h2>
        <br/>
        <input type="text" id="username" name="username" value="${username}" class="form-control"
               placeholder="用户名">
        <label id="u"> </label>
        <input type="password" id="password" name="password" class="form-control" placeholder="密码">
        <label id="p"> </label>
        <c:if test="${addCaptcha==1}">
            <input type="text" class="form-control" id="checkCode" name="captcha" placeholder="验证码">
            <label id="c"> </label>
            <img src="checkCode" id="changeCheckCode" onclick="reCaptcha()" height="35" size="10"/>
            <a href="javascript:;" onclick="reCaptcha()">换一张</a>
        </c:if>
        <label class="error">${error}</label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>

</div>
<script>
    $(function () {
        $("form").submit(function () {
            if (!$("#username").val()) {
                sendMsg(false, "请输入用户名");
                return false;
            } else {
                if ($("#username").val().length >= 10) {
                    sendMsg(false, "输入用户名过长");
                    return false;
                }
            }
            if (!$("#password").val()) {
                sendMsg(false, "请输入密码");
                return false;
            } else {
                if ($("#password").val().length >= 10) {
                    sendMsg(false, "输入密码过长");
                    return false;
                }
            }
            <c:if test="${addCaptcha==1}">
            if (!$("#checkCode").val()) {
                sendMsg(false, "请输入验证码");
                return false;
            } else {
                if ($("#checkCode").val().length >= 10) {
                    sendMsg(false, "输入验证码过长");
                    return false;
                }
            }
            </c:if>
            return true;
        })
    })
    if (window.top.location.href != window.location.href) {
        window.top.location.replace(window.location.href);
    }
    function reCaptcha() {

        var rdm = Math.random();
        $("#changeCheckCode").attr("src", "checkCode.json?rdm=" + rdm);
    }
    function sendMsg(isOk, msg, callback) {
        if (isOk) {
            $("#operaState .modal-body i").removeClass("icon-fail").addClass("icon-success");
        } else {
            $("#operaState .modal-body i").removeClass("icon-success").addClass("icon-fail");
        }
        $("#operaState").find("#promptMsg").parent().html('<label id="promptMsg"></label>');
        var msgArray = msg.split("！");
        if (msgArray.length > 1) {
            for (var i = 0; i < msgArray.length; i++) {
                if (i == 0) {
                    $("#operaState").find("#promptMsg").html(msgArray[0] + "！\n");
                } else if (i == msgArray.length - 1) {
                    $("#operaState").find("#promptMsg").parent().append('<label>' + msgArray[i] + '</label>');
                } else {
                    $("#operaState").find("#promptMsg").parent().append('<label>' + msgArray[i] + '！</label>');
                }
            }
        } else {
            $("#operaState").find("#promptMsg").html(msg);
        }

        $("#operaState").modal("show");
        if (callback != null) {
            window.setTimeout(callback, 1000);
            window.setTimeout('$("#operaState").modal("hide")', 1000);
        } else {
            window.setTimeout('$("#operaState").modal("hide")', 1000);
        }
    }
</script>
</body>
</html>
