package server;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;
import server.domain.Slot;
import server.domain.User;
import server.domain.Appointment;

import java.sql.Connection;
import java.util.Properties;
import java.util.Scanner;

@Configuration
@RestController
@ComponentScan(basePackages = {"server"})
public class AppConfig {

    @Bean
    public Scanner getScanner(){
        return new Scanner(System.in);
    }



    @Bean
    @Scope("singleton")
    public SessionFactory getSessionFactory(){
        String url = "jdbc:mysql://localhost:3306/";
        String username = "test";
        String password = "password123!";
        String databaseName = "HealthCareDatabase";
        SessionFactory sessionFactory = null;
        try{
            org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

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
            configuration.addAnnotatedClass(Appointment.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (ServiceException e){
            System.out.println("Fail to connect to database:" +
                    "\n Please check your username and password and ensure that the '"+databaseName+" database exits'\n"
                    +e.getMessage());
            System.exit(1);
        }
        return sessionFactory;
    }


}
