<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>

</head>

<body>

    
	<nav class="navbar navbar-default navbar-static-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">
				GBOF
			</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">			
			<ul class="nav navbar-nav navbar-right">
				<li><a>Your balls: 50</a></li>
				<li><a href="<c:url value="/j_spring_security_logout" />" >Logout</a></li>
						 <br/>
						</ul>
					</li>
				</ul>
			</div><!-- /.navbar-collapse -->
		</div><!-- /.container-fluid -->
	</nav>
	<div class="container-fluid main-container">
		<div class="col-md-2 sidebar">
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">Link</a></li>
				<li><a href="#">Link</a></li>
				
			</ul>
		</div>
		<div class="col-md-10 content">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Pracownicy
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
									<label><input type="checkbox" value=""></label>
								</div>
							</td>
					          <td><c:out value="${user.name}" /></td>
					          <td><c:out value="${user.surname}" /></td>
					          <td><c:out value="${user.login}" /></td>
					          
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

	
	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>