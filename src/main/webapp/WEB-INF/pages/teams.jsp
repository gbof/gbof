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
	<title>Teams</title>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style>
	
	</style>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>

	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 

	<form method="POST" action="${pageContext.request.contextPath}/editteam">	
		<div class="container main-container">
			<div class="col-md-12">
				<div class="panel panel-default">
	                <div class="panel-heading">
	                    Employees
	                </div>
	                <div class="panel-body">
	                    <div class="container col-md-12">          
						  <table class="table table-hover col-md-12 table-responsive">
						    <thead>
						      <tr>
						      	<th>Name</th>
						      	<th>Department</th>
						        <th>Leader</th>
						        <th>Edit</th>
						      </tr>
						    </thead>
						    <tbody>
						    
								<c:forEach var="team" items="${teamlistt}" varStatus="status">
								  <tr>
							        <td><c:out value="${team.getName()}" /></td>
							        <td><c:out value="${deptNames[status.index]}" /></td>
							        <td><c:out value="${leaderNames[status.index]} ${leaderSurnames[status.index] }" /></td>
							        <td><a href="${pageContext.request.contextPath}/editteam"><button name="buttonComId" value="${team.getId()}" type="submit" class="btn btn-default btn-edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></a></td>
												      
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
			<a href="${pageContext.request.contextPath}/settings"><input style="margin-left: 30px;" class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>
		</div>
	</form>


	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>