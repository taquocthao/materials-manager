<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- header css -->
<link rel="stylesheet" href='<spring:url value="/styles/header.css"></spring:url>' />

<div class="header">
	<security:authorize access="isAuthenticated()">
	<security:authentication property="principal" var="user"/>
		<nav class="navbar ">
			<div class="container-fluid">
				<ul class="nav navbar-nav navbar-right">
	      			<li class="dropdown">
	      				<a class="dropdown-toggle" data-toggle="dropdown">
	      					${user.username }
				       		<span class="caret"></span>
				        </a>
				        <ul class="dropdown-menu">
				          <li><a href="#">Create account</a></li>
				          <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				        </ul>
	      			</li>
	      		</ul>
	      	</div>
	   	</nav>
	</security:authorize>
</div>
