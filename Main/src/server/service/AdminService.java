package server.service;
import server.domain.User;
import java.util.List;
import java.util.Optional;


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

    public static boolean addUser(User user) {
        if (isUsernameAvailable(user.getUsername())) {

            List<User> userList = UserService.getUsers();
            userList.add(user);
            UserService.saveUsersToDb(userList);
            return true;

        } else {
            System.out.println("Error: Username '" + user.getUsername() + "' already exists. Please choose a different username.");
            return false;
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
//            UserService.viewUsers();
            userList.remove(targetIndex);

            //update the user account status
            user.setAccountStatus(status);

            System.out.println("--");

//             add the updated
            userList.add(targetIndex,targetUser.get());
//            System.out.println(userList);
            UserService.saveUsersToDb(userList);
//            UserService.viewUsers();
            return;


        }

        System.out.printf("Error: No server.domain.User found against this the provided '%s' username.\n",username);

    }

    public static void main(String[] args){
        UserService.viewPatients();

        AdminService.addUser(UserService.getUsers().get(0));

        AdminService.setUserAccountStatus("admin123000",true);
        AdminService.setUserAccountStatus("admin123",true);
    }

}
