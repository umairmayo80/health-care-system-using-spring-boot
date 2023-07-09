package server.service.impl.fileSystem;
import server.context.ServiceContext;
import server.domain.Appointment;
import server.domain.Slot;
import server.service.*;
import server.domain.Schedule;
import server.utilities.ScheduleCreationException;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorServiceImpl implements DoctorService {

    public void addScheduleSlots(Schedule schedule){
        ServiceContext.getScheduleService().addScheduleEntry(schedule);
    }

    public void viewAppointments(int userID) {
        List<Appointment> appointments = ServiceContext.getAppointmentService().getAppointments().stream()
                .filter(appointment -> appointment.getDoctorId() == userID)
                .collect(Collectors.toList());
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
//        return appointments;
        }
    }

    @Override
    public void addSlotsEntry(Slot slot) {
        // will updated this to handle both file and db in next update
    }

    public void viewSlots(int userID){
        ServiceContext.getScheduleService().getSchedulesByID(userID).forEach(System.out::println);
    }

    public  void main(String[] args) throws ScheduleCreationException {
//        Doctor doctor = new Doctor(-3,"doctor1","pat123","pat123");
////
//        System.out.println(doctor);
////
//        PatientService.viewAppointments(doctor.getId());
//        System.out.println(doctor);
////
//        Schedule newScheduleDoc = new Schedule(doctor.getId(), "2023-02-02",
//                "09:00:00","17:00:00");
//        DoctorServiceImpl doctorService = new DoctorServiceImpl();
//        doctorService.addSchedule(newScheduleDoc);
    }
}