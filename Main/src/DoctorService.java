import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {

    public static void addSchedule(Schedule schedule){
        ScheduleService.addScheduleEntry(schedule);
    }

    public static void viewAppointments(int userID) {
        List<Appointment> appointments = AppointmentService.getAppointments().stream()
                .filter(appointment -> appointment.getDoctorId() == userID)
                .collect(Collectors.toList());
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
//        return appointments;
        }
    }

    public static void main(String[] args){
        Doctor doctor = new Doctor(-3,"doctor1","pat123","pat123");

        System.out.println(doctor);

        PatientService.viewAppointments(doctor.getId());
        System.out.println(doctor);

        Schedule newScheduleDoc = new Schedule(doctor.getId(), "2023-02-02",
                "09:00:00","17:00:00");

        DoctorService.addSchedule(newScheduleDoc);
    }
}
