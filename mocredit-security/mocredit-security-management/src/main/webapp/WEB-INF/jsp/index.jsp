<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>权限管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layout-default-latest.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/plugin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
</head>
<body>

<iframe name="content" class="ui-layout-center"
        src="${pageContext.request.contextPath}/welcome" frameborder="0" scrolling="auto"></iframe>
<div class="ui-layout-north">欢迎[<shiro:principal/>]，<a href="${pageContext.request.contextPath}/logout">退出</a></div>
<div class="ui-layout-south">
    <div class="footer" align="center">
        <p>Copyright&copy;北京亿美汇金科技有限公司</p>

        <p>
            <a href="http://www.miibeian.gov.cn/" target="_blank" rel="nofollow">京ICP证101025号</a>
            <span>|</span>京ICP备12017803号-4
            <span>|</span><a href="http://www.mocredit.cn/">网址</a>
        </p>
    </div>
</div>
<div class="ui-layout-west">
    <%--    <c:forEach items="${menus}" var="m">
            <a href="${pageContext.request.contextPath}${m.url}" target="content">${m.name}</a><br/>
        </c:forEach>--%>

    <ul class="nav" data-spy="affix" data-offset-top="50">
        <c:forEach items="${menus}" var="m">
            <li class="dropdown-submenu">
                <a href="${pageContext.request.contextPath}${m.url}" target="content" class="changePage"><i
                        class="icon-list icon-xlarge"></i>${m.name}</a>
                <c:if test="${!empty m.secondResourceList }">
                    <ul class="dropdown-menu">
                        <c:forEach items="${m.secondResourceList}" var="n">
                            <li><a href="${pageContext.request.contextPath}${n.url}" target="content"
                                   class="changePage">${n.name}</a></li>
                        </c:forEach>
                    </ul>
                </c:if>
            </li>
            </li>
        </c:forEach>
    </ul>
</div>

<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.layout-latest.min.js"></script>
<script>
    $(function () {
        $(document).ready(function () {
            $('body').layout({applyDemoStyles: true});
        });
    });
</script>
</body>
</html>