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


    private static SlotServiceHibernateImpl slotServiceHibernate;

    private static AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate;

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


    public static AppointmentServiceV1 getAppointmentServiceV1(){
        if(appointmentServiceV1 == null){
            synchronized (ServiceContext.class){
                if(appointmentServiceV1 == null){
                    appointmentServiceV1 = new AppointmentServiceV1DBImpl();
                }
            }
        }
        return appointmentServiceV1;
    }



    public static SlotServiceHibernateImpl getSlotServiceHibernate(){
        if(slotServiceHibernate == null){
            synchronized (ServiceContext.class){
                if(slotServiceHibernate == null){
                    slotServiceHibernate = new SlotServiceHibernateImpl();
                }
            }
        }
        return slotServiceHibernate;
    }


    public static AppointmentServiceV1HibernateImpl getAppointmentServiceV1Hibernate(){
        if(appointmentServiceV1Hibernate == null){
            synchronized (ServiceContext.class){
                if(appointmentServiceV1Hibernate == null){
                    appointmentServiceV1Hibernate = new AppointmentServiceV1HibernateImpl();
                }
            }
        }
        return appointmentServiceV1Hibernate;
    }



}
