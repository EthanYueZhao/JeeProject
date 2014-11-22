<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View by date</title>
</head>
<body>
<%@ include file="Banner.jsp" %>
<form action="${pageContext.request.contextPath }/Faculty }" method="post">
You are viewing course ${sessionScope.course }  <input type="submit" name="submit" value="View General">
<br>
Here should be the attendance details table
<br>
</form>
<a href="MainPage.jsp">Back to main page</a>
</body>
</html>