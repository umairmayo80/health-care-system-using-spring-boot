package server.service;
import server.domain.User;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    boolean addUserEntry(User user);
    void addUsersListToStorage(List<User> userList);

    User validateUserLogin(String username, String password, String userRole);

    List<User> getPatients();
    List<User> getDoctors();

    void viewUsers();
    void viewPatients();
    void viewDoctors();

    boolean deleteUser(String username);


}
