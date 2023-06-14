import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin(String name) {
        super(name, "admin");
    }

    public Admin(String name, String username, String password) {
        super(name, "admin", username, password);
    }

    public void addUser(User user) {
        List<String> lines = new ArrayList<>();
        lines.add(user.getId() + "," + user.getName() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getRoll()
        + "," +user.getAccountStatus());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error adding user to the file: " + e.getMessage());
        }
    }

    public void viewUsers() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("users.csv"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String roll = parts[4];
                    boolean accountLocked = Boolean.parseBoolean(parts[5]);

                    User user = new User(id,name, roll, username, password,accountLocked);
//                    user.setId(id);

                    System.out.println(user.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users from the file: " + e.getMessage());
        }
    }

    public void setUserAccountStatus(User user,boolean status) {
        user.accountLocked = status;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", roll='" + super.getRoll() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", accountStatus='" + super.accountLocked + '\'' +
                '}';
    }
}
