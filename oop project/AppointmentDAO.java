import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public void bookAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointment.getDoctor().getId());
            stmt.setInt(2, appointment.getPatient().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            stmt.setString(4, appointment.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<Appointment> getDoctorAppointments(int doctorId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE doctor_id = ?";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Fetch doctor and patient details
                DoctorDAO doctorDAO = new DoctorDAO();
                PatientDAO patientDAO = new PatientDAO();
                Doctor doctor = doctorDAO.getDoctorById(rs.getInt("doctor_id"));
                Patient patient = patientDAO.getPatientById(rs.getInt("patient_id"));
                appointments.add(new Appointment(rs.getInt("id"), doctor, patient,
                        rs.getTimestamp("appointment_date").toLocalDateTime(), rs.getString("status")));
            }
        }
        return appointments;
    }

    public void cancelAppointment(int appointmentId) throws SQLException {
        String query = "UPDATE appointments SET status = 'CANCELED' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        }
    }
}

