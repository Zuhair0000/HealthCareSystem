
public class Doctor {
  private int id;
  private User user; // User information for the doctor
  private String specialization;

  public Doctor(int id, User user, String specialization) {
      this.id = id;
      this.user = user;
      this.specialization = specialization;
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

  public String getSpecialization() {
      return specialization;
  }

  public void setSpecialization(String specialization) {
      this.specialization = specialization;
  }

  // Add getUserId() to retrieve the ID of the User
  public int getUserId() {
      return this.user.getId(); // Assuming the User class has a getId() method
  }
}
