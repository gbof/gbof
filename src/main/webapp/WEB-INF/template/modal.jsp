<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="comment" items="${commentList}">
	<div id="Modal1${comment.getComId()}" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Delete</h4>
	      </div>
	      <div class="modal-body">
	           	
	      </div>
	      <div class="modal-footer">
	        <button type="button" style="width:80px" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
	         <button type="submit" name="deleteButton" class="btn btn-primary btn-change pull-right" value="${comment.getComId()}">Delete</button>
	      </div>
	    
	    </div>
	
	  </div>
	</div>
</c:forEach>