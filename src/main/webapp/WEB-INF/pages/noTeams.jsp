<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>

<html>
<head>
	<title>GBOF - Teams</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
		
		<style>
       		<%@include file="/web-resources/css/comments.css" %>
		</style>
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
	

</head>

<body>
	
	<br/>
	<div style="text-align: center; color:red; font-size:160%;" >
<span id="balls"></span>
</div>
	<!-- Comments place -->
	<div class="container">
		<div class="col-md-4-offset-4">
		
		    <div class="form-area">  
			        <br style="clear:both">
	                    <h3 style="margin-bottom: 25px; text-align: center;">No teams created in this department.</h3>
		      
		        
		    </div>
		</div>
		 <a href="${pageContext.request.contextPath}/settings"><input style="width: 100px;" class="btn btn-primary btn-change pull-right btn-back" value="Back"/></a>
		
	</div>
		



	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>