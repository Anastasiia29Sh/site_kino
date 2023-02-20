<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style_index.css">
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
		
		<!-- список фильмов -->
		<main>
			<h2>${name_head}</h2>
			
			<form action="film_search2?type=${type}" method="post">
		    <jsp:include page="search.jsp" />
		    </form>
		    
		    <script>
				<c:if test="${genre != null}">
				let list_g = document.getElementsByClassName('genreee');
				for (var i = 0; i < list_g.length; i++) {
					if(list_g[i].innerHTML == "${genre}") 
						list_g[i].setAttribute("selected", "selected");
				}
				</c:if>
				<c:if test="${country != null}">
				let list_c = document.getElementsByClassName('countryyy');
				for (var i = 0; i < list_c.length; i++) {
					if(list_c[i].innerHTML == "${country}") 
						list_c[i].setAttribute("selected", "selected");
				}
				</c:if>
				<c:if test="${year != null}">
				let list_y = document.getElementsByClassName('yearrr');
				for (var i = 0; i < list_y.length; i++) {
					if(list_y[i].innerHTML == "${year}") 
						list_y[i].setAttribute("selected", "selected");
				}
				</c:if>
		    </script>

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
			<p class="mess_f">${mess}</p>
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
		
		<div class="footer_img"></div>
		
	</div>

</body>
</html>