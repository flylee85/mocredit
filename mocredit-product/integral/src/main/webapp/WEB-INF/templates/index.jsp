<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Demo</title>
    <jsp:include page="common/common.jsp" />
  </head>

  <body>
  	<jsp:include page="common/header.jsp" />
  
    <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">
			<jsp:include page="common/menu.jsp" />
			
<!-- 		begin：-这是 每个页面右边 不同的部分 	 -->
		<!--       <div class="col-xs-12 col-sm-9"> -->
		<!--         <div class="jumbotron"> -->
		<!--           <h1>欢迎您!!</h1> -->
		<!--         </div> -->
		<!--       </div> -->

<!-- 		示例 开始 -->
		<div class="col-xs-12 col-sm-10">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">司机信息</h3>
					</div>
					<div class="panel-body">
						<form action="${path}/driver" id="searchForm" method="post" class="form-horizontal" >
							<div class="col-xs-12" style="border: 1px solid #f5f5f5;padding: 5px;margin-bottom:5px">
								<div class="form-group">
									<label class="col-xs-1 control-label">司机姓名:</label>
									<div class="col-xs-2">
										<input type="text" name="name" value="${search.name}" class="form-control borderRadiusIE8">
									</div>
									<label class="col-xs-1 control-label">车牌号:</label>
									<div class="col-xs-2">
										<input type="text" name="license" value="${search.license}" class="form-control borderRadiusIE8">
									</div>
									<label class="col-xs-1 control-label">驾驶证号:</label>
									<div class="col-xs-2">
										<input type="text" name="idcard" value="${search.idcard}" class="form-control borderRadiusIE8">
									</div>
									<label class="col-xs-1 control-label">司机状态:</label>
									<div class="col-xs-2">
										<select name="cancelFlag" class="form-control">
											<option value="2" ${search.cancelFlag == '2' ? 'selected' : ''}>全部</option>
											<option value="0" ${search.cancelFlag == '0' ? 'selected' : ''}>正常</option>
											<option value="1" ${search.cancelFlag == '1' ? 'selected' : ''}>已注销</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-1 control-label input-width">创建时间:</label>
									<div class="col-xs-2">
										<input type="text" name="startime" value="${search.startime}"  class="form-control borderRadiusIE8 datetimepicker1" readonly>
									</div>
									<label for="" class="control-label col-xs-1 text-center label-arrows no-lr-padding">—</label>
									<div class="col-xs-2">
										<input type="text" name="endtime" value="${search.endtime}"  class="form-control borderRadiusIE8 datetimepicker2" readonly>
									</div>
									<button type="button" class="btn btn-primary" onclick="searchDriver('1')">搜索</button>
									<button type="button" class="btn btn-primary" onclick="emptyForm('searchForm')">清空</button>
								</div>
							</div>

							<div class="col-xs-12" style="padding: 0px;margin-bottom:5px">
								<button type="button" class="btn btn-success btn-sm" onclick="window.location.href='${path}/demo/edit'">[demo]新增</button>
								<button type="button" class="btn btn-success btn-sm"  onclick="batchDriver('1')">注销</button>
								<button type="button" class="btn btn-success btn-sm"  onclick="batchDriver('0')">激活</button>
							</div>

                            <div class="col-xs-12" style="padding: 0px;margin-bottom:5px">
								<table class="table table-bordered table-condensed table-hover table-striped">
									<thead>
										<tr class="success">
											<th><input type="checkbox" id="checkAllBox" name="checkAllBox" onclick="checkAllDriver(this)"></th>
											<th>司机编号</th>
											<th>司机姓名</th>
											<th>车牌号</th>
											<th>联系电话</th>
											<th>驾驶证号</th>
											<th>信用等级</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${drivers}" var="driver">
											<tr>
												<td><input type="checkbox" id="${driver.id}" name="driverBox" value="${driver.id}"></td>
												<td><a href="javascript:void(0);" onclick="jump('${driver.id}','detail','driver')">${driver.driverno}</a></td>
												<td>${driver.name}</td>
												<td><a href="javascript:void(0);" onclick="jump('${driver.id}','detail','car')">${driver.license}</a></td>
												<td>${driver.tel}</td>
												<td>${driver.idcard}</td>
												<td><span class="rateit" data-rateit-value="${driver.starImg}" data-rateit-ispreset="true" data-rateit-readonly="true"></span></td>
												<td>${driver.generatetime}</td>
												<td>
													<a href="#" onclick="jump('${driver.id}','toModify','')"><span title="修改" class="glyphicon glyphicon-pencil"></span></a>&nbsp; 
													<c:choose>
														<c:when test="${driver.cancelFlag == '0'}">
															<a href="#" onclick="cancelDriver('${driver.id}','1')"><span title="注销" class="glyphicon glyphicon-ban-circle"></span></a>
														</c:when>
														<c:otherwise>
															<a href="#" onclick="cancelDriver('${driver.id}','0')"><span title="激活" class="glyphicon glyphicon-ok-circle"></span></a>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

							<div class='text-center'>

								<c:choose>
									<c:when test="${empty drivers}">
										<b>没有查询到相应的数据</b>
									</c:when>
									<c:when test="${search.totalPage == 1}">
									</c:when>
									<c:otherwise>
										<ul class="pagination" id="pagination">
											<!--
											<li><a href="javascript:void(0);searchDriver('1')">首页</a></li>
											-->

											<c:if test="${search.hasLastPage == true}">
												<li><a href="javascript:void(0);searchDriver('${search.lastPage}')">上一页</a></li>
											</c:if>
											<c:if test="${1<(search.currentPage-3)}">
												<li><a href="javascript:void(0)">...</a></li>
											</c:if>
											<c:forEach var="x" begin="1" end="${search.totalPage}">
												<c:if test="${x>=(search.currentPage-3) && x<=(search.currentPage+3)}">
													<c:choose>
														<c:when test="${x==search.currentPage}">
															<li class="active"><a href="javascript:void(0);searchDriver('${x}')">${x}</a></li>
														</c:when>
														<c:otherwise>
															<li><a href="javascript:void(0);searchDriver('${x}')">${x}</a></li>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
											<c:if test="${(search.totalPage)>(search.currentPage+3)}">
												<li><a href="javascript:void(0)">....</a></li>
											</c:if>
											<c:if test="${search.hasNextPage == true}">
												<li><a href="javascript:void(0);searchDriver('${search.nextPage}')">下一页</a></li>
											</c:if>
											<!--
											<li><a href="javascript:void(0);searchDriver('${search.totalPage}')">尾页</a></li>

											<li>&nbsp;每页 <select name="pageC" onchange="$('#pageSize').val(this.value);searchDriver('1')">
													<option value="1" ${search.pageSize == '1' ? 'selected' : ''}>1</option>
													<option value="5" ${search.pageSize == '5' ? 'selected' : ''}>5</option>
													<option value="10" ${search.pageSize == '10' ? 'selected' : ''}>10</option>
													<option value="15" ${search.pageSize == '15' ? 'selected' : ''}>15</option>
													<option value="20" ${search.pageSize == '20' ? 'selected' : ''}>20</option>
											</select>条
											</li>
											-->
										</ul>
									</c:otherwise>
								</c:choose>


							</div>
								<input type="hidden" id="currentPage" name="currentPage" value="${search.currentPage}"> 
								<input type="hidden" id="pageSize" name="pageSize" value="">
								<input type="hidden" id="driverId" name="driverId" value="">
								<input type="hidden" id="opType" name="opType" value="">
								<input type="hidden" id="pageType" name="pageType" value="">
						</form>
					</div>
				</div>
			</div>
<!-- 		示例 结束 -->
		
		
<!-- 			end- -这是 每个页面右边 不同的部分 -->
			
				
				
      </div>

      <footer>
         <!--nothing-->
      </footer>
    </div>
  </body>
</html>
