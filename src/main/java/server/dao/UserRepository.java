package server.dao;

import server.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    boolean addUserEntry(User user);
    void addUsersListToStorage(List<User> userList);
    List<User> getPatients();
    List<User> getDoctors();
    boolean deleteUser(String username);
    User getUserByUsername(String username);
}
