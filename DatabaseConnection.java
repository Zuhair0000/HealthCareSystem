
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Update the following details according to your database configuration
                String url = "jdbc:mysql://localhost:3306/healthcare_system"; // Replace with your DB URL
                String username = "root"; // Replace with your DB username
                String password = "Zoherama1122@"; // Replace with your DB password
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
