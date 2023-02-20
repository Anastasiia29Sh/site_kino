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

@WebServlet("/kino_vo")
public class main extends HttpServlet  {
	private static final long serialVersionUID = 1L;
    
    bd.bd connection = new bd.bd();
	Connection con = null;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        list_film list_film = null;
        ArrayList<list_film> list_film2 = new ArrayList<list_film>();
        ArrayList<list_film> list_film3 = new ArrayList<list_film>();
        
    
        try {
            con = connection.getConnection();
            
            Statement stmt = con.createStatement();
            String sql;
            sql = "SELECT id, title, year, img_cover, rating FROM film";
            ResultSet rs = stmt.executeQuery(sql);
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
        
    	request.setAttribute("count_film", list_film2.size());
    	request.setAttribute("list_film", list_film3);
    	request.setAttribute("title", "kino_vo!");
    	request.setAttribute("name_str", "kino_vo");
    	
    	if(list_film3.size() == 0) request.setAttribute("mess", "По Вашему запросу ничего не найдено");
    	
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
        
    	request.getRequestDispatcher("page/index.jsp").forward(request, response);
	}
	

}
