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
    public int setUserAccountStatus(String username, boolean status) {
        return userService.setUserAccountStatus(username,status);
    }
}
