import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private static boolean isUsernameAvailable(String username) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("users.csv"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String existingUsername = parts[2];
                    if (existingUsername.equals(username)) {
                        return false; // Username already exists
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users from the file: " + e.getMessage());
        }
        return true; // Username is available
    }

    public static void addUser(User user) {
        if (isUsernameAvailable(user.getUsername())) {

            List<User> userList = UserService.getUsers();
            userList.add(user);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", false))) {
                for (User itrUser : userList) {
                    writer.write(itrUser.getId() + "," + itrUser.getName() + "," + itrUser.getUsername() + "," + itrUser.getPassword() + "," + itrUser.getRoll()
                            + "," + itrUser.getAccountStatus());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error adding user to the file: " + e.getMessage());
            }

        } else {
            System.out.println("Username '" + user.getUsername() + "' already exists. Please choose a different username.");
        }
    }

    public static void viewUsers() {
        List<User> users = UserService.getUsers();
        for(User user: users){
            System.out.println(user.toString());
        }
    }

    public static void setUserAccountStatus(User user,boolean status) {
        user.accountLocked = status;
        // Now write code to update the current user in list and then store the complete list on disk
    }

}
