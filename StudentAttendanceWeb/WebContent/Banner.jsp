<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Banner</title>
</head>
<body>

	<h3>Welcome ${sessionScope.userName}</h3>
	<p>
		The current time:<b> ${sessionScope.currentTime}</b>
	</p>
	<p>You are a ${sessionScope.userType}.</p>

	<form action="${pageContext.request.contextPath}/LogIn" method="get">
		<input type="submit" value="log out" />
	</form>
	<hr />
</body>
</html>