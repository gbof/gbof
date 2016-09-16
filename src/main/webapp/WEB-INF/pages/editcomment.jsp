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
       		.modal {
				padding: 05%;
			}
		</style>
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>


</head>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
<body>


	 
	<tiles:insertDefinition name="headerTemplate">
	</tiles:insertDefinition> 
	
	<div style="text-align: center; color:red; font-size:160%;" >
<span id="balls"></span>
</div>
	
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
							<c:set var="rola" value="${commentId.getUser().getId()}"/>
							<c:set var="admin" value="1"/>
							<c:if test="${rola == admin}">
							<c:if test="${user.getRole().getId() != admin}">
							This person is admin, You cannot give him balls
							<input type="number" min="0" max="0" class="form-control" id="mobile" name="ballsNumber" 
								placeholder="Number of balls" value="0"  required onkeyup="findTotal();" onmouseup="findTotal();"/> 
							
							</c:if>
							</c:if>
							<c:set var="rola" value="${commentId.getUser().getId()}"/>
							<c:set var="admin" value="1"/>
							<c:if test="${rola != admin}" >
							<c:if test="${user.getRole().getId() != admin}">
							
							<input type="number" value="${commentId.getBallsPerCom()}" min="0" max="${kule}" class="form-control" id="mobile" name="ballsNumber" 
								placeholder="Number of balls" value="${ballsNumberList[status.index]}"  required onkeyup="findTotal();" onmouseup="findTotal();"/> 
							
							
							</c:if>
							</c:if>
							<c:if test="${user.getRole().getId() == admin}">
							<input type="hidden" id="mobile" name="ballsNumber" value="${commentId.getBallsPerCom()}"  />
							</c:if>
							</div>
		                    <div class="form-group">
		                    	<textarea class="form-control"  type="textarea" name="message1" id="message" placeholder="What did you like?" maxlength="140" rows="7" required>${commentId.getFirstCom()}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
		                    </div>
		                    
		                    <div class="form-group">
		                    	<textarea class="form-control" type="textarea" name="message2" id="message" placeholder="What can she/he do better?" maxlength="140" rows="7" required>${commentId.getSecondCom()}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
		                    </div>
		                    
		                    <input type="submit" id="submit" name="submit" value="Save" id="btnSave" class="btn btn-primary pull-right" > 
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
	<script>
	function findTotal(){
	    var arr = document.getElementsByName('ballsNumber');
	    var totalCount = ${kule + commentId.getBallsPerCom()};
	    var tot=0;
	    var total = ${kule};
	    for(var i=0;i<arr.length;i++){
	        if(parseInt(arr[i].value))
	            tot += parseInt(arr[i].value);
	    }
	    if(totalCount-tot<0)
	    	$('#balls').text("Dont be so generous, you are out of balls");
	    else
	    	{
	    		total = totalCount;
	    		var ballsLeft = total-tot;
	    		$('#balls').text(ballsLeft).val();
	    		balls = ballsLeft;
	    	}
	}

	</script>

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>