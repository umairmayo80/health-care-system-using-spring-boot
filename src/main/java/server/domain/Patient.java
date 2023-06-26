package server.domain;

public class Patient extends User{

    public Patient(String name, String username, String password) {
        super(name, "patient", username, password);
    }   public Patient(int id, String name, String username, String password) {
        super(id,name, "patient", username, password,false);
    }



    @Override
    public String toString() {
        return "server.domain.Patient{" +
                "id=" + super.getId() +
        ", name='" + super.getName() + '\'' +
        ", roll='" + super.getRoll() + '\'' +
        ", username='" + super.getUsername() + '\'' +
        ", password='" + super.getPassword() + '\'' +
        ", accountBlocked='" + super.getAccountStatus() + '\'' +
        '}';
}

}