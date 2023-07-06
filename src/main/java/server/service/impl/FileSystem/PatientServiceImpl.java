package server.service.impl.FileSystem;
import server.context.ServiceContext;
import server.domain.Appointment;
import server.domain.version1.AppointmentV1;
import server.service.PatientService;

import java.util.List;
import java.util.stream.Collectors;

public class PatientServiceImpl implements PatientService {

    public  void viewAppointments(int patient_id){
        List<Appointment> appointments = ServiceContext.getAppointmentService().getAppointments().stream()
                .filter(appointment -> appointment.getPatientId() == patient_id)
                .collect(Collectors.toList());
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    public boolean addAppointment(Appointment appointment){
        //check if the doctor is available or not
        List<server.domain.Schedule> scheduleList = ServiceContext.getScheduleService().getSchedules();
        for(server.domain.Schedule schedule: scheduleList){
            if(schedule.getUserId() == appointment.getDoctorId() &&
                    appointment.getDateTime().toLocalDate().equals(schedule.getDate()) &&
                    (appointment.getDateTime().toLocalTime().equals(schedule.getStartTime())
                            || appointment.getDateTime().toLocalTime().isAfter(schedule.getStartTime())) &&
                    appointment.getDateTime().toLocalTime().isBefore(schedule.getEndTime()))
            {
                ServiceContext.getAppointmentService().addAppointmentEntry(appointment); //add to appointments list
                ServiceContext.getAppointmentService().saveAppointmentsToFile(); //save the updated list to disk
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean addAppointment(AppointmentV1 appointment) {
        return false;
    }


}
