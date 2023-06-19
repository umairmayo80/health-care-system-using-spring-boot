package server.service;
import server.context.ServiceContext;
import server.domain.User;
import java.util.List;
import java.util.Optional;


public interface AdminService {

    public boolean addUser(User user);

    public void setUserAccountStatus(String username,boolean status);

    public static void main(String[] args){
//        UserService.viewPatients();
//
//        AdminService.addUser(UserService.getUsers().get(0));
//
//        AdminService.setUserAccountStatus("admin123000",true);
//        AdminService.setUserAccountStatus("admin123",true);
    }

}
