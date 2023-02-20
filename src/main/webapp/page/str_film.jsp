<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style_index.css">
	<link rel="stylesheet" href="css/film_style.css">
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
		<script type="text/javascript" src="js/main_js.js"></script>
		
		<main>
			<h2>${title}</h2>
			<div class="film">
				<div class="film_img">
					<img src="img/film/${img_cover}" alt="">
					<a class="add_me" id="add_me" onclick="add_me(${id})">${title_buttom}</a>
					<div id="no_add">
						<a onclick="l_out()">&#8855</a>
						<p>Необходима регистрация</p>
					</div>
					<script>
					function add_me(id){
						<c:if test="${name_user != null}">
					    document.getElementById("add_me").href = "add_film_me?id_f=" + id;
						</c:if>
						<c:if test="${name_user == null}">
					    document.getElementById("no_add").style.display = "block";
						</c:if>
					}
					function l_out(){
						document.getElementById("no_add").style.display = "none";
					}
					</script>
				</div>
				<div class="film_info">	
					<ul>
						<li><span>Год выхода: </span>${year}</li>
						<li><span>Страна: </span>${country}</li>
						<li><span>Режиссер: </span>${producer}</li>
						<li><span>Актеры: </span>${main_roles}</li>
						<li><span>Жанр: </span><c:forEach var="genree" items="${genre}"> ${genree}</c:forEach></li>
						<li><span>Рейтинг: </span>${rating}</li>
					</ul>
					
					<p class="des"><span>Описание: </span>${description}</p>
				</div>
			</div>

			<h3>Смотреть "${title}" онлайн в хорошем качестве</h3>
			<div class="video">
				<video width="100%" height="500" controls="controls">
					<source src="img/s-koshkami.mp4" type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
					<!-- <a href="#">Скачайте видео</a> -->
				</video>
			</div>


			<h3>Оставь свой отзыв</h3>
			<div class="comment">
			<c:forEach var="comm" items="${comm}">
				<div class="comm1">
					<div class="icon">
						<p>${comm.name_user.charAt(0)}</p>
					</div>
					<div class="text_comment">
						<div class="info_comment">
							<p>${comm.name_user}</p>
							<p>${comm.time_comment}</p>
						</div>
						<p>${comm.text_comment}</p>
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
			function getRandom(min, max){
				  return Math.ceil(Math.random() * (max - min) + min)
			}
			</script>
			
			<form action="str_film?id_f=${id}" method="post" class="form_comment" onsubmit="return validate_form();">
					<div class="info_user_comment">
						<input type="text" placeholder="Ваше имя" id="name_user_comment" name="name_user_comment">
						<input type="email" placeholder="Ваш email" id="email_user_comment" name="email_user_comment">	
					</div>
					<textarea id="text_comm" name="text_comment" cols="30" rows="7" placeholder="Ваш комментарий"></textarea>
					<input type="submit" value="Добавить" class="add_comment"> 
					<span id="mes_comment"></span>
			</form>
			
			
			<script>
			function validate_form(){
				let valid = true;
				let name_user_comment = document.getElementById("name_user_comment");
				let email_user_comment = document.getElementById("email_user_comment");
				let text_comm = document.getElementById("text_comm");
				if(name_user_comment.value == "" || email_user_comment.value == "" || text_comm.value == ""){
					document.getElementById("mes_comment").innerHTML = "Пожалуйста, заполните все поля";
					valid = false;
				}
				return valid;
			}
			</script>
			

		</main>
		
		<div class="footer_img"></div>
		
	</div>

</body>
</html>