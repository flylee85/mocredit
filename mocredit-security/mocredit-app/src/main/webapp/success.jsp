<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<body>
hello app1.<br/>
<c:forEach items="${menus}" var="m">
    <li class="dropdown-submenu">
        <a href="${m.url}" class="changePage"><i class="icon-list icon-xlarge"></i>${m.name}</a>
        <c:if test="${!empty m.secondResourceList }">
            <ul class="dropdown-menu">
                <c:forEach items="${m.secondResourceList}" var="n">
                    <li><a href="${n.url}" class="changePage">${n.name}</a></li>
                </c:forEach>
            </ul>
        </c:if>
    </li>
    </li>
</c:forEach>

<shiro:guest>
    <a href="${pageContext.request.contextPath}/login?backUrl=${pageContext.request.contextPath}/hello">点击登录</a>
</shiro:guest>

<shiro:authenticated>
    欢迎<shiro:principal/>登录<br/>
    <shiro:hasRole name="admin">
        您拥有admin角色<br/>
    </shiro:hasRole>
    <shiro:lacksRole name="role2">
        您没有role2角色<br/>
    </shiro:lacksRole>
    <shiro:lacksRole name="role1">
        您没有role1角色<br/>
    </shiro:lacksRole>

    <h2>设置会话属性</h2>

    <form action="${pageContext.request.contextPath}/attr" method="post">
        键：<input type="text" name="key">
        值：<input type="text" name="value">
        <input type="submit" value="设置会话属性">
    </form>
    <h2>获取会话属性</h2>

    <form action="${pageContext.request.contextPath}/attr" method="get">
        键：<input type="text" name="key">
        值：<input type="text" value="${value}">
        <input type="submit" value="获取会话属性">
    </form>
    <a href="${pageContext.request.contextPath}/logout?backUrl=${pageContext.request.contextPath}">注销</a>
</shiro:authenticated>

</body>
</html>