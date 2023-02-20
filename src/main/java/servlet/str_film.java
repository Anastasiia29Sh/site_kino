package servlet;

import jakarta.servlet.ServletContext;
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

import classs.comment;
import classs.list_film;

@WebServlet("/str_film")
public class str_film extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	bd.bd connection = new bd.bd();
	Connection con = null;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        int id_f = Integer.parseInt(request.getParameter("id_f"));
       
        String id_userrr = "0";
        
        String title_buttom = "В закладки";
        String name_userr=null;
        
        comment comment = null;
        ArrayList<comment> comm = new ArrayList<comment>();
        
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
                	name_userr = cookie.getValue();
                	request.setAttribute("name_user", cookie.getValue());
                }
                if (cookieName2.equals(cookie.getName())) 
                {
                	request.setAttribute("status_user", cookie.getValue());
                }
                if (cookieName3.equals(cookie.getName())) 
                {
                	id_userrr = cookie.getValue();
                }
            }
        }

        
        
        try {
            con = connection.getConnection();  
            Statement stmt = con.createStatement();
            
            PreparedStatement pS = con.prepareStatement("SELECT DISTINCT aif.id, aif.title, aif.year, aif.rating, aif.description, aif.img_cover, aif.src_video, aif.country, aif.producer, aif.main_roles \r\n"
            		+ "FROM all_info_film aif\r\n"
            		+ "WHERE aif.id=?");
            pS.setInt(1, id_f);
            ResultSet rs = pS.executeQuery();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT genre FROM all_info_film WHERE id=?");
            PreparedStatement pComment = con.prepareStatement("SELECT name_user, email, text_comment, time_comment FROM comments WHERE id_film=?");
            
         // Извлечь данные из набора результатов
            while(rs.next()){
            	int id = rs.getInt("id");
                String title = rs.getString("title");
                String yearr = rs.getString("year");
                String img_cover = rs.getString("img_cover");
                int rating = rs.getInt("rating");
                String description = rs.getString("description");
                String src_video = rs.getString("src_video");
                String country = rs.getString("country");
                String producer = rs.getString("producer");
                String main_roles = rs.getString("main_roles");
                
                
                List<String> genre = new ArrayList();
                preparedStatement.setInt(1, id);
                ResultSet gs = preparedStatement.executeQuery();
                while(gs.next()) { genre.add(gs.getString("genre")); }
                
               
                pComment.setInt(1, id);
                ResultSet com = pComment.executeQuery();
                while(com.next()) { 
                	String name_user = com.getString("name_user");
                    String email = com.getString("email");
                    String text_comment = com.getString("text_comment");
                    String time_comment = com.getString("time_comment");
                	comment = new comment(name_user, email, text_comment, time_comment);
                	comm.add(comment);
                }
                
                request.setAttribute("title", title);
                request.setAttribute("id", id);
            	request.setAttribute("year", yearr);
            	request.setAttribute("img_cover", img_cover);
            	request.setAttribute("rating", rating);
            	request.setAttribute("description", description);
            	request.setAttribute("src_video", src_video);
            	request.setAttribute("country", country);
            	request.setAttribute("producer", producer);
            	request.setAttribute("main_roles", main_roles);
            	request.setAttribute("genre", genre);
                }
            
            
            if(name_userr != null) {
	            PreparedStatement prStatement = con.prepareStatement("SELECT COUNT(*) as count FROM bookmarks b WHERE b.id_user=? AND b.id_film=?");
	            prStatement.setInt(1, Integer.parseInt(id_userrr));
	            prStatement.setInt(2, id_f);
	            ResultSet rs3 = prStatement.executeQuery();
	            while(rs3.next()) {
	            	int count = rs3.getInt("count");
	            	if(count == 1) {
	            		title_buttom = "Из закладок";
	            	}else if(count == 0) {
	            		title_buttom = "В закладки";
	            	}
	            }
            }
            request.setAttribute("title_buttom", title_buttom);
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        request.setAttribute("comm", comm);
        
        HttpSession session = request.getSession();
        request.setAttribute("mess_login", (String) session.getAttribute("mass_login"));
        
        
    	
        request.getRequestDispatcher("page/str_film.jsp").forward(request, response);
        
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        int id_f = Integer.parseInt(request.getParameter("id_f"));
        
        try {
            con = connection.getConnection();
            
            String name_user = request.getParameter("name_user_comment");
            String email_user = request.getParameter("email_user_comment");
            String text_comment = request.getParameter("text_comment");
            
            Statement stmt = con.createStatement();
            if(!name_user.equals("") && !email_user.equals("") && !text_comment.equals("")) {
	            PreparedStatement pS = con.prepareStatement("CALL add_comment(?, ?, ?, ?)");
	            pS.setInt(1, id_f);
	            pS.setString(2, name_user);
	            pS.setString(3, email_user);
	            pS.setString(4, text_comment);
	            ResultSet rs = pS.executeQuery();
            }
            name_user = "";
            email_user = "";
            text_comment = "";
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
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
