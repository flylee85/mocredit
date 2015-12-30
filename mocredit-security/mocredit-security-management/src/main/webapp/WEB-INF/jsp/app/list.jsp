<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mfn" uri="/WEB-INF/tld/mocredit-functions.tld" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
</head>
<body>

<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>
<div>
    <shiro:hasPermission name="app:create">
        <div class="row">
            <div class="form-group">
                <div class="col-md-12">
                    <div class="col-md-12 text-left">
                        <a class="btn btn-primary" data-toggle="modal"
                            <%--data-target="#myModal"--%>
                           href="${pageContext.request.contextPath}/app/create">应用新增</a>
                    </div>
                </div>
            </div>
        </div>
    </shiro:hasPermission>
    <div class="form-group">
        <div>
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>应用名称</th>
                    <th>应用KEY</th>
                    <th>应用安全码</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${appList}" var="app">
                    <tr>
                        <td>${app.name}</td>
                        <td>${app.appKey}</td>
                        <td>${app.appSecret}</td>
                        <td>
                            <shiro:hasPermission name="app:update">
                                <a href="${pageContext.request.contextPath}/app/${app.id}/update">修改</a>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="app:delete">
                                <a href="${pageContext.request.contextPath}/app/${app.id}/delete">删除</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${pageTotal>=1}">
                <ul class="pagination">
                    <li><a href="app?page=1">首页</a></li>
                    <c:if test="${page>1}">
                        <li><a href="app?page=${page-1 }">上一页</a></li>
                    </c:if>
                    <c:if test="${page<=1}">
                        <li class="disabled"><a href="#">上一页</a></li>
                    </c:if>
                    <li><a>${page}/${pageTotal}</a></li>
                    <c:if test="${pageTotal-page>=1}">
                        <li><a href="app?page=${page+1 }">下一页</a></li>
                    </c:if>
                    <c:if test="${pageTotal-page<1}">
                        <li class="disabled"><a href="#">下一页</a></li>
                    </c:if>
                    <li><a href="app?page=${pageTotal}">末页</a></li>
                </ul>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>