<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Students Attendance</title>
</head>
<body>
<%@ include file="Banner.jsp" %>
You are viewing course ${sessionScope.course }
<br>
<br><form action="${pageContext.request.contextPath}/MainServlet" method="post">
		<input type="submit" name="attend" value="I am here!">
		<br><br><input type="submit" name="backToMain" value="Back to main page">
	</form><br>
</body>
</html>