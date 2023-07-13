package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.context.ServiceContext;
import server.domain.User;
import server.service.AdminService;
import server.service.UserService;

@Component
public class AdminServiceHibernateImpl implements AdminService {
    UserService userService;

    public AdminServiceHibernateImpl() {
    }

    @Autowired
    public AdminServiceHibernateImpl(UserServiceHibernateImpl userService) {
        this.userService = userService;
        System.out.println("AdminServiceHibernateImpl-Autowire-C-userService-"+userService);
    }

    @Override
    public boolean addUser(User user) {
//        return ServiceContext.getUserServiceHibernate().addUserEntry(user);
        return userService.addUserEntry(user);
    }

    @Override
    public boolean setUserAccountStatus(String username, boolean status) {
        Session session = ServiceContext.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String query = "UPDATE User as u SET u.accountLocked ="+status
                    +" where u.username='"+username+"'";

            //update the user accountLocked status where username = username
            int rowsAffected = session.createQuery(query).executeUpdate();

            //save the changes to database
            transaction.commit();
            if(rowsAffected>0){
                return true;
            }
            else {
                System.out.printf("Error: No User found against this the provided '%s' username.\n",username);
            }}
        catch (Exception e){
            System.out.println("Error: Unable to write data to Database using hibernate" + e.getMessage());
            e.printStackTrace();
            if(transaction!=null){
                transaction.rollback();
            }
        }
        session.close();
        return false;
    }
}
