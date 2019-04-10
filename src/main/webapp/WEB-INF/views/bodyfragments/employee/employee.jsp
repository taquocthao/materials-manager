<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>

<!-- search employee script -->
<script>
	$(document).ready(function() {
		
			// <!-- jquery search employee -->
			$("#search").on("keyup",function() {
				var value = $(this).val().toLowerCase();
				$("#tableEmployee tr").filter(function() {
					$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
				});
			});
			
			// declare id employee and get it when user click delete button in row table
			var idEmployee;
			// show modal verify delete employee
			$("table tbody tr td button.btn-delete").on("click", function(){
				event.preventDefault();
				idEmployee = $(this).attr("value");
				var firstname = $(this).closest("tr").find("td").eq(1).html();
				var lastname = $(this).closest("tr").find("td").eq(2).html();
				$("#labelName").html(lastname + " " +firstname);
				$("#verifyModal").modal("show");
			});
			
			//verify delete employee
			$("#btnVerifyDelete").on("click", function(){
				event.preventDefault();
				$("#verifyModal").modal("hide");
				var urlDelete = $(this).attr("href", "employee/" + idEmployee + "?action=delete");
				window.location = $(urlDelete).attr("href");
				
			});
			
			// get alert message
			// thông báo thành công
			var msgSuccess = $("#messageSuccess").html();
			var msgFailure = $("#messageFailure").html();
			
			if(msgSuccess === ""){

				 $("#divSuccess").hide();
				 
			} else {
			
				$("#divSuccess").fadeIn(3000, function(){
					 $(this).fadeOut(2000, function(){
						 msgSuccess = "";
					 });
						// xét giá trị rỗng cho thông báo
				 });
			}
			
			// thông báo lỗi
			if(msgFailure === ""){				
				 $("#divFailure").hide();
			} else {
				
				$("#divFailure").fadeIn(3000, function(){
					 $(this).fadeOut(2000);
					// xét giá trị rỗng cho thông báo
				 });
			}
			
			// hiển thị hoặc ẩn pagination
			
	});
</script>
<!-- end search script -->

<div style="margin-bottom: 30px">
	<a class="btn btn-success" href="employee?action=add"> Add employee
	</a>
</div>

<div class="alert alert-success" id="divSuccess"
	style="text-align: center;">
	<strong id="messageSuccess">${ msgSuccess }</strong>
</div>

<div class="alert alert-warning" id="divFailure"
	style="text-align: center;">
	<strong id="messageFailure">${ msgFailure }</strong>
</div>

<div class="row">
	<div class="col-sm-6">
		<input type="search" class="form-control" id="search"
			placeholder="Search" />
	</div>
</div>


<!-- Modal verify delete using bootstrap -->

<div id="verifyModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Xác nhận xóa nhân viên</h4>
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

<div class="table-responsive">
<table class="table table-bordered" id="tableEmployee">
	<thead>
		<tr>
			<th>#No</th>
			<th>First name</th>
			<th>Last name</th>
			<th>Address</th>
			<th>DoB</th>
			<th>Salary</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employee" items="${listEmployees }" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${employee.firstname }</td>
				<td>${employee.lastname }</td>
				<td>${employee.address }</td>
				<td>${employee.birthday }</td>
				<td><fmt:setLocale value="vi_VN" /> <fmt:formatNumber
						value="${employee.salary }" type="currency" /></td>
				<td>
					<!--  data-toggle="modal" data-target="#verifyModal" -->
					<button class="btn btn-danger btn-delete" value="${employee.id }">Delete</button>
					| <a class="btn btn-primary btn-update"
					href="?action=update&id=${employee.id }">Update</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>	
</div>

<div  class="row" style="display: flex; justify-content: center" >
	<ul class="pagination">
		<li><a>Previous</a></li>
		<li><a>1</a></li>
		<li><a>2</a></li>
		<li><a>3</a></li>
		<li><a>Next</a></li>
	</ul>
</div>

