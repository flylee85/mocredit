<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>亿美汇金订单平台</title>
    <!--     <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png"> -->
    <!-- 公共资源 -->
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/static/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/lib/bootstrap-datetimepicker/bootstrap-datetimepicker.css"
          rel="stylesheet"/>
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/static/css/offcanvas.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/lib/rateit/rateit.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap/css/signin.css"/>
    <script src="${pageContext.request.contextPath}/static/js/md5.js"></script>
    <script src="${pageContext.request.contextPath}/common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <style>.error {
        color: red;
    }</style>
</head>

<body>
<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"></a>
        </div>
        <c:if test="${sessionScope.webuser!=null}">
            <div class="collapse navbar-collapse" style="float:right">
                <ul class="nav " id="quit">
                    <li class="active"><a href="${path}/userInfo/quit">退出</a></li>
                </ul>
            </div>
        </c:if>
    </div>
    <!-- /.container -->
</div>
<div class="container">
    <form class="form-signin" action="loginAction" role="form" method="post">
        <h2 class="form-signin-heading" align="center">
            亿美汇金订单平台
        </h2>
        <br/>
        <input type="text" id="username" name="username" value="${username}" class="form-control" placeholder="用户名">
        <label id="u"> </label>
        <input type="password" id="password" class="form-control" placeholder="密码">
        <input type="hidden" name="password"/>
        <label id="p"> </label>
        <input type="text" class="form-control" id="checkCode" name="checkCode" placeholder="验证码">
        <img src="checkCode" id="changeCheckCode" onclick="reCaptcha()" height="35" size="10"/>
        <a href="#" onclick="reCaptcha()">看不清楚,点我</a>
        <label class="error">${error}</label>
        <button class="btn btn-lg btn-primary btn-block">登录</button>
    </form>

</div>
<script>
    $(function () {
        $("form").submit(function () {
            if (!$("#username").val()) {
                alert("请输入用户名")
                return false;
            }
            if (!$("#password").val()) {
                alert("请输入密码")
                return false;
            }
            if (!$("#checkCode").val()) {
                alert("请输入验证码")
                return false;
            }
            var password = $("#password").val();
            $("input[name='password']").val(hex_md5(password));
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
</script>
</body>
</html>
