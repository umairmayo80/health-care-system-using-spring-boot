package server.context;
import server.service.*;
import server.service.impl.database.AppointmentServiceV1DBImpl;
import server.service.impl.database.SlotsServiceDBImpl;
import server.service.impl.database.UserServiceDBImpl;
import server.service.impl.hibernate.AppointmentServiceV1HibernateImpl;
import server.service.impl.hibernate.SlotServiceHibernateImpl;
import server.service.version1.AppointmentServiceV1;
import server.utilities.DatabaseConnection;

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

    // repository

    private ServiceContext(){
        System.out.println("Service context default constructor");
    }


    public static UserService getUserServiceDB(){
        if (userServiceDb == null){
            synchronized (ServiceContext.class){
                if (userServiceDb == null){
                    userServiceDb = new UserServiceDBImpl();
                }
            }
        }
        return userServiceDb;
    }




    public static Connection getDatabaseConnection(){
        if(databaseConnection == null){
            synchronized (ServiceContext.class){
                if(databaseConnection == null){
                    databaseConnection = new DatabaseConnection().getConnection();
                }
            }
        }
        return databaseConnection;
    }


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


    public static SlotService getSlotService(){
        if(slotService == null){
            synchronized (ServiceContext.class){
                if(slotService == null){
                    slotService = new SlotsServiceDBImpl();
                }
            }
        }
        return slotService;
    }













}
