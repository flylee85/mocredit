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
    <shiro:hasPermission name="authorization:create">
        <div class="row">
            <div class="form-group">
                <div class="col-md-12">
                    <div class="col-md-12 text-left">
                        <a class="btn btn-primary" data-toggle="modal"
                            <%--data-target="#myModal"--%>
                           href="${pageContext.request.contextPath}/authorization/create">授权新增</a>
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
                    <th>应用</th>
                    <th>用户</th>
                    <th>角色列表</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${authorizationList}" var="authorization">
                    <tr>
                        <td>${mfn:appName(authorization.appId)}</td>
                        <td>${mfn:username(authorization.userId)}</td>
                        <td>${mfn:roleNames(authorization.roleIds)}</td>
                        <td>
                            <shiro:hasPermission name="authorization:update">
                                <a href="${pageContext.request.contextPath}/authorization/${authorization.id}/update">修改</a>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="authorization:delete">
                                <a href="${pageContext.request.contextPath}/authorization/${authorization.id}/delete">删除</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>