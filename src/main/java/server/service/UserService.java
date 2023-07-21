package server.service;
import server.domain.User;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    boolean addUserEntry(User user);

    User validateUserLogin(String username, String password, String userRole);

    List<User> getPatients();
    List<User> getDoctors();

    List<User> getByRole(String role);

    boolean deleteUser(String username);

    int setUserAccountStatus(String username,boolean status);

    User getByUsername(String username);

    User getByUserId(int userId);


}
