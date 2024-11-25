
import java.util.Scanner;

public class UserActions {

    private static Scanner scanner = new Scanner(System.in);
    private static boolean isLoggedIn = false;
    private static String currentUserEmail;
    private static boolean isDoctor = false;

    public static void main(String[] args) {
        while (true) {
            if (!isLoggedIn) {
                loginMenu();
            } else {
                // Show the doctor or patient menu depending on the user type
                if (isDoctor) {
                    doctorMenu();
                } else {
                    patientMenu();
                }
            }
        }
    }

    // Login Menu
    public static void loginMenu() {
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        // Assuming you have a method to authenticate the user (this should also check if the user is a doctor or patient)
        if (authenticateUser(email, password)) {
            System.out.println("Login successful! Welcome, " + currentUserEmail);
            isLoggedIn = true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    // Method to authenticate the user and determine if the user is a doctor or patient
    public static boolean authenticateUser(String email, String password) {
        // This is just a mock check; replace with actual authentication logic.
        if (email.equals("doctor@gmail.com") && password.equals("doctor123")) {
            currentUserEmail = email;
            isDoctor = true; // User is a doctor
            return true;
        } else if (email.equals("patient@gmail.com") && password.equals("patient123")) {
            currentUserEmail = email;
            isDoctor = false; // User is a patient
            return true;
        }
        return false; // Invalid credentials
    }

    // Doctor Menu
    public static void doctorMenu() {
        System.out.println("Doctor actions here...");
        System.out.println("1. View Patients");
        System.out.println("2. View Appointments");
        System.out.println("3. Logout");
        System.out.println("Choose an option: ");
        
        int option = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (option) {
            case 1:
                viewPatients();
                break;
            case 2:
                viewAppointments();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }
    }

    // Patient Menu
    public static void patientMenu() {
        System.out.println("Patient actions here...");
        System.out.println("1. Register");
        System.out.println("2. View Medical History");
        System.out.println("3. Logout");
        System.out.println("Choose an option: ");
        
        int option = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (option) {
            case 1:
                registerPatient();
                break;
            case 2:
                viewMedicalHistory();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }
    }

    // Doctor-specific actions
    public static void viewPatients() {
        System.out.println("Viewing list of patients...");
        // Logic to fetch and display patient information
    }

    public static void viewAppointments() {
        System.out.println("Viewing appointments...");
        // Logic to fetch and display appointments
    }

    // Patient-specific actions
    public static void registerPatient() {
        System.out.println("Registering patient...");
        // Logic for registering a patient
    }

    public static void viewMedicalHistory() {
        System.out.println("Viewing medical history...");
        // Logic to display the patient's medical history
    }

    // Method to log out
    public static void logout() {
        System.out.println("Logging out...");
        isLoggedIn = false;
    }
}
