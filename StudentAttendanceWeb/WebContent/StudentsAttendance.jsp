<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Students Attendance</title>
</head>
<body>
	<%@ include file="Banner.jsp"%>
	You are viewing course ${sessionScope.course }
	<br>
	<br>
		<form action="${pageContext.request.contextPath}/Sign" method="post">
		<input type="submit" name="attend" value="I am here!">
		</form>
	<form action="${pageContext.request.contextPath}/MainServlet"
		method="post">
		<c:choose>
			<c:when
				test='${(requestScope.attendanceSize == 0)&&(requestScope.attendedNum == 0)}'>
				<p>No attendance recode yet</p>
			</c:when>
			<c:otherwise>
				<p>
					Attended:
					<c:out value="${requestScope.attendedNum}" />
					/
					<c:out value="${requestScope.attendanceSize}" />
				</p>
						Attendance History:
				<table>
					<c:forEach items="${requestScope.attendanceList}" var="eachAttend">
						<tr>
							<th>Date</th>
							<th>Attendance</th>
						</tr>
						<tr>
							<td><c:out value="${eachAttend.getCourseschedule().getCoursedate()}"></c:out></td>
							<td><c:if test="${eachAttend.getAttendance() == 1}">Attended</c:if>
								<c:if test="${eachAttend.getAttendance() == 0}">Absent</c:if></td>
						</tr>
					</c:forEach>
				</table>

			</c:otherwise>
		</c:choose>
		<br> <br> <input type="submit" name="backToMain"
			value="Back to main page">
	</form>
	<br>
</body>
</html>