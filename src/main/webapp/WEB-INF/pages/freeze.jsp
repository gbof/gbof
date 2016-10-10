<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>

<html>
<head>
	<title>GBOF - Home</title>
		<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
		<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
		<style>
       		<%@include file="/web-resources/css/comments.css" %>
		</style>
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
	

</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	<br/>
	<div style="text-align: center; color:red; font-size:160%;" >
		<span id="balls"></span>
	</div>
	<!-- Comments place -->
		
		    <div class="form-area" style="margin: 50px; ">  
			        <br style="clear:both">
	                    <h4 style="margin-bottom: 25px; text-align: center;">The GBOF session is finished, you can see the comments that you received below.</h4>
		        
		    </div>
		    
 		    
		    <div class="container-fluid">
				<div class="container-fluid main-container">
					<div class="col-md-12">
			            <div class="panel panel-default">
			                <div class="panel-heading">
			                    Received comments
			                </div>
			               
			                <div class="panel-body">
			                    <div class="container col-md-12"> 
								  <h4>Balls: ${ballsSum }</h4>
								  
								  <br>          
								  <table class="table col-md-12">
						
								    <thead>
								      <tr >
								        <th>What did they like?</th>
								        <th>What can you do better?</th>
								      </tr>
								    </thead>
								    <tbody>
								    
								      
								    	<tr>
								   	 	<td style="max-width:300px;">
								   	 		<c:forEach var="comment" items="${commentlistt}" varStatus="loop">
								   	 			<c:out value="${comment.getFirstCom() }"/>
								   	 			<br>
								   	 			<br>
								   	 		</c:forEach>
								   	 	</td>
								        <td style="max-width:300px;">
								        	<c:forEach var="comment" items="${commentlistt}" varStatus="loop">
								        		<c:out value="${comment.getSecondCom() }"/>
								        		<br>
								        		<br>
								        	</c:forEach>
								        </td>
								          
								      </tr>
			            			
								    </tbody>
								  </table>
								</div>
			                </div>
			            </div>
					</div>
				</div>
			
		</div>

		

							
	


	
	


	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>