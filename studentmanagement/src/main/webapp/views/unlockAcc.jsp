<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

</head>
<body>

	<div align="center">
		<h3>Unlock Account Here</h3>

		<font color='red'>${failMsg}</font> 
		<font color='green'>${succMsg}</font>

		<form:form action="unlockAccount?email=${userAcc.email}" 
				   method="POST"
					modelAttribute="userAcc">

			<table>
				<tr>
					<td>Email:</td>
					<td>${userAcc.email}</td>
				</tr>

				<tr>
					<td>Temporary Password:</td>
					<td><form:password path="tempPwd" /></td>
				</tr>


				<tr>
					<td>Choose New Password :</td>
					<td><form:password path="newPwd" /></td>
				</tr>


				<tr>
					<td>Confirm Password :</td>
					<td><form:password path="confirmPwd" /></td>
				</tr>

				<tr>
					<td><input type="reset" value="Reset" /></td>
					<td><input type="submit" value="Submit" id="Unlock Account" /></td>
				</tr>

			</table>

		</form:form>


	</div>

</body>
</html>