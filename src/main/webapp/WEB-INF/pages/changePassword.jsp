<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>GBOF - Change password</title>
	<style>
	.error {
		color: red;
	}
	.correct {
		color: green;
	}
	
	</style>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />
	
	<style>
      		<%@include file="/web-resources/css/changePassword.css" %>
	</style>
		<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition>
		
	<div class="container">		
		<h3>Change password</h3>
		
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
		<p>
		<c:if test="${error2 == true}">
			<b class="error">New passwords are not the same</b>
		</c:if>
		</p>
		
		<form method="POST" action="${pageContext.request.contextPath}/password-verify">
			<table>
				<tbody>
				<tr>
					<td>Old password:</td>
					<td><input class="inputPass" type="password" name="oldpassword" size="30" maxlength="32" required/></td>
				</tr>
				<tr>
					<td>New password:</td>
					<td><input class="inputPass" type="password" name="newpassword" size="30" maxlength="32" required/></td>
				</tr>
				<tr>
					<td>Repeat new password:</td>
					<td><input class="inputPass" type="password" name="newpassword2" size="30" maxlength="32" required/></td>
				</tr>
				<tr>
					<td></td>
					<td>
					<input type="submit" class="btn btn-primary btn-change pull-right" value="Change" />
				</td>
				
				</tr>
				</tbody>
			</table>
		</form>
		
	</div>
	<p>
		<a href="${pageContext.request.contextPath}/success-login"><input style="width: 100px; margin-left: 30px;" class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>
		</p>

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>