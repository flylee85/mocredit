<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName();
	if(request.getServerPort()!=80){
		basePath+=":"+request.getServerPort();
	}
	basePath+=path+"/";
	String url = request.getServerName();
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亿美汇金话费充值平台</title>

<link rel="stylesheet" href="<%=basePath %>/css/login.css" type="text/css" media="screen" />
<script type="text/javascript" charset="utf-8">
	
	function check(){
		var phone = document.getElementById("phone");
		var phone2 = document.getElementById("phone2");
		var charcode = document.getElementById("charcode");
		var rand = document.getElementById("rand");
		var phonereg = /^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/;
		var isphone = phone.value.replace(phonereg, "");
		if (phone==null||phone.value.length == 0) {
			alert("请输入手机号!");
			return false;
		}
		if (isphone.length > 0) {
			alert("手机号格式错误!");
			return false;
		}
		if (phone2==null||phone2.value.length == 0) {
			alert("请再次输入手机号!");
			return false;
		}
		if(phone.value!=phone2.value){
			alert("两次输入的手机号不一致!");
			return false;
		}
		if (charcode==null||charcode.value.length == 0) {
			alert("请输入礼券号!");
			return false;
		}
		if (charcode.value.length != 12 ) {
			alert("输入的礼券号有误!");
			return false;
		}
		if (rand==null||rand.value.length == 0) {
			alert("请输入验证码!");
			return false;
		}
	}
	function disnableself(obj){
		var form1 = document.getElementById("form1");
		obj.disabled = true;
		form1.submit();
	}
	function changeValidateCode(obj){
		obj.src="imageview?r="+Math.random();
	}
	
</script>
</head>
<body>
<div class="login">

<div class="logo">
<%  if(url.contains("nyyhcz.huilife365.com")){ //农行%>
<img src="images/logo1.png">
<%}else if(url.contains("jsyhcz.huilife365.com")){//建行 %>
<img src="images/logo2.png">
<%}else { //光大%>
<img src="images/logo.png">
<%} %>
</div>

<form id="form1" action="recharge" method="post" onsubmit="return check();">
<div class="login_1">
<table width=338 height="260" class="table1">
<tr>
  <td></td>
  <td height="19"></td>
  <td>&nbsp;</td>
</tr>
<tr>
  <td width="31"></td>
  <td width="106" height="40">请输入手机号：</td>
  <td width="185"><input id="phone"  name="phone" class="login_input" /></td></tr>
<tr>
  <td></td>
  <td height="40">请确认手机号：</td>
  <td><input id="phone2"  name="phone2" class="login_input"></input></td></tr>
<tr>
  <td></td>
  <td height="40">请输入礼券号：</td>
  <td><input id="charcode"  name="charcode" class="login_input"></input></td></tr>
<tr>
  <td>&nbsp;</td>
  <td height="40">请输入验证码：</td>
  <td><input id="rand"  name="rand" class="login_input1"></input>
  <span><img src="imageview" title="验证码" onclick="changeValidateCode(this)"  style="vertical-align: middle;" /></span></td>
</tr>
<tr>
  <td height="41"></td>
  <td></td>
  <td><input id="sub" name="sub" type="submit"   onclick="disnableself(this)"  value="充值" class="login_input3" ></td>
</tr>
<tr>
  <td></td>
  <td></td>
  <td>&nbsp;</td>
</tr>
</table>
 </div>
</form>
<div id="foot">&#169; Copyright 2010 亿美汇金 | 北京亿美汇金信息技术有限公司 </div>
</div>
<script type="text/javascript" charset="utf-8">
var message = "${message}";
	var subbtn = document.getElementById("sub");
	subbtn.disabled = false;
    	if(message!=null){
	    	if(message.split(":")[0]=="Error"){
	    		alert(message.split(":")[1]);
	    	}else if(message.split(":")[0]=="Success"){
	    		alert(message.split(":")[1]);
	    	}
    	}
    	</script>
</body>

</html>

