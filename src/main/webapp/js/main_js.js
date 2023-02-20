
 let list_genre = ['Боевик', 'Биография', 'Вестерн', 'Военный', 'Детектив', 'Документальный',
					'Драма', 'Катастрофы', 'Комедия', 'Криминал', 'Мелодрама', 'Мистика', 'Мюзикл', 'Приключения',
					'Семейный', 'Спорт', 'Триллер', 'Ужасы', 'Фантастика', 'Фэнтези'];
let l_genre = ['action', 'biography', 'western', 'military', 'detective', 'documentary',
					'drama', 'catastrophes', 'comedy', 'crime', 'melodrama', 'mystic', 'musical', 'adventures',
					'family', 'sport', 'thriller', 'horror', 'fiction', 'fantasy'];
					
				
var list_genre2 = document.getElementById('genre');
for (var i = 0; i < list_genre.length; i++) {
	var div = document.createElement('a');
	div.href = 'films?type=' + l_genre[i];
	div.innerHTML = list_genre[i];
	list_genre2.appendChild(div);
}

var list_year = document.getElementById('year');
for (var i = 1930; i <= 2022; i++) {
	var opt = document.createElement('option');
	opt.value = i;
	opt.innerHTML = i;
	opt.className = "yearrr";
	list_year.appendChild(opt);
}

function fun_list_genre() {
	list_genre2.style.display = 'grid';
	let a_genre = document.getElementById('a_genre');
    a_genre.style.backgroundColor = 'rgba(222, 187, 0, 0.36)';
}

function fun_list_genre1() {
	list_genre2.style.display = 'none';
	let a_genre = document.getElementById('a_genre');
	a_genre.style.backgroundColor = 'black';
}


