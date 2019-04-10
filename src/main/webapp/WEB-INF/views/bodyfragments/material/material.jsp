<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- CSS for table -->
<style>
	/* make text center in table */
	
	table thead tr th{
		text-align: center;
	} 
	table{
		text-align: center;
	}
</style>

<!-- JQuery -->
<script type="text/javascript">

	
	$(document).ready(function(){
		
		//show modal verify delete
		var materialID;
		$("table tbody tr td button.btn-delete").on("click", function(){
			event.preventDefault();
			var materialName = $(this).closest("tr").find("td").eq(1).html();
			$("#labelName").html(materialName);
			materialID = $(this).val(); // id material
//			console.log(materialName);
			$("#verifyModal").modal("show");
		});
		
		//
		$("#btnVerifyDelete").on("click", function(){
//			console.log(materialID);
			var urlDelete = $(this).attr("href", "material/" + materialID + "?action=delete");
			window.location = $(urlDelete).attr("href");
		});
		
//		thông báo thành công
		var msgSuccess = $("#messageSuccess").html();
		console.log(msgSuccess);
		if(msgSuccess === ""){ // thông báo rỗng
			$("#divSuccess").hide();
		} else {
			$("#divSuccess").fadeIn(3000, function(){
				$(this).fadeOut(2000);
			});
		}
		
// 		thông báo thất bại		
		var msgFailure = $("#messageFailure").html();
		console.log(msgFailure);
		if(msgFailure === ""){ // thông báo rỗng
			$("#divFailure").hide();
		} else {
			$("#divFailure").fadeIn(3000, function(){
				$(this).fadeOut(2000);
			});
		}
		
// 		button add event - show modal 
		$("#btnAdd").on("click", function(event){
			
			event.preventDefault();
			
			$(".modal-title").html("Thêm vật tư"); // set title for modal add
			$("#btnVerify").html("Thêm");
			$("#materialModal").modal("show");
			
		});
		
//		set form action for add material		
		$("#btnVerify").click(function(event){
			
			event.preventDefault();
			
			var btn = $("#btnVerify").html();
			
			if(btn === "Thêm"){

				$("#materialForm").attr("action", "material?action=add");
			} else {
				
				$("#materialForm").attr("action", "material?action=update&id="+materialID);
			}
			
			
			$("#materialForm").submit();
		});
		
//		button update event - show modal
		$("table tbody tr td .btn-update").on("click", function(){
			
			event.preventDefault();
			
			materialID = $(this).val(); 
			
			// lấy giá trị tên và đơn vị tính của vật tư từ hàng muốn cập nhật
			var materialName = $(this).closest("tr").find("td").eq(1).html();
			var materialUnit = $(this).closest("tr").find("td").eq(2).html();
			
			// gán giá trị tên, dvt cho input
			$("#name").val(materialName);
			$("#unit").val(materialUnit);
			
			$(".modal-title").html("Cập nhật vật tư"); // set title for modal update
			$("#btnVerify").html("Cập nhật");
			$("#materialModal").modal("show");
		});
		
	});

</script>


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
						<form:input path="name" class="form-control" id="name"/>
						<p>
							<form:errors path="name"></form:errors>
						</p>
					</div>		
					
					<div class="form-group">
						<label for="unit">Unit:</label>
						<form:input path="unit" class="form-control" id="unit"/>
						<p>
							<form:errors path="unit"></form:errors>
						</p>
					</div>
					
					<button type="submit" class="btn btn-primary" id="btnVerify"></button>
					
				</form:form> <!-- end form  -->
				
			</div> <!-- end modal body -->
			
			<div class="modal-footer">
				
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		
		</div>
		
	</div>
</div> <!-- end material modal --> 

<!-- button add material -->

<div style="margin-bottom: 30px">
	<button type="button" class="btn btn-success" id="btnAdd">Add material</button>
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
						<button class="btn btn-danger btn-delete" value="${material.id }">Delete</button> | 
						<button class="btn btn-primary btn-update" value="${material.id }">Update</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</div>
