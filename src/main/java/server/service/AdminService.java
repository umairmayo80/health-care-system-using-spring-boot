package server.service;
import server.domain.User;


public interface AdminService {

    boolean addUser(User user);

    int setUserAccountStatus(String username,boolean status);


}
