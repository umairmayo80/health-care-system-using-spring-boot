package server.service;
import server.domain.User;
import java.util.List;

public interface UserService {
    public final static String userFilePath = "users.csv";

    List<User> getUsers();

    void saveUsersToDb(List<User> userList);


    User validateUserLogin(String username, String password, String userRole);

    List<User> getPatients();
    List<User> getDoctors();

    void viewUsers();
    void viewPatients();
    void viewDoctors();


}
