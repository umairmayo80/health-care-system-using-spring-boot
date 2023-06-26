package client;

import server.context.ServiceContext;
import server.domain.Appointment;
import server.domain.Schedule;
import server.domain.User;
import server.service.*;
import server.utilities.ScheduleCreationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OldCLIUsingFile {
    private final Scanner scanner;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final UserService userService;
    private final AdminService adminService;
    private final AppointmentService appointmentService;
    private User currentUser; // to hold the current user


    OldCLIUsingFile() {
        this.patientService = ServiceContext.getPatientService();
        this.doctorService = ServiceContext.getDoctorService();
        this.userService = ServiceContext.getUserService();
        this.adminService = ServiceContext.getAdminService();
        this.appointmentService = ServiceContext.getAppointmentService();
        this.currentUser = null;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        OldCLIUsingFile cli = new OldCLIUsingFile();
        cli.displayWelcomeMenu();
    }

    public void displayWelcomeMenu() {

        int choice = 0;

        do {
            System.out.println("=== Health Care System ===");
            System.out.println("1. Login");
            System.out.println("2. About");
            System.out.println("3. Help");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    about();
                    break;
                case 3:
                    help();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);
    }

    public void login() {
        int choice = 0;
        do {
            System.out.println("=== Login As ===");
            System.out.println("1. Admin");
            System.out.println("2. Patient");
            System.out.println("3. Doctor");
            System.out.println("4. Back to Menu");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    patientMenu();
                    break;
                case 3:
                    doctorMenu();
                    break;
                case 4:
                    System.out.println("Menu Back");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);

    }

    public boolean validateLogin(String userRoll) {
        System.out.printf("=== Login As %s===\n", userRoll);
        System.out.print("Enter username: ");
        String username = scanner.nextLine().strip();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().strip();

//        scanner.close();
        User user = userService.validateUserLogin(username, password, userRoll);
        if (user != null && user.getRoll().equals(userRoll.toLowerCase())) {
            System.out.println("Login successful!");
            currentUser = user;
            return true;
        }

        System.out.println("Invalid username or password. Please try again.");
        return false;
    }


    public void about() {
        System.out.println("About function called");
    }

    public void help() {
        System.out.println("Help function called");
    }


    public void adminMenu() {
        boolean login = validateLogin("Admin");
        if (!login) return;
        System.out.println("Welcome server.domain.Admin " + currentUser.getName());
        int choice;

        do {
            System.out.println("=== server.domain.Admin Menu ===");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. View Patients");
            System.out.println("4. View Doctors");
            System.out.println("5. Lock User");
            System.out.println("6. Unlock User");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().strip());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    viewPatients();
                    break;
                case 4:
                    viewDoctors();
                    break;
                case 5:
                    lockUser();
                    break;
                case 6:
                    unlockUser();
                    break;
                case 7:
                    viewAppointments();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    currentUser = null; // Reset the current user
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    public void addUser() {
        // Implementation for adding a user
        System.out.println("Add User function called");

        System.out.println("Enter user information:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Roll: ");
        String roll = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Create server.domain.User object with the entered information
        User user = new User(name, roll, username, password);

        //Save the user to database
        if (adminService.addUser(user)) {
            System.out.println("User Added Successfully!");
        }


    }


    public void displayUsers(List<User> usersList) {
        // Print table header
        System.out.format("%-5s %-15s %-10s %-15s %-15s %-10s%n", "ID", "Name", "Role", "Username", "Password", "Account Status");

        // Print table rows
        for (User user : usersList) {
            System.out.format("%-5d %-15s %-10s %-15s %-15s %-10s%n",
                    user.getId(), user.getName(), user.getRoll(), user.getUsername(), user.getPassword(), user.getAccountStatus());
        }
    }

    public void viewAllUsers() {
        System.out.println("View All Users function called");

        List<User> usersList = userService.getUsers();
        displayUsers(usersList);
    }


    public void viewPatients() {
        // Implementation for viewing patients
        System.out.println("View Patients function called");
        List<User> patients = userService.getPatients();

        displayUsers(patients);

    }

    public void viewDoctors() {
        // Implementation for viewing doctors
        System.out.println("View Doctors function called");
        List<User> doctors = userService.getDoctors();
        displayUsers(doctors);
    }

    public void viewAppointments() {
        System.out.println("View Appointments function called");
        List<Appointment> appointmentList = appointmentService.getAppointments();

        System.out.println("patient_id,doctor_id,datetime");
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment.getPatientId() + "," + appointment.getDoctorId()
                    + "," + appointment.getDateTime());
        }
    }

    public void lockUser() {
        // Implementation for locking a user
        System.out.println("Lock server.domain.User function called");
        System.out.print("Enter target username:");
        String username = scanner.nextLine().strip();
        adminService.setUserAccountStatus(username, true);
    }

    public void unlockUser() {
        // Implementation for unlocking a user
        System.out.println("UnLock server.domain.User function called");
        System.out.print("Enter target username:");
        String username = scanner.nextLine().strip();
        adminService.setUserAccountStatus(username, false);
    }


    public void doctorMenu() {
        System.out.println("server.domain.Doctor menu called");
        boolean login = validateLogin("Doctor");
        if (!login) return;
        System.out.println("Welcome server.domain.Doctor " + currentUser.getName());
        int choice = 0;

        do {
            System.out.println("=== server.domain.Doctor Menu ===");
            System.out.println("1. View Schedule");
            System.out.println("2. View Appointments");
            System.out.println("3. Create new shift entry");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    viewSchedule();
                    break;
                case 2:
                    viewDoctorAppointments();
                    break;
                case 3:
                    newScheduleEntry();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    currentUser = null; // Reset the current user
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    public void patientMenu() {
        System.out.println("server.domain.Patient menu called");
        boolean login = validateLogin("Patient");
        if (!login) return;
        System.out.println("Welcome server.domain.Patient " + currentUser.getName());
        int choice = 0;

        do {
            System.out.println("=== server.domain.Patient Menu ===");
            System.out.println("1. View Doctors");
            System.out.println("2. View Appointments");
            System.out.println("3. Create Appointment");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    viewDoctors();
                    break;
                case 2:
                    viewPatientAppointments();
                    break;
                case 3:
                    createAppointment();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    currentUser = null; // Reset the current user
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    private void createAppointment() {
        int docId = 0;
        LocalDateTime dateTime = null;
        System.out.println("createAppointments method called");
        System.out.println("Enter appointment details:");
        System.out.print("\tEnter doctor ID:");
        try {
            docId = Integer.parseInt(scanner.nextLine().strip());
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid Input. Try again.");
            return;
        }
        System.out.print("\tEnter appointment date and time (input format:2000-12-30T09:30:00):");
        try {
            dateTime = LocalDateTime.parse(scanner.nextLine().strip());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Input");
            return;
        }
        Appointment newAppointment = new Appointment(currentUser.getId(), docId, dateTime);
        if (patientService.addAppointment(newAppointment)) {
            System.out.println("Appointment successfully created ");
            return;
        }
        System.out.println("No Doctor available at this ");

    }

    public void viewPatientAppointments() {
        List<Appointment> patientAppointments = appointmentService.getAppointmentsByPatientId(currentUser.getId());
        System.out.println("patient_id,doctor_id,datetime");
        for (Appointment appointment : patientAppointments) {
            System.out.println(appointment.getPatientId() + "," + appointment.getDoctorId()
                    + "," + appointment.getDateTime());
        }
    }

    public void viewSchedule() {
        System.out.println("View schedule function called...");
        doctorService.viewSlots(currentUser.getId());

    }

    public void viewDoctorAppointments() {
        System.out.println("Viewing doctor appointments function called...");
        List<Appointment> appointmentsByDoctorId = appointmentService.getAppointmentsByDoctorId(currentUser.getId());
        System.out.println("patient_id,doctor_id,datetime");
        for (Appointment appointment : appointmentsByDoctorId) {
            System.out.println(appointment.getPatientId() + "," + appointment.getDoctorId()
                    + "," + appointment.getDateTime());
        }
    }

    public void newScheduleEntry() {
        System.out.println("Creating new schedule entry function called...");

        System.out.println("Enter shift details:");

        System.out.print("\tEnter date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().strip();

        System.out.print("\tEnter start time (HH:MM:SS): ");
        String startTimeStr = scanner.nextLine().strip();

        System.out.print("\tEnter end time (HH:MM:SS): ");
        String endTimeStr = scanner.nextLine().strip();


        try {
            Schedule newSchedule = new Schedule(currentUser.getId(), dateStr,
                    startTimeStr, endTimeStr);
            doctorService.addScheduleSlots(newSchedule);
            System.out.println("Entry added successfully");
        } catch (ScheduleCreationException e) {
            System.out.println("Error creating schedule: " + e.getMessage());
        }


    }
}



