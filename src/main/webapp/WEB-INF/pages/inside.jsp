<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		.error {
		color: red;
		}
		.outOfBalls {
		color: red;
		}
		.notEnoughBalls {
		color: red;
		}
		.nullComment {
		color: red;
		}
       		<%@include file="/web-resources/css/inside.css" %>
       		
		</style>
		
</head>

<body>
	 <form method="POST" action="${pageContext.request.contextPath}/comments">
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition>   
	
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
						
					</ul>
					</div>
					</div>
		<div class="tab-content">
		<div class="tab-pane active" id="tab_default_1">
		<div class="container-fluid main-container">
		<div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Employees
                </div>
                <p>
		<c:if test="${outOfBalls == true}">
			<b class="outOfBalls">You are out of balls.</b>
		</c:if>
		</p>
		<p>
		<c:if test="${notEnoughBalls == true}">
			<b class="notEnoughBalls">To many balls given. Dont be so generous.</b>
		</c:if>
		</p>
		<p>
		<c:if test="${nullComment == true}">
			<b class="nullComment">Comment can not be empty.</b>
		</c:if>
		</p>
                <div class="panel-body">
                    <div class="container col-md-12">          
					  <table class="table table-hover col-md-12">
			
					    <thead>
					      <tr>
					      	<th>Add balls</th>
					      	<th>Name</th>
					        <th>Surname</th>
					        <th>Login</th>
					        <th>Team</th>
					        <th>Balls</th>
					      </tr>
					    </thead>
					    <tbody>
					    
					      
					      <c:forEach var="user" items="${listt}" begin="0" end="${listt.size()-1}" varStatus="loop">
					    <tr>
					    <td>
						    	<div class="checkbox">
									<label><input type="checkbox" name = "userIds" value = "${user.getId()} "></label>
								</div>
							</td>
					          <td><c:out value="${user.name}" /></td>
					          <td><c:out value="${user.surname}" /></td>
					          <td><c:out value="${user.login}" /></td>
					          <td><c:out value="${user.getTeam().getName()}" /></td>

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
		
		
	
	
	<div class=text-right>   
	    <button type="submit"  class="btn-info btn btn-lg">Next</button>
	
	</div>
	</form>	
	</div>
	
	
	<div class="tab-pane" id="tab_default_2">
	  <form method="POST" action="${pageContext.request.contextPath}/editcomment">
			<div class="container-fluid main-container">
					<div class="col-md-12">
						    <div class="panel panel-default">
						             
						                <div class="panel-body">
						                    <div class="container col-md-12">          
											  <table class="table table-hover col-md-12 table-responsive">
											    <thead>
											    	<tr>
											      	<th>Name</th>
											        <th>Surname</th>
											        <th>Balls</th>
											        <th>First Comment</th>
											        <th>Second Comment</th>
											        <th>Edit</th>
											      
											        <th>Delete</th>
											      </tr>
											    </thead>
											    <tbody>
											    
											      <c:forEach var="comment" items="${yourComments}">
											    <tr>
										          <td><c:out value="${comment.getUser().getName()}" /></td>
										          <td><c:out value="${comment.getUser().getSurname()}" /></td>
										          <td><c:out value="${comment.getBallsPerCom()}" /></td>
										          <td><c:out value="${comment.getFirstCom()}" /></td>
											      <td><c:out value="${comment.getSecondCom()}" /></td>   
											  
											      <td> <a href="${pageContext.request.contextPath}/editcomment"><button name="buttonComId" value="${comment.getComId()}"   type="submit" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></a></td>
											      <td><button type="button" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td>
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
						</form>
					</div>
					
	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>