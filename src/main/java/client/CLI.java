package client;

import server.context.ServiceContext;

import server.domain.Slot;
import server.domain.User;
import server.domain.version1.AppointmentV1;
import server.service.*;
import server.service.impl.Database.AdminServiceDBImpl;
import server.service.impl.Database.AppointmentServiceV1DBImpl;
import server.service.impl.Database.SlotsServiceDBImpl;
import server.service.impl.Database.UserServiceDBImpl;
import server.service.impl.FileSystem.*;
import server.service.impl.hibernate.*;
import server.service.version1.AppointmentServiceV1;
import server.utilities.InitializeHibernateDb;
import server.utilities.ScheduleCreationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final UserService userService;
    private final AdminService adminService;
    private User currentUser; // to hold the current user
    private final AppointmentServiceV1 appointmentServiceV1;
    private final SlotService slotService;


    // Constructor injection
    CLI( UserService userService, AdminService adminService,
        PatientService patientService, DoctorService doctorService,
        AppointmentServiceV1 appointmentServiceV1, SlotService slotService) {
        this.userService = userService;
        this.adminService = adminService;
        this.patientService = patientService;
        this.doctorService = doctorService;

        this.appointmentServiceV1 = appointmentServiceV1;
        this.slotService = slotService;

        this.currentUser = null;
        this.scanner = ServiceContext.getScanner();
    }

    private static CLI initializeCLI() {
        int choice = 0;
        do {
            System.out.print("\nWelcome to Health Care System"
                    + "Select Storage Type:"
                    + "\n\t1. File System"
                    + "\n\t2. MySQL Database"
                    + "\n\t3. Hibernate"
                    + "\n\t4. Exit"
                    + "\n\tEnter your choice:");
            try {
                choice = Integer.parseInt(ServiceContext.getScanner().nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    UserService userServiceFile = new UserServiceImpl();
                    AdminService adminServiceFile = new AdminServiceImpl();
                    PatientService patientServiceFile = new PatientServiceImpl();
                    DoctorService doctorServiceFile = new DoctorServiceImpl();
                    AppointServiceV1FileImpl appointmentServiceFile = new AppointServiceV1FileImpl();
                    SlotServiceFileImpl slotServiceFile = new SlotServiceFileImpl();
                    return new CLI(userServiceFile, adminServiceFile, patientServiceFile, doctorServiceFile,
                            appointmentServiceFile, slotServiceFile);

                case 2:
                    UserService userServiceDB = new UserServiceDBImpl();
                    AdminService adminServiceDB = new AdminServiceDBImpl();
                    PatientService patientServiceDB = new PatientServiceImpl();
                    DoctorService doctorServiceDB = new DoctorServiceImpl();
                    AppointmentServiceV1 appointmentServiceDB = new AppointmentServiceV1DBImpl();
                    SlotService slotService1 = new SlotsServiceDBImpl();
                    return new CLI(userServiceDB, adminServiceDB, patientServiceDB, doctorServiceDB,
                            appointmentServiceDB, slotService1);
                case 3:
                    UserService userServiceHibernate = new UserServiceHibernateImpl();
                    AdminService adminServiceHibernate = new AdminServiceHibernateImpl();
                    PatientService patientServiceHibernate = new PatientServiceHibernateImpl();
                    DoctorService doctorServiceHibernate = new DoctorServiceHibernateImpl();
                    AppointmentServiceV1 appointmentServiceHibernate = new AppointmentServiceV1HibernateImpl();
                    SlotService slotServiceHibernate = new SlotServiceHibernateImpl();

                    // Initialize Hibernate System
                    InitializeHibernateDb.initializeHibernateDb();

                    return new CLI(userServiceHibernate, adminServiceHibernate, patientServiceHibernate, doctorServiceHibernate,
                            appointmentServiceHibernate, slotServiceHibernate);
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);

    }

    public static void main(String[] args) {
        CLI cli = CLI.initializeCLI();
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
        if (user != null && user.getRole().equals(userRoll.toLowerCase())) {
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
        System.out.println("Welcome Admin " + currentUser.getName());
        int choice;

        do {
            System.out.println("=== Admin Menu ===");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. View Patients");
            System.out.println("4. View Doctors");
            System.out.println("5. Lock User");
            System.out.println("6. Unlock User");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. Delete User");
            System.out.println("9. Logout");
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
                    deleteUser();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    currentUser = null; // Reset the current user
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    private void deleteUser() {
        // Implementation for adding a user
        System.out.println("Delete User function called");

        System.out.print("Enter target user`s username:");
        String username = scanner.nextLine().strip();

        //Save the user to database
        if (userService.deleteUser(username)) {
            System.out.println("User Deleted Successfully!");
        } else {
            System.out.println("Error: Unable to delete user.");
        }

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
        } else {
            System.out.println("Error: Unable to add user.");
        }
    }

    public void viewAllUsers() {
        System.out.println("View All Users function called");
        userService.viewUsers();
    }


    public void viewPatients() {
        // Implementation for viewing patients
        System.out.println("View Patients function called");
        userService.viewPatients();

    }

    public void viewDoctors(){
        System.out.println("View Doctors function called");
        userService.viewDoctors();
    }

    public void viewAvailableDoctorSlots() {
        // Implementation for viewing doctors
        System.out.println("View Available Doctor Slots function called");
        slotService.viewFreeSlots();
    }

    public void viewAppointments() {
        System.out.println("View Appointments function called");
        appointmentServiceV1.viewAllAppointments();
    }

    public void lockUser() {
        // Implementation for locking a user
        System.out.println("Lock User function called");
        System.out.print("Enter target username:");
        String username = scanner.nextLine().strip();
        if (adminService.setUserAccountStatus(username, true)) {
            System.out.println("\t'" + username + "' account locked");
        } else {
            System.out.println("\tError: unable to update to account status");
        }
    }

    public void unlockUser() {
        // Implementation for unlocking a user
        System.out.println("UnLock server.domain.User function called");
        System.out.print("Enter target username:");
        String username = scanner.nextLine().strip();
        if (adminService.setUserAccountStatus(username, false)) {
            System.out.println("\t'" + username + "' account unlocked");
        } else {
            System.out.println("\tError: unable to update to account status");
        }
    }


    public void doctorMenu() {
        System.out.println("Doctor menu called");
        boolean login = validateLogin("Doctor");
        if (!login) return;
        System.out.println("Doctor " + currentUser.getName());
        int choice = 0;

        do {
            System.out.println("=== Doctor Menu ===");
            System.out.println("1. View Scheduled Slots");
            System.out.println("2. View Appointments");
            System.out.println("3. Add New Slot entry");
            System.out.println("4. View Booked Slots");
            System.out.println("5. View Free Slots");
            System.out.println("6. Logout");
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
                    newSlotEntry();
                    break;
                case 4:
                    viewBookedSlots();
                    break;
                case 5:
                    viewFreeSlots();
                    break;

                case 6:
                    System.out.println("Logging out...");
                    currentUser = null; // Reset the current user
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    public void viewBookedSlots() {
        System.out.println("View Booked Slots function called");
        slotService.viewBookedSlotsById(currentUser.getUserId());
    }

    public void viewFreeSlots() {
        System.out.println("View Free Slots Function called");
        slotService.viewFreeSlotsById(currentUser.getUserId());
    }

    public void patientMenu() {
        System.out.println("Patient menu called");
        boolean login = validateLogin("Patient");
        if (!login) return;
        System.out.println("Welcome server.domain.Patient " + currentUser.getName());
        int choice = 0;

        do {
            System.out.println("=== Patient Menu ===");
            System.out.println("1. View Available Doctor slots");
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
                    viewAvailableDoctorSlots();
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
        int selectedSlotId = 0;
        System.out.println("Create new Appointment function called");
        System.out.println("\t--------------List of available slots-----------");
        slotService.viewFreeSlots();
        System.out.print("\tEnter desired slot id:");
        try {
            selectedSlotId = Integer.parseInt(scanner.nextLine().strip());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input");
            return;
        }
        AppointmentV1 newAppointment = new AppointmentV1(currentUser.getUserId(), selectedSlotId);

        // now associate the new appointment with the parents
        currentUser.addAppointmentV1(newAppointment);

        if (appointmentServiceV1.addAppointmentEntry(newAppointment))
            System.out.println("Appointment created successfully");
    }

    public void viewPatientAppointments() {
        appointmentServiceV1.viewAppointmentsByPatientId(currentUser.getUserId());
    }

    public void viewSchedule() {
        System.out.println("View schedule function called...");
        slotService.viewSlotsById(currentUser.getUserId());
    }

    public void viewDoctorAppointments() {
        System.out.println("Viewing doctor appointments function called...");
        appointmentServiceV1.viewAppointmentsByDoctorId(currentUser.getUserId());
    }

    public void newSlotEntry() {
        System.out.println("Adding New Slot Entry function called...");

        int doctorId = currentUser.getUserId();

        // Prompt the user for input
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine().strip();

        System.out.print("Enter startTime (HH:MM): ");
        String startTime = scanner.nextLine().strip();

        System.out.print("Enter endTime (HH:MM): ");
        String endTime = scanner.nextLine().strip();
        try {
            Slot newSlot = new Slot(doctorId, date, startTime, endTime);
            //associate the slot with the parent
            currentUser.addSlot(newSlot);

            if (slotService.addSlotEntry(newSlot))
                System.out.println("Entry added successfully");
            else
                System.out.println("Error, unable to add new slot");
        } catch (ScheduleCreationException e) {
            System.out.println("Error creating new slot: " + e.getMessage());
        }

    }
}



