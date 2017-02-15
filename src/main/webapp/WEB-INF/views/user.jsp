<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
<div style="text-align: center">
    <br> <br>
    <h1>Hello, ${pageContext.request.remoteUser}!</h1>
    <br><br>
    <p>Click <a href="logout">here</a> to logout</p>
</div>
</body>
</html>
