<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Welcome.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- 
	<script type="text/javascript">
		var myfile=getElementsByName("myfile");
		var submit=getElementById("submit");
		if(myfile==null){
			submit.disabled=true;
		}else{
			submit.disabled=false;
		}
	
	</script>
-->
  </head>
  
  <body>
    <h1>Welcome!</h1>
    ${sessionScope.user.name }<br/>
    <br/>
    <a href="user.do?method=logout">退出登录</a>
    <hr/>
    <form action="pic.do?method=upload" method="post" enctype="multipart/form-data">    	
	    	<input type="file" name="myfile">${uploadErr }<br/>
	    	<input type="submit" value="上传" id="submit"><br/><br/>
	    	<a href="pic.do?method=findAll">查看所有</a><br/><br/>
	    	<a href="user.do?method=ctrl">控制请求</a>
    </form>
  </body>
</html>
