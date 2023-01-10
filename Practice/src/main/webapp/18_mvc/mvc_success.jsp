<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.LoginBean"%>

<html>
<head>
<title>MVC</title>

</head>

<body>
	<p>로그인 성공
	<p>
	<%
		LoginBean bean = (LoginBean) request.getAttribute("bean");
		out.print("아이디 :" + bean.getId());
	%>
</body>
</html>