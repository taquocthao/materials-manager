<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="icon" type="image/png" href='<spring:url value="/image/material-icon.png"></spring:url>' /> 

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<!-- font awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- common css -->
<link rel="stylesheet" href='<spring:url value="/styles/common.css"></spring:url>' />

<!-- common js -->
<script src='<spring:url value="/js/common.js"></spring:url>'></script>

	
<script type="text/javascript">
	$(document).ready(function() {

		var uri = window.location.pathname.split("/")[4];
		console.log(uri);
		$("#list li").removeClass("active");
		$("#list a[href*=" + uri + "]").parent().addClass("active");

	})
</script>

</head>
<body>
	<div class="container-fluid">
		<div class="row content">
			<!-- column left -->
			<div class="col-sm-3 sidenav">
				<h4>Quản lý vật tư</h4>
				<tiles:insertAttribute name="menu" />
			</div>
			<!-- column right -->
			<div class="col-sm-9 body-wrapper">
				<div class="header-content">
					<tiles:insertAttribute name="header" />
				</div>
				<div class="body-content">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
	</div>
	<tiles:insertAttribute name="footer" />


</body>
</html>