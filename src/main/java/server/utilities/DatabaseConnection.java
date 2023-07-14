package server.utilities;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DatabaseConnection {
    private final String url;
    private final String username;
    private final String password;
    private final String databaseName;
    Connection connection;

    public DatabaseConnection() {
        url = "jdbc:mysql://localhost:3306/";
        username = "test";
        password = "password123!";
        databaseName = "HealthCareTest";
    }


    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    private Connection initConnection() throws SQLException {

        MysqlDataSource dataSource = new MysqlDataSource();

        // Set the connection parameters
        dataSource.setUrl(url + databaseName);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        try {
            connection = dataSource.getConnection();
            System.out.println("Connection to database successful");
        } catch (SQLSyntaxErrorException e) {

            System.out.println("Creating new database");
            // Establish the database connection
            connection = DriverManager.getConnection(url, username, password);

            // Create the database if not exists
            Statement createDatabaseStatement = connection.createStatement();
            createDatabaseStatement.executeUpdate("CREATE database " + databaseName + ";");

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
