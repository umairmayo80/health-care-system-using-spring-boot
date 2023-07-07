package server.service.impl.FileSystem;
import server.context.RepositoryContext;
import server.domain.User;
import server.service.UserService;
import server.dao.impl.fileSystem.UserRepoFileImpl;

import java.util.List;

public class UserServiceImpl implements UserService{
    public final static String userFilePath = "users.csv";
    private final UserRepoFileImpl userRepoFile;

    public UserServiceImpl(){
        userRepoFile = RepositoryContext.getUserRepoFile();
    }

    public List<User> getUsers() {
        return userRepoFile.getUsers();
    }

    @Override
    public boolean addUserEntry(User user) {
       return userRepoFile.addUserEntry(user);
    }

    // saves the users list to file. It overwrites the previous data with new data
    public void addUsersListToStorage(List<User> userList){
        userRepoFile.addUsersListToStorage(userList);
    }

    public User validateUserLogin(String username, String password, String userRole){
        // Logic to validate username and password
       User user = userRepoFile.getUserByUsername(username);
       if(user==null){
           return null;
       }
       else if(user.getPassword().equals(password) && user.getPassword().equals(password)){
           return user;
       }
       return null;
    }



    public void viewUsers() {
        List<User> users = getUsers();
        for(User user: users){
            System.out.println(user.toString());
        }
    }
    public List<User> getPatients() {
        return userRepoFile.getPatients();
    }
    public List<User> getDoctors() {
        return userRepoFile.getDoctors();
    }

    public void viewPatients(){
        List<User> users = getPatients();
        for(User user: users){
            System.out.println(user.toString());
        }
    }

    public void viewDoctors(){
        List<User> users = getDoctors();
        for(User user: users){
            System.out.println(user.toString());
        }
    }

    @Override
    public boolean deleteUser(String username) {
        return userRepoFile.deleteUser(username);
    }


}
