package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.User;
import server.service.AdminService;

@Component
public class AdminServiceHibernateImpl implements AdminService {
    private final UserServiceHibernateImpl userService;

    @Autowired
    public AdminServiceHibernateImpl(UserServiceHibernateImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean addUser(User user) {
        return userService.addUserEntry(user);
    }

    @Override
    public boolean setUserAccountStatus(String username, boolean status) {
        int rowsAffected = userService.setUserAccountStatus(username,status);
        return rowsAffected > 0;
    }
}
