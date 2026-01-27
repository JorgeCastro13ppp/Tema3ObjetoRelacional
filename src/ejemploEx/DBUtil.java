package ejemploEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/bdgym_jorge";
    private static final String USER = "postgres";
    private static final String PASSWORD = "toor";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

