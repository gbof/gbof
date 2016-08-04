<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change password page</title>
<style>
.error {
	color: red;
}
.correct {
	color: green;
}

</style>
</head>
<body>
<h1>Change password page</h1>

<p>
<c:if test="${error == true}">
	<b class="error">Invalid old password.</b>
</c:if>
</p>
<p>
<c:if test="${correct == true}">
	<b class="correct">Password changed.</b>
</c:if>
</p>

<form method="POST" action="${pageContext.request.contextPath}/password-verify">
<table>
<tbody>
<tr>
<td>Old password:</td>
<td><input type="password" name="oldpassword" size="30" maxlength="32" /></td>
</tr>
<tr>
<td>New Password:</td>
<td><input type="password" name="newpassword" size="30" maxlength="32" /></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="Change" /></td>

</tr>
</tbody>
</table>
</form>
<p>
<a href="${pageContext.request.contextPath}/index.html">Home page</a><br/>
</p>
</body>
</html>