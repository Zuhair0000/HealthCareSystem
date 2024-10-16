import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class HealthcareSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        System.out.println("Welcome to the Healthcare Appointment Booking System!");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    registerUser(scanner, userDAO, doctorDAO, null);
                    break;
                case 2:
                    loginUser(scanner, userDAO, doctorDAO, appointmentDAO);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner, UserDAO userDAO, DoctorDAO doctorDAO, PatientDAO patientDAO) {
      try {
          System.out.println("Register as:");
          System.out.println("1. Doctor");
          System.out.println("2. Patient");
          System.out.println("3. Admin");
          System.out.print("Enter your choice: ");
          int choice = scanner.nextInt();
          scanner.nextLine(); // Consume newline
  
          // Common registration fields
          System.out.print("Enter your name: ");
          String name = scanner.nextLine();
          System.out.print("Enter your email: ");
          String email = scanner.nextLine();
          System.out.print("Enter your password: ");
          String password = scanner.nextLine();
  
          // Create a user object to insert into the database
          User user = new User(0, name, email, password, null);
  
          switch (choice) {
              case 1: // Register as Doctor
                  System.out.print("Enter your specialization: ");
                  String specialization = scanner.nextLine();
  
                  // Set user role as "DOCTOR"
                  user.setRole("DOCTOR");
                  
                  // Add user to the database
                  userDAO.addUser(user);
  
                  // Retrieve the newly created user ID
                  int doctorUserId = userDAO.getUserByEmail(email).getId(); // Fetch user ID after insertion
  
                  // Create Doctor object and insert it into the database
                  Doctor doctor = new Doctor(0, new User(doctorUserId, name, email, password, "DOCTOR"), specialization);
                  doctorDAO.addDoctor(doctor);
  
                  System.out.println("Doctor registered successfully.");
                  break;
  
              case 2: // Register as Patient
                  System.out.print("Enter your medical history: ");
                  String medicalHistory = scanner.nextLine();
  
                  // Set user role as "PATIENT"
                  user.setRole("PATIENT");
                  
                  // Add user to the database
                  userDAO.addUser(user);
  
                  // Retrieve the newly created user ID
                  int patientUserId = userDAO.getUserByEmail(email).getId(); // Fetch user ID after insertion
  
                  // Create Patient object and insert it into the database
                  Patient patient = new Patient(0, new User(patientUserId, name, email, password, "PATIENT"), medicalHistory);
                  patientDAO.addPatient(patient);
  
                  System.out.println("Patient registered successfully.");
                  break;
  
              case 3: // Register as Admin
                  // Set user role as "ADMIN"
                  user.setRole("ADMIN");
                  
                  // Add user to the database
                  userDAO.addUser(user);
  
                  System.out.println("Admin registered successfully.");
                  break;
  
              default:
                  System.out.println("Invalid choice.");
          }
      } catch (SQLException e) {
          System.out.println("Error during registration: " + e.getMessage());
      }
  }
  

  private static void loginUser(Scanner scanner, UserDAO userDAO, DoctorDAO doctorDAO, AppointmentDAO appointmentDAO) {
    try {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = userDAO.login(email, password);
        if (user == null) {
            System.out.println("Invalid login credentials.");
            return;
        }

        // Check the user role and direct them to their respective actions
        switch (user.getRole()) {
            case "ADMIN":
                handleAdminActions(scanner, doctorDAO);
                break;
            case "DOCTOR":
                Doctor doctor = doctorDAO.getDoctorById(user.getId());  // Fetch doctor by user ID
                if (doctor == null) {
                    System.out.println("Doctor profile not found.");
                    return;
                }
                handleDoctorActions(scanner, doctorDAO, appointmentDAO, user);
                break;
            case "PATIENT":
                handlePatientActions(scanner, doctorDAO, appointmentDAO, user);
                break;
            default:
                System.out.println("Invalid user role.");
        }
    } catch (SQLException e) {
        System.out.println("Error during login: " + e.getMessage());
    }
}


    private static void handleAdminActions(Scanner scanner, DoctorDAO doctorDAO) throws SQLException {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Doctor");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice == 2) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter doctor name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter doctor email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter doctor password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter doctor specialization: ");
                    String specialization = scanner.nextLine();

                    User doctorUser = new User(0, name, email, password, "DOCTOR");
                    doctorDAO.addDoctor(new Doctor(0, doctorUser, specialization));
                    System.out.println("Doctor added successfully!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void handleDoctorActions(Scanner scanner, DoctorDAO doctorDAO, AppointmentDAO appointmentDAO, User user) throws SQLException {
        Doctor doctor = doctorDAO.getDoctorById(user.getId());
        if (doctor == null) {
            System.out.println("Doctor profile not found.");
            return;
        }

        while (true) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Appointments");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 2) break;

            switch (choice) {
                case 1:
                    List<Appointment> appointments = appointmentDAO.getDoctorAppointments(doctor.getId());
                    for (Appointment appointment : appointments) {
                        System.out.println("Appointment ID: " + appointment.getId() + 
                                           ", Patient: " + appointment.getPatient().getUser().getName() + 
                                           ", Date: " + appointment.getAppointmentDate() +
                                           ", Status: " + appointment.getStatus());
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void handlePatientActions(Scanner scanner, DoctorDAO doctorDAO, AppointmentDAO appointmentDAO, User user) throws SQLException {
        while (true) {
            System.out.println("\nPatient Menu:");
            System.out.println("1. Book Appointment");
            System.out.println("2. Cancel Appointment");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 3) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter doctor ID to book: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
                    String appointmentDateStr = scanner.nextLine();

                    Doctor doctor = doctorDAO.getDoctorById(doctorId);
                    if (doctor == null) {
                        System.out.println("Doctor not found.");
                        break;
                    }

                    LocalDateTime appointmentDate = LocalDateTime.parse(appointmentDateStr.replace(" ", "T"));
                    Appointment appointment = new Appointment(0, doctor, new Patient(user.getId(), user, null), appointmentDate, "BOOKED");
                    appointmentDAO.bookAppointment(appointment);
                    System.out.println("Appointment booked successfully!");
                    break;
                case 2:
                    System.out.print("Enter appointment ID to cancel: ");
                    int appointmentId = scanner.nextInt();
                    appointmentDAO.cancelAppointment(appointmentId);
                    System.out.println("Appointment canceled successfully!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
