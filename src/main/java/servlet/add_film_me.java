package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
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

@WebServlet("/add_film_me")
public class add_film_me extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	bd.bd connection = new bd.bd();
	Connection con = null;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        String id_film = request.getParameter("id_f");
        
        String id_userrr = "0";
        
        String title_buttom = "";
        
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
                	id_userrr = cookie.getValue();
                }
            }
        }
        
        try {
        	con = connection.getConnection();
            Statement stmt = con.createStatement();
            
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) as count FROM bookmarks b WHERE b.id_user=? AND b.id_film=?");
        	preparedStatement.setInt(1, Integer.parseInt(id_userrr));
        	preparedStatement.setInt(2, Integer.parseInt(id_film));
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
            	int count = rs.getInt("count");
            	if(count == 1) {
            		PreparedStatement pS1 = con.prepareStatement("call delete_bookmarks(?, ?)");
                	pS1.setInt(1, Integer.parseInt(id_userrr));
                	pS1.setInt(2, Integer.parseInt(id_film));
                    ResultSet rss1 = pS1.executeQuery();
            		title_buttom = "В закладки";
            	}else if(count == 0) {
            		PreparedStatement pS = con.prepareStatement("call add_bookmarks(?, ?)");
                	pS.setInt(1, Integer.parseInt(id_userrr));
                	pS.setInt(2, Integer.parseInt(id_film));
                    ResultSet rss = pS.executeQuery();
            		title_buttom = "Из закладок";
            	}
            }
            
            request.setAttribute("title_buttom", title_buttom);
            
            
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        response.sendRedirect(request.getContextPath() + "/str_film?id_f=" + id_film);
        
	}
}
