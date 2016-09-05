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
		
		
			<h3 style="margin-bottom: 25px; text-align: center;">Comments</h3>
				<form id="commentsform" role="form" method="POST" action="${pageContext.request.contextPath}/commentAdded">
					<div class="panel panel-default">
						<c:forEach var="user" items="${userList}" varStatus="status">
							<div class="panel-heading">
								<label>${user.name} ${user.surname}</label>
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
												placeholder="" value="0"  required onkeyup="findTotal();" onmouseup="findTotal();" /> 
										</div>	
										</c:if>
										<c:set var="rola" value="${user.role.id}"/>
										<c:set var="admin" value="1"/>
										<c:if test="${rola != admin}" >
											
										<div class="col-md-3 col-sm-3 col-xs-2">	
											<input type="number" min="0" max="${kule}" class="form-control" id="ballsnumber" name="ballsNumber" 
												placeholder="" value="${ballsNumberList[status.index]}"  required onkeyup="findTotal();" onmouseup="findTotal();" /> 
										</div>	
										
										</c:if>
									
									</div>
								</div>
								<div class="row">
				                    <div class="col-md-6 col-sm-12">
				                    	<textarea style="resize: none;" class="form-control" type="textarea" value="message1" name="message1" id="message1"
			
				                    		placeholder="What did you like?" maxlength="140" rows="7" required>${message1List[status.index]}</textarea>
				                        <span class="help-block"><p id="characterLeft" class="help-block " ></p></span>                    
			
				                    </div>
					                    
				                    <div class="col-md-6 col-sm-12">
				                    	<textarea style="resize: none;" class="form-control" type="textarea" value="message2" name="message2" id="message2"
				                    		placeholder="What can (s)he do better?" maxlength="140" rows="7" required>${message2List[status.index]}</textarea>
				                        <span class="help-block"><p id="characterLeft" class="help-block "></p></span>                    
				                    </div>
			           			</div>
			           		</div>
			           		
			    		</c:forEach>
			    		<div class="panel-footer">	
					    	<input style="width: 100px;" type="submit" id="submit" name="submit" value="Save" id="btnSave" class="btn btn-primary btn-change pull-right btn-back" > 
					    	<input type="submit" name="addMore" value="Add more users" class="btn btn-default" formnovalidate >
				      	</div>
				     </div>
			    </form>


		</div>
	</div>
		
	
	<script>
	function findTotal(){
	    var arr = document.getElementsByName('ballsNumber');
	    var totalCount = ${kule};
	    var tot=0;
	    var total = ${kule};
	    for(var i=0;i<arr.length;i++){
	        if(parseInt(arr[i].value))
	            tot += parseInt(arr[i].value);
	    }
	    if(totalCount-tot<0)
	    	$('#balls').text("Rozdales za duzo kulek");
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