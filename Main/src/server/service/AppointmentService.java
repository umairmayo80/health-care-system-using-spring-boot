package server.service;

import server.domain.Appointment;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface AppointmentService {
    static final String AppointmentsFilePath = "appointments.csv";

    // Load appointments from CSV file
    public List<Appointment> loadAppointmentsFromFile(String filePath);

    // Save appointments to CSV file
    public void saveAppointmentsToFile();

    public List<Appointment> getAppointments();

    public List<Appointment> getAppointmentsByPatientId(int patientId);

    public List<Appointment> getAppointmentsByDoctorId(int doctorId);



    public void addAppointmentEntry(Appointment appointment);

    public static void main(String[] args)
    {
        String filePath = "appointments.csv";

////        // Load appointments from CSV file
////        List<server.domain.Appointment> appointments = loadAppointmentsFromFile(filePath);
//////
////        List<server.domain.Appointment> appointments = server.service.AppointmentService.loadAppointmentsFromFile("appointments.csv");
//
//        System.out.println("Appointments loaded from the file:");
//        for (Appointment appointment : AppointmentService.getAppointments()) {
//            System.out.println(appointment);
//        }
//
//
//        // Create a new appointment
//        Appointment newAppointment = new Appointment(-5, 4, LocalDateTime.parse("2023-06-15T09:30:00"));
//
//        // Add the new appointment to the list
//        AppointmentService.addAppointmentEntry(newAppointment);
//
//        // Save the updated appointments to the CSV file
////        saveAppointmentsToFile();
//
//
//        System.out.println("Appointments saved to the file.\n" +
//                "New Appointments File:\n"
//        +AppointmentService.getAppointments());

    }

}
