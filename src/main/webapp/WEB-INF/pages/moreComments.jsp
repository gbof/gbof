<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>

<html>
<head>
	<title>Home</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
		
		<style>
		</style>
		


</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
<div class="container-fluid main-container">
	<div class="col-md-12">
           <div class="panel panel-default">
               <div class="panel-heading">
                   Employees
               </div>
			  	<form class="form-inline" role="form" method="POST" action="${pageContext.request.contextPath}/moreComments"> 
			  		<table class="table table-hover table-responsive">
			  			<thead>
					      <tr>
					      	<th>Add</th>
					      	<th>Name</th>
					        <th>Surname</th>
					        <th>Login</th>
					        <th>Team</th>
					        <th>Balls</th>
					      </tr>
					    </thead>
					    <tbody>
						<c:forEach var="user" items="${listt1}">
				    	<tr>
				   			<td>
						    	<div class="checkbox">
									<label><input type="checkbox" name = "userAddMoreIds" value = "${user.getId()}"></label>
								</div>
							</td>
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.surname}" /></td>
							<td><c:out value="${user.login}" /></td>
				          <td><c:out value="${user.getTeam().getName()}" /></td>
				          <td><c:out value="${user.getBall().getReceivedBalls()}" /></td>
				      	</tr>
			       			</c:forEach>
				    </tbody>
			  	</table>
		    	<button type="button" class="btn btn-default pull-left" >Cancel</button>
		    	<input type="submit" class="btn btn-primary btn-change pull-right" value="Add" />
			</form>
		</div>
	</div>
</div>
	
		
	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>