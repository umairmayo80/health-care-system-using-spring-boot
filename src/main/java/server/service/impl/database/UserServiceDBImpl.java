package server.service.impl.database;

import server.context.RepositoryContext;
import server.domain.User;
import server.service.UserService;
import server.dao.impl.database.UserRepoDbImpl;
import server.utilities.DisplayFormatting;

import java.util.List;

public class UserServiceDBImpl implements UserService {
    UserRepoDbImpl userRepoDb;

    public UserServiceDBImpl() {
        userRepoDb = RepositoryContext.getUserRepoDb();
    }

    public List<User> getUsers() {
       return userRepoDb.getUsers();
    }

    // add new user to the database
    @Override
    public boolean addUserEntry(User user) {
        return userRepoDb.addUserEntry(user);
    }

    @Override
    public void addUsersListToStorage(List<User> userList) {
        userList.forEach(this::addUserEntry);
    }


    public User validateUserLogin(String username, String password, String userRole) {
        User user = userRepoDb.getUserByUsername(username);
        if(user==null){
            return null;
        }
        else if(user.getPassword().equals(password) && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }


    @Override
    public void viewUsers() {
        List<User> users = getUsers();
        DisplayFormatting.displayUsers(users);
    }

    @Override
    public void viewPatients() {
        List<User> patients = getPatients();
        DisplayFormatting.displayUsers(patients);
    }

    @Override
    public void viewDoctors() {
        List<User> doctors = getDoctors();
        DisplayFormatting.displayUsers(doctors);
    }

    @Override
    public boolean deleteUser(String username) {
        return userRepoDb.deleteUser(username);
    }

    public List<User> getPatients() {
        return userRepoDb.getPatients();
    }

    public List<User> getDoctors() {
        return userRepoDb.getDoctors();
    }

}
