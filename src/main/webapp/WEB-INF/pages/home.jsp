<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
		
		<style>
       		<%@include file="/web-resources/css/login.css" %>
		</style>
		<style>
			.error {
				color: red;
			}
		</style>
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>

	<h1 align="center">
		Great Balls of Fire
	</h1>
	
	<!-- LOGIN FORM -->
	<div class="text-center" style="padding:50px 0">
		<div class="logo">login</div>
		<!-- Main Form -->
		<p>
		<c:if test="${error == true}">
			<b class="error">Invalid login or password.</b>
		</c:if>
		</p>
		<div class="login-form-1">
			<form id="login-form" class="text-left" method="post" action="<c:url value='j_spring_security_check'/>" >
				<div class="login-form-main-message"></div>
				<div class="main-login-form">
					<div class="login-group">
						<div class="form-group">
							<label for="j_username" class="sr-only">Username</label>
							<input type="text" name="j_username" id="j_username" size="25" maxlength="40"  />
						</div>
						<div class="form-group">
							<label for="j_password" class="sr-only">Password</label>
							<input type="password" name="j_password" id="j_password" size="25" maxlength="40"  />
						</div>
					</div>
					<button type="submit" class="login-button"><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></button>
				</div>
				<div class="etc-login-form">
					<p>forgot your password? <a href="#">click here</a></p>
					 <c:forEach var="user" items="${customerList}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.age}" /></td>
                    <td><c:out value="${user.salary}" /></td>
                </tr>
            </c:forEach>
				</div>
			</form>
		</div>
		<!-- end:Main Form -->
	</div>
	

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>
