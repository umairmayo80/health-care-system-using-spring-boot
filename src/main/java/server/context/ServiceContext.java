package server.context;
import server.service.*;
import server.service.version1.AppointmentServiceV1;
import java.sql.Connection;
import java.util.Scanner;


/**

 The ServiceContext class provides access to various service implementations and resources.
 It follows the Singleton design pattern to ensure only one instance of each service is created.
 The class also manages the database connection and provides a scanner for user input.
 *
 */
public class ServiceContext {

    private static UserService userServiceDb;

    private static SlotService slotService; // it replaces scheduleService

    private static AppointmentServiceV1 appointmentServiceV1;

    private static Connection databaseConnection;

    // services related to hibernate



    private static Scanner scanner;




    public static Scanner getScanner(){
        if(scanner == null){
            synchronized (ServiceContext.class){
                if(scanner == null){
                    scanner = new Scanner(System.in);
                }
            }
        }
        return scanner;
    }














}
