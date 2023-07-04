package server.domain;

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
                "id=" + super.getUserId() +
                ", name='" + super.getName() + '\'' +
                ", roll='" + super.getRole() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", accountStatus='" + super.getAccountStatus() + '\'' +
                '}';
    }

}
