import java.sql.*;

public class DoctorDAO {
    private Connection conn;

    public DoctorDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (user_id, specialization) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, doctor.getUser().getId());
        stmt.setString(2, doctor.getSpecialization());
        stmt.executeUpdate();
    }

    public Doctor getDoctorById(int userId) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new UserDAO().getUserByEmail(rs.getString("user_id"));
            return new Doctor(rs.getInt("id"), user, rs.getString("specialization"));
        }
        return null;
    }

    public Doctor getDoctorByName(String doctorName) throws SQLException {
        String sql = "SELECT d.id, u.name, u.email FROM doctors d INNER JOIN users u ON d.user_id = u.id WHERE u.name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctorName);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                User doctorUser = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), null, null);
                return new Doctor(rs.getInt("id"), doctorUser, null);
            }
        }
        return null; // Return null if no doctor is found
    }
}