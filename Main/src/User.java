import java.io.*;
import java.util.Objects;

public class User {
    private static final String ID_FILE_PATH = "lastAssignedId.txt";
    private static int lastAssignedId;

    private final int id;
    private String name;
    private String roll;
    private String username;
    private String password;

    private boolean accountLocked;


    static {
        loadLastAssignedId();
    }

    public User(String name, String roll) {
        this(name, roll, "", "");
    }

    public User(String name, String roll, String username, String password) {
        this.id = generateNewId();
        this.name = name;
        this.roll = roll;
        this.username = username;
        this.password = password;
        this.accountLocked = false;
    }
    protected User(int id,String name, String roll, String username, String password,boolean accountLocked) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.username = username;
        this.password = password;
        this.accountLocked = accountLocked;
    }

    private static int generateNewId() {
        lastAssignedId++;
        saveLastAssignedId();
        return lastAssignedId;
    }

    private static void saveLastAssignedId() {
        try (FileWriter writer = new FileWriter(ID_FILE_PATH)) {
            writer.write(String.valueOf(lastAssignedId));
        } catch (IOException e) {
            System.out.println("Error saving lastAssignedId to file: " + e.getMessage());
        }
    }

    private static void loadLastAssignedId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                lastAssignedId = Integer.parseInt(line.trim());
            }
        } catch (FileNotFoundException e) {
            // File does not exist yet, initialize lastAssignedId to 0
            lastAssignedId = 0;
        } catch (IOException e) {
            System.out.println("Error loading lastAssignedId from file: " + e.getMessage());
        }
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountStatus(boolean status){
        this.accountLocked = status;
    }
    public boolean getAccountStatus(){
        return accountLocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roll='" + roll + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountStatus='" + accountLocked + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                accountLocked == user.accountLocked &&
                Objects.equals(name, user.name) &&
                Objects.equals(roll, user.roll) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roll, username, password, accountLocked);
    }
}
