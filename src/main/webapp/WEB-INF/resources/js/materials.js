$(document).ready(function(){
	
//	global variable
	var materialID;
	var msgSuccess = $("#messageSuccess").html();
	var msgFailure = $("#messageFailure").html();
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
	
	//show modal verify delete
	$("table tbody tr td button.btn-delete").on("click", function(){
		event.preventDefault();
		var materialName = $(this).closest("tr").find("td").eq(1).html();
		$("#labelName").html(materialName);
		materialID = $(this).val();
		$("#verifyModal").modal("show");
	});
		
	$("#btnVerifyDelete").on("click", function(){
		var urlDelete = $(this).attr("href", "material/" + materialID + "?action=delete");
		window.location = $(urlDelete).attr("href");
	});
	
//	message success
	if(msgSuccess === ""){ 
		$("#divSuccess").hide();
	} else { 
		$("#divSuccess").fadeIn(3000, function(){
			$(this).fadeOut(2000);
		});
	}
	
//	message failure
	if(msgFailure === ""){
		$("#divFailure").hide();
	} else {
		$("#divFailure").fadeIn(3000, function(){
			$(this).fadeOut(2000);
		});
	}
	
// 	button add event - show modal 
	$("#btnAdd").on("click", function(event){
		event.preventDefault();
		
		$(".modal-title").html("Thêm vật tư"); // set title for modal add
		$("#btnVerify").html("Thêm");
		$("#materialModal").modal("show");
		
	});
	
//	set form action for add material		
	$("#btnVerify").click(function(event){
		
		event.preventDefault();
		
		var btn = $("#btnVerify").html();
		
		if(btn === "Thêm"){

			$("#materialForm").attr("action", "material?action=add");
		} else {
			
			$("#materialForm").attr("action", "material?action=update&id=" + materialID);
		}
		
		$("#materialForm").submit();
	});
	
//	button update event - show modal
	$("table tbody tr td .btn-update").on("click", function(){
		event.preventDefault();
		
		materialID = $(this).val(); 
		//get name, unit value in table
		var materialName = $(this).closest("tr").find("td").eq(1).html();
		var materialUnit = $(this).closest("tr").find("td").eq(2).html();
		
		// set name, unit value for input
		$("#name").val(materialName);
		$("#unit").val(materialUnit);
		
		// set title for modal update
		$(".modal-title").html("Cập nhật vật tư"); 
		$("#btnVerify").html("Cập nhật");
		$("#materialModal").modal("show");
	});		
});