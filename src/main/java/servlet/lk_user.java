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

import classs.comment;
import classs.list_film;

@WebServlet("/lk_user")
public class lk_user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	bd.bd connection = new bd.bd();
	Connection con = null;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        comment comment = null;
        ArrayList<comment> comm = new ArrayList<comment>();
        
        list_film list_film = null;
        ArrayList<list_film> list_film2 = new ArrayList<list_film>();
        ArrayList<list_film> list_film3 = new ArrayList<list_film>();
        
        String doo = request.getParameter("do");
        
        String id_userrr = "0";
        
        HttpSession session = request.getSession();
        request.setAttribute("mess_login", (String) session.getAttribute("mass_login"));
        
        HttpSession sessionn = request.getSession();
        request.setAttribute("mes_edit", (String) sessionn.getAttribute("mes_edit"));
        
        
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
        	
        	if(doo.equals("c")) {
            	request.setAttribute("title", "Мои комментарии");
            	PreparedStatement preparedStatement = con.prepareStatement("SELECT name_user, text_comment, time_comment, title, id_film FROM user_film_comment WHERE id_user=?");
            	preparedStatement.setInt(1, Integer.parseInt(id_userrr));
                ResultSet gs = preparedStatement.executeQuery();
                while(gs.next()) {
                	String name_user = gs.getString("name_user");
                	String text_comment = gs.getString("text_comment");
                	String time_comment = gs.getString("time_comment");
                	String title_film = gs.getString("title");
                	int id_film = gs.getInt("id_film");
                	
                	comment = new comment(name_user, text_comment, time_comment, title_film, id_film);
                	comm.add(comment);
                	request.setAttribute("comment_user", comm);
                }
            }else if(doo.equals("p")) {
            	request.setAttribute("title", "Мой профиль");
            	PreparedStatement preparedStatement = con.prepareStatement("SELECT name, email FROM user WHERE id=?");
            	preparedStatement.setInt(1, Integer.parseInt(id_userrr));
            	PreparedStatement preparedStatement2 = con.prepareStatement("SELECT password FROM authorization WHERE login like ?");
                ResultSet gs = preparedStatement.executeQuery();
                while(gs.next()) {
                	String name_user = gs.getString("name");
                	String email_user = gs.getString("email");
                	String password = "";
                	
                	preparedStatement2.setString(1, email_user);
                	ResultSet gs2 = preparedStatement2.executeQuery();
                	while(gs2.next()) {
                		password = gs2.getString("password");
                	}
                	
                    String key = "2341";
                    String deshifr="";
                    
                 // разделение исходного пароля на части по key*key символам
                    String[] deshifr_key = password.split("(?<=\\G.{" + key.length()*key.length() + "})");
                    
                    for(int i = 0; i<deshifr_key.length; i++) {
                    	deshifr += decrypt(deshifr_key[i], key);
                    }
                	
                	request.setAttribute("name_user", name_user);
                	request.setAttribute("email_user", email_user);
                	request.setAttribute("password", deshifr.replaceAll("[/]", ""));
                }
            	
            	
            }else if(doo.equals("z")) {
            	request.setAttribute("title", "Мои закладки");
            	PreparedStatement preparedStatement = con.prepareStatement("SELECT DISTINCT id_film, title, year, rating, img_cover FROM film_in_bookmarks WHERE id_user=?");
            	preparedStatement.setInt(1, Integer.parseInt(id_userrr));
                ResultSet rs = preparedStatement.executeQuery();
                PreparedStatement preparedStatement1 = con.prepareStatement("SELECT genre FROM film_in_bookmarks WHERE id_film=?");
                while(rs.next()) {
                	int id = rs.getInt("id_film");
                    String title = rs.getString("title");
                    String yearr = rs.getString("year");
                    String img_cover = rs.getString("img_cover");
                    int rating = rs.getInt("rating");
                    
                    List<String> genre = new ArrayList();
                    preparedStatement1.setInt(1, id);
                    ResultSet gs = preparedStatement1.executeQuery();
                    while(gs.next()) { genre.add(gs.getString("genre")); }
                    
                    list_film = new list_film(id, title, yearr, img_cover, rating, genre);
                    list_film2.add(list_film);
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
            	request.setAttribute("name_str", "lk_user?do=z");
            	
            	if(list_film3.size() == 0) request.setAttribute("mess", "По Вашему запросу ничего не найдено");
            	
            }else {
            	request.setAttribute("title", "Настройки");
            }
        	
            
            
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        
        
        
        request.getRequestDispatcher("page/lk.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        String id_userrr = "0";
        
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
		
        
        String username = request.getParameter("username");
        String useremail = request.getParameter("useremail");
        String userpass = request.getParameter("userpass");
        
        String key = "2341";
        String shifr="";
        
        // разделение исходного пароля на части по key*key символам
        String[] pass_key = userpass.split("(?<=\\G.{" + key.length()*key.length() + "})");
        
        for(int i = 0; i<pass_key.length; i++) {
        	shifr += encrypt(pass_key[i], key);
        }
        
        String mes_edit= "";
        
        
        try {
            con = connection.getConnection();
            Statement stmt = con.createStatement();
            
            
            PreparedStatement pS = con.prepareStatement("SELECT COUNT(*) AS count FROM all_info_user WHERE email=? and id_user != ?");
			pS.setString(1, useremail);
			pS.setInt(2, Integer.parseInt(id_userrr));
			ResultSet rs = pS.executeQuery();
			PreparedStatement pS1 = con.prepareStatement("SELECT COUNT(*) AS count FROM all_info_user WHERE password=? and id_user != ?");
			pS1.setString(1, shifr);
			pS1.setInt(2, Integer.parseInt(id_userrr));
			ResultSet rs1 = pS1.executeQuery();
			
			if(rs.next() && rs1.next()) {
				int count = rs.getInt("count");
				int count1 = rs1.getInt("count");
				if(count!=0) {
					mes_edit = "Пользователь с таким email уже существует";
				}else if(count1!=0) {
					mes_edit = "Введенный Вами пароль уже занят";
				}else {
					PreparedStatement pS2 = con.prepareStatement("CALL edit_user(?, ?, ?, ?)");
					pS2.setInt(1, Integer.parseInt(id_userrr));
					pS2.setString(2, username);
					pS2.setString(3, useremail);
					pS2.setString(4, shifr);
					pS2.executeQuery();
				}
			}
			
            con.close();
        } catch (SQLException e1) {
        	out.print ("Ошибка подключения к данным ");
        	out.print(e1.toString());
        }
        
        HttpSession sessionn = request.getSession();
        sessionn.setAttribute("mes_edit", mes_edit);
        
        response.sendRedirect(request.getContextPath() + "/lk_user?do=p");
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
			
			
			
			// функция, котрая дешифрует
			public String decrypt(String pass, String key){
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
			    // перестановка строк
		  		for(int i=0; i<key.length(); i++){
		  			for(int j=0; j<key.length(); j++){
		  				int y = Integer.parseInt(Character.toString(key.charAt(i)));
		  				String z = startMatrix[y-1][j];
		  				RowMatrix[i][j]=z;
		  			}
		  		}
		  		// перестановка столбцов
			    for(int i=0; i<key.length(); i++){
					for(int j=0; j<key.length(); j++){
						int y = Integer.parseInt(Character.toString(key.charAt(i)));
						String z = RowMatrix[j][y-1];
						ColMatrix[j][i]=z;
					}
				}
			    // расшифрованная часть 
			    String deshifr = "";
				for(int i=0; i<key.length(); i++){
					for(int j=0; j<key.length(); j++){
						deshifr+=ColMatrix[i][j];
					}
				}
				return deshifr;
			}


}
