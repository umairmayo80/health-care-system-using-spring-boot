import org.w3c.dom.ls.LSException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminService {

    private static boolean isUsernameAvailable(String username) {
            List<User> userList = UserService.getUsers();
            for (User user : userList) {
                    String existingUsername = user.getUsername();
                    if (existingUsername.equals(username)) {
                        return false; // Username already exists
                    }
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
            System.out.println("Error: Username '" + user.getUsername() + "' already exists. Please choose a different username.");
        }
    }

    public static void viewUsers() {
        List<User> users = UserService.getUsers();
        for(User user: users){
            System.out.println(user.toString());
        }
    }    public static void viewPatients() {
        List<User> users = UserService.getUsers();
        List<User> patients = users.stream()
                .filter(user -> user.getRoll().equals("patient"))
                .collect(Collectors.toList());

        for(User patient: patients){
            System.out.println(patient.toString());
        }
    }

    public static void setUserAccountStatus(String username,boolean status) {

        Optional<User> targetUser = UserService.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        if(targetUser.isPresent())
        {
            User user = targetUser.get();
            System.out.println(user);
            // Now write code to update the current user in list and then store the complete list on disk
            List<User> userList = UserService.getUsers();
            int targetIndex = userList.indexOf(user);
            System.out.println(targetIndex);
            AdminService.viewUsers();
            userList.remove(targetIndex);

            //update the user account status
            user.setAccountStatus(status);

            System.out.println("--");

//             add the updated
            userList.add(targetIndex,targetUser.get());
            AdminService.viewUsers();
            return;


        }

        System.out.printf("Error: No User found against this the provided '%s' username.\n",username);

    }

    public static void main(String[] args){
        AdminService.viewPatients();

        AdminService.addUser(UserService.getUsers().get(0));

        AdminService.setUserAccountStatus("admin123000",true);
        AdminService.setUserAccountStatus("admin123",true);
    }

}
