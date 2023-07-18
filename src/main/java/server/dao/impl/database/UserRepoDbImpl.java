package server.dao.impl.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.User;
import server.dao.UserRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepoDbImpl implements UserRepository {
    private final Connection connection;


    @Autowired
    public UserRepoDbImpl(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from user_table;");

            // Iterate through the result set and retrieve data
            while (resultSet.next()) {
                users.add(fetchUserDataFromQueryResult(resultSet));
            }


        } catch (SQLException e) {
            System.out.println("Error reading users from the Database: " + e.getMessage());
        }
        return users;
    }

    // add new user to the database
    @Override
    public boolean add(User user) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO user_table (name, username, password, role, accountLocked) VALUES ('"
                    + user.getName() + "','"
                    + user.getUsername() + "','"
                    + user.getPassword() + "','"
                    + user.getRole() + "',"
                    + user.getAccountStatus() + ")";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Error: Unable to add user" + e.getMessage());
            return false;
        }
    }

    @Override
    public void addUsersListToStorage(List<User> userList) {
        userList.forEach(this::add);
    }


    @Override
    public boolean delete(String username) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM user_table WHERE username ='" + username + "';";
            //update the user accountLocked status where username = username
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.printf("Error: No User found against this the provided '%s' username.\n", username);
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to write data to database" + e.getMessage());
        }
        return false;
    }

    @Override
    public User getByUsername(String username) {
        User user = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM user_table WHERE username = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                user = fetchUserDataFromQueryResult(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error reading users from the Database: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User getById(int id) {
        User user = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM user_table WHERE userid ="+ id+";";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                user = fetchUserDataFromQueryResult(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error reading users from the Database: " + e.getMessage());
        }
        return user;
    }

    public List<User> getPatients() {
        List<User> patientsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from user_table where role = \"patient\";");

            // Iterate through the result set and retrieve data
            while (resultSet.next()) {
                patientsList.add(fetchUserDataFromQueryResult(resultSet));
            }


        } catch (SQLException e) {
            System.out.println("Error reading users from the Database: " + e.getMessage());
        }
        return patientsList;
    }

    private User fetchUserDataFromQueryResult(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userid");
        String name = resultSet.getString("name");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        boolean accountLocked = resultSet.getBoolean("accountLocked");
        return new User(userId, name, role, username, password, accountLocked);
    }

    public List<User> getDoctors() {
        List<User> doctorsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from user_table where role = \"doctor\";");
            while (resultSet.next()) {
                doctorsList.add(fetchUserDataFromQueryResult(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error reading users from the Database: " + e.getMessage());
        }
        return doctorsList;
    }
}
