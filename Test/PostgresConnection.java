package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
    
    private static String serveur = "localhost";
    private static String baseName = "projetquentin";
    private static String user = "postgres";
    private static String password = "Olgus91";

    private static Connection connection;

    public static Connection getInstance() {

        if (connection == null) {
            String url = "jdbc:postgresql://"+ serveur + "/" + baseName;
            try {
                connection = DriverManager.getConnection(url, user, password);
            }
            catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return connection;

    }
    
}
