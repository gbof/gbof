<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" %>

<html>
<body>


<form method="POST" action="settingsAdd" >

   <input type="text" name="ballsPerPers" />Balls per person<br>
   <input type="text" name="money" />Money<br>
   <input type="text" name="deadline" />Deadline<br>
  
   <button type="submit" name="submit" >Next</button>

</form>

</body>
</html>