package server.dao.impl.fileSystem;
import server.domain.User;
import server.dao.UserRepository;
import server.service.impl.fileSystem.UserServiceImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepoFileImpl implements UserRepository {
    public final static String userFilePath = "users.csv";

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(UserRepoFileImpl.userFilePath));
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
                    users.add(user);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading users from the file: " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean addUserEntry(User user) {
        List<User> userList= getUsers();
        userList.add(user);
        addUsersListToStorage(userList);
        return true;
    }

    @Override
    // saves the users list to file. It overwrites the previous data with new data
    public void addUsersListToStorage(List<User> userList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UserServiceImpl.userFilePath, false))) {
            for (User itrUser : userList) {
                writer.write(itrUser.getUserId() + "," + itrUser.getName() + "," + itrUser.getUsername() + "," + itrUser.getPassword() + "," + itrUser.getRole()
                        + "," + itrUser.getAccountStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error adding user to the file: " + e.getMessage());
        }
    }


    @Override
    public List<User> getPatients() {
        List<User> users = getUsers();
        List<User> patients = users.stream()
                .filter(user -> user.getRole().equals("patient"))
                .collect(Collectors.toList());
        return patients;

    }

    @Override
    public List<User> getDoctors() {
        List<User> users = getUsers();
        List<User> doctors = users.stream()
                .filter(user -> user.getRole().equals("doctor"))
                .collect(Collectors.toList());
        return doctors;
    }


    @Override
    public boolean deleteUser(String username) {
        System.out.println("File System impl is pending");
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        // Logic to validate username and password
        List<User> users = getUsers();
        Optional<User> loginUser = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        return loginUser.orElse(null);
    }


}
