<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="list-group">
	<span class="list-group-item disabled">账户管理</span> <a
		href="account/saleaccount?accounttype=2" target="right-content"
		class="list-group-item text-center active">商户账户</a> <a
		href="account/operationright?accounttype=1" target="right-content"
		class="list-group-item text-center ">运营账户</a> <span
		class="list-group-item disabled">账款管理</span> <a
		href="creditRecord/container" target="right-content"
		class="list-group-item text-center">账款管理</a> <span
		class="list-group-item disabled">账单管理</span> <a
		href="billRecord/container" target="right-content"
		class="list-group-item text-center">账单管理</a> <span
		class="list-group-item disabled">升级维护</span> <a href="app/list"
		target="right-content" class="list-group-item text-center">升级维护</a> <span
		class="list-group-item disabled">系统管理</span> <a
		href="system/container" target="right-content"
		class="list-group-item text-center ">用户管理</a> <a href="role/container"
		target="right-content" class="list-group-item text-center">角色管理</a>
	<c:if test="${sessionScope.webuser.role eq 1}">
		<a href="system/opCenter" target="right-content"
			class="list-group-item text-center">运营中心</a>
	</c:if>

</div>