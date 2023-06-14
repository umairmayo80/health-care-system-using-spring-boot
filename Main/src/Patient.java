import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Patient extends User{
    private List<Appointment> appointments;

    public Patient(String name, String username, String password) {
        super(name, "patient", username, password);
        appointments = new ArrayList<>();
    }   public Patient(int id, String name, String username, String password) {
        super(id,name, "patient", username, password,false);
        appointments = new ArrayList<>();
    }



    public void viewAppointments(List<Appointment> appointments){
        this.appointments = appointments.stream()
                .filter(appointment -> appointment.getPatientId() == this.getId())
                .collect(Collectors.toList());
        for (Appointment appointment : this.appointments) {
            System.out.println(appointment);
        }
    }

    public void addAppointment(Appointment appointment,List<Appointment> appointments){
        //check if the doctor is available or not
        appointments.add(appointment);
        Appointment.saveAppointmentsToFile("appointments.csv",appointments);
    }
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + super.getId() +
        ", name='" + super.getName() + '\'' +
        ", roll='" + super.getRoll() + '\'' +
        ", username='" + super.getUsername() + '\'' +
        ", password='" + super.getPassword() + '\'' +
        ", accountBlocked='" + super.accountLocked + '\'' +
        "appointments=" + appointments +
        '}';
}

    public static void main(String[] args){
        Patient patient = new Patient(-1,"patient1","pat123","pat123");

        System.out.println(patient);

        List<Appointment> appointments = Appointment.loadAppointmentsFromFile("appointments.csv");

        patient.viewAppointments(appointments);
        System.out.println(patient);
    }
}