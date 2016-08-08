<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
       		<%@include file="/web-resources/css/inside.css" %>
		</style>
		
</head>

<body>
	 <form method="POST" action="${pageContext.request.contextPath}/comments">
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition>   
    
    
	<!--  Sidebar menu -->
	<!--  
	
		<div class="col-md-2 sidebar">
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="test">Dalej</a></li>
				
				
			</ul>
		</div>
		-->
		<div class="container-fluid main-container">
		<div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Employees
                </div>
                <div class="panel-body">
                    <div class="container col-md-12">          
					  <table class="table table-hover col-md-12">
					    <thead>
					      <tr>
					      	<th>Add balls</th>
					      	<th>Name</th>
					        <th>Surname</th>
					        <th>Login</th>
					        <th>Team</th>
					        <th>Balls</th>
					      </tr>
					    </thead>
					    <tbody>
					    
					      <c:forEach var="user" items="${listt}">
					    <tr>
					    <td>
						    	<div class="checkbox">
									<label><input type="checkbox" name = "userIds" value = "${user.getId()}"></label>
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
					</div>
                </div>
            </div>
		</div>


		<footer class="pull-left footer">

		</footer>
	</div>
	
	<div class=text-right>
	     <button type="submit" class="btn-info btn btn-lg">Next</button>
	</div>
	</form>	

	
	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>