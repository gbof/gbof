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
	
	<style>
      		<%@include file="/web-resources/css/adminview.css" %>
	</style>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition>  
	
	
	<form method="POST" action="${pageContext.request.contextPath}/comments">

		<div class="col-md-12">
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
							<div class="container-fluid main-container">
								<div class="col-md-12">
						            <div class="panel panel-default">
						                <div class="panel-heading">
						                    Employees
						                </div>
						                <div class="panel-body">
						                    <div class="container col-md-12">  											        
        									<button type="button" class="btn btn-success btn-filter" data-target="all">All</button>
									        <c:forEach var="team" items="${teamlistt}">
						                            <button type="button" class="btn btn-success btn-filter" data-target="${team.getName() }">${team.getName() }</button>
						                      </c:forEach>    
											  <table  class="table table-filter table-hover col-md-12 table-responsive">
											    <thead>
											      <tr data-status="tableeee">
											      	<th>Add balls</th>
											      	<th>Name</th>
											        <th>Surname</th>
											        <th>Login</th>
											        <th>Team</th>
											        <th>Balls</th>
											        <th>Balls distributed</th>
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
							<div class=text-right>
							     <button type="submit"class="btn-info btn btn-lg">Next</button></a>
							</div>
							
						
						</div>
						
			<div class="tab-pane" id="tab_default_2">
			<form method="POST" action="${pageContext.request.contextPath}/editcomment">
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
											    <tr data-status="tableeee">
											      	<th>Name</th>
											        <th>Surname</th>
											        <th>Balls</th>
											        <th>"What did you like?"</th>
											        <th>"What can (s)he do better?"</th>
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
											      	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></a></td>
											      
											      
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
							</form>	
						</div>
						
					
						
						<div class="tab-pane" id="tab_default_3">
							<form method="POST" action="${pageContext.request.contextPath}/confirmedComm">
							
							
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
											    	<tr data-status="tableeee">
											      	<th>Name</th>
											        <th>Surname</th>
											        
											        <th>"What did you like?"</th>
											        <th>"What can (s)he do better?"</th>
											        <th align="right" style="width:40px">Edit</th>
											        
											        <th align="right" style="width:40px">Delete</th>
											      </tr>
											    </thead>
											    <tbody>
											    <!-- 
											      <c:forEach var="comment" items="${commentList}">
											      
											    <tr data-status="tableeee">
											    
										          <td><c:out value="${comment.getUser().getName()}" /></td>
										          
										          <td><c:out value="${comment.getUser().getSurname()}" /></td>
										       
										          <td style="max-width:300px; word-wrap: normal;"><c:out value="${comment.getFirstCom()}" /></td>
											      <td style="max-width:300px; word-wrap: normal;"><c:out value="${comment.getSecondCom()}" /></td>   
											      
											      <td align="center"><button type="submit" name="buttonComId" value="${comment.getComId()}" style="width:40px" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>

											      <td align="center"><button data-toggle="modal" style="width:40px" data-target="#Modal1${comment.getComId()}" type="button" value="${comment.getComId()}" class="btn btn-default btn-edit">
											      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td>
											      
											      </tr>
											      
											      
											      
						            			</c:forEach>
											     -->
											      <c:forEach var="user" items="${listt}"  begin="0" end="${listt.size()-1}" varStatus="loop">
											    <tr data-status="${user.getTeam().getName()}">
											    
											    	
											         
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
						            				 <td align="center"><button type="submit" name="buttonComId" value="${user.getId()}" style="width:40px" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>

											      <td align="center"><button data-toggle="modal" style="width:40px" data-target="#Modal1${user.getId()}" type="button" value="${comment.getComId()}" class="btn btn-default btn-edit">
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
						</div>

						
										<!-- Modal -->
<c:forEach var="comment" items="${commentList}">
	<div id="Modal${comment.getComId()}" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Confirm</h4>
	      </div>
	      <div class="modal-body">
	           	Confirm this comment ?
	      </div>
	      <div class="modal-footer">
	        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         <button type="submit" name="confirmButton" class="btn btn-primary btn-change pull-right" value="${comment.getComId()}">Save</button>
	      </div>
	    
	    </div>
	
	  </div>
	</div>
</c:forEach>




<c:forEach var="comment" items="${commentList}">
	<div id="Modal1${comment.getComId()}" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Delete</h4>
	      </div>
	      <div class="modal-body">
	           	Do you want remove this comment ?
	      </div>
	      <div class="modal-footer">
	        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         <button type="submit" name="deleteButton" class="btn btn-primary btn-change pull-right" value="${comment.getComId()}">Delete</button>
	      </div>
	    
	    </div>
	
	  </div>
	</div>
</c:forEach>

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
	           	
	      </div>
	      <div class="modal-footer">
	        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         <button type="submit" name="deleteButton" class="btn btn-primary btn-change pull-right" value="${comment.getComId()}">Delete</button>
	      </div>
	    
	    </div>
	
	  </div>
	</div>
</c:forEach>
					
			</form>			


						<div class="tab-pane" id="tab_default_4">

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
											      <tr data-status="tableeee">
											      	<th>Name</th>
											        <th>Surname</th>
											        <th>Balls</th>
											        <th>"What did you like?"</th>
											        <th>"What can (s)he do better?"</th>
											      </tr>
											    </thead>
											    <tbody>
											    
											      <c:forEach var="commentsC" items="${commentConfirmedList}">
											    <tr data-status="tableeee">
										          <td><c:out value="${commentsC.getUser().getName() }" /></td>
										          <td><c:out value="${commentsC.getUser().getSurname() }" /></td>
										          <td><c:out value="${commentsC.getBallsPerCom()}" /></td>
										          <td style="max-width:300px; word-wrap: normal;"><c:out value="${commentsC.getFirstCom()}" /></td>
											      <td style="max-width:300px; word-wrap: normal;"><c:out value="${commentsC.getSecondCom()}" /></td>   
											      </tr>
						            			</c:forEach>
											    
										
											    </tbody>
											  </table>
											</div>
						                </div>
						            </div>
								</div>
							</div>	
						</div>
						
						
		<!-- 	<form method="POST" action="${pageContext.request.contextPath}/sendMail">
				<input type="submit" value="Send mail" />
			</form>		
		 -->	



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
		      } else {
		        $('.table tr').css('display', 'none').fadeIn('slow');
		      }
		    });
		 });
	</script>
	
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>