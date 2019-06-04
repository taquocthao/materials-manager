$(document).ready(function(){
	
//	global variable
	var employeeId;
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
	
//  <!-- jquery search employee -->
	$("#search").on("keyup",function() {
		var value = $(this).val().toLowerCase();
		$("#tableEmployee tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});

	// show modal verify delete employee
	$("table tbody tr td button.btn-delete").on("click", function() {
		event.preventDefault();
		employeeId = $(this).attr("value");
		var firstname = $(this).closest("tr").find("td").eq(1).html();
		var lastname = $(this).closest("tr").find("td").eq(2).html();
		$("#labelName").html(lastname + " " + firstname);
		$("#verifyModal").modal("show");
	});

	// verify delete employee
	$("#btnVerifyDelete").on("click",function() {
		event.preventDefault();

		$("#verifyModal").modal("hide");
		var urlDelete = $(this).attr("href","employee/" + employeeId + "?action=delete");
		window.location = $(urlDelete).attr("href");
	});

	// get alert message
	if (msgSuccess === "") {
		$("#divSuccess").hide();
	} else {
		$("#divSuccess").fadeIn(3000, function() {
			$(this).fadeOut(2000, function() {
				msgSuccess = "";
			});
		});
	}

	if (msgFailure === "") {
		$("#divFailure").hide();
	} else {
		$("#divFailure").fadeIn(3000, function() {
			$(this).fadeOut(2000);
		});
	}
	
});