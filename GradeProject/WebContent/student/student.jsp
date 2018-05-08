<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Student Classes</title>
</head>

<body>
<h1>Grades</h1>
	<table style='font-family:"Times New Roman", Times New Roman, monospace; font-size:200%' border=1>
		<thead>
			<tr>
				<th>Class</th>
				<th>Grade</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${classes}" var="studentclass">
				<tr>
					<td align="center"><c:out value="${studentclass.className}" /></td>
					<td align="center"><c:out value="${studentclass.grade}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
