package server.dao;

import server.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();
    boolean add (User user);
    void addUsersListToStorage(List<User> userList);
    List<User> getPatients();
    List<User> getDoctors();
    boolean delete(String username);
    User getByUsername(String username);
}
