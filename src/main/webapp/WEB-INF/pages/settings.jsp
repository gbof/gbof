<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
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
		.Uadded {
		color: green;
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
             		<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/settingsAdd">
             		<div class="panel-body">
						<div class="panel-group col-md-6">
							<div class="funkyradio">
						        <div class="funkyradio-primary">
						        	<c:if test="${checked == false}">
						            		<input type="checkbox" name="checkbox" id="checkbox" value="1"/>
						          		
						            <label for="checkbox">Block the function of giving balls for all users</label>
						            </c:if>
						            
						            <c:if test="${checked == true}">
						            		<input type="checkbox" name="checkbox" id="checkbox" value="1" checked/>
						          		
						            <label for="checkbox">Block the function of giving balls for all users</label>
						            </c:if>
						        </div>
						    </div>
					    </div>
	
				            
					        	
						            <div class="form-group col-md-12">
								      <label class="col-md-3">Balls Per Person </label>
								      <div class="col-md-6">
								     	 <input class="form-control" value="${settingsList.get(0).getBallsPerPerson()}" type="text" name="ballsPerPers" required/>
								      </div>
								    </div>
							  
					       
						            <div class="form-group col-md-12">
								      <label class="col-md-3">Money </label>
								      <div class="col-md-6">
								      	<input class="form-control" value="${settingsList.get(0).getMoney()}" type =text name="money" placeholder="PLN"/>
								      </div>
								    </div>
							
					        
						            <div class="form-group col-md-12">
								      <label class="col-md-3" >Deadline</label>
								      <div class="col-md-6">
								      	<input class="form-control" value="${settingsList.get(0).getDeadline()}" type=text name="deadline"/>
								      </div>
								    </div>
							
					    
						            <div class="form-group col-md-12">
								      <label class="col-md-3" for="deadline">Extra balls </label>
								      <div class="col-md-6">
								      	<input class="form-control" value="${settingsList.get(0).getExtraBalls()}" type=text name="extraBalls"/>
								      </div>
								    </div>
								     <div class="form-area">
								      <label class="col-md-3" for="deadline">Help message </label>
								      <div class="col-md-6">
								      	<textarea class="form-control" type=text name="helpMsg" ${settingsList.get(0).getHelpMsg()}>${settingsList.get(0).getHelpMsg()}</textarea>
								      </div>
								    </div>
						
						
	              	</div>
	              	<div class="panel-footer">
						<input type="submit" style="min-width: 100px;" class="btn btn-primary pull-right " value="Save"/>
						</form>
					</div>
	           </div>
	       </div>
	       <p>
		<c:if test="${error == true}">
			<b class="error">Settings not saved, missing data</b>
		</c:if>
		</p>
		
      		<div class="col-md-6">
        		 <div class="panel panel-default">
        		
             		<div class="panel-heading">
             			Users management
             		</div>
             		
             		<div class="panel-body">
             		<p>
					<c:if test="${Uadded == true}">
					<b class="correct">User added</b>
					</c:if>
					</p>
					<p>
					<c:if test="${uRemoved == true}">
					<b class="correct">User removed</b>
					</c:if>
					</p>
					<p>
					<c:if test="${tadded == true}">
					<b class="correct">team added</b>
					</c:if>
					</p>
					<p>
					<c:if test="${tremoved == true}">
					<b class="correct">team removed</b>
					</c:if>
					</p>
			        <div class="panel-group col-md-5 btn-group-vertical">
			        	<button class="btn btn-default btn-user btn-lg " data-toggle="modal" data-target="#myModal">New user</button>
			        	<button class="btn btn-default btn-user btn-lg " data-toggle="modal" data-target="#delUserModal">Remove user</button>
	        			<button class="btn btn-default btn-user btn-md " data-toggle="modal" data-target="#teamModal" >New team</button>
			        	<button class="btn btn-default btn-user btn-md " data-toggle="modal" data-target="#delTeamModal">Remove team</button>
	        			<a href="${pageContext.request.contextPath}/users"><button class="btn btn-success btn-user btn-lg" >Edit users</button></a>
	        			<!-- <button class="btn btn-default btn-user btn-md " data-toggle="modal" data-target="#deptModal" >New department ???</button> -->
			        </div>
			        <div class="panel-group col-md-6">
			        </div>
			         <div class="panel-group col-md-6">
			        </div>
			        <div class="panel-group col-md-6">
			        </div>
			        <div class="panel-group col-md-6">
			        </div>
			        <div class="panel-group col-md-6">
			        </div>

              	</div>
				<div class="panel-footer">
				</div>
           </div>
       </div>
   </div>
   <a href="${pageContext.request.contextPath}/adminview"><input  class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>

	
	<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add user</h4>
      </div>
  	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/userAdded"> 
      <div class="modal-body">
           	<div class="form-group col-md-12">
           		<div class="col-md-6 col-sm-6">
	      		<label for="name">Name</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="name" class="form-control" id="name" type="text" placeholder="name" required/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="surname">Last name</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="surname" class="form-control" id="surname" type="text" placeholder="last name" required/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="login">Login</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="login" class="form-control" id="login" type="text" placeholder="login" required/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="password">Password</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="password" class="form-control" id="password" type="text" placeholder="password" required/>
	      		</div>
	    	</div>
		      <div class="form-group col-md-12">
		        <label class="col-md-6 control-label">Role</label>
		        <div class="col-md-6 selectContainer">
		            <select class="form-control" name="roleName" required>
		               
			            <c:forEach var="role" items="${rolelistt}">
			                <option name = "roleName" value="${role.getRole()}" required>
			                	<c:out value="${role.getRole()}"/>
			               
			            </c:forEach>
		            </select>
		        </div>
	    	</div>
		     <div class="form-group col-md-12">
		        <label class="col-md-6 control-label">Team</label>
		        <div class="col-md-6 selectContainer">
		            <select class="form-control" name="teamName" required>
		               
			            <c:forEach var="team" items="${teamlistt}">
			                <option name = "teamName" value="${team.getName()}"  >
			                	<c:out value="${team.getName()}" />
			               
			            </c:forEach>
		            </select>
		        </div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="mail">Mail</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="mail" class="form-control" id="mail" type="text" placeholder="mail" required/>
	      		</div>
	    	</div>
	    	 <div class="form-group col-md-12">
		        <label class="col-md-6 control-label">Department</label>
		        <div class="col-md-6 selectContainer">
		            <select class="form-control" name="deptName" required>
		          
			            <c:forEach var="dept" items="${deptlistt}">
			                <option name = "deptName" value="${dept.getDeptName()}"  >
			                	<c:out value="${dept.getDeptName()}" />
			                
			            </c:forEach>
			            
			            
		            </select>
		        </div>
	    	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
        <input type="submit" id="submit" class="btn btn-primary btn-change pull-right" value="Save" />
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
				      			<input name="teamName" class="form-control" id="teamName" type="text" placeholder="name" required/>
				      		</div>
				    	</div>
				    	<div class="form-group col-md-12">
					        <label class="col-md-6 control-label">Department</label>
					        <div class="col-md-6 selectContainer">
					            <select class="form-control" name="deptName">
					               
						            <c:forEach var="dept" items="${deptlistt}">
						                <option name = "deptName" value="${dept.getDeptName()}"  >
						                	<c:out value="${dept.getDeptName()}" />
						               
						            </c:forEach>
					            </select>
					        </div>
				    	</div>
					      <div class="form-group col-md-12">
					        <label class="col-md-6 control-label">Leader</label>
					        <div class="col-md-6 selectContainer">
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
				      			<input name="deptName" class="form-control" id="teamName" type="text" placeholder="name" required/>
				      		</div>
				    	</div>
					      <div class="form-group col-md-12">
					        <label class="col-md-6 control-label">Leader</label>
					        <div class="col-md-6 selectContainer">
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

<div id="delUserModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal">&times;</button>
        		<h4 class="modal-title">Remove user</h4>
      		</div>
  			<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/userRemoved"> 
      			<div class="modal-body">
      				<table class="table table-hover table-responsive">
      					<thead>
					      <tr>
					      	<th>Remove</th>
					      	<th>Name</th>
					        <th>Surname</th>
					        <th>Login</th>
					      </tr>
					    </thead>
					    <tbody>
    						<c:forEach var="user" items="${listt}">
						    	<tr>
						   			<td>
								    	<div class="checkbox">
											<label><input type="checkbox" name = "userDelIds" value = "${user.getId()}"></label>
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
			<div class="modal-footer">
			    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
			    <button type="button" data-toggle="modal" data-target="#ModalRemoveUser" class="btn btn-primary btn-change pull-right" value="Remove">Remove</button>
			</div>
			
		</div>
	</div>
</div>
<!-- modal -->

	<div id="ModalRemoveUser" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Confirm</h4>
	      </div>
	      <div class="modal-body">
	           	Delete this user(s) ?
	      </div>
	      <div class="modal-footer">
	        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         <button type="submit" name="confirmButton" class="btn btn-primary btn-change pull-right" >Remove</button>
	      </div>
	    
	    </div>
	
	  </div>
	</div>
	</form>


<div id="delTeamModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal">&times;</button>
        		<h4 class="modal-title">Remove team</h4>
      		</div>
  			<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/teamRemoved"> 
      			<div class="modal-body">
      				<table class="table table-hover table-responsive">
      					<thead>
					      <tr>
					      	<th>Remove</th>
					      	<th>Name</th>
					      </tr>
					    </thead>
					    <tbody>
    						<c:forEach var="team" items="${teamlistt}">
						    	<tr>
						   			<td>
								    	<div class="checkbox">
											<label><input type="checkbox" name = "teamDelIds" value = "${team.getId()}"></label>
										</div>
									</td>
									<td><c:out value="${team.name}"/></td>
						      	</tr>
	            			</c:forEach>
					    </tbody>
      				</table>
				</div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
			    <button type="button" data-toggle="modal" data-target="#ModalRemoveTeam" class="btn btn-primary btn-change pull-right" value="Remove" >Remove</button>
			</div>
			
		</div>
	</div>
</div>
<!-- modal -->

	<div id="ModalRemoveTeam" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Confirm</h4>
	      </div>
	      <div class="modal-body">
	           	Delete this team(s) ?
	      </div>
	      <div class="modal-footer">
	        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         <button type="submit" name="confirmButton" class="btn btn-primary btn-change pull-right" >Remove</button>
	      </div>
	    </form>
	    </div>
	
	  </div>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>