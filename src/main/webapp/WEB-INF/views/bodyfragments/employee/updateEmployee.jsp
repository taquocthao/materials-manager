<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page isELIgnored="false"%>

<style>
p {
	color: red;
}
</style>

<div>
	<form:form action="employee?action=update" method="post"
		modelAttribute="employee">

		<div class="form-group hide">
			<form:input path="id" class="form-control" />
		</div>
		<div class="form-group">
			<label for="firstname">First name: </label>
			<form:input path="firstname" id="firstname" class="form-control" />
			<p>
				<form:errors path="firstname"></form:errors>
			</p>
		</div>

		<div class="form-group">
			<label for="lastname">Last name: </label>
			<form:input path="lastname" id="lastname" class="form-control" />
			<p>
				<form:errors path="lastname"></form:errors>
			</p>
		</div>

		<div class="form-group">
			<label for="address">Address: </label>
			<form:textarea path="address" class="form-control" id="address" />
			<p>
				<form:errors path="address"></form:errors>
			</p>
		</div>

		<div class="form-group">
			<label for="birthday">Birthday:</label>
			<form:input path="birthday" id="birthday" class="form-control" />
			<p>
				<form:errors path="birthday"></form:errors>
			</p>
		</div>

		<div class="form-group">
			<label for="salary">Salary:</label>
			<form:input type="number" path="salary" id="salary"
				class="form-control" />
			<p>
				<form:errors path="salary"></form:errors>
			</p>
		</div>

		<div class="form-group">
			<label>Branch: </label>
			<form:select path="branch_id" items="${branchs }" itemValue="id"
				itemLabel="branch">
			</form:select>
			<p>
				<form:errors path="branch_id"></form:errors>
			</p>
		</div>

		<div>
			<button class="btn btn-success" type="submit">Update</button>
		</div>
	</form:form>

	<!-- end form -->
</div>
