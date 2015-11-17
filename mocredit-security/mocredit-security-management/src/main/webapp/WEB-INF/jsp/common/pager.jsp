<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="index_paging">
	<c:choose>
		<c:when test="${empty page.list}">
			<b>没有查询到相应的数据</b>
		</c:when>
		<c:otherwise>
			<span>每页显示${page.pageSize }条</span>
			<span class="fy"> <a
				href="javascript:void(0);serachPageInfos('1',$('#pagehiddenhref').val(),'${page.pages}')"
				class="fy_sw">首页</a> <a
				href="javascript:void(0);serachPageInfos('${page.prePage}',$('#pagehiddenhref').val(),'${page.pages}')"
				class="fy_sw">上一页</a> 当前${page.pageNum}/${page.pages}页 <a
				href="javascript:void(0);serachPageInfos('${page.nextPage}',$('#pagehiddenhref').val(),'${page.pages}')"
				class="fy_sw">下一页</a> <a
				href="javascript:void(0);serachPageInfos('${page.pages}',$('#pagehiddenhref').val(),'${page.pages}')"
				class="fy_sw">尾页</a>
			</span>
		</c:otherwise>
	</c:choose>
</div>