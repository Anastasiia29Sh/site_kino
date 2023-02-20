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
</head>

<body>


	<div class="main_container">
		<!-- шапка -->
		<jsp:include page="header.jsp" />


		<!-- список фильмов -->
		<main>
			<h2>ГЛАВНАЯ</h2>
			
			<form action="film_search" method="post">
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
			<a href="${name_str}?countf=${loop.index}" id="but${loop.index}">${loop.index}</a>
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


		<div class="about_text">
			<h3>Лучшие новинки кино бесплатно в хорошем HD 720 качестве, смотрите онлайн на КИНО <span
					class="logo_super">ВО!</span></h3>
			<p>
				Ежегодно кинематографисты всех стран выпускают сотни фильмов. Некоторые произведения ждёт прокат в
				кинотеатрах, огромные
				кассовые сборы или коммерческое фиаско. Посещать показ каждого «свежего» фильма практически нереально.
				Ведь помимо
				финансов необходимо найти время для культпохода, что значительно сложнее. Идеальным выходом становится
				наш онлайн
				кинотеатр, с постоянно пополняемой фильмотекой. Приглашаем познакомиться с самой актуальной коллекцией
				новинок кино
				бесплатно. Теперь для этого не надо регистрироваться и отправлять всякие СМС. Просто выбирайте фильм по
				настроению, и
				наслаждайтесь просмотром в удобное именно для вас время. Особое внимание наш коллектив уделяет
				своевременности
				добавления фильмов, высокому качеству видео и звука представленных новинок. Ведь ни для кого не секрет,
				что кино,
				например, фантастического жанра состоит в основном их зрелищных спецэффектов и уникальной компьютерной
				графики. Оценить
				усилия съёмочной группы в «пиратском» варианте просто невозможно! В нашей фильмотеке благодаря удобному
				интерфейсу сайта
				вы без труда сориентируетесь в многообразии новинок. Запутанные детективные истории, леденящие кровь
				фильмы ужасов,
				загадочные триллеры, мистические приключения ожидают своих поклонников со стальными нервами.
			</p>
			<p>
				Для желающих познакомиться с историческими событиями в трактовке кинематографа, мы пополняем коллекцию
				масштабными
				постановками о важнейших вехах развития человечества и биографиями самых значимых персонами. Любителям от
				души
				посмеяться придутся по вкусу новинки в жанре кинокомедии: от молодёжных и прямолинейных до саркастических
				пародий.
				Трогательные мелодрамы с мировыми звёздами в главных ролях, а также с начинающими актёрами станут хорошим
				выбором для
				романтичных зрителей. Некоторые выходящие фильмы не предназначены к прокату. И если бы не интернет, могли
				остаться
				достоянием лишь узкого круга эстетов. Предлагаем совершить знакомство с удивительным арт-хаусом и
				экспериментальными
				картинами. Возможно, вас ждут поразительные открытия! Отдохнуть от повседневности и ощутить магию
				кинематографа легко и
				просто. Самостоятельно назначайте время сеанса, собирайтесь с друзьями, семьёй, или смотрите в одиночку
				лучшие новинки
				кино. Помните, что коллекция постоянно обновляется, и заглядывайте к нам регулярно. Приятного просмотра и
				отличного вам
				настроения!
			</p>
			<p class="text_email">По вопросам сотрудничества обращайтесь по адресу kino.ep.net@gmail.com</p>
		</div>




		<div class="footer_img">
		</div>

	</div>

</body>

</html>