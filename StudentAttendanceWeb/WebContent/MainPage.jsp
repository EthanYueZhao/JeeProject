<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>
</head>
<body>
	<%@ include file="Banner.jsp"%>
	<form action="${pageContext.request.contextPath }/MainServlet" method="post">
		Please select your course: <select name="course">
			<option value="0" selected>None</option>

			<c:forEach items="${requestScope.courses}" var="course">
				<option value="${course.getIdcourse()}">${course.getCoursename()}</option>
			</c:forEach>
		</select>
		<button type="submit"  name="submit" value="${sessionScope.userType}">Go!</button>
	</form>
</body>
</html>