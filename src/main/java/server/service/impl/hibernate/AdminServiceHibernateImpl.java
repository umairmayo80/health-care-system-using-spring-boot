package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.context.ServiceContext;
import server.domain.User;
import server.service.AdminService;

public class AdminServiceHibernateImpl implements AdminService {
    @Override
    public boolean addUser(User user) {
        return ServiceContext.getUserServiceHibernate().addUserEntry(user);
    }

    @Override
    public boolean setUserAccountStatus(String username, boolean status) {
        try {
            Session session = ServiceContext.getSessionFactory().openSession();

            Transaction transaction = session.beginTransaction();
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
        }
        return false;
    }
}
