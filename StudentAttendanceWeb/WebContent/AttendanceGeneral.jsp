<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance General</title>
</head>
<body>
	<%@ include file="Banner.jsp"%>
	<form action="${pageContext.request.contextPath }/Faculty }"
		method="post">
		You are viewing course ${requestScope.courseName} <input type="submit"
			name="submit" value="View By Date"> <br> Here should be
		the attendance general table <br>
		<table border="1">
			<c:forEach items="${requestScope.students}" var="user">
			<%int p = 0; %>
				<tr>
					<td>${user.getIduser()}</td>
					<td>${user.getUsername()}</td>
					<td>
					<c:forEach items="${user.getAttendances()}" var="attendance">
						<c:if test="${attendance.getAttendance() == 1}" var="result">
						<%p = p+1; %>
						
						</c:if>
					</c:forEach>
					<%=p %> / ${user.getAttendances().size()}
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	<a href="MainPage.jsp">Back to main page</a>
</body>
</html>