<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName();
	if(request.getServerPort()!=80){
		basePath+=":"+request.getServerPort();
	}
	basePath+=path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手机充值</title>

<link href="css/ebank.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--head-start-->
<div id="head">
<div class="top">

<div class="logo"><img src="images/ebank_logo.png"> 
</div>
</div>

<div class="menu"> 
<ul>
<li class="select">手机充值</li>
</ul>
</div>
</div>
<!--head-end-->




<div id="main">

<div id="other">
<div class="all_line"></div>






<div class="shop_win">
<div class="shop_win_bg"></div>

提示信息：将在24小时内充值完毕，请耐心等待</br>
<a href="<%=basePath%>/recharge.jsp" class="a4">返回充值页面</a>
</div>



</div>







</div>
<div id="foot">
<div class="foot_text">2012 北京亿美汇金信息技术有限公司　ICP证：京B2-20100257</div>
</div>
</body>
</html>

