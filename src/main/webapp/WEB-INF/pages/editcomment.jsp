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
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>


</head>

<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	
	<!-- Comments place -->
	<div class="container">
		<div class="col-md-4-offset-4">
		
		    <div class="form-area">  
		        <form role="form" method="POST" action="${pageContext.request.contextPath}/commentEdited">
		        <br style="clear:both">
		                    <h3 style="margin-bottom: 25px; text-align: center;">Edit comment</h3>
		       				
							<div class="form-group">
								<label>${commentId.getUser().getName()} ${commentId.getUser().getSurname()}</label>
							</div>
							<div class="form-group col-md-4 col-sm-6">
								<input type="number" value="${commentId.getBallsPerCom()}" class="form-control" min="0" max="${kule}" id="mobile" name="ballsNumber" placeholder="Number of balls" required />
							</div>
		                    <div class="form-group">
		                    	<textarea class="form-control"  type="textarea" name="message1" id="message" placeholder="What did you like?" maxlength="140" rows="7" required>${commentId.getFirstCom()}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
		                    </div>
		                    
		                    <div class="form-group">
		                    	<textarea class="form-control" type="textarea" name="message2" id="message" placeholder="What should I work on?" maxlength="140" rows="7" required>${commentId.getSecondCom()}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
		                    </div>
		                    
		                    <input type="submit" id="submit" name="submit" value="Edit" id="btnSave" class="btn btn-primary pull-right" > 
			        		<input type="hidden" name="commentToUserId" value="${commentId.getUser().getId()}" />
		           			<input type="hidden" name="comId" value="${commentId.getComId()}"/>
		           				

		           		</form>
		        		<a href="${pageContext.request.contextPath}/success-login"><input type="button" style="min-width: 100px;" class="btn btn-default pull-left " id="back" name="back"value="Back" /></a>

		        
		        	<!-- Modal -->
		        
		        
	<div id="ModalCommentEdited" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        
	      </div>
	      <div class="modal-body">
	           	<center><h4 class="modal-body">Comment Edited</h4></center>
	      </div>
	      <div class="modal-footer">
	       <button type="submit" id="submit" name="submit" value="OK" id="btnSave" class="submit_button pull-right">OK</button>
	       </form>
	      </div>
	    
	    </div>
	
	  </div>
	</div>
		        
		        

		    </div>
		</div>
		</div>
	

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>