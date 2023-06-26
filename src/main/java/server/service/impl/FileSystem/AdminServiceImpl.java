package server.service.impl.FileSystem;

import server.context.ServiceContext;
import server.domain.User;
import server.service.AdminService;

import java.util.List;
import java.util.Optional;


public class AdminServiceImpl implements AdminService {

    private boolean isUsernameAvailable(String username) {
        List<User> userList = ServiceContext.getUserService().getUsers();
        for (User user : userList) {
            String existingUsername = user.getUsername();
            if (existingUsername.equals(username)) {
                return false; // Username already exists
            }
        }
        return true; // Username is available
    }

    public boolean addUser(User user) {
        if (isUsernameAvailable(user.getUsername())) {
            return ServiceContext.getUserService().addUserEntry(user);
        } else {
            System.out.println("Error: Username '" + user.getUsername() + "' already exists. Please choose a different username.");
            return false;
        }
    }


    public boolean setUserAccountStatus(String username,boolean status) {

        Optional<User> targetUser = ServiceContext.getUserService().getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        if(targetUser.isPresent())
        {
            User user = targetUser.get();
            System.out.println(user);
            //code to update the current user in list and then store the complete list on disk
            List<User> userList = ServiceContext.getUserService().getUsers();
            int targetIndex = userList.indexOf(user);
            System.out.println(targetIndex);

            userList.remove(targetIndex);

            //update the user account status
            user.setAccountStatus(status);

            System.out.println("--");

//             add the updated
            userList.add(targetIndex,targetUser.get());

            ServiceContext.getUserService().addUsersListToStorage(userList);

            return true;

        }

        System.out.printf("Error: No User found against this the provided '%s' username.\n",username);
        return false;

    }


}
