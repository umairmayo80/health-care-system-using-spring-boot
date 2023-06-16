import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Patient extends User{
//    private List<Appointment> appointments;

    public Patient(String name, String username, String password) {
        super(name, "patient", username, password);
//        appointments = new ArrayList<>();
    }   public Patient(int id, String name, String username, String password) {
        super(id,name, "patient", username, password,false);
//        appointments = new ArrayList<>();
    }



    @Override
    public String toString() {
        return "Patient{" +
                "id=" + super.getId() +
        ", name='" + super.getName() + '\'' +
        ", roll='" + super.getRoll() + '\'' +
        ", username='" + super.getUsername() + '\'' +
        ", password='" + super.getPassword() + '\'' +
        ", accountBlocked='" + super.getAccountStatus() + '\'' +
//        "appointments=" + appointments +
        '}';
}

    public static void main(String[] args){
        Patient patient = new Patient(-1,"patient1","pat123","pat123");

        System.out.println(patient);


        PatientService.viewAppointments(patient.getId());
        System.out.println(patient);
    }
}