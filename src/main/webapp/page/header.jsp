
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
 
       <header>
			<div class="img_background"></div>
			<!-- меню -->
			<nav>
				<div class="logo">
					<a href="kino_vo">КИНО<span class="logo_super">ВО!</span></a>
				</div>
				<ul>
					<li><a href="films?type=f">ФИЛЬМЫ</a></li>
					<li><a href="films?type=s">СЕРИАЛЫ</a></li>
					<li><a href="films?type=m">МУЛЬТИКИ</a></li>
					<li><a href="#" onmouseover="fun_list_genre()" onmouseout="fun_list_genre1()" id="a_genre">ЖАНРЫ</a></li>
				</ul>
			</nav>

			<div class="list_genre" id="genre" onmouseover="fun_list_genre()" onmouseout="fun_list_genre1()">
				
			</div>

			<div class="registrazia">
				<div class="buttoms" id="log_reg">
					<a onclick="log_in()">Войти</a>
					<a href="rules_site">Регистрация</a>
				</div>
				<div class="buttoms2" id="buttoms2">
					<a onclick="lk_user()" id="name_us"></a>
				</div>
				<form action="film_search_words" method="post">
					<input type="text" name="words" placeholder="Найдется все, попробуй!">
					<input type="submit" value="Найти" class="search_words"> 
				</form>
			</div>
			
			<div class="lk_user" id="lk_user">
				<a href="lk_user?do=p">Мой профиль</a>
				<a href="lk_user?do=z">Мои закладки</a>
				<a href="lk_user?do=c">Мои комментарии</a>
				<a href="lk_user?do=s" id="admin_settings">Настройки</a>
				<a href="authorization?exit=exit">Выйти</a>
			</div>
			
			<div class="log_in" id="log_in">
				<a onclick="log_out()">&#8855</a>
				<h3>Авторизация</h3>
				<form action="authorization" method="post" onsubmit="return validate_form1();">
					<input id="url_str" name="url_str" type="text">
					<input type="email" name="login" id="login" placeholder="Ваш логин (email)">
					<input type="password" name="password" id="password" placeholder="Ваш пароль">
					<input type="submit" value="Войти">
					<span id="mes_log">${mess_login}</span>
				</form>
			</div>
			
			<script>
		    <c:if test="${mess_login != null}">
		    document.getElementById("log_in").style.display = "block";
		    document.getElementById("mes_log").innerHTML = "${mess_login}";
			</c:if>
			
			<c:if test="${name_user != null}">
		    document.getElementById("log_in").style.display = "none";
		    document.getElementById("log_reg").style.display = "none";
		    document.getElementById("buttoms2").style.display = "block";
		    <c:if test="${status_user == 'admin'}">
		    document.getElementById("admin_settings").style.display = "block";
		    </c:if>
		    document.getElementById("name_us").innerHTML = "${name_user}";
			</c:if>
		    </script>

			<script>
			document.getElementById("url_str").value = window.location.href.split('http://localhost:8082/web_kino').pop();
				function log_in(){
					let win_div = document.getElementById("log_in");
					win_div.style.display = "block";
				}
				function log_out(){
					let win_div = document.getElementById("log_in");
					win_div.style.display = "none";
					<%session.invalidate();%>
				}
				
				function validate_form1(){
					let valid = true;
					let login = document.getElementById("login");
					let password = document.getElementById("password");
					if(login.value == "" || password.value == ""){
						document.getElementById("mes_log").innerHTML = "Пожалуйста, заполните все поля";
						valid = false;
					}
					return valid;
				}
				function lk_user(){
					let lk = document.getElementById("lk_user");
					let state = lk.style.display;
					console.log(state);
					if(state == 'none' || state == '') lk.style.display = "flex";
					else lk.style.display = "none";
				}
			</script>
			
		</header>