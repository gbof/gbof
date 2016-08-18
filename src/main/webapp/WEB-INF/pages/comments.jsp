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
       		<%@include file="/web-resources/css/comments.css" %>
		</style>
		


</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	
	<!-- Comments place -->
	<div class="container">
		<div class="col-md-4-offset-4">
		
		    <div class="form-area">  
		        <form role="form" method="POST" action="${pageContext.request.contextPath}/commentAdded">
			        <br style="clear:both">
	                    <h3 style="margin-bottom: 25px; text-align: center;">Send balls</h3>
	       				<c:forEach var="user" items="${userList}" varStatus="status">
							<div class="form-group">
								<label>${user.name} ${user.surname}</label>
							</div>
							<div class="form-group col-md-4 col-sm-6">
								<input type="number" min="0" max="${kule}" class="form-control" id="mobile" name="ballsNumber" 
									placeholder="Number of balls" value="${ballsNumberList[status.index]}"  required /> 
							</div>
		                    <div class="form-group">
		                    	<textarea class="form-control" type="textarea" value="message1" name="message1" id="message1" 
		                    		placeholder="What did you like?" maxlength="140" rows="7">${message1List[status.index]}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block ">You have reached the limit</p></span>                    
		                    </div>
		                    
		                    <div class="form-group">
		                    	<textarea class="form-control" type="textarea" value="message2" name="message2" id="message2" 
		                    		placeholder="What can I do better?" maxlength="140" rows="7">${message2List[status.index]}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block ">You have reached the limit</p></span>                    
		                    </div>
	           			</c:forEach>
	           			
			        <input type="submit" id="submit" name="submit" value="Send" id="btnSave" class="submit_button pull-right" > 
			        <input type="submit" name="addMore" value="Add more users" class="btn btn-default" formnovalidate >
		        </form>
		        
		    </div>
		</div>
	</div>
		

	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>