$(document).ready(function(){
	
	var warehouseID;
	var role = $("#role").html();
	
//	check role
//	if role = company
//	then hide add, delete, update button
//	because company read only data
	if(role === "role_company"){
		$("#btnAdd").hide();
		$(".btn-delete").attr("disabled", true);
		$(".btn-update").attr("disabled", true);
	}
	
	/*hide and show message */
	$("#messageVerifyDelete").hide();
	var msgSuccess =  $("#messageSuccess").html();
	
	if(msgSuccess === ""){
		$("#divSuccess").hide();
	} else {
		$("#divSuccess").fadeIn(3000, function(){
			$(this).fadeOut(2000);
		});
	}
	
	var msgFailure = $("#messageFailure").html();
	
	if(msgFailure === ""){
		$("#divFailure").hide();
	} else {
		$("#divFailure").fadeIn(3000, function(){
			$(this).fadeOut(2000);
		});
	}
	
	/*Button add event*/
	$("#btnAdd").on("click", function(){
		
		event.preventDefault();
		
		// set title for modal
		$(".modal-title").html("Thêm kho");
		$("#btnVerify").html("Thêm");
		
		$("#warehouseForm").show();
		$("#messageVerifyDelete").hide();
		$("#warehouseModal").modal("show");
	});
	
	/*update button event */	
	
	$("table tbody tr td #btnUpdate").on("click", function(){
		
		event.preventDefault();
		
		warehouseID = $(this).val();
		
		var warehouseName = $(this).closest("tr").find("td").eq(1).html();
		var warehouseAddress = $(this).closest("tr").find("td").eq(2).html();
		var warehouseBranch = $(this).closest("tr").find("td").eq(3).html();
		
		$("#name").val(warehouseName);
		$("#address").val(warehouseAddress);
		$("#branch").val(warehouseBranch);
		
		$(".modal-title").html("Cập nhật thông tin kho");
		$("#btnVerify").html("Cập nhật")
		
		$("#warehouseForm").show();
		$("#messageVerifyDelete").hide();
		$("#warehouseModal").modal("show");
	});
	
	// button delete event
	
	$("table tbody tr td #btnDelete").on("click", function(){
		
		event.preventDefault();
		
		warehouseID = $(this).val();
		var warehouseName = $(this).closest("tr").find("td").eq(1).html();
		
		$(".modal-title").html("Xác nhận xóa thông tin kho");
		$("#btnVerify").html("Xóa");
		
		$("#warehouseForm").hide();
		
		$("#messageVerifyDelete").html("Bạn có muốn xóa "+ warehouseName);
		
		$("#messageVerifyDelete").show();
		$("#warehouseModal").modal("show");
		
	});
	
	
	/*button verify event*/
	
	$("#btnVerify").on("click", function(){
		
		event.preventDefault();
		
		var btn = $(this).html();
		if(btn === "Thêm"){
			
			$("#warehouseForm").attr("action", "warehouse?action=add");
			
		} else if(btn === "Cập nhật"){
			
			$("#warehouseForm").attr("action", "warehouse?action=update&id="+warehouseID);
			
		} else if(btn === "Xóa"){
			
			$("#warehouseForm").attr("action", "warehouse?action=delete&id="+warehouseID);
			
		}
		
		$("#warehouseForm").submit();
	});
	
	
	
});