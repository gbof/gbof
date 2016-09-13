<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>SuperUser</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style>
      	<%@include file="/web-resources/css/settings.css" %>
      	.correct {
		color: green;
		}
		
		.error {
		color: red;
		}
		
		.Ubadlogin {
		color: red;
		}
		
		.uRemoved{
		color: green
		}
		.tadded{
		color: green
		}
		.tRemoved{
		color: green
		}
	}
	
	</style>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>

<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">	
					<div class="panel-heading">
						Current departments
					</div>	
					<form method="POST" action="${pageContext.request.contextPath}/editdept">	
					<input type="hidden" name="dept_id" value="${dept.getDeptId()}"/>		
					<div style="min-height: 300px;" class="panel-body">
						<table  class="table table-filter table-hover col-md-12 table-responsive">
						    <thead>
						      <tr>
						      	<th>Name</th>
						        <th>Leader</th>
						        <th>Edit</th>
						        <th>Remove</th>
						      </tr>
						    </thead>
						    <tbody>
								<c:forEach var="dept" items="${deptlistt}" varStatus="status">
								<tr>
									<td><c:out value="${dept.getDeptName()}" /></td>
									<td><c:out value="${deptLeaders[status.index] }" /></td>
									 <td><button name="buttonComId" value="${dept.getDeptId()}" type="submit" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></a></td>
									<td><a><button name="delete"  data-toggle="modal" data-target="#ModalRemoveDept${dept.getDeptId()}" value="${dept.getDeptId()}" type="button" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></a></td>		      
								   
								</tr>
								</c:forEach>
							</tbody>
						</table>					   
					</div>
					<div class="panel-footer">
					</div>
					
						<c:forEach var="dept" items="${deptlistt}">
					  		<div id="ModalRemoveDept${dept.getDeptId()}" class="modal fade" role="dialog">
							  <div class="modal-dialog">
							
							    <!-- Modal content-->
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal">&times;</button>
							        <h4 class="modal-title">Confirm</h4>
							      </div>
							      <div class="modal-body">
							           	Deleting the department will also remove all employees included. Are you sure you want to delete?
							      </div>
								      <div class="modal-footer">
								        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
								         <button type="submit" value="${dept.getDeptId()}" name="delete" class="btn btn-primary btn-change pull-right" >Delete</button>
								      </div>
							    
							    </div>
							
							  </div>
							</div>		
						</c:forEach>
			
					</form>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						New departments
					</div>
					<div style="min-height: 300px;" class="panel-body">
					<p>
					<c:if test="${Uadded == true}">
					<b class="correct">User added</b>
					</c:if>
					</p>
					<p>
					<c:if test="${Ubadlogin == true}">
					<b class="error">User can not be added, login is not available</b>
					</c:if>
					</p>
						<a class="col-md-12 col-xs-8" ><button style="width: 30%; border-radius: 0;" class="btn btn-default btn-user btn-md " 
							data-toggle="modal" data-target="#deptModal" >New department</button></a>
						<a class="col-md-12 col-xs-8" ><button style="width: 30%; border-radius: 0;" class="btn btn-default btn-user btn-md " 
							data-toggle="modal" data-target="#myModal" >New admin</button></a>	
						
					</div>
					<div class="panel-footer">
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						Admins
					</div>
					<form class="form-inline modal-form " role="form" method="GET" action="${pageContext.request.contextPath}/SUuserRemoved">
						<div style="min-height: 300px;" class="panel-body">
							<table class="table table-hover table-responsive">
		      					<thead>
							      <tr>
							      	<th>Name</th>
							        <th>Surname</th>
							        <th>Login</th>
							      	<th>Remove</th>
							      </tr>
							    </thead>
							    <tbody>
		    						<c:forEach var="user" items="${adminlistt}">
								    	<tr>
											<td><c:out value="${user.name}" /></td>
											<td><c:out value="${user.surname}" /></td>
											<td><c:out value="${user.login}" /></td>
								   			<td>
								   			<a><button name="delete"  data-toggle="modal" data-target="#ModalRemoveUser${user.getId()}" value="${user.getId()}" type="button" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></a>
											</td>
								      	</tr>
			            			</c:forEach>
							    </tbody>
		      				</table>
						</div>
						<div class="panel-footer">
						</div>
						<c:forEach var="user" items="${adminlistt}">
							<div id="ModalRemoveUser${user.getId()}" class="modal fade" role="dialog">
							  <div class="modal-dialog">
							
							    <!-- Modal content-->
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal">&times;</button>
							        <h4 class="modal-title">Confirm</h4>
							      </div>
							      <div class="modal-body">
							           	Delete user ?
							      </div>
							      <div class="modal-footer">
							        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
							         <button type="submit" value="${user.getId() }" name="delete" class="btn btn-primary btn-change pull-right" >Delete</button>
							      </div>
							    
							    </div>
							
							  </div>
							</div>
						</c:forEach>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	

	
		<!-- deptModal -->
	<div id="deptModal" class="modal fade" role="dialog">
	 	<div class="modal-dialog">
	
	    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		           <button type="button" class="close" data-dismiss="modal">&times;</button>
		           <h4 class="modal-title">Add department</h4>
		      </div>
		      	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/deptAdded">
		      	      <div class="">
		      	     	 	<div class="form-group col-md-12 col-sm-12">
		      	      	   		<div class="col-md-6 col-sm-6">
					      			<label for="deptName">Name</label>
					      		</div>
					      		<div class="col-md-6 col-sm-6">
					      			<input name="deptName" class="form-control" id="teamName" type="text" placeholder="name" required/>
					      		</div>
					    	</div>
						      <div class="form-group col-md-12 col-sm-12">
						        <label class="col-md-6 col-sm-6 control-label">Leader</label>
						        <div class="col-md-6 col-sm-6 selectContainer">
						            <select class="form-control" name="leaderLogin">
						           
							            <c:forEach var="user" items="${userBasiclistt}">
							                <option name = "leaderLogin" value="${user}">
							                	<c:out value="${user}" />
							            
							            </c:forEach>
						            </select>
						        </div>
					    	</div>
					    </div>
					    <div class="modal-footer">
					        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
					        <input type="submit" class="btn btn-primary btn-change pull-right" value="Save" />
					    </div>
		      	</form>
			</div>
		</div>
	</div>
	
		<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Add user</h4>
	      </div>
	  	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/SUuserAdded"> 
	      <div class="">
	           	<div class="form-group col-md-12 col-sm-12">
	           		<div class="col-md-6 col-sm-6">
		      		<label for="name">Name</label>
		      		</div>
		      		<div class="col-md-6 col-sm-6">
		      		<input name="name" class="form-control" maxlength=40 id="name" type="text" placeholder="name" required/>
		      		</div>
		    	</div>
		    	<div class="form-group col-md-12 col-sm-12">
		    	<div class="col-md-6 col-sm-6">
		      		<label for="surname">Last name</label>
		      		</div>
		      		<div class="col-md-6 col-sm-6">
		      		<input name="surname" class="form-control" maxlength=40 id="surname" type="text" placeholder="last name" required/>
		      		</div>
		    	</div>
		    	<div class="form-group col-md-12 col-sm-12">
		    	<div class="col-md-6 col-sm-6">
		      		<label for="login">Login</label>
		      		</div>
		      		<div class="col-md-6 col-sm-6">
		      		<input name="login" class="form-control" maxlength=5 id="login" type="text" placeholder="login" required/>
		      		</div>
		    	</div>
		    	<c:set var="rola" scope="session" value="${rola}"/>
				<c:set var="superuser" scope="session" value="superuser"/>
		    	<c:if test="${rola == superuser}" >
			    	<div class="form-group col-md-12 col-sm-12">
				        <label class="col-md-6 col-sm-6 control-label">Department</label>
	        			<div class="col-md-6 col-sm-6 selectContainer">
		            		<select class="form-control" name="deptName" required>
					            <c:forEach var="dept" items="${deptlistt}">
				                <option name = "deptName" value="${dept.getDeptName()}"  >
				                	<c:out value="${dept.getDeptName()}" />
					            </c:forEach>
				            </select>
				        </div>
			    	</div>
				</c:if>
		    	<div class="form-group col-md-12 col-sm-12">
		    	<div class="col-md-6 col-sm-6">
		      		<label for="mail">Mail (only nickname)</label>
		      		</div>
		      		<div class="col-md-6 col-sm-6">
		      		<input name="mail" class="form-control" maxlength=30 id="mail" type="text" placeholder="mail" required/>
		      		</div>
		    	</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	        <input type="submit" id="submit" class="btn btn-primary btn-change pull-right" value="Add" />
	      </div>
	    </form>
	    </div>
	
	  </div>
	</div>
	
	

  			 


	
	
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>