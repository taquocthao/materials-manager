<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- script for page warehouse -->
<script src='<spring:url value="/js/warehouse.js"></spring:url>'></script>

<!-- button add -->
<div style="margin-bottom: 30px">
	<button class="btn btn-success" id="btnAdd">Add Warehouse</button>
</div>


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

<!-- Modal warehouse -->
<div id="warehouseModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		
		<!-- Modal content -->
		<div class="modal-content">
			
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			
			<div class="modal-body">
			
				<form:form id="warehouseForm" modelAttribute="warehouse" method="post">
					
					<div class="form-group">
						<label for="name">Name:</label>
						<form:input path="name" class="form-control" id="name"/>
						<p>
							<form:errors path="name"></form:errors>
						</p>
					</div>		
					
					<div class="form-group">
						<label for="address">Address:</label>
						<form:input path="address" class="form-control" id="address"/>
						<p>
							<form:errors path="address"></form:errors>
						</p>
					</div>
					
					<div class="form-group">
						<label>Branch: </label>
						<form:select path="branch_id" id="branch">
							<c:forEach var="branch" items="${branchs }">
								<option value="${branch.id }">${branch.branch }</option>
							</c:forEach>
						</form:select>
						<p>
							<form:errors path="branch_id"></form:errors>
						</p>
					</div>
	
				</form:form>
				
				<p id="messageVerifyDelete" ></p>
				
			</div> <!-- end modal body -->
			
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" id="btnVerify"></button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>	
	</div>
</div> <!-- end modal warehouse --> 

<!-- search -->
<div class="row">
	<div class="form-group col-sm-6">
		<input type="search" class="form-control" placeholder="Search">
	</div>
</div> 

<!-- table -->
<div class="table-responsive">
	<table class="table table-bordered">
		
		<thead>
			<tr>
				<th>#No</th>
				<th>Name</th>
				<th>Address</th>
				<th>Branch</th>
				<th>Action</th>
			</tr>
		</thead>
		
		<tbody>
			
			<c:forEach var="warehouse" items="${listWarehouse }" varStatus="status">
				<tr>
					<td>${ status.index + 1}</td>
					<td>${ warehouse.name } </td>
					<td>${ warehouse.address } </td>
					<td>${ warehouse.branch_id }</td>
					<td>
						<button class="btn btn-danger" id="btnDelete" value="${ warehouse.id }">Delete</button> | 
						<button class="btn btn-primary" id="btnUpdate" value="${ warehouse.id }">Update</button>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	
	</table>
</div>
<!-- end table -->

