package server.service;

import org.w3c.dom.ls.LSInput;
import server.domain.Appointment;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {
    private static final String AppointmentsFilePath = "appointments.csv";
    private static List<Appointment> appointments = loadAppointmentsFromFile(AppointmentsFilePath);



    // Load appointments from CSV file
    public static List<Appointment> loadAppointmentsFromFile(String filePath) {
        List<Appointment> appointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] appointmentData = line.split(",");
                if (appointmentData.length == 3) {
                    int patientId = Integer.parseInt(appointmentData[0]);
                    int doctorId = Integer.parseInt(appointmentData[1]);
                    LocalDateTime dateTime = LocalDateTime.parse(appointmentData[2], DateTimeFormatter.ISO_DATE_TIME);

                    Appointment appointment = new Appointment(patientId, doctorId, dateTime);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // Save appointments to CSV file
    public static void saveAppointmentsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(AppointmentsFilePath))) {
            for (Appointment appointment : AppointmentService.appointments) {
                String line = appointment.getPatientId() + "," + appointment.getDoctorId() + ","
                        + appointment.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Appointment> getAppointments(){
        if(AppointmentService.appointments.isEmpty() ||
                FileModificationChecker.isFileModified("appointments.csv",
                        FileModificationChecker.loadedLastModifiedInfo.get("appointments.csv")))
        {
            AppointmentService.appointments = AppointmentService.loadAppointmentsFromFile("appointments.csv");
        }
        return  AppointmentService.appointments;
    }

    public static List<Appointment> getAppointmentsByPatientId(int patientId){
        List<Appointment> appointmentList = getAppointments();
        List<Appointment> patientAppointments = appointmentList.stream()
                .filter(appointment -> appointment.getPatientId() == patientId)
                .collect(Collectors.toList());
        return  patientAppointments;
    }

    public static List<Appointment> getAppointmentsByDoctorId(int doctorId){
        List<Appointment> appointmentList = getAppointments();
        List<Appointment> doctorAppointments = appointmentList.stream()
                .filter(appointment -> appointment.getDoctorId() == doctorId)
                .collect(Collectors.toList());
        return  doctorAppointments;
    }



    public static void addAppointmentEntry(Appointment appointment){
        AppointmentService.appointments = getAppointments();
        AppointmentService.appointments.add(appointment);
        saveAppointmentsToFile();
    }

    public static void main(String[] args)
    {
        String filePath = "appointments.csv";

//        // Load appointments from CSV file
//        List<server.domain.Appointment> appointments = loadAppointmentsFromFile(filePath);
////
//        List<server.domain.Appointment> appointments = server.service.AppointmentService.loadAppointmentsFromFile("appointments.csv");

        System.out.println("Appointments loaded from the file:");
        for (Appointment appointment : AppointmentService.getAppointments()) {
            System.out.println(appointment);
        }


        // Create a new appointment
        Appointment newAppointment = new Appointment(-5, 4, LocalDateTime.parse("2023-06-15T09:30:00"));

        // Add the new appointment to the list
        AppointmentService.addAppointmentEntry(newAppointment);

        // Save the updated appointments to the CSV file
//        saveAppointmentsToFile();


        System.out.println("Appointments saved to the file.\n" +
                "New Appointments File:\n"
        +AppointmentService.getAppointments());

    }

}
