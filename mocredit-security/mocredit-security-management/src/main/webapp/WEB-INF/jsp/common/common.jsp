<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    request.setAttribute("path", path);
%>
<!--     <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png"> -->
<!-- 公共资源 -->
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/static/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/lib/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet"/>
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/static/css/offcanvas.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/lib/rateit/rateit.css" rel="stylesheet" />
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/static/lib/html5shiv.min.js"/>
<script src="${pageContext.request.contextPath}/static/lib/respond.min.js"/>
<![endif]-->

<script>var _path = '/>${pageContext.request.contextPath}/static';</script>
<script src="${pageContext.request.contextPath}/static/lib/jquery.min.js"/>
<script src="${pageContext.request.contextPath}/static/lib/rateit/jquery.rateit.min.js"/>
<script src="${pageContext.request.contextPath}/static/lib/bootstrap/js/bootstrap.js"/>
<script src="${pageContext.request.contextPath}/static/lib/bootstrap-paginator.js"/>
<script src="${pageContext.request.contextPath}/static/lib/jquery.cookie.js"/>

<script src="${pageContext.request.contextPath}/static/lib/bootstrap-datetimepicker/bootstrap-datetimepicker.js"/>
<script src="${pageContext.request.contextPath}/static/js/main.js"/>
<script src="${pageContext.request.contextPath}/static/lib/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"/>
<!--
<style  type="text/css">
.form-control[readonly]{
cursor:default;
background-color:#ffffff;
}
</style>
-->