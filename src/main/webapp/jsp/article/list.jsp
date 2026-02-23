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
<title>article list</title>
</head>
<body>

	<h2>게시글 목록</h2>

	<ul>
		<li><%=articleRows.get(0).get("id")%>번, <%=articleRows.get(0).get("title")%>,
			<%=articleRows.get(0).get("body")%></li>
		<li><%=articleRows.get(1).get("id")%>번, <%=articleRows.get(1).get("title")%>,
			<%=articleRows.get(1).get("body")%></li>
		<li><%=articleRows.get(2).get("id")%>번, <%=articleRows.get(2).get("title")%>,
			<%=articleRows.get(2).get("body")%></li>
	</ul>

	<h2>게시글 목록 버전2</h2>
	<ul>
		<%
		for (int i = 0; i < articleRows.size(); i++) {
		%>
		<li><%=articleRows.get(i).get("id")%>번, <%=articleRows.get(i).get("title")%>,
			<%=articleRows.get(i).get("body")%></li>
		<%
		}
		%>

	</ul>

	<h2>게시글 목록 버전3</h2>

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