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
	<table style='font-family:"Times New Roman", Times New Roman, monospace; font-size:200%' border=1>
		<thead>
			<tr>
				<th>Assignment</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${assignments}" var="assignment">
				<tr>
					<td><a href="TeacherController?action=viewStudents&assignmentName=<c:out value="${assignment.name}"/>&teacherID=<c:out value="${assignment.teacherID}"/>">
					<c:out value="${assignment.name}"/></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
