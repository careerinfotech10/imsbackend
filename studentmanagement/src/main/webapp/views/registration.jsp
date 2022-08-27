<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>
	$(document).ready(function() {
		$("#userEmail").blur(function() {
			$("#errMsg").text("");
			$.ajax({
				type : "GET",
				url : "uniqueMailCheck?email="+$("#userEmail").val(),
				success : function(data) {
					if(data=="DUPLICATE"){
						$("#errMsg").text("Duplicate Email");
						$("#submitBtn").prop("disabled", true);
					}else{
						$("#submitBtn").prop("disabled", false);
					}
				}
			});

		});
		
		$("#countryId").change(function(){
			$('#stateId').find('option:not(:first)').remove();
			$('#cityId').find('option:not(:first)').remove();
			$.ajax({
				type : "GET",
				url : "countryChange?countryId="+$("#countryId").val(),
				success : function(data) {
					$.each(data, function(stateId, stateName) {
			            $('#stateId').append($('<option>').text(stateName).attr('value', stateId));
			        });
				}
			});
		});
		
		$("#stateId").change(function(){
			$('#cityId').find('option:not(:first)').remove();
			$.ajax({
				type : "GET",
				url : "stateChange?stateId="+$("#stateId").val(),
				success : function(data) {
					$.each(data, function(cityId, cityName) {
			            $('#cityId').append($('<option>').text(cityName).attr('value', cityId));
			        });
				}
			});
		});
	});
</script>

</head>
<body>

	<div align="center">
		<h3>Register Here</h3>

		<font color='red'>${failMsg}</font> <font color='green'>${succMsg}</font>

		<form:form action="userRegistration" method="POST"
			modelAttribute="userAcc">

			<table>
				<tr>
					<td>First Name:</td>
					<td><form:input path="firstName" /></td>
				</tr>

				<tr>
					<td>Last Name:</td>
					<td><form:input path="userLastName" /></td>
				</tr>


				<tr>
					<td>Email:</td>
					<td><form:input path="userEmail" id="userEmail" /> 
						<font color='red'><span id="errMsg"></span></font>
					</td>
				</tr>


				<tr>
					<td>Phone Number:</td>
					<td><form:input path="userPhonenumber" /></td>
				</tr>


				<tr>
					<td>DOB:</td>
					<td><form:input path="dateOfBirth" /></td>
				</tr>


				<tr>
					<td>Gender:</td>
					<td><form:radiobutton path="gender" value="M" /> Male <form:radiobutton
							path="gender" value="F" /> Fe-Male</td>
				</tr>


				<tr>
					<td>Select Country:</td>
					<td><form:select path="countryId" id="countryId">
							<form:option value="">-Select-</form:option>
							<form:options items="${countries}" />
						</form:select></td>
				</tr>


				<tr>
					<td>Select State:</td>
					<td><form:select path="stateId" id="stateId">
							<form:option value="">-Select-</form:option>
						</form:select></td>
				</tr>


				<tr>
					<td>Select City:</td>
					<td><form:select path="cityId" id="cityId">
							<form:option value="">-Select-</form:option>
						</form:select></td>
				</tr>

				<tr>
					<td><input type="reset" value="Reset" /></td>
					<td><input type="submit" value="Submit" id="submitBtn"/></td>
				</tr>

			</table>

		</form:form>


	</div>

</body>
</html>