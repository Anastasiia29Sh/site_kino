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

@WebServlet("/film_search_words")
public class film_search_words extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	bd.bd connection = new bd.bd();
	Connection con = null;
	
	ArrayList<list_film> list_film2;
    ArrayList<list_film> list_film3;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        String name_head = "";
        
        String countf = request.getParameter("countf");
        int count_film = (countf == null) ? 1 : Integer.parseInt(countf);
        int start = 20*(count_film-1);
        int end = (list_film2.size() >= 20*count_film) ? (start+20) : list_film2.size();
        
        list_film3 = new ArrayList<list_film>();
        
        for(int i = start; i < end; i++) {
            list_film3.add(list_film2.get(i));
        }
        
        request.setAttribute("count_film", list_film2.size());
    	request.setAttribute("list_film", list_film3);
    	request.setAttribute("title", "kino_vo!");
    	request.setAttribute("name_head", name_head);
    	
    	request.setAttribute("name_str", "film_search_words");
    	
    	if(list_film3.size() == 0) request.setAttribute("mess", "По Вашему запросу ничего не найдено");
        
    	HttpSession session = request.getSession();
        request.setAttribute("mess_login", (String) session.getAttribute("mass_login"));
        
        String cookieName = "name_user";
        String cookieName2 = "status_user";
        String cookieName3 = "id_user";
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
                if (cookieName3.equals(cookie.getName())) 
                {
                	request.setAttribute("id_user", cookie.getValue());
                }
            }
        }
    	request.getRequestDispatcher("page/index.jsp").forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
		
        String words = request.getParameter("words");
        
        
        list_film list_film = null;
		list_film2 = new ArrayList<list_film>();
	    list_film3 = new ArrayList<list_film>();
	    
	    String name_head = "";
	    
	    
	    try {
            con = connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            
            if(words.equals("") || words == null) {
            	rs = stmt.executeQuery("SELECT id, title, year, img_cover, rating FROM film");
            	words = null;
    		}else {
    			PreparedStatement pS = con.prepareStatement("SELECT id, title, year, img_cover, rating FROM film WHERE title LIKE CONCAT(\"%\", ?, \"%\")");
				pS.setString(1, words);
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
		
	    request.setAttribute("name_str", "film_search_words");
    	request.setAttribute("name_head", name_head);
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
