<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href= "./css/site.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<div id="accountInfo">
		<h2>Account Information</h2>
		<div>
			<form method="POST" action="j_spring_security_check">
				<fieldset class="textbox">
					<table>
						<tr>
							<td>Username:
							<td>
							<td><input type="text" size="33" name='j_username' id="j_username" />
							<td>
						<tr>
						<tr>
							<td>Password:
							<td>
							<td><input type="password" size="14" name='j_password' id="j_password" />
							<td>
						<tr>
						<tr>
							<td><input type="submit" accesskey="l" id="login_btn"
								name="login" value="Login" />
						<tr>
							<td>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>