<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>
<html>
<head>
	<title>Users</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style>
	
	</style>

</head>
<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 

	<form method="POST" action="${pageContext.request.contextPath}/edituser">	
		<div class="container-fluid main-container">
			<div class="col-md-12">
				<div class="panel panel-default">
	                <div class="panel-heading">
	                    Employees
	                </div>
	                <div class="panel-body">
	                    <div class="container col-md-12">          
						  <table class="table table-hover col-md-12 table-responsive">
						    <thead>
						      <tr>
						      	<th>Name</th>
						      	<th>Surname</th>
						        <th>Login</th>
						        <th>Mail</th>
						        <th>Role</th>
						        <th>Department</th>
						        <th>Team</th>
						        <th>Balls to give</th>
								<th>Edit</th>
						      </tr>
						    </thead>
						    <tbody>
						    
								<c:forEach var="user" items="${listt}">
								  <tr>
							        <td><c:out value="${user.name}" /></td>
							        <td><c:out value="${user.surname}" /></td>
							        <td><c:out value="${user.login}" /></td>
							        <td><c:out value="${user.mail}" /></td>
							        <td><c:out value="${user.getRole().getRole()}" /></td>
							        <td><c:out value="${user.getDept().getDeptName()}" /></td>
							        <td><c:out value="${user.getTeam().getName()}" /></td>
							        <td><c:out value="${user.getBall().getBallsToGive() }" /></td>
							        <td><a href="${pageContext.request.contextPath}/edituser"><button name="buttonComId" value="${user.getId()}" type="submit" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></a></td>
												      
								    </tr>
								</c:forEach>
	
						    </tbody>
						  </table>
						</div>
	                </div>
	            </div>
			</div>
		</div>	
		<div class=text-right>	     
			<a href="${pageContext.request.contextPath}/settings"><input style="margin-left: 30px;" class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>
		</div>
	</form>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>