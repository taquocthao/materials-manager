<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {

		var branchId = window.location.pathname.split("/")[3];
		$("#branchId").html(branchId);
		
	})
</script>

<h2>Quản lý vật tư - chi nhánh <span id="branchId"></span></h2>
