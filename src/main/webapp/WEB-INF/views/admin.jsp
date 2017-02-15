<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" uri="com.nixsolutions.web.tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Admin</title>
</head>
<body>
<%@ include file="/WEB-INF/views/topRight.jsp" %>
<div align="center">
    <br> <br>
    <a href="add_user">Add new user</a>
    <br> <br>
</div>
<t:table users="${users}"/>
</body>
</html>
