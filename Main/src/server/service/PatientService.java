package server.service;

import server.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PatientService {
//
//    private static List<server.domain.Appointment> appointments = server.domain.Appointment.loadAppointmentsFromFile("appointments.csv");

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
        List<server.domain.Schedule> scheduleList = server.service.ScheduleService.getSchedules();

        for(server.domain.Schedule schedule: scheduleList){
            if(schedule.getUserId() == appointment.getDoctorId() &&
                appointment.getDateTime().toLocalDate().equals(schedule.getDate()) &&
                    (appointment.getDateTime().toLocalTime().equals(schedule.getStartTime())
                     || appointment.getDateTime().toLocalTime().isAfter(schedule.getStartTime())) &&
                appointment.getDateTime().toLocalTime().isBefore(schedule.getEndTime()))
            {
                    System.out.println("Date time ok");
                    AppointmentService.addAppointmentEntry(appointment); //add to appointments list
                    AppointmentService.saveAppointmentsToFile(); //save the updated list to disk
                    return;
            }
        }
        System.out.println("Date time not match");

    }



    public static void main(String[] args){

        System.out.println(FileModificationChecker.isFileModified("lastAssignedId.txt",FileModificationChecker.loadedLastModifiedInfo.get("lastAssignedId.txt")));

        System.out.println(FileModificationChecker.isFileModified("appointments.csv",
                FileModificationChecker.loadedLastModifiedInfo.get("appointments.csv")));


        PatientService.viewAppointments(-1);

        Appointment testAddAppointment = new Appointment(-6,-3, LocalDateTime.parse("2023-02-02T09:30:00"));
        PatientService.addAppointment(testAddAppointment);

    }
}
