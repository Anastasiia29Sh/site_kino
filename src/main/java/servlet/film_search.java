package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

import classs.list_film;


@WebServlet("/film_search")
public class film_search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	bd.bd connection = new bd.bd();
	Connection con = null;
	
	ArrayList<list_film> list_film2;
    ArrayList<list_film> list_film3;
    
    String genre;
	String country;
	String year;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        String countf = request.getParameter("countf");
        int count_film = (countf == null) ? 1 : Integer.parseInt(countf);
        int start = 20*(count_film-1);
        int end = (list_film2.size() >= 20*count_film) ? (start+20) : list_film2.size();
        
        list_film3 = new ArrayList<list_film>();
        
        for(int i = start; i < end; i++) {
            list_film3.add(list_film2.get(i));
        }
        
//        out.println(list_film3.size() + " " + countf + " " + start + " " + end);
        request.setAttribute("count_film", list_film2.size());
    	request.setAttribute("list_film", list_film3);
    	request.setAttribute("title", "kino_vo!");
    	
    	request.setAttribute("genre", genre);
    	request.setAttribute("country", country);
    	request.setAttribute("year", year);
    	
    	HttpSession session = request.getSession();
        request.setAttribute("mess_login", (String) session.getAttribute("mass_login"));
    	
    	if(list_film3.size() == 0) request.setAttribute("mess", "По Вашему запросу ничего не найдено");
    	
    	String cookieName = "name_user";
        String cookieName2 = "status_user";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) 
        {
            for(int i=0; i<cookies.length; i++) 
            {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) 
                {
                	request.setAttribute("name_user", cookie.getValue());
                }
                if (cookieName2.equals(cookie.getName())) 
                {
                	request.setAttribute("status_user", cookie.getValue());
                }
            }
        }
        
        request.getRequestDispatcher("page/index.jsp").forward(request, response);
        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		
		Map<String,String> eng_rus_genre = new HashMap<String,String>();
		eng_rus_genre.put("action", "Боевик");
		eng_rus_genre.put("biography", "Биография");
		eng_rus_genre.put("western", "Вестерн");
		eng_rus_genre.put("military", "Военный");
		eng_rus_genre.put("detective", "Детектив");
		eng_rus_genre.put("documentary", "Документальный");
		eng_rus_genre.put("drama", "Драма");
		eng_rus_genre.put("catastrophes", "Катастрофы");
		eng_rus_genre.put("comedy", "Комедия");
		eng_rus_genre.put("crime", "Криминал");
		eng_rus_genre.put("melodrama", "Мелодрама");
		eng_rus_genre.put("mystic", "Мистика");
		eng_rus_genre.put("musical", "Мюзикл");
		eng_rus_genre.put("adventures", "Приключения");
		eng_rus_genre.put("family", "Семейный");
		eng_rus_genre.put("sport", "Спорт");
		eng_rus_genre.put("thriller", "Триллер");
		eng_rus_genre.put("horror", "Ужасы");
		eng_rus_genre.put("fiction", "Фантастика");
		eng_rus_genre.put("fantasy", "Фэнтези");
		
		Map<String,String> eng_rus_country = new HashMap<String,String>();
		eng_rus_country.put("Russia", "Россия");
		eng_rus_country.put("GreatBritain", "Великобритания");
		eng_rus_country.put("Germany", "Германия");
		eng_rus_country.put("Spain", "Испания");
		eng_rus_country.put("Italy", "Италия");
		eng_rus_country.put("India", "Индия");
		eng_rus_country.put("Canada", "Канада");
		eng_rus_country.put("China", "Китай");
		eng_rus_country.put("USSR", "СССР");
		eng_rus_country.put("USA", "США");
		eng_rus_country.put("France", "Франция");
		eng_rus_country.put("SouthKorea", "Южная Корея");
		eng_rus_country.put("Japan", "Япония");
		
		genre = request.getParameter("genre");
		country = request.getParameter("country");
		year = request.getParameter("year");
		
		genre = eng_rus_genre.get(genre);
		country = eng_rus_country.get(country);
		
		list_film list_film = null;
		list_film2 = new ArrayList<list_film>();
	    list_film3 = new ArrayList<list_film>();
		
		try {
            con = connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            
            year = year != null && (year.equals("all")) ? null : year;
            
            if(genre == null && country == null && year == null) {
            	rs = stmt.executeQuery("SELECT id, title, year, img_cover, rating FROM film");
            	genre = null;
            	country = null;
            	year = null;
    		}else {
    			PreparedStatement pS = con.prepareStatement("CALL film_search(?, ?, ?)");
    			pS.setString(1, genre);
    			pS.setString(2, country);
    			pS.setString(3, year);
    			rs = pS.executeQuery();
    		}
            PreparedStatement preparedStatement = con.prepareStatement("SELECT genre FROM all_info_film WHERE id=?");

            while(rs.next()){
            	int id = rs.getInt("id");
                String title = rs.getString("title");
                String yearr = rs.getString("year");
                String img_cover = rs.getString("img_cover");
                int rating = rs.getInt("rating");
                
                List<String> genree = new ArrayList();
                preparedStatement.setInt(1, id);
                ResultSet gs = preparedStatement.executeQuery();
                while(gs.next()) { genree.add(gs.getString("genre")); }
                
                list_film = new list_film(id, title, yearr, img_cover, rating, genree);
                list_film2.add(list_film);
                }
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
		
    	
    	request.setAttribute("name_str", "film_search");
    	HttpSession session = request.getSession();
        request.setAttribute("mess_login", (String) session.getAttribute("mass_login"));
        
        String cookieName = "name_user";
        String cookieName2 = "status_user";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) 
        {
            for(int i=0; i<cookies.length; i++) 
            {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) 
                {
                	request.setAttribute("name_user", cookie.getValue());
                }
                if (cookieName2.equals(cookie.getName())) 
                {
                	request.setAttribute("status_user", cookie.getValue());
                }
            }
        }
		
		doGet(request, response);
	}

}
