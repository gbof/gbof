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
			<%@include file="/web-resources/css/settings.css" %>
		</style>
		
</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	
	<!-- Comments place -->
	<div class="container">
		<div class="col-md-4-offset-4">
			<form role="form" method="POST" action="${pageContext.request.contextPath}/userEdited">
		    	<div class="panel panel-default">  
		        	<div class="panel-heading">
		                    <h3 style="margin-bottom: 25px; text-align: center;">Edit user</h3>
		            </div>
		            <div class="panel-body">
		                    
							<div class="form-group col-md-6 col-sm-6">
								<label class="col-md-4">Name</label>
								<div class="col-md-6">
									<input value="${user.name }" class="form-control" name="name" required />
								</div>
							</div>
							<div class="form-group col-md-6 col-sm-6">
								<label class="col-md-4">Surname</label>
								<div class="col-md-6">
									<input value="${user.surname }" class="form-control" name="surname" required />
								</div>
							</div>
							<div class="form-group col-md-6 col-sm-6">
								<label class="col-md-4">Login</label>
								<div class="col-md-6">
									<input value="${user.login }" class="form-control" name="login" required />
								</div>
							</div>
							<div class="form-group col-md-6 col-sm-6">
								<label class="col-md-4">Mail</label>
								<div class="col-md-6">
									<input value="${user.mail }" class="form-control" name="mail" required />
								</div>
							</div>							
						    <div class="form-group col-md-6 col-sm-6">
						        <label class="col-md-4 control-label">Role</label>
						        <div class="col-md-6 selectContainer">
						            <select class="form-control" name="role">
						                <option >${user.getRole().getRole() }</option>
							            <c:forEach var="role" items="${rolelistt}">
							                <option name = "role" value="${role.getRole()}"  >
							                	<c:out value="${role.getRole()}" />
							                </option>
							            </c:forEach>
						            </select>
						        </div>
					    	</div>
					    	 <div class="form-group col-md-6 col-sm-6">
						        <label class="col-md-4 control-label">Department</label>
						        <div class="col-md-6 selectContainer">
						            <select class="form-control" name="dept">
						                <option>${user.getDept().getDeptName() }</option>
							            <c:forEach var="dept" items="${deptlistt}">
							                <option name = "dept" value="${dept.getDeptName()}"  >
							                	<c:out value="${dept.getDeptName()}" />
							                </option>
							            </c:forEach>
						            </select>
						        </div>
					    	</div>
						     <div class="form-group col-md-6 col-sm-6">
						        <label class="col-md-4 control-label">Team</label>
						        <div class="col-md-6 selectContainer">
						            <select class="form-control" name="team">
						                <option>${user.getTeam().getName() }</option>
							            <c:forEach var="team" items="${teamlistt}">
							                <option name = "team" value="${team.getName()}"  >
							                	<c:out value="${team.getName()}" />
							                </option>
							            </c:forEach>
						            </select>
						        </div>
					    	</div>	
					    	<div class="form-group col-md-6 col-sm-6">
								<label class="col-md-4">Balls</label>
								<div class="col-md-6">
									<input value="${user.getBall().getBallsToGive() }" class="form-control" name="balls" required />
								</div>
							</div>												
		           			<input type="hidden" name="user_id" value="${user.getId()}"/>
		           			
		           			
		           	</div>	
		           	<div class="panel-footer">	
  						<input type="submit" style="min-width: 100px;" class="btn btn-primary pull-right " value="Save"/>
		        		<a href="${pageContext.request.contextPath}/users"><input type="button" style="min-width: 100px;" class="btn btn-primary pull-left " id="back" name="back"value="Back" /></a>
        			</div>
		    	</div>
		    </form>
		</div>
	</div>
		
		
	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>