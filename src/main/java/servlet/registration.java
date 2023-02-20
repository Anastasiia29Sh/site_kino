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

@WebServlet("/registration")
public class registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	bd.bd connection = new bd.bd();
	Connection con = null;
	
	String username;
	String userpass;
	String userpass1;
	String useremail;
	
	String mess_reg;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        
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
        
        
        request.setAttribute("title", "Регистрация");
        request.getRequestDispatcher("page/registration.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        username = request.getParameter("username");
        userpass = request.getParameter("userpass");
        userpass1 = request.getParameter("userpass1");
        useremail = request.getParameter("useremail");
        
        String key = "2341";
        String shifr="";
        
        // разделение исходного пароля на части по key*key символам
        String[] pass_key = userpass.split("(?<=\\G.{" + key.length()*key.length() + "})");
        
        for(int i = 0; i<pass_key.length; i++) {
        	shifr += encrypt(pass_key[i], key);
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
        
        request.setAttribute("title", "Регистрация");
        
        try {
            con = connection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            ResultSet rs1;
            
            PreparedStatement pS = con.prepareStatement("SELECT COUNT(*) AS count FROM authorization WHERE login=?");
			pS.setString(1, useremail);
			rs = pS.executeQuery();
			PreparedStatement pS1 = con.prepareStatement("SELECT COUNT(*) AS count FROM authorization WHERE password=?");
			pS1.setString(1, shifr);
			rs1 = pS1.executeQuery();
			
			if(rs.next() && rs1.next()) {
				int count = rs.getInt("count");
				int count1 = rs1.getInt("count");
				if(count!=0) {
					request.setAttribute("mes_reg", "Пользователь с таким email уже существует");
					request.setAttribute("username", username);
					request.setAttribute("useremail", useremail);
				}else if(count1!=0) {
					request.setAttribute("mes_reg", "Введенный Вами пароль уже занят");
					request.setAttribute("username", username);
					request.setAttribute("useremail", useremail);
				}else {
					PreparedStatement pS2 = con.prepareStatement("CALL add_user(?, ?, ?)");
					pS2.setString(1, username);
					pS2.setString(2, useremail);
					pS2.setString(3, shifr);
					pS2.executeQuery();
					request.setAttribute("success", "success");
				}
			}
            
			request.getRequestDispatcher("page/registration.jsp").forward(request, response);
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        
//		doGet(request, response);
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
