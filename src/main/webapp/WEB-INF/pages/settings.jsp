<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
    
    
<html>
<head>
	<title>Admin</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>
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
		.tremoved{
		color: green
		}
		.tnotremoved{
		color: red
		}
	}
	
	</style>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>

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
            		<form name="form1" class="form-inline modal-form " role="form" method="POST" action="${pageContext.request.contextPath}/settingsAdd">
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
							     	 <input class="form-control" value="${settingsList.get(0).getBallsPerPerson()}" min="0" type="number"  name="ballsPerPers" required/>
							      </div>
							    </div>
						  
				       
					            <div class="form-group col-md-12">
							      <label class="col-md-3">Money </label>
							      <div class="col-md-6">
							      	<input class="form-control" value="${settingsList.get(0).getMoney()}" min="0" type="number" name="money" placeholder="PLN" required/>
							      </div>
							    </div>
							
				        
					            <div class="form-group col-md-12">
							      <label class="col-md-3" >Deadline</label>
							      <div class="col-md-6">

							      	<input type="date" class="form-control" value="${settingsList.get(0).getDeadline()}" name="deadline" required/>
							      </div>
							    </div>
							    
					           
							     <div class="form-area">
							      <label class="col-md-3" for="deadline">Help message </label>
							      <div class="col-md-6">

							      	<textarea class="col-md-12 col-sm-12 col-xs-12" style="resize: none; height: 300px;" class="form-control" type=text name="helpMsg" required>${settingsList.get(0).getHelpMsg()}</textarea>

							      </div>
							    </div>
							    <div class="form-group col-md-12">
					<label class="col-md-3">Extra money </label>
					 <div class="col-md-6">

					<input class="form-control" min="0" type="number"  value="0" name="extramoney" placeholder="PLN" required/>
					
					
					</div>

					<input type="submit" style="min-width: 100px;" name="extramoney1" class="btn btn-primary pull-right " value="Add extra money"/>

				
					</div>
					</div>
					
              	
              	<div class="panel-footer">
					<input type="submit" style="min-width: 100px;" name="save" class="btn btn-primary pull-right " value="Save" onclick="validatedate($('input#deadline'))"/>
					</form>
			<script>
		    function validatedate()  
		      {  
		      var inputText = document.getElementsByName('deadline').value;
		      var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;  
		      // Match the date format through regular expression  
		      if(inputText.value.match(dateformat))  
		      {  
		      document.form1.text1.focus();  
		      //Test which seperator is used '/' or '-'  
		      var opera1 = inputText.value.split('/');  
		      var opera2 = inputText.value.split('-');  
		      lopera1 = opera1.length;  
		      lopera2 = opera2.length;  
		      // Extract the string into month, date and year  
		      if (lopera1>1)  
		      {  
		      var pdate = inputText.value.split('/');  
		      }  
		      else if (lopera2>1)  
		      {  
		      var pdate = inputText.value.split('-');  
		      }  
		      var dd = parseInt(pdate[0]);  
		      var mm  = parseInt(pdate[1]);  
		      var yy = parseInt(pdate[2]);  
		      // Create list of days of a month [assume there is no leap year by default]  
		      var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];  
		      if (mm==1 || mm>2)  
		      {  
		      if (dd>ListofDays[mm-1])  
		      {  
		      alert('Invalid date format!');  
		      return false;  
		      }  
		      }  
		      if (mm==2)  
		      {  
		      var lyear = false;  
		      if ( (!(yy % 4) && yy % 100) || !(yy % 400))   
		      {  
		      lyear = true;  
		      }  
		      if ((lyear==false) && (dd>=29))  
		      {  
		      alert('Invalid date format!');  
		      return false;  
		      }  
		      if ((lyear==true) && (dd>29))  
		      {  
		      alert('Invalid date format!');  
		      return false;  
		      }  
		      }  
		      }  
		      else  
		      {  
		      alert("Invalid date format!");  
		      document.form1.text1.focus();  
		      return false;  
		      }  
		      }  
			</script>
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
					<c:if test="${Ubadlogin == true}">
					<b class="error">User can not be added, login is not available</b>
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
					<p>
					<c:if test="${tnotremoved == true}">
					<b class="correct">team not removed</b>
					</c:if>
					</p>
			        <div class="panel-group col-md-5 col-xs-8 btn-group-vertical">
			        	<a class="col-md-12 col-xs-8" ><button style="width: 100%; border-radius: 0;" class="btn btn-default btn-user btn-lg " data-toggle="modal" data-target="#myModal">New user</button></a>
			        	 <!-- <button class="btn btn-default btn-user btn-lg " data-toggle="modal" data-target="#delUserModal">Remove user</button>-->
	        			<a class="col-md-12 col-xs-8" ><button style="width: 100%; border-radius: 0;" class="btn btn-default btn-user btn-lg " data-toggle="modal" data-target="#teamModal" >New team</button></a>
			        	<!--<button class="btn btn-default btn-user btn-md " data-toggle="modal" data-target="#delTeamModal">Remove team</button>-->

	        			<a class="col-md-12 col-xs-8" href="${pageContext.request.contextPath}/users"><button style="width: 100%; border-radius: 0;" class="btn btn-default btn-user btn-lg" >Edit users</button></a>
						<a class="col-md-12 col-xs-8" href="${pageContext.request.contextPath}/teams"><button style="width: 100%; border-radius: 0;" class="btn btn-default btn-user btn-lg" >Edit teams</button></a>
	        			
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
   <a href="${pageContext.request.contextPath}/adminview"><input style="width: 100px;" class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>

	
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
      <div class="">
           	<div class="form-group col-md-12 col-sm-12">
           		<div class="col-md-6 col-sm-6 ">
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
		     <div class="form-group col-md-12 col-sm-12">
		        <label class="col-md-6 col-sm-6 control-label">Team</label>
		        <div class="col-md-6 col-sm-6 selectContainer">
		            <select class="form-control" name="teamName" required>
		               
			            <c:forEach var="team" items="${teamlistt}">
			                <option name = "teamName" value="${team.getName()}"  >
			                	<c:out value="${team.getName()}" />
			               
			            </c:forEach>
		            </select>
		        </div>
	    	</div>
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
	      	      <div class="">
	      	     	 	<div class="form-group col-md-12 col-sm-12">
	      	      	   		<div class="col-md-6 col-sm-6">
				      			<label for="teamName">Name</label>
				      		</div>
				      		<div class="col-md-6 col-sm-6">
				      			<input name="teamName" class="form-control" id="teamName" type="text" placeholder="name" required/>
				      		</div>
				    	</div>
					      <div class="form-group col-md-12 col-sm-12">
					        <label class="col-md-6 col-sm-6 control-label">Leader</label>
					        <div class="col-md-6 col-sm-6 selectContainer">
					            <select class="form-control" name="leaderLogin">
					        		<option name = "leaderLogin" value="no leader" >
					        				<c:out value="no leader" />
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
				        <input type="submit" class="btn btn-primary btn-change pull-right" value="Add" />
				    </div>
				    <script>
				     $('input:submit').click(function(){
				 		var v = $('input#teamName').map(function(){return $(this).val();}).get();
				 		var empty=0;
				 		$(v).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 			}
				 		});	
				 		if (empty == 0)
				 			$('input:submit').attr("disabled", true);
				 	});
				     
				 	</script>
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
      			<div class="">
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
	      <div class="">
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
      			<div class="">
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
	
<script>
function checkDates(form) {
  if (form.collectdatetime.value >= form.deliverdatetime.value) {
    alert('Collection time must be after deliver time');
    return false;
  }
}
</script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>