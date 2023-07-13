package server.context;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceException;
import server.domain.Slot;
import server.domain.User;
import server.domain.version1.AppointmentV1;
import server.service.*;
import server.service.impl.database.AppointmentServiceV1DBImpl;
import server.service.impl.database.SlotsServiceDBImpl;
import server.service.impl.database.UserServiceDBImpl;
import server.service.impl.fileSystem.*;
import server.service.impl.hibernate.AdminServiceHibernateImpl;
import server.service.impl.hibernate.AppointmentServiceV1HibernateImpl;
import server.service.impl.hibernate.SlotServiceHibernateImpl;
import server.service.impl.hibernate.UserServiceHibernateImpl;
import server.service.version1.AppointmentServiceV1;
import server.utilities.DatabaseConnection;

import java.sql.Connection;
import java.util.Properties;
import java.util.Scanner;


/**

 The ServiceContext class provides access to various service implementations and resources.
 It follows the Singleton design pattern to ensure only one instance of each service is created.
 The class also manages the database connection and provides a scanner for user input.
 *
 */
public class ServiceContext {
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static UserService userService;
    private static AdminService adminService;
    private static AppointmentService appointmentService;
    private static ScheduleService scheduleService;

    private static SlotService slotService; // it replaces scheduleService

    private static AppointmentServiceV1 appointmentServiceV1;

    private static Connection databaseConnection;

    // services related to hibernate
    private static SessionFactory sessionFactory;

    private static UserServiceHibernateImpl userServiceHibernate;

    private static AdminServiceHibernateImpl adminServiceHibernate;

    private static SlotServiceHibernateImpl slotServiceHibernate;

    private static AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate;

    private static Scanner scanner;

    // repository

    private ServiceContext(){
        System.out.println("Service context default constructor");
    }

    public static DoctorService getDoctorService(){
        if (doctorService == null){
            synchronized (ServiceContext.class){
                if (doctorService == null){
                    doctorService = new DoctorServiceImpl();
                }
            }
        }
        return doctorService;
    }

    public static UserService getUserService(){
        if (userService == null){
            synchronized (ServiceContext.class){
                if (userService == null){
                    userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    public static UserService getUserServiceDB(){
        if (userService == null){
            synchronized (ServiceContext.class){
                if (userService == null){
                    userService = new UserServiceDBImpl();
                }
            }
        }
        return userService;
    }

    public static PatientService getPatientService(){
        if (patientService == null){
            synchronized (ServiceContext.class){
                if (patientService == null){
                    patientService = new PatientServiceImpl();
                }
            }
        }
        return patientService;
    }


    public static AdminService getAdminService(){
        if(adminService == null){
            synchronized (ServiceContext.class){
                if(adminService == null){
                    adminService = new AdminServiceImpl();
                }
            }
        }
        return adminService;
    }

    public static AppointmentService getAppointmentService(){
        if(appointmentService == null){
            synchronized (ServiceContext.class){
                if(appointmentService == null){
                    appointmentService = new AppointmentServiceImpl();
                }
            }
        }
        return appointmentService;
    }

    public static ScheduleService getScheduleService(){
        if(scheduleService == null){
            synchronized (ServiceContext.class){
                if(scheduleService == null){
                    scheduleService = new ScheduleServiceImpl();
                }
            }
        }
        return scheduleService;
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



    public static SessionFactory getSessionFactory(){
        String url = "jdbc:mysql://localhost:3306/";
        String username = "test";
        String password = "password123!";
        String databaseName = "HealthCareDatabase";
        if(sessionFactory == null){
            try{
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, url+databaseName+"?userSSL=false");
                settings.put(Environment.USER,username);
                settings.put(Environment.PASS,password);
                settings.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect");

                settings.put(Environment.SHOW_SQL,"false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
                settings.put(Environment.HBM2DDL_AUTO,"create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Slot.class);
                configuration.addAnnotatedClass(AppointmentV1.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (ServiceException e){
                System.out.println("Fail to connect to database:" +
                        "\n Please check your username and password and ensure that the '"+databaseName+" database exits'\n"
                        +e.getMessage());
                System.exit(1);
            }
        }
        return sessionFactory;
    }



//    public static UserServiceHibernateImpl getUserServiceHibernate(){
//        if(userServiceHibernate == null){
//            synchronized (ServiceContext.class){
//                if(userServiceHibernate == null){
//                    userServiceHibernate = new UserServiceHibernateImpl();
//                }
//            }
//        }
//        return userServiceHibernate;
//    }


    public static AdminServiceHibernateImpl getAdminServiceHibernate(){
        if(adminServiceHibernate == null){
            synchronized (ServiceContext.class){
                if(adminServiceHibernate == null){
                    adminServiceHibernate = new AdminServiceHibernateImpl();
                }
            }
        }
        return adminServiceHibernate;
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
