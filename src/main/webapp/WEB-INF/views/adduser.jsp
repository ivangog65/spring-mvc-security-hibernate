<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new user</title>
</head>
<body>
<%@ include file="/WEB-INF/views/topRight.jsp" %>
<h2>Add new user</h2>
<form:form modelAttribute="userBean" action="add_user">
    <table>
        <tr>
            <td>Login</td>
            <td><form:input path="login"/></td>
            <td><form:errors path="login"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><form:password path="password"/></td>
            <td><form:errors path="password"/></td>
        </tr>
        <tr>
            <td>Password again</td>
            <td><form:password path="passwordAgain"/></td>
            <td><form:errors path="passwordAgain"/></td>
        </tr>
        <tr>
            <td>E-mail</td>
            <td><form:input path="email"/></td>
            <td><form:errors path="email"/></td>
        </tr>
        <tr>
            <td>First name</td>
            <td><form:input path="firstName"/></td>
            <td><form:errors path="firstName"/></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><form:input path="lastName"/></td>
            <td><form:errors path="lastName"/></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td><form:input path="birthday"/></td>
            <td><form:errors path="birthday"/></td>
        </tr>
        <tr>
            <td>Role</td>
            <td><form:select path="role" items="${roles}">
            </form:select></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td align="right"><input type="submit" style="width: 100px" value="Submit"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
