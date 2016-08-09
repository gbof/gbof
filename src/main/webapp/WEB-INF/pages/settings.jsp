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
         		 <form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/settingsAdd">
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
				        	
					            <div class="form-group">
							      <label >Balls Per Person </label>
							      <input class="form-control" type="text" name="ballsPerPers"/>
							    </div>
						  
				        </div>
				        <div class="panel-group col-md-12">
				       
					            <div class="form-group">
							      <label >Money </label>
							      <input class="form-control" type =text name="money" placeholder="PLN"/>
							    </div>
						
				        </div>
				        <div class="panel-group col-md-12">
				        
					            <div class="form-group">
							      <label >Deadline</label>
							      <input class="form-control" type=text name="deadline"/>
							    </div>
						
				        </div>
				        <div class="panel-group col-md-12">
				    
					            <div class="form-group">
							      <label  for="deadline">Extra balls </label>
							      <input class="form-control" type=text name="extraBalls"/>
							    </div>
						   
				        </div>
              		</div>
					<div class=text-right>
					     <input type="submit" class="btn btn-primary btn-change pull-right" value="Save"/>
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
        <h4 class="modal-title">Add employee</h4>
      </div>
  	<form class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/userAdded"> 
      <div class="modal-body">
           	<div class="form-group col-md-12">
           		<div class="col-md-6 col-sm-6">
	      		<label for="name">Name</label>
<<<<<<< Upstream, based on origin/master
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="name" class="form-control" id="name" type="text" placeholder="name"/>
	      		</div>
=======
	      		<input class="form-control" id="name" type="text" placeholder="name"/>
>>>>>>> 598661f Settings
	    	</div>
	    	<div class="form-group col-md-12">
<<<<<<< Upstream, based on origin/master
	    	<div class="col-md-6 col-sm-6">
	      		<label for="surname">Last name</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="surname" class="form-control" id="surname" type="text" placeholder="last name"/>
	      		</div>
=======
	      		<label for="lastname">Last name</label>
	      		<input class="form-control" id="lastname" type="text" placeholder="last name"/>
>>>>>>> 598661f Settings
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
<<<<<<< Upstream, based on origin/master
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="password" class="form-control" id="password" type="text" placeholder="password"/>
	      		</div>
=======
	      		<input class="form-control" id="password" type="text" placeholder="password"/>
>>>>>>> 598661f Settings
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="role">Role ID</label>
<<<<<<< Upstream, based on origin/master
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="roleID" class="form-control" id="role" type="text" placeholder="role id"/>
	      		</div>
=======
	      		<input class="form-control" id="role" type="text" placeholder="role id"/>
>>>>>>> 598661f Settings
	    	</div>
	    	<div class="form-group col-md-12">
<<<<<<< Upstream, based on origin/master
	    	<div class="col-md-6 col-sm-6">
	      		<label for="team">Team ID</label>
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="teamID" class="form-control" id="team" type="text" placeholder="team name"/>
	      		</div>
=======
	      		<label for="team">Team name</label>
	      		<input class="form-control" id="team" type="text" placeholder="team name"/>
>>>>>>> 598661f Settings
	    	</div>
	    	<div class="form-group col-md-12">
<<<<<<< Upstream, based on origin/master
	    	<div class="col-md-6 col-sm-6">
=======
	      		<label for="balls">Balls to give</label>
	      		<input class="form-control" id="balls" type="text" placeholder="balls to give"/>
	    	</div>
	    	<div class="form-group col-md-12">
>>>>>>> 598661f Settings
	      		<label for="mail">Mail</label>
<<<<<<< Upstream, based on origin/master
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="mail" class="form-control" id="mail" type="text" placeholder="mail"/>
	      		</div>
=======
	      		<input class="form-control" id="mail" type="text" placeholder="mail"/>
>>>>>>> 598661f Settings
	    	</div>
	    	<div class="form-group col-md-12">
	    	<div class="col-md-6 col-sm-6">
	      		<label for="dept">Dept ID</label>
<<<<<<< Upstream, based on origin/master
	      		</div>
	      		<div class="col-md-6 col-sm-6">
	      		<input name="deptID" class="form-control" id="dept" type="text" placeholder="dept"/>
	      		</div>
=======
	      		<input class="form-control" id="dept" type="text" placeholder="dept"/>
>>>>>>> 598661f Settings
	    	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
        <input type="submit" class="btn btn-primary btn-change pull-right" value="Save"/>
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