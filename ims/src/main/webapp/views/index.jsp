<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div align="center">

		<h3>Sign Here</h3>
		
		<font color='red'>${failMsg}</font>

		<form action="signin" method="POST">
			<table>
				<tr>
					<td>Email :</td>
					<td><input type="text" name="email" /></td>
				</tr>

				<tr>
					<td>Password:</td>
					<td><input type="password" name="pwd" /></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" value="Sign-In" /></td>
				</tr>

				<tr>
					<td><a href="forgotPwd" style="margin: 10px">Forgot Password?</a></td>
					<td><a href="register">Sign-Up</a></td>
				</tr>
			</table>
		</form>

	</div>

</body>
</html>