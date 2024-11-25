
import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDate;
    private String status;

    public Appointment(int id, Doctor doctor, Patient patient, LocalDateTime appointmentDate, String status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: Override toString for better debugging
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctor=" + doctor.getUser().getName() +
                ", patient=" + patient.getUser().getName() +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                '}';
    }
}
