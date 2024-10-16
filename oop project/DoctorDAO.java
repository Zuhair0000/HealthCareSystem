import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {

    public void addDoctor(Doctor doctor) throws SQLException {
        String query = "INSERT INTO doctors (user_id, specialization) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctor.getUser().getId()); // Correctly linking user ID
            stmt.setString(2, doctor.getSpecialization());
            stmt.executeUpdate();
        }
    }

    public Doctor getDoctorById(int userId) throws SQLException {
        String query = "SELECT * FROM doctors WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);  // Fetching doctor based on the user ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Fetch the User object associated with this doctor
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserById(userId);

                // Create and return the Doctor object
                return new Doctor(rs.getInt("id"), user, rs.getString("specialization"));
            }
        }
        return null;  // If no doctor is found for the given user ID
    }
}
