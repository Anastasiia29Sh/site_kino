<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style_index.css">
	<link rel="stylesheet" href="css/registration_style.css">
	<title>${title}</title>
	<style>
	.img_background {
	background-image: url('img/image22.jpg');
	height: 100px;
	}
	</style>
</head>

<body>

<script>
if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}
</script>

	<div class="main_container">
		<!-- шапка -->
		<jsp:include page="header.jsp" />
		
		
		<main class="registr" id="registr">
			<h3>Регистрация</h3>
			<p>Здравствуйте, уважаемый посетитель нашего сайта!</p>
			<form action="registration" method="post" class="registration_user" onsubmit="return validate_form3();">
				<table>
					<tr>
						<td>Логин:*</td>
						<td><input type="text" name="username" id="username"></td>
					</tr>
					<tr>
						<td>Пароль:* <br>(возможны цифры, буквы <br>и спец.символы: _ * # <br> без пробелов)</td>
						<td><input type="password" name="userpass" id="userpass"></td>
					</tr>
					<tr>
						<td>Повторите пароль:*</td>
						<td><input type="password" name="userpass1" id="userpass1"></td>
					</tr>
					<tr>
						<td>Ваш E-Mail:*</td>
						<td><input type="email" name="useremail" id="useremail"></td>
					</tr>
					
				</table>
				<input type="submit" value="Отправить" class="go_reg">
				<span id="mes_reg">${mes_reg}</span>
			</form>
			
			<script>
				function validate_form3(){
					let valid = true;
					let username = document.getElementById("username");
					let userpass = document.getElementById("userpass");
					let userpass1 = document.getElementById("userpass1");
					let useremail = document.getElementById("useremail");
					if(username.value == "" || userpass.value == "" || userpass1.value == "" || useremail.value == ""){
						document.getElementById("mes_reg").innerHTML = "Пожалуйста, заполните все поля";
						valid = false;
					}
					else if(userpass.value != userpass1.value){
						document.getElementById("mes_reg").innerHTML = "Пароль и подтверждение пароля не совпадают";
						valid = false;
					}
					else if(userpass.value.match(/[\s\n.,?\/!$%\^&\;:{}=\-`~()]/)){
						document.getElementById("mes_reg").innerHTML = "Пароль содержит недопустимые символы";
						valid = false;
					}
					return valid;
				}
			</script>
			
		</main>
		<div id="success">
		<h4>Поздравляю, Вы успешно зарегистрировались!</h4>
		<a onclick="log_in()">Войти</a>
		</div>
		<script>
				<c:if test="${username != null}">
				document.getElementById('username').value = "${username}";
				</c:if>
				<c:if test="${useremail != null}">
				document.getElementById('useremail').value = "${useremail}";
				</c:if>		
				
				<c:if test="${success != null}">
				document.getElementById('registr').style.display = 'none';
				document.getElementById('success').style.display = 'block';
				</c:if>
				
		</script>
		
		
		<div class="footer_img"></div>

	</div>

</body>
</html>