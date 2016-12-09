<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'picList.jsp' starting page</title>
    
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
    用户：${sessionScope.user.name}<br/> 
    
    <img src="upload/jack/ok.gif"></img>
    <input type="button" value="返回" onclick="history.back()" />
    <table border=1 >
    <tr>
    	<th>标题</th><th>上传时间</th><th>src</th><th>上传用户</th>
    </tr>
	    <c:forEach items="${requestScope.picList}" var="pic">
	    	<tr>
	    		<td>${pic.name }</td>
	    		<td>${pic.time }</td>
	    		<td><img  src="upload/${pic.user.name}/${pic.name}"/><br/>${pic.path }</td>
	    		<td>${pic.user.name }</td>
	    	</tr>
	    </c:forEach>
	</table>
  </body>
</html>
