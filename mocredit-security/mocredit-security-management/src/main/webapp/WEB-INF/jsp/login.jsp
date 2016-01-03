<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <base href="${pageContext.request.contextPath}/static">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>亿美汇金订单平台</title>
    <jsp:include page="common/common.jsp"></jsp:include>
    <script src="${pageContext.request.contextPath}/static/lib/md5.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap/css/signin.css"/>
    <style>.error {
        color: red;
    }</style>
</head>

<body>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <form class="form-signin" action="${path}/login" role="form" onsubmit="return enter_();" method="post">
        <h2 class="form-signin-heading" align="center">
            亿美汇金订单平台
        </h2>
        <br/>
        <input type="text" id="user" name="username" value="<shiro:principal/>" class="form-control" placeholder="用户名">
        <label id="u"> </label>
        <input type="password" name="password" class="form-control" placeholder="密码">
        <label id="p"> </label>
        <%--<label class="checkbox">--%>
        <%--<input type="checkbox" value="rememberMe" value="true"> 自动登录--%>
        <%--</label>--%>
        <label class="error">${error}</label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>

</div>
<script>
    if (window.top.location.href != window.location.href) {
        window.top.location.replace(window.location.href);
    }
</script>
</body>
</html>
