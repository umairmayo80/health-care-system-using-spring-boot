package server.impl;

import server.domain.User;
import server.service.UserService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService{

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(UserService.userFilePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1].strip();
                    String username = parts[2].strip();
                    String password = parts[3].strip();
                    String roll = parts[4].strip();
                    boolean accountLocked = Boolean.parseBoolean(parts[5]);

                    User user = new User(id,name, roll, username, password,accountLocked);
//                    user.setId(id);
                    users.add(user);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading users from the file: " + e.getMessage());
        }
        return users;
    }

    public void saveUsersToDb(List<User> userList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UserService.userFilePath, false))) {
            for (User itrUser : userList) {
                writer.write(itrUser.getId() + "," + itrUser.getName() + "," + itrUser.getUsername() + "," + itrUser.getPassword() + "," + itrUser.getRoll()
                        + "," + itrUser.getAccountStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error adding user to the file: " + e.getMessage());
        }
    }

    public User validateUserLogin(String username, String password){
        // Logic to validate username and password
        List<User> users = getUsers();
        Optional<User> loginUser = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password) )
                .findFirst();
        return loginUser.orElse(null);

    }



    public void viewUsers() {
        List<User> users = getUsers();
        for(User user: users){
            System.out.println(user.toString());
        }
    }
    public void viewPatients() {
        List<User> users = getUsers();
        List<User> patients = users.stream()
                .filter(user -> user.getRoll().equals("patient"))
                .collect(Collectors.toList());

        for(User patient: patients){
            System.out.println(patient.toString());
        }
    }
    public void viewDoctors() {
        List<User> users = getUsers();
        List<User> doctors = users.stream()
                .filter(user -> user.getRoll().equals("doctor"))
                .collect(Collectors.toList());

        for(User doctor: doctors){
            System.out.println(doctor.toString());
        }
    }


}
