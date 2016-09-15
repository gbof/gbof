<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<%@include file="/web-resources/css/settings.css" %>
		</style>
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	
	<!-- Comments place -->
	<div class="container">
		<div class="col-md-4-offset-4">
			<form role="form" method="POST" action="${pageContext.request.contextPath}/deptEdited">
		    	<div class="panel panel-default">  
		        	<div class="panel-heading">
		                    <h3 style="margin-bottom: 25px; text-align: center;">Edit department</h3>
		            </div>
		            <div class="panel-body">
		                    
							<div class="form-group col-md-6 col-sm-6">
								<label class="col-md-4">Name</label>
								<div class="col-md-6">
									<input value="${dept.getDeptName() }" class="form-control" name="name" required />
								</div>
							</div>
						     <div class="form-group col-md-6 col-sm-6">
						        <label class="col-md-4 control-label">Leader</label>
						        <div class="col-md-6 selectContainer">
						            <select class="form-control" name="user">
						                <option>${leaderName } ${leaderSurname } ${leaderLogin }</option>
							            <c:forEach var="user" items="${listt}">
							                <option name = "user" value="${user.getName()} ${user.getSurname() } ${user.getLogin()}"  >
							                	<c:out value="${user.getName()} ${user.getSurname() } ${user.getLogin()}" />
							                </option>
							            </c:forEach>
						            </select>
						        </div>
					    	</div>												
		           			<input type="hidden" name="dept_id" value="${dept.getDeptId()}"/>
		           			<br>
		           			
		           	</div>	
		           	<div class="panel-footer">	
  						<input type="submit" name="save" style="min-width: 100px;" class="btn btn-primary pull-right " value="Save"/>
  						<!--<button type="button" data-toggle="modal" data-target="#ModalRemoveDept" class="btn btn-default btn-change pull-right" value="Remove">Delete</button>-->
		        		<a href="${pageContext.request.contextPath}/success-login"><input type="button" style="min-width: 100px;" class="btn btn-primary pull-left " id="back" name="back"value="Back" /></a>
        			</div>
		    	</div>
		    	
		    		<div id="ModalRemoveDept" class="modal fade" role="dialog">
					  <div class="modal-dialog">
					
					    <!-- Modal content-->
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal">&times;</button>
					        <h4 class="modal-title">Confirm</h4>
					      </div>
					      <div class="modal-body">
					           	Delete this department ?
					      </div>
					      <div class="modal-footer">
					        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
					         <button type="submit" name="delete" class="btn btn-primary btn-change pull-right" >Delete</button>
					      </div>
					    
					    </div>
					
					  </div>
					</div>
		    	
		    	
		    </form>
		</div>
	</div>
		
		
	

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>