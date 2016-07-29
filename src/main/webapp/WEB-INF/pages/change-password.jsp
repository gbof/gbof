<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change password page</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
<h1>Change password page</h1>

<p>
<c:if test="${error == true}">
	<b class="error">Invalid password.</b>
</c:if>
</p>

<table>
<tbody>
<tr>
<td>Old password:</td>
<td><input type="password" name="j_password1" id="j_password1" size="30" maxlength="32" /></td>
</tr>
<tr>
<td>New Password:</td>
<td><input type="password" name="j_password" id="j_password" size="30" maxlength="32" /></td>
</tr>
<tr>
<td></td>
<td><input type="submit" name="save" class="button" value="Save" /></td>

</tr>
</tbody>
</table>

<p>
<a href="${pageContext.request.contextPath}/index.html">Home page</a><br/>
</p>
</body>
</html>