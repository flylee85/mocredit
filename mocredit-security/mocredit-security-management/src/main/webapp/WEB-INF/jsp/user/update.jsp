<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mfn" uri="/WEB-INF/tld/mocredit-functions.tld" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/css/zTreeStyle/zTreeStyle.css">
    <style>
        ul.ztree {
            margin-top: 10px;
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 220px;
            height: 200px;
            overflow-y: scroll;
            overflow-x: auto;
        }
    </style>
</head>
<body>
<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>
<div>
    <form action="update" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${user.id}"/>
        <input type="hidden" name="salt" value="${user.salt}"/>

        <div class="form-group has-success has-feedback">
            <label class="control-label col-sm-3" for="username">用户名</label>

            <div class="col-sm-9">
                <input id="username" name="username"
                       type="text" prompt="填写用户名" min="1" max="64" value="${user.username}"
                       placeholder="填写用户名" class="form-control">
            </div>
        </div>
        <div class="form-group has-success has-feedback">
            <label class="control-label col-sm-3" for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>

            <div class="col-sm-9">
                <input id="password"
                       name="password" type="password" prompt="请密码" min="1" value="${user.password}"
                       max="64" placeholder="填写密码" class="form-control">
            </div>
        </div>
        <div class="form-group has-success has-feedback">
            <label class="control-label col-sm-3" for="password">状&nbsp;&nbsp;&nbsp;&nbsp;态</label>

            <div class="col-sm-9">
                <select id="locked" name="locked" class="form-control">
                    <option value="false"
                            <c:if test="${!user.locked}">selected="selected"</c:if>>启用
                    </option>
                    <option value="true"
                            <c:if test="${user.locked}">selected="selected"</c:if>>冻结
                    </option>
                </select>
            </div>
        </div>
        <div class="form-group has-success has-feedback">
            <label class="control-label col-sm-3" for="roleId">角&nbsp;&nbsp;&nbsp;&nbsp;色</label>

            <div class="col-sm-9">
                <select id="roleId" name="roleId" class="form-control">
                    <option value="">请选择角色</option>
                    <c:forEach items="${roleList}" var="r">
                        <option value="${r.id}"
                                <c:if test="${r.id eq roleId}">selected="selected"</c:if>>${r.role}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">确认</button>
        </div>
    </form>
</div>
<!-- /.modal-content -->
<!-- /.modal-dialog -->
<!-- /.modal -->
<script src="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
</body>
</html>