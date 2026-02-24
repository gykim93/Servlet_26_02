<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<body>

	<h1>게시글 목록</h1>
	<a href="https://www.naver.com" target="_blank">네이버</a>
	<a href="http://localhost:8080/gy_servlet/article/list" target="_blank">리스트 새탭</a>
	<a href="http://localhost:8080/gy_servlet/article/detail" target="_blank">상세보기 새탭</a>

	<ul>
		<%
		for (Map<String, Object> articleRow : articleRows) {
		%>
		<li><%=articleRow.get("id")%>번, <%=articleRow.get("title")%>, <%=articleRow.get("body")%></li>
		<%
		}
		%>

	</ul>


</body>
</html>