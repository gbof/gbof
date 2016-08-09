<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>
<html>
<head>
	<title>Admin</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style>
      		<%@include file="/web-resources/css/settings.css" %>
	</style>

</head>
<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	

	<div class="container-fluid main-container">
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
				        	<button class="btn btn-default btn-user btn-lg pull-right" data-toggle="modal" data-target="#myModal">Add new user</button>
				        </div>
				        <div class="panel-group col-md-12">
				        	<form class="form-inline" role="form">
					            <div class="form-group">
							      <label class="budgetInput" for="budget">Budget</label>
							      <input class="form-control" id="budget" type="text" placeholder="PLN">
							    </div>
						    </form>
				        </div>
				        <div class="panel-group col-md-12">
				        	<form class="form-inline" role="form">
					            <div class="form-group">
							      <label class="deadlineInput" for="deadline">Deadline</label>
							      <input class="form-control" id="deadline" type="date" placeholder="">
							    </div>
						    </form>
				        </div>
				        <div class="panel-group col-md-12">
				        	<form class="form-inline" role="form">
					            <div class="form-group">
							      <label class="deadlineInput" for="deadline">Number of balls</label>
							      <input class="form-control" id="deadline" type="number" placeholder="">
							    </div>
						    </form>
				        </div>
              		</div>
					<div class=text-right>
					     <a href="#"><button class="btn-info btn btn-lg">Save</button></a>
					</div>
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
	    	<div class="col-md-6 col-sm-6">
	      		<label for="role">Role ID</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="roleID" class="form-control" id="role" type="text" placeholder="role id"/>
	      		</div>
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="team">Team ID</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="teamID" class="form-control" id="team" type="text" placeholder="team name"/>
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
	    	<div class="col-md-6 col-sm-6">
	      		<label for="dept">Dept ID</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="deptID" class="form-control" id="dept" type="text" placeholder="dept"/>
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

<!--  
<div class="container">
	<form role="form" method="POST" action="${pageContext.request.contextPath}/ballAdded">
	<input name="received_balls" placeholder="received balls"/>
	<input name="balls_to_give" placeholder="balls to give" />
	<input name="locked" placeholder="locked" />
	<input name="cash" placeholder="cash" />
	<input type="submit" value="Save" />
	</form>
</div>
-->




	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>