import java.io.IOException;
import java.lang.invoke.CallSite;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("users.csv"));
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


}
