package server.domain;


public class Doctor extends User{

//    private List<server.domain.Appointment> appointments;

    public Doctor(String name, String username, String password) {
        super(name, "doctor", username, password);
//        appointments = new ArrayList<>();
    }   public Doctor(int id, String name, String username, String password) {
        super(id,name, "doctor", username, password,false);
//        appointments = new ArrayList<>();
    }





    @Override
    public String toString() {
        return "server.domain.Doctor{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", roll='" + super.getRoll() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", accountBlocked='" + super.getAccountStatus() + '\'' +
//        "appointments=" + appointments +
                '}';
    }


}