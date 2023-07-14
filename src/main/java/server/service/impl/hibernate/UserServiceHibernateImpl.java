package server.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.dao.impl.hibernate.UserRepoHibernateImpl;
import server.domain.User;
import server.service.UserService;
import server.utilities.DisplayFormatting;

import java.util.List;

@Component
@Scope("singleton")
public class UserServiceHibernateImpl implements UserService {
    private UserRepoHibernateImpl userRepoHibernate;

    public UserServiceHibernateImpl(){
    }

    @Autowired
    public void setUserRepoHibernate(UserRepoHibernateImpl userRepoHibernate) {
        this.userRepoHibernate = userRepoHibernate;
    }

    @Override
    public List<User> getUsers() {
        return userRepoHibernate.getAll();
    }


    @Override
    public boolean addUserEntry(User user) {
        return userRepoHibernate.add(user);
    }

    @Override
    public void addUsersListToStorage(List<User> userList) {
        userList.forEach(this::addUserEntry);
    }

    @Override
    public User validateUserLogin(String username, String password, String userRole) {
        User user = userRepoHibernate.getByUsername(username);
        if(user==null){
            return null;
        }
        else if(user.getPassword().equals(password) && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    @Override
    public List<User> getPatients() {
        return userRepoHibernate.getPatients();
    }

    @Override
    public List<User> getDoctors() {
        return userRepoHibernate.getDoctors();
    }

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
        return userRepoHibernate.delete(username);
    }
}
