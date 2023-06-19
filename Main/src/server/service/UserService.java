package server.service;

import server.domain.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    public final static String userFilePath = "users.csv";

    public List<User> getUsers();

    public void saveUsersToDb(List<User> userList);

    public User validateUserLogin(String username, String password);


    public void viewUsers();
    public void viewPatients();
    public void viewDoctors();


}
