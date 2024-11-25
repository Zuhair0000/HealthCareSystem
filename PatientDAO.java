import java.sql.*;

public class PatientDAO {
    private Connection conn;

    public PatientDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public Patient getPatientByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM patients WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserById(userId); // Fetch the user using UserDAO
                return new Patient(rs.getInt("id"), user, rs.getString("medical_history"));
            }
        }
        return null;
    }


    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (user_id, medical_history) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, patient.getUser().getId());
        stmt.setString(2, patient.getMedicalHistory());
        stmt.executeUpdate();
    }

    public Patient getPatientById(int patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, patientId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new UserDAO().getUserByEmail(rs.getString("user_id")); // Fetch user details using user_id
            return new Patient(rs.getInt("id"), user, rs.getString("medical_history"));
        }
        return null;
    }
}