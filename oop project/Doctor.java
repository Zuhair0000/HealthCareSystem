public class Doctor {
  private int id;
  private User user;
  private String specialization;

  public Doctor(int id, User user, String specialization) {
      this.id = id;
      this.user = user;
      this.specialization = specialization;
  }

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

  
}

