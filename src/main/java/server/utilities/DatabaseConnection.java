package server.utilities;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;

public class DatabaseConnection {
    String url;
    String username;
    String password;
    String databaseName;
    Connection connection;

    public DatabaseConnection(){
        url = "jdbc:mysql://localhost:3306/";
        username = "root";
        password = "password123";
        databaseName = "HealthCareTest";
    }

    private Connection initConnection() throws SQLException{

        MysqlDataSource dataSource = new MysqlDataSource();

        // Set the connection parameters
        dataSource.setUrl("jdbc:mysql://localhost:3306/"+databaseName);
        dataSource.setUser("root");
        dataSource.setPassword(password);

        try {
            connection = dataSource.getConnection();
            System.out.println("Connection to database successful");
        } catch (SQLSyntaxErrorException e){

            System.out.println("Creating new database");
            url = "jdbc:mysql://localhost:3306/";
            // Establish the database connection
            connection = DriverManager.getConnection(url, username, password);

            // Create the database if not exists
            Statement createDatabaseStatement = connection.createStatement();
            createDatabaseStatement.executeUpdate("CREATE database "+databaseName+";");

            // switch to the database
            connection.setCatalog(databaseName);

            // create  user table
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate("CREATE TABLE user_table (\n" +
                    "    userid INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name VARCHAR(20),\n" +
                    "    username VARCHAR(20) UNIQUE,\n" +
                    "    password VARCHAR(20),\n" +
                    "    role VARCHAR(10),\n" +
                    "    accountLocked BOOLEAN\n" +
                    ");\n");

            // insert dummy data
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO user_table (name, username, password, role, accountLocked)\n" +
                    "VALUES ('John Doe', 'admin123', 'password123', 'admin', FALSE);\n");


            statement.executeUpdate("INSERT INTO user_table (name, username, password, role, accountLocked)\n" +
                    "VALUES ('Jane Smith', 'patient123', 'patient123', 'patient', FALSE);\n" +
                    "\n");


            statement.executeUpdate("INSERT INTO user_table (name, username, password, role, accountLocked)\n" +
                    "VALUES ('Robert Johnson', 'doctor123', 'doctor123', 'doctor', FALSE);\n");


            // create slot table
            createTableStatement.executeUpdate("CREATE TABLE slot_table (\n" +
                    "    slotId INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    doctorId INT,\n" +
                    "    FOREIGN KEY (doctorId) REFERENCES user_table(userid) ON DELETE CASCADE,\n" +
                    "    date DATE,\n" +
                    "    startTime TIME,\n" +
                    "    endTime TIME,\n" +
                    "    occupied BOOLEAN\n" +
                    ");\n");

            // Constraint for unique records
            String alterTableQuery = "ALTER TABLE slot_table " +
                                     "ADD CONSTRAINT unique_slot UNIQUE (doctorId, date, startTime, endTime)";

            statement.executeUpdate(alterTableQuery);


            // insert dummy data
            statement.executeUpdate("INSERT INTO slot_table (doctorId, date, startTime, endTime, occupied)\n" +
                    "VALUES (3, '2023-06-22', '09:00:00', '10:00:00', FALSE);\n");

            statement.executeUpdate("INSERT INTO slot_table (doctorId, date, startTime, endTime, occupied)\n" +
                    "VALUES (3, '2023-06-23', '14:30:00', '15:30:00', TRUE);\n");

            statement.executeUpdate("INSERT INTO slot_table (doctorId, date, startTime, endTime, occupied)\n" +
                    "VALUES (3, '2023-06-24', '11:00:00', '12:00:00', FALSE);\n");

            // create appointment table
            createTableStatement.executeUpdate("CREATE TABLE appointment_table (\n" +
                    "    appointmentId INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    patientId INT,\n" +
                    "    slotId INT,\n" +
                    "    FOREIGN KEY (patientId) REFERENCES user_table(userid) ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY (slotId) REFERENCES slot_table(slotId) ON DELETE CASCADE\n" +
                    ");\n");

            // insert dummy data
            statement.executeUpdate("INSERT INTO appointment_table (patientId, slotId)\n" +
                    "VALUES (2, 2);\n");

            System.out.println("Database successfully setup");

        }
        return connection;


    }
    public Connection getConnection() {
        try {
            return initConnection();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnection dbConnectionObj = new DatabaseConnection();
        Connection connection = dbConnectionObj.getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select * from user_table;");

        // Iterate through the result set and retrieve data
        while (resultSet.next()) {
            int userId = resultSet.getInt("userid");
            String name = resultSet.getString("name");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            boolean accountLocked = resultSet.getBoolean("accountLocked");

            System.out.println("User ID: " + userId);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("Role: " + role);
            System.out.println("Account Locked: " + accountLocked);
            System.out.println();
        }


    }
}
