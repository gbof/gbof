<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>
<html>
<head>
	<title>Admin</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" href="webjars/bootstrap-datepicker/1.0.1/css/datepicker.css" />
		
	<style>
      		<%@include file="/web-resources/css/adminview.css" %>
      		.modal {
				padding: 05%;
			}
	</style>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition>  
	
	
	

		<div class="container-fluid">
			<div class="tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs ">
						<li class="active">
							<a href="#tab_default_1" data-toggle="tab">
							Users List </a>
						</li>
						<li>
							<a href="#tab_default_2" data-toggle="tab">
							Your Comments </a>
						</li>
						<li>
							<a href="#tab_default_3" data-toggle="tab">
							Given comments </a>
						</li>
						<li>
							<a href="#tab_default_4" data-toggle="tab">
							Confirmed list </a>
						</li>
					</ul>
				</div>
				<div class="tab-content">
					<div class="tab-pane active" id="tab_default_1">
						<form method="POST" action="${pageContext.request.contextPath}/comments">
							<div class="container-fluid main-container">
								<div class="col-md-12 col-sm-12 col-xs-12">
						            <div class="panel panel-default">
						                <div class="panel-heading">
						                    Employees
						                </div>
						                <div class="panel-body">
						                    <div class="container col-md-12 col-sm-12">  											        
        									<button type="button" class="btn btn-success btn-filter" data-target="all">ALL</button>
									        <c:forEach var="team" items="${teamlistt}">
						                            <button type="button" class="btn btn-success btn-filter" data-target="${team.getName() }">${team.getName() }</button>
						                      </c:forEach>    
											  <table  class="table table-filter table-hover col-md-12 col-sm-12 table-responsive">
											    <thead>
											      <tr data-status="tableeee">
											      	<th>Add balls</th>
											      	<th>Name</th>
											        <th>Surname</th>
											        <th>Login</th>
											        <th>Team</th>
											        <th>Balls</th>
											        <th>Balls distributed</th>
											        <th>Money (money for balls + extra money)</th>
											      </tr>
											    </thead>
											    <tbody>

											      <c:forEach var="user" items="${listt}"  begin="0" end="${listt.size()-1}" varStatus="loop">
											    <tr data-status="${user.getTeam().getName()}">
											    <td>
												    	<div class="checkbox">
															<label><input type="checkbox" name = "userIds" value = "${user.getId()}"></label>
														</div>
													</td>
											          <td><c:out value="${user.name}" /></td>
											          <td><c:out value="${user.surname}" /></td>
											          <td><c:out value="${user.login}" /></td>
											          <td><c:out value="${user.getTeam().getName()}" /></td>
											          <td><c:out value="${user.getBall().getBallsToGive()}" /></td>
											          <td><c:out value="${allBallsGivenTo.get(loop.count-1)}" /></td>
											          <td><c:out value="${summedMoneyList.get(loop.count-1)} zl ( ${moneyList.get(loop.count-1)} zl + ${extraMoneyList.get(loop.count-1)} zl )"/></td>
											      </tr>
						            			</c:forEach>
											    </tbody>
											  </table>
											<table class="table table-filter table-hover col-md-12 table-responsive">
												  <thead>
													  <tr data-status="tableeee123">
														  <th style="width: 35%">
														  </th>
														  <th style="width: 6%">
														  </th>
													  </tr>
													  </thead>
													  <tbody>
													  <tr data-status="tableeee123">
														  <td>
														  </td>
														  <td><b>Sum </b><c:out value="${suma} zl" /></td>							
													  </tr>
												  </tbody>
											  </table>
											</div>
						                </div>
						            </div>
								</div>
							</div>	
							<div class=text-right>
							     <button type="submit"class="btn-info btn btn-lg">Next</button>
							</div>
						</form>	
					</div>
				
					<div class="tab-pane" id="tab_default_2">
						<form method="POST" action="${pageContext.request.contextPath}/editcomment">
							<div class="container-fluid main-container">
									<div class="col-md-12">
										    <div class="panel panel-default">
										             <div class="panel-heading">
										                    Comments that you gave
										                </div>
										                <div class="panel-body">
										                    <div class="container col-md-12">          
															  <table class="table table-hover col-md-12 table-responsive">
															    <thead>
															    <tr data-status="tableeee">
															      	<th>Name</th>
															        <th>Surname</th>
															        <th>Balls</th>
															        <th>What did you like?</th>
															        <th>What can she/he do better?</th>
															        <th align="right" style="width:30px">Edit</th>
															      
															        <th align="right" style="width:40px">Delete</th>
															      </tr>
															    </thead>
															    <tbody>
															    
															      <c:forEach var="comment" items="${yourComments}">
															    <tr data-status="tableeee">
														          <td><c:out value="${comment.getUser().getName()}" /></td>
														          <td><c:out value="${comment.getUser().getSurname()}" /></td>
														          <td><c:out value="${comment.getBallsPerCom()}" /></td>
														          <td style="max-width:300px; word-wrap: normal;"><c:out value="${comment.getFirstCom()}" /></td>
															      <td style="max-width:300px; word-wrap: normal;"><c:out value="${comment.getSecondCom()}" /></td>   
															     
															      <td align="center"><button name="buttonComId" style="width:40px" value="${comment.getComId()}"   type="submit" class="btn btn-default btn-edit">
															      	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
															      
															      
															      <td align="center"><button data-toggle="modal" style="width:40px" data-target="#Modal2${comment.getComId()}" type="button" value="${comment.getComId()}" class="btn btn-default btn-edit">
															      	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td>
															      </tr>
										            			</c:forEach>
				
															    </tbody>
															  </table>
															</div>
										                </div>
										            </div>
												</div>
											</div>
											<c:forEach var="comment" items="${yourComments}">
												<div id="Modal2${comment.getComId()}" class="modal fade" role="dialog">
												  <div class="modal-dialog">
												
												    <!-- Modal content-->
												    <div class="modal-content">
												      <div class="modal-header">
												        <button type="button" class="close" data-dismiss="modal">&times;</button>
												        <h4 class="modal-title">Delete</h4>
												      </div>
												      <div class="modal-body">
												           	Delete this comment ?
												      </div>
												      <div class="modal-footer">
												        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
												         <button type="submit" name="deleteButton1" class="btn btn-primary btn-change pull-right" value="${comment.getComId()}">Delete</button>
												      </div>
												    
												    </div>
												
												  </div>
												</div>
											</c:forEach>
										</form>	
									</div>
						
					
						
						<div class="tab-pane" id="tab_default_3">
							<form method="POST" action="${pageContext.request.contextPath}/confirmedComm">
							
							
							<div class="container-fluid main-container">
								<div class="col-md-12">
						            <div class="panel panel-default">
						                <div class="panel-heading">
						                    Employees who received comments
						                </div>
						                <div class="panel-body">
						                    <div class="container col-md-12">          
											  <table class="table table-hover col-md-12 table-responsive">
											    <thead>
											    	<tr data-status="tableeee">
											      	<th>Name</th>
											        <th>Surname</th>
											        
											        <th>What did you like?</th>
											        <th>What can she/he do better?</th>
											        <th align="right" style="width:40px">Edit</th>
											        
											      </tr>
											    </thead>
											    <tbody>
											      <c:forEach var="user" items="${listt}"  begin="0" end="${listt.size()-1}" varStatus="loop">
											    <tr data-status="tableeee">
  
											          <td><c:out value="${user.name}" /></td>
											          <td><c:out value="${user.surname}" /></td>
											        
											        <td style="max-width:300px; word-wrap: normal;">
												        <c:forEach var="comment" items="${commentList}">
													        
													        <c:set var="userId" value="${user.getId()}"/>
															<c:set var="userComId" value="${comment.getUser().getId()}"/>
															<c:if test="${userId == userComId}" >
																<c:out value="${comment.getFirstCom()}"/>
																<br>
															</c:if>
												        
											         	</c:forEach>
											         	
						            				</td>
						            				
						            				<td style="max-width:300px; word-wrap: normal;">
												        <c:forEach var="comment" items="${commentList}">
													        
													        <c:set var="userId" value="${user.getId()}"/>
															<c:set var="userComId" value="${comment.getUser().getId()}"/>
															<c:if test="${userId == userComId}" >
																<c:out value="${comment.getSecondCom()}"/>
																<br>
															</c:if>
												         	
											         	
											         	</c:forEach>
											         	
						            				</td>

													<td align="center">
													<c:set var="licznik1" value="0"/>
													<c:forEach var="comment" items="${commentList}">
													        
													        <c:set var="userId" value="${user.getId()}"/>
															<c:set var="userComId" value="${comment.getUser().getId()}"/>

															<c:if test="${userId == userComId}" >
															<c:set var="licznik1" value="1"/>
																
															</c:if>
											       </c:forEach> 
											       <c:if test="${licznik1 == 1}" >
													 	
											         	<button type="submit"  name="buttonComId" value="${user.getId()}" style="width:40px" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
													</c:if>
													<c:if test="${licznik1 == 0}" >
													 	
											         	<button type="submit"  name="buttonComId" disabled value="${user.getId()}" style="width:40px" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
													</c:if>
														
													 </td>
											      </tr>
						            			</c:forEach>
						    						            			
											    </tbody>
											  </table>
											</div>
						                </div>
						            </div>
								</div>
							</div>
					</form>	
				</div>


				
				<div class="tab-pane" id="tab_default_4">

					<div class="container-fluid main-container">
						<div class="col-md-12">
				            <div class="panel panel-default">
				                <div class="panel-heading">
				                    Confirmed comments
				                </div>
				                <div class="panel-body">
				                    <div class="container col-md-12">        
									  <table class="table table-hover col-md-12 table-responsive">
									    <thead>
									      <tr data-status="tableeee">
									      	<th>Name</th>
									        <th>Surname</th>
									        <th>What did you like?</th>
									        <th>What can she/he do better?</th>
									      </tr>
									    </thead>
									    <tbody>
											      <c:forEach var="user" items="${listt}"  begin="0" end="${listt.size()-1}" varStatus="loop">
											    <tr data-status="tableeee">
  
											          <td><c:out value="${user.name}" /></td>
											          <td><c:out value="${user.surname}" /></td>
											        
											        <td style="max-width:300px; word-wrap: normal;">
												        <c:forEach var="commentsC" items="${commentConfirmedList}">
													        
													        <c:set var="userId" value="${user.getId()}"/>
															<c:set var="userComId" value="${commentsC.getUser().getId()}"/>
															<c:if test="${userId == userComId}" >
																<c:out value="${commentsC.getFirstCom()}"/>
																<br>
															</c:if>
												        
											         	</c:forEach>
											         	
						            				</td>
						            				
						            				<td style="max-width:300px; word-wrap: normal;">
												        <c:forEach var="commentsC" items="${commentConfirmedList}">
													        
													        <c:set var="userId" value="${user.getId()}"/>
															<c:set var="userComId" value="${commentsC.getUser().getId()}"/>
															<c:if test="${userId == userComId}" >
																<c:out value="${commentsC.getSecondCom()}"/>
																<br>
															</c:if>
												         	
											         	
											         	</c:forEach>
											         	
						            				</td>
													
											      </tr>
						            			</c:forEach>
						    						            			
											    </tbody>
									  
									  </table>
									</div>
				                </div>
				            </div>
						</div>
						<c:set var="checkAreAllCommentsConfirmed" value="${checkAreAllCommentsConfirmed}"/>
						<c:set var="locked" value="1"/>
						<c:set var="unlocked" value="0"/>
						<form method="POST" action="${pageContext.request.contextPath}/sendMail">
						<c:if test="${checkAreAllCommentsConfirmed == locked}" >
					<div class=text-right>
							     <button data-toggle="modal" data-target="#setSettingssModal" type="button" class="btn-info btn btn-lg" disabled>Send mails</button>
							</div>
							</c:if>
							<c:if test="${checkAreAllCommentsConfirmed == unlocked}" >
								<div class=text-right>
							     <button data-toggle="modal" data-target="#setSettingssModal" type="button" class="btn-info btn btn-lg">Send mails</button>
							</div>
							</c:if>
						
							
						<div id="setSettingssModal" class="modal fade" role="dialog">
							  <div class="modal-dialog">
							    <!-- Modal content-->
							    	<div class="modal-content">
							      		<div class="modal-header">
							      			<button type="button" class="close" data-dismiss="modal">&times;</button>
	        								<h4 class="modal-title">New settings</h4>
										</div>
										<div class="modal-body">
											<p style="padding-bottom: 10px;"> Before sending mails you need to set balls number, amount of money and deadline for the next session.</p>
											
											<div class="form-group col-md-12">
										      <label class="col-md-4">Balls Per Person </label>
										      <div class="col-md-4">
										     	 <input class="form-control" value="${settingsList.get(0).getBallsPerPerson()}" type="number" min="0" name="ballsPerPers" required/>
										      </div>
										    </div>
							    			<div class="form-group col-md-12">
										      <label class="col-md-4">Money </label>
										      <div class="col-md-4">
										      	<input class="form-control" value="${settingsList.get(0).getMoney()}" type="number" min="0" name="money" placeholder="PLN" required/>
										      </div>
										    </div>
							    			<div class="form-group col-md-12">
										        <label class="col-md-4">Deadline</label>
										        <div class="col-md-6 date">
										            <div class="input-group input-append date" id="dateRangePicker">
										                <input readonly value="${settingsList.get(0).getDeadline()}" type="text" class="form-control" name="deadline" />
										                <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
										            </div>
										        </div>
										    </div>
										</div>
										<div class="modal-footer">
											<button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         								<button type="button" data-toggle="modal" data-target="#sendMailsModal" data-dismiss="modal" class="btn btn-primary btn-change pull-right" >Save</button>
										</div>
									</div>
								</div>
							</div>
							
							<div id="sendMailsModal" class="modal fade" role="dialog">
							  <div class="modal-dialog">
							    <!-- Modal content-->
							    	<div class="modal-content">
							      		<div class="modal-header">
							      			<button type="button" class="close" data-dismiss="modal">&times;</button>
	        								<h4 class="modal-title">Confirm</h4>
										</div>
										<div class="modal-body">
											Selecting "OK" will cause mails to be sent and the settings will be reset.
											<br />
											Are you sure you want to do that?
										</div>
										<div class="modal-footer">
											<button data-toggle="modal" data-target="#setSettingssModal" type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         								<button type="submit" name="confirmButton" class="btn btn-primary btn-change pull-right" >OK</button>
										</div>
									</div>
								</div>
							</div>
						</form>	

					</div>	
				</div>
			</div>
		</div>	
	</div>			



	<script>
		$(document).ready(function () {
		
			$('.star').on('click', function () {
		      $(this).toggleClass('star-checked');
		    });
		
		    $('.ckbox label').on('click', function () {
		      $(this).parents('tr').toggleClass('selected');
		    });
		
		    $('.btn-filter').on('click', function () {
		      var $target = $(this).data('target');
		      if ($target != 'all') {
		        $('.table tr').css('display', 'none');
		        $('.table tr[data-status="' + $target + '"]').fadeIn('slow');
		        $('.table tr[data-status="tableeee"]').fadeIn('slow');
		        $('.table tr[data-status="tableeee123"]').fadeOut('slow');
		      } else {
		        $('.table tr').css('display', 'none').fadeIn('slow');
		      }
		    });
		 });
	</script>
	
	<script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>
	<script>
	$(document).ready(function() {
	    $('#dateRangePicker')
	        .datepicker({
	            format: 'mm/dd/yyyy',
	            startDate: '01/01/2010',
	            endDate: '12/30/2020'
	        })
	        .on('changeDate', function(e) {
	            // Revalidate the date field
	            $('#dateRangeForm').formValidation('revalidateField', 'deadline');
	        });
	
	    $('#dateRangeForm').formValidation({
	        framework: 'bootstrap',
	        icon: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            date: {
	                validators: {
	                    notEmpty: {
	                        message: 'The date is required'
	                    },
	                    date: {
	                        format: 'MM/DD/YYYY',
	                        min: '01/01/2010',
	                        max: '12/30/2020',
	                        message: 'The date is not a valid'
	                    }
	                }
	            }
	        }
	    });
	});
	</script>
	
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>