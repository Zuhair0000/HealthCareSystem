import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {

    public void addPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO patients (user_id, medical_history) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patient.getUser().getId());
            stmt.setString(2, patient.getMedicalHistory());
            stmt.executeUpdate();
        }
    }

    public Patient getPatientById(int id) throws SQLException {
        String query = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserDAO userDAO = new UserDAO();
                int userId = rs.getInt("user_id");  // Get the user_id from the result set
                User user = userDAO.getUserById(userId);  // Correctly pass the userId
                return new Patient(rs.getInt("id"), user, rs.getString("medical_history"));
            }
        }
        return null;
    }
}
