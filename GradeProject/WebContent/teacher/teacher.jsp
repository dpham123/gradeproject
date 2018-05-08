<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Teacher Classes</title>
</head>

<body>
<h1>Classes</h1>
	<table style='font-family:"Times New Roman", Times New Roman, monospace; font-size:200%' border=1>
		<thead>
			<tr>
				<th>Class</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${classes}" var="studentclass">
				<tr>
					<td><a href="TeacherController?action=viewAssignments&className=<c:out value="${studentclass.className}"/>&teacherID=<c:out value="${studentclass.teacherID}"/>">
					<c:out value="${studentclass.className}"/></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
