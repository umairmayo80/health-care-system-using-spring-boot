package server.service;
import server.domain.User;


public interface AdminService {

    public boolean addUser(User user);

    public void setUserAccountStatus(String username,boolean status);


}
