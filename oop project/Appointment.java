import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDate;
    private String status; // 'BOOKED', 'CANCELED', 'COMPLETED'

    public Appointment(int id, Doctor doctor, Patient patient, LocalDateTime appointmentDate, String status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public Doctor getDoctor() {
      return doctor;
    }

    public void setDoctor(Doctor doctor) {
      this.doctor = doctor;
    }

    public Patient getPatient() {
      return patient;
    }

    public void setPatient(Patient patient) {
      this.patient = patient;
    }

    public LocalDateTime getAppointmentDate() {
      return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
      this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    
}

