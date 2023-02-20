package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ooo extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");   
        PrintWriter out = response.getWriter();
        
        String pass = "Приветмирномунаселению";
        String key = "2341";
        String shifr="";
        String deshifr="";
        
        
        
        // разделение исходного пароля на части по key*key символам
        String[] pass_key = pass.split("(?<=\\G.{" + key.length()*key.length() + "})");
        
        for(int i = 0; i<pass_key.length; i++) {
        	shifr += encrypt(pass_key[i], key);
        }
		
		out.println("***" + shifr);
		
		
		// разделение исходного пароля на части по key*key символам
        String[] deshifr_key = shifr.split("(?<=\\G.{" + key.length()*key.length() + "})");
        
        for(int i = 0; i<deshifr_key.length; i++) {
        	deshifr += decrypt(deshifr_key[i], key);
        }
        
        out.println(deshifr);
	    
	    
		
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
