package server.domain;

import server.service.AdminService;

public class Admin extends User {

    public Admin(String name) {
        super(name, "admin");
    }

    public Admin(String name, String username, String password) {
        super(name, "admin", username, password);
    }

    @Override
    public String toString() {
        return "server.domain.Admin{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", roll='" + super.getRoll() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", accountStatus='" + super.getAccountStatus() + '\'' +
                '}';
    }


    public static void main(String[] args){
        AdminService.addUser(new User(-10,"adminServiceTest","admin","admin12","admin123",false));

    }
}