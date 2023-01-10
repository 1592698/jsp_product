<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href ="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>주문 완료</title>
</head>
<body>
	<%
	String shipping_cartId= "";
	String shipping_shippingDate = "";
	Cookie[] cookies = request.getCookies();
	
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];
			String n = thisCookie.getName();
			if (n.equals("Shipping_cartId"))
				shipping_cartId = URLDecoder.decode((thisCookie.getValue()), "utf-8");
			if(n.equals("Shipping_shippingDate"))
				shipping_shippingDate = URLDecoder.decode((thisCookie.getValue()), "utf-8");
		}
	}
	
	%>
	
	<jsp:include page="../inc/menu.jsp"/>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3"> 주문완료</h1>
		</div>
	</div>

	<div class="container">
		<h2 class="alert alert-danger">주문해주셔서 ㄳㄳ</h2>
		<p>주문은 <% out.println(shipping_shippingDate);  %>에 배송될 예정입니당!@
		<p>주문번호 : <% out.println(shipping_cartId); %>
	</div>
	<div class="container">
		<p><a href="./products.jsp" class="btn btn-secondary"> &laquo; 상품 목록</a>
	</div>
</body>
</html>

<%

for (int i = 0; i < cookies.length; i++) {
	Cookie thisCookie = cookies[i];
	String n = thisCookie.getName();
	if (n.equals("Shipping_cartId"))
		thisCookie.setMaxAge(0);
	
	if(n.equals("Shipping_name"))
		thisCookie.setMaxAge(0);
	
	if(n.equals("Shipping_shippingDate"))
		thisCookie.setMaxAge(0);
	
	if(n.equals("Shipping_country"))
		thisCookie.setMaxAge(0);
	
	if(n.equals("Shipping_zipCode"))
		thisCookie.setMaxAge(0);
	if(n.equals("Shipping_addressName"))
		thisCookie.setMaxAge(0);
	
	response.addCookie(thisCookie);
	}
%>