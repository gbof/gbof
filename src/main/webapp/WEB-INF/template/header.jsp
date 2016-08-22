<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-default navbar-static-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<c:set var="rola" scope="session" value="${rola}"/>
			<c:set var="admin" scope="session" value="admin"/>
			<c:if test="${rola == admin}" >
				<a class="navbar-brand" href="${pageContext.request.contextPath}/adminview">
					GBOF
				</a>
			</c:if>
			<c:set var="rola" scope="session" value="${rola}"/>
			<c:set var="admin" scope="session" value="admin"/>
			<c:if test="${rola == moderator}" >
				<a class="navbar-brand" href="${pageContext.request.contextPath}/inside">
					GBOF
				</a>
			</c:if>
			<c:set var="rola" scope="session" value="${rola}"/>
			<c:set var="user" scope="session" value="user"/>
			<c:if test="${rola == user}" >
				<a class="navbar-brand" href="${pageContext.request.contextPath}/inside">
					GBOF
				</a>
			</c:if>
		</div>
	
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">			
			<ul class="nav navbar-nav navbar-right">
			<c:set var="rola" scope="session" value="${rola}"/>
			<c:set var="admin" scope="session" value="admin"/>
			<c:if test="${rola == admin}" >
			
				<li><a href="${pageContext.request.contextPath}/settings">Settings</a></li>
				</c:if>
				<li><a>Username: ${login}</a></li>
				<li><a>Your balls: ${kule}</a></li>
				<li><a>One ball value: ${money} zl</a></li>
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