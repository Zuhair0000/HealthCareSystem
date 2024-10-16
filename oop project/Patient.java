public class Patient {
  private int id;
  private User user;
  private String medicalHistory;

  public Patient(int id, User user, String medicalHistory) {
      this.id = id;
      this.user = user;
      this.medicalHistory = medicalHistory;
  }

  // Getters and Setters
  public int getId() {
      return id;
  }

  public void setId(int id) {
      this.id = id;
  }

  public User getUser() {
      return user;
  }

  public void setUser(User user) {
      this.user = user;
  }

  public String getMedicalHistory() {
      return medicalHistory;
  }

  public void setMedicalHistory(String medicalHistory) {
      this.medicalHistory = medicalHistory;
  }
}

