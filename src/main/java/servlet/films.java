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

@WebServlet("/films")
public class films extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	bd.bd connection = new bd.bd();
	Connection con = null;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        list_film list_film = null;
        ArrayList<list_film> list_film2 = new ArrayList<list_film>();
        ArrayList<list_film> list_film3 = new ArrayList<list_film>();
        PreparedStatement tf;
        
        Map<String,String> type_f = new HashMap<String,String>();
        type_f.put("action", "Боевик");
        type_f.put("biography", "Биография");
        type_f.put("western", "Вестерн");
        type_f.put("military", "Военный");
        type_f.put("detective", "Детектив");
        type_f.put("documentary", "Документальный");
        type_f.put("drama", "Драма");
        type_f.put("catastrophes", "Катастрофы");
        type_f.put("comedy", "Комедия");
        type_f.put("crime", "Криминал");
        type_f.put("melodrama", "Мелодрама");
        type_f.put("mystic", "Мистика");
        type_f.put("musical", "Мюзикл");
        type_f.put("adventures", "Приключения");
		type_f.put("family", "Семейный");
		type_f.put("sport", "Спорт");
		type_f.put("thriller", "Триллер");
		type_f.put("horror", "Ужасы");
		type_f.put("fiction", "Фантастика");
		type_f.put("fantasy", "Фэнтези");
		type_f.put("f", "фильм");
		type_f.put("s", "сериал");
		type_f.put("m", "мультфильм");
		
        
        String type = request.getParameter("type");
        String typee = type_f.get(type);
//        String typee = (type.equals("f")) ? "фильм" : ((type.equals("s")) ? "сериал" : "мультфильм");
        String name_head = (type.equals("f")) ? "ФИЛЬМЫ" : ((type.equals("s")) ? "СЕРИАЛЫ" : ((type.equals("m")) ? "МУЛЬТФИЛЬМЫ" : ""));
    
        try {
            con = connection.getConnection();
            
            Statement stmt = con.createStatement();
            if(typee.equals("фильм") || typee.equals("сериал") || typee.equals("мультфильм")) {
            	tf = con.prepareStatement("SELECT DISTINCT id, title, year, img_cover, rating FROM all_info_film WHERE type=?");
            }else {
            	tf = con.prepareStatement("SELECT DISTINCT id, title, year, img_cover, rating FROM all_info_film WHERE genre=?");
            }
            tf.setString(1, typee);
            ResultSet rs = tf.executeQuery();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT genre FROM all_info_film WHERE id=?");
            
            
         // Извлечь данные из набора результатов
            while(rs.next()){
            	int id = rs.getInt("id");
                String title = rs.getString("title");
                String yearr = rs.getString("year");
                String img_cover = rs.getString("img_cover");
                int rating = rs.getInt("rating");
                
                List<String> genre = new ArrayList();
                preparedStatement.setInt(1, id);
                ResultSet gs = preparedStatement.executeQuery();
                while(gs.next()) { genre.add(gs.getString("genre")); }
                
                list_film = new list_film(id, title, yearr, img_cover, rating, genre);
                list_film2.add(list_film);
                }
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        String countf = request.getParameter("countf");
        int count_film = (countf == null) ? 1 : Integer.parseInt(countf);
        int start = 20*(count_film-1);
        int end = (list_film2.size() >= 20*count_film) ? (start+20) : list_film2.size();
        
        for(int i = start; i < end; i++) {
            list_film3.add(list_film2.get(i));
        }
        
        
        if(!typee.equals("фильм") && !typee.equals("сериал") && !typee.equals("мультфильм")) {
        	request.setAttribute("genre", typee);
        }
        
//        out.println(countf + " " + start + " " + end);
    	request.setAttribute("count_film", list_film2.size());
    	request.setAttribute("list_film", list_film3);
    	request.setAttribute("title", "kino_vo!");
    	request.setAttribute("name_str", "films?type=" + type);
    	request.setAttribute("name_head", name_head);
    	request.setAttribute("type", type);
    	
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
        
        request.getRequestDispatcher("page/films.jsp").forward(request, response);
	}
	
	

}
