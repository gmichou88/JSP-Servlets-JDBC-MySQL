<!DOCTYPE htm>
<html>
<head>
	<title>Update Student</title>
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/add-student-style.css">	
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h1>UOC</h1>
		</div>
	</div>
	<div id="container">
		<h2>Update Student</h2>
		
		<form action="StudentControllerServlet" method="POST">
		
			<input type="hidden" name="command" value="UPDATE">
			<input type="hidden" name="id" value="${student.id}">
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" value="${student.firstName}"></td>
					</tr>
					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" value="${student.lastName}"></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" value="${student.email}"></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Submit" class="save"></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div style="clear:both;">
			<p>
				<a href="StudentControllerServlet">Back to List</a>
			</p>
		</div>
	</div>
	<div id="footer">
	
	</div>
</body>

</html>
