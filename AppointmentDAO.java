import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private Connection conn;

    public AppointmentDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public void bookAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, status) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, appointment.getDoctor().getId());
        stmt.setInt(2, appointment.getPatient().getId());
        stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
        stmt.setString(4, appointment.getStatus());
        stmt.executeUpdate();
    }

    public void cancelAppointment(int appointmentId) throws SQLException {
        String sql = "UPDATE appointments SET status = 'CANCELED' WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, appointmentId);
        stmt.executeUpdate();
    }

    public List<Appointment> getDoctorAppointments(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
    
        String query = "SELECT a.id, a.appointment_date, a.status, p.id AS patient_id, u.id AS user_id, u.name AS user_name, u.email AS user_email " +
                       "FROM appointments a " +
                       "JOIN patients p ON a.patient_id = p.id " +
                       "JOIN users u ON p.user_id = u.id " +
                       "WHERE a.doctor_id = ?";
                       
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                // Fetch user details for the patient
                User patientUser = new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_email"), null, null);
                Patient patient = new Patient(rs.getInt("patient_id"), patientUser, null);
    
                // Create an Appointment object
                Appointment appointment = new Appointment(
                    rs.getInt("id"),
                    null, // You can fill in the doctor details if needed
                    patient,
                    rs.getTimestamp("appointment_date").toLocalDateTime(),
                    rs.getString("status")
                );
                appointments.add(appointment);
            }
        }
        return appointments;
    }
}