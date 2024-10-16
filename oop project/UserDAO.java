import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Method to fetch a User by their ID
    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Extract user details from the ResultSet
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");

                // Create and return a User object
                return new User(id, name, email, password, role);
            }
        }
        return null; // If user not found
    }

    public void addUser(User user) throws SQLException {
      String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
      try (Connection conn = DBConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(query)) {
          stmt.setString(1, user.getName());
          stmt.setString(2, user.getEmail());
          stmt.setString(3, user.getPassword());
          stmt.setString(4, user.getRole());
          stmt.executeUpdate();
      }
  }
  public User getUserByEmail(String email) throws SQLException {
    String query = "SELECT * FROM users WHERE email = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
        }
    }
    return null; // Return null if user is not found
}
public User login(String email, String password) throws SQLException {
  String query = "SELECT * FROM users WHERE email = ? AND password = ?";
  try (Connection conn = DBConnection.getConnection();
       PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, email);
      stmt.setString(2, password);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
          // If user is found, create a User object and return it
          return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                          rs.getString("password"), rs.getString("role"));
      }
  }
  return null; // Return null if credentials are invalid
}

}
