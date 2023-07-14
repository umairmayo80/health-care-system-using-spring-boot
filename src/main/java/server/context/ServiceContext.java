package server.context;
import java.util.Scanner;


/**

 The ServiceContext class provides access to various service implementations and resources.
 It follows the Singleton design pattern to ensure only one instance of each service is created.
 The class also manages the database connection and provides a scanner for user input.
 *
 */
public class ServiceContext {
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
