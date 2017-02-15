<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; encoding=utf-8" %>
<body>
<div align="center" class="centerLayer">
    <div class="caption">
        <br>
        <h2>Welcome!</h2>
        <br>
    </div>

    <p style="color: red">
        <c:if test="${param.error == 'true'}">
            <c:out value="Login or password are not correct"/>
        </c:if>
    </p>

    <form name="login_form" action="j_spring_security_check" id="login_form" method="post">

        <table align="center">
            <tbody>
            <tr>
                <td class="td_nowrap">Login :</td>
                <td><input type="text" name="j_username" value=""/>
                </td>
            </tr>
            <tr>
                <td class="td_nowrap">Password :</td>
                <td><input type="password" name="j_password" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right" style="text-align: right">
                    <input type="submit" name="login" value="Sign In"/>
                </td>
            </tr>
            </tbody>
        </table>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
    <a href="sign_up">Sign Up</a>
</div>
</body>
</html>
