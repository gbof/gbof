<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>

<html>
<head>
	<title>GBOF - Comments</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
		
		<style>
		</style>
		
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>

</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	<div class="container-fluid main-container">
		<div class="col-md-12">
	           <div class="panel panel-default">
	               <div class="panel-heading">
	                   Employees
	               </div>
	               <div class="panel-body">
		               <button type="button" class="btn btn-success btn-filter" data-target="all">ALL</button>
				        <c:forEach var="team" items="${teamlistt}">
	                            <button type="button" class="btn btn-success btn-filter" data-target="${team.getName() }">${team.getName() }</button>
	                      </c:forEach>   
					  	<form class="form-inline" role="form" method="POST" action="${pageContext.request.contextPath}/moreComments"> 
					  		<table class="table table-hover table-responsive table-filter">
					  			<thead>
							      <tr data-status="tableeee">
							      	<th>Add</th>
							      	<th>Name</th>
							        <th>Surname</th>
							        <th>Login</th>
							        <th>Team</th>
							        <th>Balls</th>
							      </tr>
							    </thead>
							    <tbody>
								<c:forEach var="user" items="${listt1}" varStatus="loop">
						    	<tr data-status="${user.getTeam().getName()}">
						   			<td>
								    	<div class="checkbox">
											<label><input type="checkbox" name = "userAddMoreIds" value = "${user.getId()}" ></label>
										</div>
									</td>
									<td><c:out value="${user.name}" /></td>
									<td><c:out value="${user.surname}" /></td>
									<td><c:out value="${user.login}" /></td>
						          <td><c:out value="${user.getTeam().getName()}" /></td>
						          <td><c:out value="${allBallsGivenTo.get(loop.count-1)}" /></td>
						      	</tr>
					  	<!--  <input type="hidden" name="message1List" value="<c:out value="${message1List[status.index]}" />"/>-->
					  	
					       			</c:forEach>
						    </tbody>
					  	</table>
					  	
		       			<input type="hidden" name="allMess1s" value="<c:out value="${allMess1s}" />"/>
		       			<input type="hidden" name="allMess2s" value="<c:out value="${allMess2s}" />"/>
		       			<input type="hidden" name="allBallss" value="<c:out value="${allBallss}" />"/>
				    	<input style="width: 100px;" type="submit" name="back" class="btn btn-primary btn-change pull-left" value="Back"/>
				    	<input type="submit" name="submit" class="btn btn-primary btn-change pull-right" value="Add" />
					</form>
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
		
	

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>