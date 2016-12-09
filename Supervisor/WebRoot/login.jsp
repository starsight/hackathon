<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <h1>用户登录</h1>
  <hr/>
    <form action="Auser.do?method=login" method="post">
    	用户名：<input type="text" id="uname" name="uname"/><br/>
    	密&nbsp;码&nbsp;：<input type="password" id="upwd" name="upwd"><br/>
    	<input type="submit" value="登录"/>
    	<br/>
		<a href="reg.jsp" target="_parent">注册</a><br/>
    	<input type="radio" name="userRight" checked value="3"/>普通用户&nbsp;&nbsp;
    	<input type="radio" name="userRight" value="1"/>系统管理员<br/>
    	
    	<span>${loginErr }</span>
   	</form>
    	
    	<hr/>

  </body>
</html>
