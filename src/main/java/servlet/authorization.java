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


@WebServlet("/authorization")
public class authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	bd.bd connection = new bd.bd();
	Connection con = null;
	
	String url_str;
	String login;
	String password;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
  
        if(request.getParameter("exit") != null && request.getParameter("exit").equals("exit")) {
        	Cookie name_user = new Cookie("name_user", null);
    		name_user.setMaxAge(0);
    		response.addCookie(name_user);
    		Cookie status_user = new Cookie("status_user", null);
    		status_user.setMaxAge(0);
    		response.addCookie(status_user);
    		Cookie id_user = new Cookie("id_user", null);
    		id_user.setMaxAge(0);
    		response.addCookie(id_user);
        }
        
//        out.print(url_str);
        if(url_str==null) url_str = "/kino_vo";
        response.sendRedirect(request.getContextPath() + url_str);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        String mess = null;
        
        url_str = request.getParameter("url_str");
        login = request.getParameter("login");
        password = request.getParameter("password");
        
        String key = "2341";       
        String shifr="";
        
        // разделение исходного пароля на части по key*key символам
        String[] pass_key = password.split("(?<=\\G.{" + key.length()*key.length() + "})");
        
        for(int i = 0; i<pass_key.length; i++) {
        	shifr += encrypt(pass_key[i], key);
        }
        
        
        try {
            con = connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            
            PreparedStatement pS = con.prepareStatement("SELECT COUNT(*) AS count FROM authorization WHERE login=? AND password=?");
			pS.setString(1, login);
			pS.setString(2, shifr);
			rs = pS.executeQuery();
            

            while(rs.next()){
            	int count = rs.getInt("count");
            	if(count==0) {
            		mess = "Неверный логин или пароль";
            		Cookie name_user = new Cookie("name_user", "");
            		name_user.setMaxAge(0);
            		response.addCookie(name_user);
            	}else {
            		mess = null;
            		PreparedStatement ns = con.prepareStatement("SELECT id, name, status FROM user WHERE email = ?");
        			ns.setString(1, login);
        			rs = ns.executeQuery();
        			while(rs.next()){
        				Cookie name_user = new Cookie("name_user", rs.getString("name"));
        				Cookie status_user = new Cookie("status_user", rs.getString("status"));
        				Cookie id_user = new Cookie("id_user", rs.getString("id"));
                		response.addCookie(name_user);
                		response.addCookie(status_user);
                		response.addCookie(id_user);
                		name_user.setMaxAge(2*60*60);
                		status_user.setMaxAge(2*60*60);
                		id_user.setMaxAge(2*60*60);
        			}
        			if(url_str.equals("/registration")) {url_str = "/kino_vo";}
            		
            	}
               
            }
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("mass_login", mess);
        
        request.setAttribute("mess_login", (String) session.getAttribute("mass_login"));
		
		doGet(request, response);
	}
	
	
	
		//функция, которая шифрует 
		public String encrypt(String pass, String key){
			final int RADIX = 10;
			while(pass.length() < key.length()*key.length()) {
				pass = pass + "/";
			}
			// начальная матрица с исходным паролем
		    String[][] startMatrix = new String[key.length()][key.length()];
			for(int i=0; i<key.length(); i++){
				for(int j=0; j<key.length(); j++){
					startMatrix[i][j] = Character.toString(pass.charAt(i*key.length() + j));
				}
			}
			
		    String[][] ColMatrix = new String[key.length()][key.length()];
		    String[][] RowMatrix = new String[key.length()][key.length()];
		    for(int i=0; i<key.length(); i++){
				for(int j=0; j<key.length(); j++){
					ColMatrix[j][i]="0";
					RowMatrix[j][i]="0";
				}
			}
		    // перестановка столбцов
		    for(int i=0; i<key.length(); i++){
				for(int j=0; j<key.length(); j++){
					int y = key.indexOf(( Character.forDigit( i+1, RADIX)));
					String z = startMatrix[j][y];
					ColMatrix[j][i]=z;
				}
			}
		    // перестановка строк
	 		for(int i=0; i<key.length(); i++){
	 			for(int j=0; j<key.length(); j++){
	 				int y = key.indexOf(( Character.forDigit( i+1, RADIX)));
	 				String z = ColMatrix[y][j];
	 				RowMatrix[i][j]=z;
	 			}
	 		}
	 	    // шифрованная часть 
			String shifr = "";
			for(int i=0; i<key.length(); i++){
				for(int j=0; j<key.length(); j++){
					shifr+=RowMatrix[i][j];
				}
			}
			return shifr;
		}

}
