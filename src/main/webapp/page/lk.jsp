<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style_index.css">
	<link rel="stylesheet" href="css/lk_style.css">
	<title>${title}</title>
	<style>
	.img_background {
	background-image: url('img/image22.jpg');
	height: 100px;
	}
	</style>
</head>

<body>

	<div class="main_container">
		<!-- шапка -->
		<jsp:include page="header.jsp" />

		<h2 id="title_str">${title}</h2>
		
		<!-- Мой Профиль -->
		<main class="user_profile_lk" id="user_profile_lk">
			<form action="lk_user" method="post" class="edit_unfo_user" onsubmit="return validate_form_user();">
				<input type="text" name="username" id="username" placeholder="Имя" value="${name_user}">
				<input type="email" name="useremail" id="useremail" placeholder="Email" value="${email_user}">
				<input type="password" name="userpass" id="userpass" placeholder="Пароль" value="${password}">
				
				<input type="submit" value="Сохранить" class="save">
				<span id="mes_edit">${mes_edit}</span>
			</form>
		</main>
		
		<script>
				function validate_form_user(){
					let valid = true;
					let username = document.getElementById("username");
					let userpass = document.getElementById("userpass");
					let useremail = document.getElementById("useremail");
					if(username.value == "" || userpass.value == "" || useremail.value == ""){
						document.getElementById("mes_edit").innerHTML = "Пожалуйста, заполните все поля";
						valid = false;
					}
					return valid;
				}
			</script>
		
		
		
		<!-- Мои Закладки -->
		<main class="user_film_lk" id="user_film_lk">
			<!-- *************** СПИСОК ФИЛЬМОВ ************************* -->
				<div class="list_film" id="list_films">
					<!-- карточка фильма -->
					<c:forEach var="list_film" items="${list_film}">
					<div class="card_film" onclick="str_film(${list_film.id})">
						<div class="box">
							<img src="img/film/${list_film.img_cover}" alt="">
						</div>
						<div class="box b_text">
							<p style="font-size: 1.2em;" title="${list_film.title}">${list_film.title}</p>
						</div>
						<div class="box b_text">
							<p>${list_film.yearr}, <c:forEach var="genre" items="${list_film.genre}"> ${genre}</c:forEach></p>
						</div>
						<div class="box rating">
							<p>+${list_film.rating}</p>
						</div>
					</div>
					</c:forEach>
				</div>
				<!-- *************** СПИСОК ФИЛЬМОВ ************************* -->
				
				<c:set var="count" value="${Math.ceil(count_film/20)}" />
				<div class="but_list_film">
				<c:forEach begin="1" end="${count}" varStatus="loop">
				<a href="${name_str}&countf=${loop.index}" id="but${loop.index}">${loop.index}</a>
				</c:forEach>
				</div>
				
				<c:if test="${mess != null}">
				<p class="mess_f imess">${mess}</p>
				</c:if>
				
				<script>
				let s = window.location.href;
				let name_str = s.split('=').pop();
				console.log(name_str);
				let but = document.getElementById('but'+name_str);
				but.style.backgroundColor = "#DE9300";
				
				function str_film(id){
					window.location = "str_film?id_f=" + id;	
				}
				</script>
		</main>
		
		
		<!-- Мои Комментарии -->
		<main class="user_comment_lk" id="user_comment_lk">
			<div class="list_comment_lk">
			<c:forEach var="comment_user" items="${comment_user}">
				<div class="com_lk">
					<p onclick="str_film(${comment_user.id_film})">${comment_user.title_film}</p>
					<div class="comm3">
						<div class="icon"></div>
						<div class="text_comment">
							<div class="info_comment">
								<p>${comment_user.name_user}</p>
								<p>${comment_user.time_comment}</p>
							</div>
							<p>${comment_user.text_comment}</p>
						</div>
					</div>
				</div>
			</c:forEach>
			</div>

			<script>
			let icon = document.getElementsByClassName('icon');
			for (var i = 0; i < icon.length; i++) {
				icon[i].style.backgroundColor = 'rgb(' + (Math.floor((156-229)*Math.random()) + 230) + ',' + 
                (Math.floor((156-229)*Math.random()) + 230) + ',' + 
                (Math.floor((156-229)*Math.random()) + 230) + ')';
			}
			function str_film(id){
				window.location = "str_film?id_f=" + id;	
			}
			</script>
		</main>
		
		
		<!-- Настройки для админа -->
		<main class="settings_lk" id="settings_lk">
		</main>





		<script>
		let title_str = document.getElementById('title_str').textContent;
		if(title_str == "Мои комментарии"){
			document.getElementById('user_comment_lk').style.display = "block";
			document.getElementById('user_film_lk').style.display = "none";
			document.getElementById('user_profile_lk').style.display = "none";
			document.getElementById('settings_lk').style.display = "none";
		}else if(title_str == "Мои закладки"){
			document.getElementById('user_comment_lk').style.display = "none";
			document.getElementById('user_film_lk').style.display = "block";
			document.getElementById('user_profile_lk').style.display = "none";
			document.getElementById('settings_lk').style.display = "none";
			console.log("${count_film}");
		}else if(title_str == "Мой профиль"){
			document.getElementById('user_comment_lk').style.display = "none";
			document.getElementById('user_film_lk').style.display = "none";
			document.getElementById('user_profile_lk').style.display = "block";
			document.getElementById('settings_lk').style.display = "none";
		}else if(title_str == "Настройки"){
			document.getElementById('user_comment_lk').style.display = "none";
			document.getElementById('user_film_lk').style.display = "none";
			document.getElementById('user_profile_lk').style.display = "none";
			document.getElementById('settings_lk').style.display = "block";
		}
		</script>

		<div class="footer_img">
		</div>

	</div>
	
</body>
</html>