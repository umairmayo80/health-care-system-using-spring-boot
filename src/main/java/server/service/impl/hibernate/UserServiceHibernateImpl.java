package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import server.context.RepositoryContext;
import server.context.ServiceContext;
import server.dao.impl.hibernate.UserRepoHibernateImpl;
import server.domain.Slot;
import server.domain.User;
import server.domain.version1.AppointmentV1;
import server.service.UserService;
import server.utilities.DisplayFormatting;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

public class UserServiceHibernateImpl implements UserService {
    private UserRepoHibernateImpl userRepoHibernate;

    public UserServiceHibernateImpl(){
        userRepoHibernate = RepositoryContext.getUserRepoHibernate();
    }
    @Override
    public List<User> getUsers() {
        return userRepoHibernate.getUsers();
    }


    @Override
    public boolean addUserEntry(User user) {
        return userRepoHibernate.addUserEntry(user);
    }

    @Override
    public void addUsersListToStorage(List<User> userList) {
        userList.forEach(this::addUserEntry);
    }

    @Override
    public User validateUserLogin(String username, String password, String userRole) {
        User user = userRepoHibernate.getUserByUsername(username);
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
        return userRepoHibernate.deleteUser(username);
    }
}
