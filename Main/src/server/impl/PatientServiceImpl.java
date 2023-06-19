package server.impl;
import server.context.ServiceContext;
import server.domain.Appointment;
import server.service.PatientService;
import server.service.ScheduleService;

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

    public void addAppointment(Appointment appointment){
        //check if the doctor is available or not
        List<server.domain.Schedule> scheduleList = ServiceContext.getScheduleService().getSchedules();

        for(server.domain.Schedule schedule: scheduleList){
            if(schedule.getUserId() == appointment.getDoctorId() &&
                    appointment.getDateTime().toLocalDate().equals(schedule.getDate()) &&
                    (appointment.getDateTime().toLocalTime().equals(schedule.getStartTime())
                            || appointment.getDateTime().toLocalTime().isAfter(schedule.getStartTime())) &&
                    appointment.getDateTime().toLocalTime().isBefore(schedule.getEndTime()))
            {
                System.out.println("Date time ok");
                ServiceContext.getAppointmentService().addAppointmentEntry(appointment); //add to appointments list
                ServiceContext.getAppointmentService().saveAppointmentsToFile(); //save the updated list to disk
                return;
            }
        }
        System.out.println("Date time not match");

    }



    public static void main(String[] args){

//        System.out.println(FileModificationChecker.isFileModified("lastAssignedId.txt",FileModificationChecker.loadedLastModifiedInfo.get("lastAssignedId.txt")));
//
//        System.out.println(FileModificationChecker.isFileModified("appointments.csv",
//                FileModificationChecker.loadedLastModifiedInfo.get("appointments.csv")));
//
//
//        server.service.PatientService.viewAppointments(-1);
//
//        Appointment testAddAppointment = new Appointment(-6,-3, LocalDateTime.parse("2023-02-02T09:30:00"));
//        server.service.PatientService.addAppointment(testAddAppointment);

    }
}
