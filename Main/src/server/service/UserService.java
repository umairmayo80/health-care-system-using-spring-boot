package server.service;
import server.domain.User;
import java.util.List;

public interface UserService {
    public final static String userFilePath = "users.csv";

    public List<User> getUsers();

    public void saveUsersToDb(List<User> userList);

    public User validateUserLogin(String username, String password);


    public List<User> getPatients();
    public List<User> getDoctors();


}
