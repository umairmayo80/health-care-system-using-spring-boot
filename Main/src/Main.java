import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static User currentUser; // Static field to hold the current user
    public static void main(String[] args){
        System.out.println("ok");
        displayWelcomeMenu();

//
//
//        Admin admin = new Admin("A2","admin123","admin123");
//        System.out.println(admin);
//
//
//
//
//        admin.addUser(admin);
//
//        User user = new User(-1,"test user","user","user123","user123",true);
//
//        admin.addUser(user);
//
//        admin.viewUsers();



    }

    public static void displayWelcomeMenu() {

        int choice;

        do {
            System.out.println("=== Health Care System ===");
            System.out.println("1. Login");
            System.out.println("2. About");
            System.out.println("3. Help");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

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

    public static void login() {
        System.out.println("=== Login ===");
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

//        scanner.close();
        // Logic to validate username and password
        List<User> users = UserService.getUsers();
        Optional<User> loginUser = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password) )
                .findFirst();
        if(loginUser.isPresent())
        {
            currentUser = loginUser.get();
            System.out.println("Login successful!");

            // display control menu according to the user roll
            if(currentUser.getRoll().equals("admin")){
                adminMenu();
            }
        }
        else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }


    public static void about() {
        // Implementation for the about functionality
        System.out.println("About function called");
    }

    public static void help() {
        // Implementation for the help functionality
        System.out.println("Help function called");
    }


    public static void adminMenu() {
        System.out.println("Welcome Admin " + currentUser.getName());
        int choice;

        do {
            System.out.println("=== Admin Menu ===");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. View Patients");
            System.out.println("4. View Doctors");
            System.out.println("5. Lock User");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

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
                    System.out.println("Logging out...");
                    currentUser = null; // Reset the current user
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    public static void addUser() {
        // Implementation for adding a user
        System.out.println("Add User function called");
    }

    public static void viewAllUsers() {
        // Implementation for viewing all users
        System.out.println("View All Users function called");
    }

    public static void viewPatients() {
        // Implementation for viewing patients
        System.out.println("View Patients function called");
    }

    public static void viewDoctors() {
        // Implementation for viewing doctors
        System.out.println("View Doctors function called");
    }

    public static void lockUser() {
        // Implementation for locking a user
        System.out.println("Lock User function called");
    }

}



