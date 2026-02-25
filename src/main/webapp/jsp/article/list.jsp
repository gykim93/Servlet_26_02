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
<style>
table>thead>tr>th, table>tbody>tr>td {
	padding: 10px;
}
</style>
</head>
<body>
	<!-- <a href="../home/main">메인으로 이동</a>  -->
	<h1>게시글 목록</h1>
	<table border="1"
		style="border-collapse: collapse; border-color: green">
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>제목</th>
				<th>내용</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Map<String, Object> articleRow : articleRows) {
			%>
			<tr style="text-algin: center;">
				<td><%=articleRow.get("id")%>번</td>
				<td><%=articleRow.get("regDate")%></td>

				<td><a href="detail?id=<%=articleRow.get("id")%>"><%=articleRow.get("title")%></a></td>

				<td><%=articleRow.get("body")%></td>
				<td><a
					onclick="if(confirm('정말 삭제하시겠습니까?') == false) {return false;}"
					href="doDelete?id=<%=articleRow.get("id")%>">delete</a></td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>


</body>
</html>