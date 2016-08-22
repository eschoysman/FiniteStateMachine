<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagine di prova</title>
<style type="text/css">
table,tr,td {
	border: 1px solid;
	border-collapse: collapse;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#form").on("submit",function() {
// 		$("#success").hide();
		var persona = {};
		persona.nome = $("#nome").val();
		persona.cognome = $("#cognome").val();
		persona.age = $("#age").val();
		persona.address = {};
		persona.address.address = $("#address").val();
		persona.address.city = $("#city").val();
		$.ajax({
			  url:"person/gestione",
			  type:"POST",
			  data:JSON.stringify(persona),
			  contentType:"application/json; charset=utf-8",
			}).success(function(data) {
				$("#form").reset();
			});
		return false;
	});
});
</script>
</head>
<body>
<p style="font-size: 2em; font-weight: bold; font-variant: small-caps;">Iscriviti:</p>
<form action="#" id="form" method="post">
<table style="width: 400px; ">
	<tr><td>Nome</td>		<td><input type="text" name="nome" id="nome"></td></tr>
	<tr><td>Cognome</td>	<td><input type="text" name="cognome" id="cognome"></td></tr>
	<tr><td>Età</td>		<td><input type="text" name="age" id="age"></td></tr>
	<tr><td>Indirizzo</td>	<td><input type="text" name="address" id="address"></td></tr>
	<tr><td>Città</td>		<td><input type="text" name="city" id="city"></td></tr>
	<tr><td colspan="2" style="text-align: center;"><input type="submit" value="Submit" style="width: 50%"></td></tr>
</table>
</form>
<p>get lista persone: <a href="person/gestione">JSON</a>/<a href="person/gestione/xml">XML</a></p>
</body>
</html>
