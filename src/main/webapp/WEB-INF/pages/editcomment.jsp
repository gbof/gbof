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
		<style>
			<%@include file="/web-resources/css/all.css" %>
		</style>
			<script src="webjars/jquery/2.1.4/jquery.min.js"></script>


</head>
	<script src="webjars/jquery/2.1.4/jquery.min.js"></script>
<body>



<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/success-login">
					GBOF
				</a>
		</div>
	
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">			
			<ul class="nav navbar-nav navbar-right">
			<c:set var="rola" value="${rola}"/>
			<c:set var="admin" value="admin"/>
			<c:set var="superuser" value="superuser" />
			<li>
			<a style="text-align: center; color:red;" id="balls">Balls left: ${kule}</a>
			</li>
			<c:if test="${rola != superuser }" >
				<li><a href="${pageContext.request.contextPath}/helpPage">Help</a></li>
			</c:if>
			<c:set var="rola" value="${rola}"/>
			<c:set var="admin" value="admin"/>
			<c:if test="${rola == admin}">
				<li><a href="${pageContext.request.contextPath}/settings">Settings</a></li>
				</c:if>
				<li><a>Username: ${login}</a></li>
				<li class="dropdown ">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Account<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
						<li><a href="${pageContext.request.contextPath}/change">Change password</a></li>
						<li class="divider"></li>
						<li>
							<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
						</li>
					</ul>
				</li>
			</ul>
		</div><!-- /.navbar-collapse -->
	</div><!-- /.container-fluid -->
</nav>
	<br/>
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
							
							<input type="number" value="${commentId.getBallsPerCom()}" min="0" max="${kule+commentId.getBallsPerCom()}" class="form-control" id="mobile" name="ballsNumber" 
								placeholder="Number of balls" value="${ballsNumberList[status.index]}"  required onkeyup="findTotal();" onmouseup="findTotal();"/> 
							
							
							</c:if>
							</c:if>
							<c:if test="${user.getRole().getId() == admin}">
							<input type="hidden" id="mobile" name="ballsNumber" value="${commentId.getBallsPerCom()}"  />
							</c:if>
							</div>
		                    <div class="form-group">
		                    	<textarea style="resize: none;" class="form-control"  type="textarea" name="message1" id="message" placeholder="What did you like?" maxlength="140" rows="7" required onkeyup="findSpaces();" onmouseup="findSpaces();">${commentId.getFirstCom()}</textarea>
		                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
		                    </div>
		                    
		                    <div class="form-group">
		                    	<textarea style="resize: none;" class="form-control" type="textarea" name="message2" id="message" placeholder="What can she/he do better?" maxlength="140" rows="7" required onkeyup="findSpaces();" onmouseup="findSpaces();">${commentId.getSecondCom()}</textarea>
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
				 	function findSpaces(){
				 		var message1List = document.getElementsByName('message1');
				 		var message2List = document.getElementsByName('message2');
				 		var ballsNumberList = document.getElementsByName('ballsNumber');
				 		var tmp=0;
				 	    $('#submit').attr('disabled',false);
				 	    	 for(var i=0;i<message1List.length;i++){
				 	        	if((message1List[i].value.trim().length != 0) && (message2List[i].value.trim().length != 0) && (ballsNumberList[i].value.trim().length != 0))
				 	        		if(tmp<message1List.length)
				 	            	tmp++;
				 	        		else{}
				 	        	else
				 	        		if(tmp>0)
				 	        			tmp--;
				 	    	 		}
				 	    	 if(tmp==message1List.length){
				 	    		 $('#submit').attr('disabled',false);
				 	    		findTotal();
				 	    	 }
				 	    	 else
				 	    		 $('#submit').attr('disabled',true);
				 	}
				 	
				 	$('document').ready(findSpaces());
				 	</script>
				 	
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
	    if(totalCount-tot<0){
	    	$('#balls').text("Dont be so generous, you are out of balls");
	    	$('input:submit').attr("disabled", true);
	    }
	    else if(totalCount-tot>totalCount){
	    	$('#balls').text("Balls left: "+totalCount);
	    	$('input:submit').attr("disabled", true);
	    }
	    else
	    	{
	    		total = totalCount;
	    		var ballsLeft = total-tot;
	    		$('#balls').text("Balls left: "+ballsLeft).val();
	    		balls = ballsLeft;
	    		$('input:submit').attr("disabled", false);
	    	}
	}

	</script>
 <script>
				     $('input:submit').click(function(){
				 		var v = $('input#ballsnumber').map(function(){return $(this).val();}).get();
				 		var m = $('textarea#message1').map(function(){return $(this).val();}).get();
				 		var mm = $('textarea#message2').map(function(){return $(this).val();}).get();;
						var max = ${kule};
				 		var empty=0;
				 		$(v).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 				$('input:submit').attr("disabled", true);
				 				$('#back').attr('disabled',true);
				 			}
				 			if (el < 0){
			 					empty=empty+1;
			 					$('input:submit').attr("disabled", true);
			 					$('#back').attr('disabled',true);
			 				}
				    	 if (el > max){
		 						empty=empty+1;
		 					}
		 				});	
				 		$(m).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 				$('input:submit').attr("disabled", true);
				 				$('#back').attr('disabled',true);
				 			}
				 		});
				 		
				 		$(mm).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 				$('input:submit').attr("disabled", true);
				 				$('#back').attr('disabled',true);
				 			}
				 		});
				 		
				 		if (empty == 0)
				 			$('input:submit').attr("disabled", true);
				 		$('#back').attr('disabled',true);
				 	});

				 	</script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>