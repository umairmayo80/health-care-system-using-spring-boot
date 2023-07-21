package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.dao.UserRepository;
import server.domain.Appointment;
import server.domain.Slot;
import server.domain.User;
import server.service.UserService;

import java.util.List;

@Service
@Scope("singleton")
public class UserServiceHibernateImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceHibernateImpl(){
    }

    @Autowired
    public UserServiceHibernateImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public boolean addUserEntry(User user) {
        // add logic to check if username already taken or not?
        User existingUser = userRepository.getUserByUsername(user.getUsername());
        if(existingUser!=null){
            throw new IllegalStateException("Username '"+user.getUsername()+"' is not available");
        }
        userRepository.save(user);
        return true;
    }


    @Override
    public User validateUserLogin(String username, String password, String userRole) {
        User user = userRepository.getUserByUsername(username);
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
        return userRepository.getUsersByRole("patient");
    }

    @Override
    public List<User> getDoctors() {
        return userRepository.getUsersByRole("doctor");
    }

    @Override
    public List<User> getByRole(String role) {
        if(role.equals("admin") || role.equals("patient") || role.equals("doctor")){
            return userRepository.getUsersByRole(role);
        }
        throw new IllegalStateException("Invalid role.");
    }

    @Override
    public boolean deleteUser(String username) {
        User user = userRepository.getUserByUsername(username);
        if(user==null){
            throw new IllegalStateException("No user found against provided username:"+username);
        }

        // Remove the association between AppointmentV1 and Slot entities
        for (Appointment appointment : user.getAppointmentV1List()) {
            Slot slot = appointment.getSlot();
            slot.removeAppointmentV1(); // If we don`t remove the child appointment from parent slot, hibernate will through exception Exception in thread "main" javax.persistence.EntityNotFoundException: deleted object would be re-saved by cascade (remove deleted object from associations): [server.domain.AppointmentV1#1]
        }

        // As we have already defined cascading to parent will automatically delete the children upon deletion, so no need
        user.getAppointmentV1List().clear();
        user.getSlots().clear();

        userRepository.delete(user);
        return true;
    }

    @Transactional
    @Override
    public int setUserAccountStatus(String username, boolean status) {
        return userRepository.updateUserAccountStatus(username,status);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getByUserId(int userId) {
        return userRepository.getUserByUserId(userId);
    }
}
