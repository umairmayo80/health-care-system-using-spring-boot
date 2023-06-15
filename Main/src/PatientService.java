import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PatientService {
//
//    private static List<Appointment> appointments = Appointment.loadAppointmentsFromFile("appointments.csv");

    public static void viewAppointments(int patient_id){
        List<Appointment> appointments = AppointmentService.getAppointments().stream()
                .filter(appointment -> appointment.getPatientId() == patient_id)
                .collect(Collectors.toList());
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
//        return appointments;
    }
    }

    public static void addAppointment(Appointment appointment){
        //check if the doctor is available or not
//        List<Schedule> scheduleList = ScheduleService.getSchedules();
//        List<Schedule> docShedule = scheduleList.stream()
//                .filter(schedule -> schedule.getUserId() == appointment.getDoctorId())
//                .collect(Collectors.toList());

//        for(Schedule schedule: scheduleList){
//            if(schedule.getUserId() == appointment.getDoctorId() &&
//                appointment.getDateTime().toLocalDate().equals(schedule.getDate()) &&
//                    (appointment.getDateTime().toLocalTime().equals(schedule.getStartTime())
//                     || appointment.getDateTime().toLocalTime().isAfter(schedule.getStartTime())) &&
//                appointment.getDateTime().toLocalTime().isBefore(schedule.getEndTime()))
//            {
//
//            }
//        }
        AppointmentService.addAppointment(appointment);
//        AppointmentService.saveAppointmentsToFile();
    }



    public static void main(String[] args){

        System.out.println(FileModificationChecker.isFileModified("lastAssignedId.txt",FileModificationChecker.loadedLastModifiedInfo.get("lastAssignedId.txt")));

        System.out.println(FileModificationChecker.isFileModified("appointments.csv",
                FileModificationChecker.loadedLastModifiedInfo.get("appointments.csv")));


        PatientService.viewAppointments(-1);
    }
}
