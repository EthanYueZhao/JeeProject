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
<%@ include file="Banner.jsp" %>
<form action="${pageContext.request.contextPath }/Main" method="post">
Please select your course: <select name="course">
						<option value="none" selected>None</option>
						
						<c:forEach items="${sessionScope.courses}" var="cse">
							 <option value="${cse.idcourse}">${cse.coursename}</option> 
						</c:forEach>
				</select>
				<button type="submit">Go!</button>
</form>
</body>
</html>