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
		<style>
			<%@include file="/web-resources/css/all.css" %>
			body {
				padding-bottom: 100px;
			}
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
		
		
			<h3 style="margin-bottom: 25px; text-align: center;">Comments</h3>
				<form name="commentsform" id="commentsform" role="form" method="POST" action="${pageContext.request.contextPath}/commentAdded">
					<div class="panel panel-default">
						<c:set var="li" value="0"/>
						<c:forEach var="user" items="${userList}" varStatus="status">
							<div class="panel-heading">
								<label>${user.name} ${user.surname}</label>
															
								<button type="submit" name="delUser" id="delUser" value="${li}" class="btn btn-default pull-right" formnovalidate>X</button>
				      			
				      			<input type="hidden" id="name" name="name" value="${user.name}" />
								<c:set var="li" value="${li+1}"/>
							</div>

									
							<div class="panel-body formm">
								<div class="row">
									<div style="padding-bottom: 10px;"  class="col-md-5">
										<c:set var="rola" value="${user.role.id}"/>
										<c:set var="admin" value="1"/>
										<c:if test="${rola == admin}" >
										<div>This person is an admin, You cannot give him balls</div>
										<div class="col-md-3 col-sm-3 col-xs-5">	
											<input type="number" min="0" max="0" class="form-control" id="mobile" name="ballsNumber" 
												placeholder="" value="${ballsNumberList[status.index]}" required onkeyup="findTotal(); findSpaces();" onmouseup="findTotal(); findSpaces();" /> 
										</div>	
										</c:if>
										<c:set var="locked" value="${user.getBall().getLocked()}"/>
										<c:set var="isLocked" value="1"/>
										<c:if test="${locked == isLocked}" >
										<div>This person is locked, You cannot give him balls</div>
										<div class="col-md-3 col-sm-3 col-xs-5">	
											<input type="number" min="0" max="0" class="form-control" id="mobile" name="ballsNumber" 
												placeholder="" value="${ballsNumberList[status.index]}" required onkeyup="findTotal(); findSpaces();" onmouseup="findTotal(); findSpaces();" /> 
										</div>	
										</c:if>
										<c:set var="rola" value="${user.role.id}"/>
										<c:set var="admin" value="1"/>
										<c:if test="${rola != admin && locked != isLocked}" >
											
										<div class="col-md-3 col-sm-3 col-xs-2">	
											<input type="number" min="0" max="${kule}" class="form-control" id="ballsnumber" name="ballsNumber" 
												placeholder="" value="${ballsNumberList[status.index]}" required onkeyup="findTotal(); findSpaces();" onmouseup="findTotal(); findSpaces();" /> 
										</div>	
										
										</c:if>
									
									</div>
								</div>
								<div class="row">
				                    <div class="col-md-6 col-sm-12">
				                    	<textarea style="resize: none;" class="form-control" type="textarea" value="message1" name="message1" id="message1"
			
				                    		placeholder="What did you like?" maxlength="140" rows="7" required onclick="findSpaces();" onkeyup="findSpaces();">${message1List[status.index]}</textarea>
				                        <span class="help-block"><p id="characterLeft" class="help-block " ></p></span>                    
			
				                    </div>
					                    
				                    <div class="col-md-6 col-sm-12">
				                    	<textarea style="resize: none;" class="form-control" type="textarea" value="message2" name="message2" id="message2"
				                    		placeholder="What can she/he do better?" maxlength="140" rows="7" required onclick="findSpaces();" onkeyup="findSpaces();">${message2List[status.index]}</textarea>
				                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
				                    </div>
			           			</div>
			           		</div>
			           		
			    		</c:forEach>
			    		<div class="panel-footer">	
					    	<input style="width: 100px;" type="submit" id="submit" name="submit" value="Save" id="btnSave" class="btn btn-primary btn-change pull-right btn-back" > 
						
				     <script>
				     $('input:submit').click(function(){
				 		var v = $('input#ballsnumber').map(function(){return $(this).val();}).get();
				 		var m = $('textarea#message1').map(function(){return $(this).val();}).get();
				 		var mm = $('textarea#message2').map(function(){return $(this).val();}).get();;
				 		var empty=0;
				 		$(v).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 			}
				 		});
				 		$(m).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 			}
				 		});
				 		
				 		$(mm).each( function( i, el ){
				 			if (el == ""){
				 				empty=empty+1;
				 			}
				 		});
				 		
				 		if (empty == 0)
				 			$('input:submit').attr("disabled", true);
				 	});
				 	</script>
				 	    	<input type="submit" name="addMore" value="Add more users" class="btn btn-default" formnovalidate >
				      	<a href="${pageContext.request.contextPath}/success-login"><input style="width: 100px;" class="btn btn-primary btn-change pull-left btn-back" value="Back"/></a>
				      	
				      	</div>
				     </div>
			    </form>


		</div>
	</div>
		
	
	<script>
	function findTotal(){
	    var arr = document.getElementsByName('ballsNumber');
 	    var inputs = document.querySelectorAll("#commentsform input[name='ballsNumber']"); 
	    var totalCount = ${kule};
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
	    		var bl = ballsLeft;
	    		var val = [];
    	    	for (var i = 0; i < inputs.length; i++){
	    	    		val[i] = inputs[i].value;
	    	    	}
 	    	    if(totalCount-tot == 0){
	    			for (i = 0; i < inputs.length; i++) {
		    	    	inputs[i].max = (parseInt(val[i]) + parseInt(bl)).toString();
	    			}
	    	    } else {
	    	    	for (i = 0; i < inputs.length; i++) {
	    	    		inputs[i].max = ${kule};
	    	    	} 
	    	    }
	    	}
	}
	</script>

	
					 <script>
				 	function findSpaces(){
				 		var message1List = document.getElementsByName('message1');
				 		var message2List = document.getElementsByName('message2');
				 		var ballsNumberList = document.getElementsByName('ballsNumber');
				 		var rola =  document.getElementsByName('rola');
				 		var tmp=0;
				 	    $('#submit').attr('disabled',true);
				 	    	 for(var i=0;i<message1List.length;i++){
				 	        	if((message1List[i].value.trim().length != 0) && (message2List[i].value.trim().length != 0) && (ballsNumberList[i].value.trim().length != 0))
				 	        		if(tmp<message1List.length)
				 	            	tmp++;
				 	        		else{}
				 	        	else
				 	        		if(tmp>0)
				 	        			tmp--;
				 	    	 if(tmp==message1List.length){
				 	    		 $('#submit').attr('disabled',false);
				 	    		findTotal();
				 	    	 }
				 	    	 else
				 	    		 $('#submit').attr('disabled',true);
				 	}
				 	
				 	$('document').ready(findSpaces());
				 	</script>
			                    

	
	


	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</body>
</html>
