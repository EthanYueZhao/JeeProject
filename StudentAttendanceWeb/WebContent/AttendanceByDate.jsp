<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View by date</title>
<style>
table {
	width: 50%;
}

table,th,td {
	border: 1px solid black;
	border-collapse: collapse;
}

th,td {
	padding: 5px;
	text-align: left;
}

table#t01 tr:nth-child(even) {
	background-color: #eee;
}

table#t01 tr:nth-child(odd) {
	background-color: #fff;
}

table#t01 th {
	background-color: black;
	color: white;
}
</style>
</head>
<body>
	<%@ include file="Banner.jsp"%>
	<form action="${pageContext.request.contextPath }/MainServlet"
		method="get">
		You are viewing course ${requestScope.courseName} <input type="submit"
			name="submit" value="General View"> <br> Here should be
		the attendance general table <br> <input type="hidden"
			name="course" value="${requestScope.courseId}" />
	</form>
	<form action="${pageContext.request.contextPath }/Faculty" method="get">
	<input type="hidden" name="courseId" value="${requestScope.courseId}">
		<select name="courseSch" onchange="this.form.submit()">
			<c:forEach items="${requestScope.courseschedules}"
				var="courseschedule">
				<c:if test="${requestScope.courseSchId != 0}" var="result">
					<c:choose>
						<c:when
							test="${courseschedule.getIdcourseschedule() == requestScope.courseSchId}">
							<option selected value="${courseschedule.getIdcourseschedule()}">${courseschedule.getCoursedate()}</option>
						</c:when>
						<c:otherwise>
							<option value="${courseschedule.getIdcourseschedule()}">${courseschedule.getCoursedate()}</option>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${requestScope.courseSchId == 0}" var="result">
					<option value="${courseschedule.getIdcourseschedule()}">${courseschedule.getCoursedate()}</option>
				</c:if>
			</c:forEach>
		</select>
		<table border="1">
			<tr bgcolor="rightgray">
				<td>Student ID</td>
				<td>Student Name</td>
				<td>Attendance / Absence</td>
			</tr>
			<c:forEach items="${requestScope.students}" var="user">
				<tr>
					<td>${user.getIduser()}</td>
					<td>${user.getUsername()}</td>
					<c:forEach
							items="${user.getAttendances()}" var="attendance">
							<c:if
								test="${attendance.getCourseschedule().getIdcourseschedule() == requestScope.Idcourseschedule}"
								var="result">
								<c:choose>
									<c:when test="${attendance.getAttendance() == 1}">
									<td>Attendance</td>
									</c:when>
									<c:otherwise>
									<td bgcolor="red">Absence</td>
									</c:otherwise>
								</c:choose>
							</c:if>

						</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/MainServlet"
		method="post">
		<br> <br> <input type="submit" name="backToMain"
			value="Back to main page">
	</form>
</body>
</html>