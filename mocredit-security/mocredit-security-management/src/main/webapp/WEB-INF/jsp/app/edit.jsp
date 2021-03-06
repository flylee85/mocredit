<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mfn" uri="/WEB-INF/tld/mocredit-functions.tld" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
</head>
<body>

    <form:form method="post" commandName="app">
        <form:hidden path="id"/>
        <form:hidden path="available"/>

        <div class="form-group">
            <form:label path="name">应用名称：</form:label>
            <form:input path="name" size="40"/>
        </div>

        <div class="form-group">
            <form:label path="appKey">应用KEY：</form:label>
            <form:input path="appKey" size="40"/>
        </div>

        <div class="form-group">
            <form:label path="appSecret">应用安全码：</form:label>
            <form:input path="appSecret" size="40"/>
        </div>

        <form:button>${op}</form:button>

    </form:form>

</body>
</html>