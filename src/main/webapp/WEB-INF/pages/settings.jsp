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
	<title>Admin</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style>
      	<%@include file="/web-resources/css/settings.css" %>
      	.correct {
		color: green;
		}
		.error {
		color: red;
	}
	
	</style>

</head>
<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	

	<div class="container-fluid main-container">
	<p>
		<c:if test="${correct == true}">
			<b class="correct">Settings changed.</b>
		</c:if>
		</p>
		<p>
		<c:if test="${error == true}">
			<b class="error">Settings not saved, missing data</b>
		</c:if>
		</p>
		<div class="col-md-6">
         		 <div class="panel panel-default">
         		
              		<div class="panel-heading">
              			Settings
              		</div>
              		
              		<div class="panel-body">
						<div class="panel-group col-md-6">
							<div class="funkyradio">
						        <div class="funkyradio-primary">
						            <input type="checkbox" name="checkbox" id="checkbox1" checked/>
						            <label for="checkbox1">Block the function of giving balls for all users</label>
						        </div>
						    </div>
					    </div>
				        <div class="panel-group col-md-6">
				        	<button class="btn btn-default btn-user btn-lg pull-right" data-toggle="modal" data-target="#myModal">New user</button>
				        </div>
				        <div class="panel-group col-md-6">
		        			<button class="btn btn-default btn-user btn-lg pull-right" data-toggle="modal" data-target="#teamModal" >New team</button>
				        </div>
				        <div class="panel-group col-md-12">
		        			<button class="btn btn-default btn-user btn-lg pull-right" data-toggle="modal" data-target="#deptModal" >New department</button>
				        </div>
				        <form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/settingsAdd">
				        <div class="panel-group col-md-12">
				        	
					            <div class="form-group">
							      <label >Balls Per Person </label>
							      <input class="form-control" value="${settingsList.get(0).getBallsPerPerson()}" type="text" name="ballsPerPers"/>
							    </div>
						  
				        </div>
				        <div class="panel-group col-md-12">
				       
					            <div class="form-group">
							      <label >Money </label>
							      <input class="form-control" value="${settingsList.get(0).getMoney()}" type =text name="money" placeholder="PLN"/>
							    </div>
						
				        </div>
				        <div class="panel-group col-md-12">
				        
					            <div class="form-group">
							      <label >Deadline</label>
							      <input class="form-control" value="${settingsList.get(0).getDeadline()}" type=text name="deadline"/>
							    </div>
						
				        </div>
				        <div class="panel-group col-md-12">
				    
					            <div class="form-group">
							      <label  for="deadline">Extra balls </label>
							      <input class="form-control" value="${settingsList.get(0).getExtraBalls()}" type=text name="extraBalls"/>
							    </div>
						   
				        </div>
				        <div class=text-right>
					<input type="submit" class="btn btn-primary btn-change pull-right" value="Save"/>
					
					</div>
              		</div>
					
					</form>
              	</div>
           </div>
       </div>
   <a href="${pageContext.request.contextPath}/adminview"><input  class="btn btn-primary btn-change pull-right" value="Back"/></a>

	
	<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add employee</h4>
      </div>
  	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/userAdded"> 
      <div class="modal-body">
           	<div class="form-group col-md-12">
           		<div class="col-md-6 col-sm-6">
	      		<label for="name">Name</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="name" class="form-control" id="name" type="text" placeholder="name"/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="surname">Last name</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="surname" class="form-control" id="surname" type="text" placeholder="last name"/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="login">Login</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="login" class="form-control" id="login" type="text" placeholder="login"/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="password">Password</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="password" class="form-control" id="password" type="text" placeholder="password"/>
	      		</div>
	    	</div>
		      <div class="form-group col-md-12">
		        <label class="col-md-6 control-label">Role</label>
		        <div class="col-md-6 selectContainer">
		            <select class="form-control" name="roleName">
		                <option value="-1">--- Select ---</option>
			            <c:forEach var="role" items="${rolelistt}">
			                <option name = "roleName" value="${role.getRole()}"  >
			                	<c:out value="${role.getRole()}" />
			                </option>
			            </c:forEach>
		            </select>
		        </div>
	    	</div>
		     <div class="form-group col-md-12">
		        <label class="col-md-6 control-label">Team</label>
		        <div class="col-md-6 selectContainer">
		            <select class="form-control" name="teamName">
		                <option value="-1">--- Select ---</option>
			            <c:forEach var="team" items="${teamlistt}">
			                <option name = "teamName" value="${team.getName()}"  >
			                	<c:out value="${team.getName()}" />
			                </option>
			            </c:forEach>
		            </select>
		        </div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="mail">Mail</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="mail" class="form-control" id="mail" type="text" placeholder="mail"/>
	      		</div>
	    	</div>
	    	 <div class="form-group col-md-12">
		        <label class="col-md-6 control-label">Department</label>
		        <div class="col-md-6 selectContainer">
		            <select class="form-control" name="deptName">
		                <option value="-1">--- Select ---</option>
			            <c:forEach var="dept" items="${deptlistt}">
			                <option name = "deptName" value="${dept.getDeptName()}"  >
			                	<c:out value="${dept.getDeptName()}" />
			                </option>
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

	<!-- teamModal -->
<div id="teamModal" class="modal fade" role="dialog">
 	<div class="modal-dialog">

    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	           <button type="button" class="close" data-dismiss="modal">&times;</button>
	           <h4 class="modal-title">Add team</h4>
	      </div>
	      	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/teamAdded">
	      	      <div class="modal-body">
	      	     	 	<div class="form-group col-md-12">
	      	      	   		<div class="col-md-6 col-sm-6">
				      			<label for="teamName">Name</label>
				      		</div>
				      		<div class="col-md-6 col-sm-6">
				      			<input name="teamName" class="form-control" id="teamName" type="text" placeholder="name"/>
				      		</div>
				    	</div>
				    	<div class="form-group col-md-12">
					        <label class="col-md-6 control-label">Department</label>
					        <div class="col-md-6 selectContainer">
					            <select class="form-control" name="deptName">
					                <option value="-1">--- Select ---</option>
						            <c:forEach var="dept" items="${deptlistt}">
						                <option name = "deptName" value="${dept.getDeptName()}"  >
						                	<c:out value="${dept.getDeptName()}" />
						                </option>
						            </c:forEach>
					            </select>
					        </div>
				    	</div>
					      <div class="form-group col-md-12">
					        <label class="col-md-6 control-label">Leader</label>
					        <div class="col-md-6 selectContainer">
					            <select class="form-control" name="leaderLogin">
					                <option value="-1">--- Select ---</option>
						            <c:forEach var="user" items="${userBasiclistt}">
						                <option name = "leaderLogin" value="${user}">
						                	<c:out value="${user}" />
						                </option>
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

	<!-- teamModal -->
<div id="deptModal" class="modal fade" role="dialog">
 	<div class="modal-dialog">

    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	           <button type="button" class="close" data-dismiss="modal">&times;</button>
	           <h4 class="modal-title">Add department</h4>
	      </div>
	      	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/deptAdded">
	      	      <div class="modal-body">
	      	     	 	<div class="form-group col-md-12">
	      	      	   		<div class="col-md-6 col-sm-6">
				      			<label for="deptName">Name</label>
				      		</div>
				      		<div class="col-md-6 col-sm-6">
				      			<input name="deptName" class="form-control" id="teamName" type="text" placeholder="name"/>
				      		</div>
				    	</div>
					      <div class="form-group col-md-12">
					        <label class="col-md-6 control-label">Leader</label>
					        <div class="col-md-6 selectContainer">
					            <select class="form-control" name="leaderLogin">
					                <option value="-1">--- Select ---</option>
						            <c:forEach var="user" items="${userBasiclistt}">
						                <option name = "leaderLogin" value="${user}">
						                	<c:out value="${user}" />
						                </option>
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




	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>