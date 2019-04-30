<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>
		Login
	</title>
	<link rel="icon" href='<spring:url value="/image/login.png"></spring:url>' />
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	
	<!-- css login file -->
	<link rel="stylesheet" href="<spring:url value='/styles/login.css'></spring:url>" />
	
</head>
<body>
	<div class="container">
		<div class="main">
			<div class="panel-title">
				<h2>Login</h2>
			</div>
			<form action="login" method="post">
				<div class="form-group">
					<label for="branch">Branch:</label>
					<select id="branch" name="branch" class="form-control">
						<c:forEach var="branch" items="${branchs }">
							<option value="${branch.id }" > ${branch.branch } </option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="username">Username:</label>
					<input class="form-control" type="text" id="username" name="username" />
				</div>
				<div class="form-group">
					<label for="password">Password:</label>
					<input class="form-control" type="password" id="password" name="password" />
				</div>
				
				<div>
					<c:choose>
						<c:when test="${param.error }">
							<p class="message">Invalid username and password.</p>
						</c:when>
						<c:when test="${param.logout }">
							<p class="message">You have been logged out.</p>
						</c:when>
					</c:choose>
				</div>
				
				<div>
					<button type="submit" class="btn btn-primary" >Login</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>