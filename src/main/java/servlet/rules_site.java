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

@WebServlet("/rules_site")
public class rules_site extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//	bd.bd connection = new bd.bd();
//	Connection con = null;
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        String agree = request.getParameter("agree");
        
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
        
        
    	request.setAttribute("name_str", "rules_site");

    	if(agree != null) {
        	if(agree.equals("yes")) {
        		request.setAttribute("title", "Регистрация");
        		request.getRequestDispatcher("page/registration.jsp").forward(request, response);
        	}else {
        		response.sendRedirect(request.getContextPath() + "/kino_vo");
        	}
        }else {
        	request.setAttribute("title", "Регистрация");
        	request.getRequestDispatcher("page/rules_site.jsp").forward(request, response);
        }

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		doGet(request, response);
	}

}
