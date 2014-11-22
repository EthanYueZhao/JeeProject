<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
</head>
<body>
<h1>Welcome to Attendance Register</h1>
<h3>Please Sign in</h3>
<form action="${pageContext.request.contextPath }/LogIn" method="post">
Username:<input name="username">
<br>
Password:<input name="password">
<br>
<button type="submit">Sign In</button>



</form>
</body>
</html>