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
							<div class="form-group">
								<label>Name of a person</label>
							</div>
							<div class="form-group col-md-4 col-sm-6">
								<input type="number" min="0" max="20" class="form-control" id="mobile" name="ballsNumber" placeholder="Number of balls" required>
							</div>
		                    <div class="form-group">
		                    	<textarea class="form-control" type="textarea" name="message1" id="message" placeholder="What did you like?" maxlength="140" rows="7"></textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block ">You have reached the limit</p></span>                    
		                    </div>
		                    
		                    <div class="form-group">
		                    	<textarea class="form-control" type="textarea" name="message2" id="message" placeholder="What should I work on?" maxlength="140" rows="7"></textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block ">You have reached the limit</p></span>                    
		                    </div>
		            
		        <button type="submit" id="submit" name="submit" class="btn btn-primary pull-right">Submit Form</button>
		        </form>
		    </div>
		</div>
		</div>

	
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>