package bd;

import java.sql.*;

public class bd {
	
	static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print ("Не удалось загрузить драйвер!" + e.toString ());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/super_cinema", "root", "anasha");
    }
}
