<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>
<html>
<head>
	<title>GBOF - Help page</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>

			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
</head>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>

<body>
<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition>   
	
		<br/>
	<div style="text-align: center; color:red; font-size:160%;" >
<span id="balls"></span>
</div>
	<!-- Comments place -->
	<div class="container">
		<div class="col-md-4-offset-4">
		
		    <div class="form-area">  
		      
			        <br style="clear:both">
	                    <h3 style="margin-bottom: 25px; text-align: center;">Help message</h3>
	       				<center>
	       				</br>
	           			<div class="form-group">
								<label>${settingsList.get(0).getHelpMsg()}</label>
								
								
								
						</div>
			        
		       <a href="${pageContext.request.contextPath}/success-login"><input style="width: 100px;" class="btn btn-primary btn-change pull-back btn-back" value="Back"/></a>
		      
		     
		        	</center>
		    </div>
		</div>
	</div>
	
	
</body>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
		