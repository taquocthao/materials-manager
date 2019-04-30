<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- CSS for table -->

<style>
/* make text center in table */
table thead tr th {
	text-align: center;
}

table {
	text-align: center;
}
</style>

<!-- JQuery -->
<script src='<spring:url value="/js/materials.js"></spring:url>' ></script>



<!-- Modal verify delete using bootstrap -->

<div id="verifyModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Xác nhận xóa vật tư</h4>
			</div>
			<div class="modal-body">
				<p>
					Bạn có muốn xóa <strong id="labelName"></strong>?
				</p>
			</div>
			<div class="modal-footer">
				<a type="button" class="btn btn-danger" id="btnVerifyDelete">Có</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Không</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal material -->
<div id="materialModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content -->
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>

			<div class="modal-body">

				<form:form id="materialForm" modelAttribute="material" method="post">

					<div class="form-group">
						<label for="name">Name:</label>
						<form:input path="name" class="form-control" id="name" />
						<p>
							<form:errors path="name"></form:errors>
						</p>
					</div>

					<div class="form-group">
						<label for="unit">Unit:</label>
						<form:input path="unit" class="form-control" id="unit" />
						<p>
							<form:errors path="unit"></form:errors>
						</p>
					</div>

					<button type="submit" class="btn btn-primary" id="btnVerify"></button>

				</form:form>
				<!-- end form  -->

			</div>
			<!-- end modal body -->

			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>

		</div>

	</div>
</div>
<!-- end material modal -->


<div id="role" class="hide">${role }</div>


<!-- button add material -->
<div style="margin-bottom: 30px">
	<button type="button" class="btn btn-success" id="btnAdd">Add
		material</button>
</div>
<!--  -->

<!-- div thông báo thành công -->

<div class="alert alert-success" id="divSuccess"
	style="text-align: center;">
	<strong id="messageSuccess">${ msgSuccess }</strong>
</div>

<!-- div thông báo thất bại -->

<div class="alert alert-warning" id="divFailure"
	style="text-align: center;">
	<strong id="messageFailure">${ msgFailure }</strong>
</div>

<!-- thanh tìm kiếm -->

<div class="row" style="margin-bottom: 20px">
	<div class="col-sm-6">
		<input type="search" class="form-control" id="search"
			placeholder="Search" />
	</div>
</div>

<div class="table-responsive">
	<table class="table table-bordered" id="tableMaterial">
		<thead>
			<tr>
				<th style="text-align: center;">#No</th>
				<th>Name</th>
				<th>Unit</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="material" items="${materials}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${material.name }</td>
					<td>${material.unit }</td>
					<td>
						<button class="btn btn-danger btn-delete" value="${material.id }">Delete</button>
						|
						<button class="btn btn-primary btn-update" value="${material.id }">Update</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
