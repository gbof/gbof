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
		<%@include file="/web-resources/css/settings.css" %>
		
	.Ubadlogin {
	color: red
	}
		
	.Uedited {
	color: green
	}	
	
	</style>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
<div style="text-align: center; color:green; font-size:100%;" >
	<p>
 <c:if test="${Uedited == true}">
<b class="correct">User edited</b>
</c:if>
</p>
</div>
<div style="text-align: center; color:red; font-size:100%;" >
<p>
<c:if test="${Ubadlogin == true}">
<b class="error">User cannot be edited, login is not available</b>
</c:if>
</p>
</div>
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
								<th>Delete</th>
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
							        <td><a href="${pageContext.request.contextPath}/edituser"><button name="buttonComId" value="${user.getId()}" type="submit" 
							        	class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></a></td>
									<td><a ><button name="delete" data-toggle="modal" data-target="#ModalRemoveUser${user.getId()}" 
										value="${user.getId()}" type="button"  class="btn btn-default btn-edit"><span class="glyphicon glyphicon-remove" 
										aria-hidden="true"></span></button></a></td>			      
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
			<a href="${pageContext.request.contextPath}/settings"><input style="width: 100px; margin-left: 30px;" class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>
		</div>

		<c:forEach var="user" items="${listt}">
	  		<div id="ModalRemoveUser${user.getId()}" class="modal fade" role="dialog">
			  <div class="modal-dialog">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title">Confirm</h4>
			      </div>
			      <div class="modal-body">
			           	Delete this user ?
			      </div>
			      <div class="modal-footer">
			        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
			         <button type="submit" value="${user.getId()}" name="delete" class="btn btn-primary btn-change pull-right" >Delete</button>
			      </div>
			    
			    </div>
			
			  </div>
			</div>		
		</c:forEach>
	
	
	</form>
	
		
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>