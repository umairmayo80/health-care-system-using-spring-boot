package server.service.impl.FileSystem;

import server.domain.Appointment;
import server.service.AppointmentService;
import server.utilities.FileModificationChecker;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentServiceImpl implements AppointmentService {
    private List<Appointment> appointments;

    public AppointmentServiceImpl(){
        this.appointments = new ArrayList<>();
    }
    // Load appointments from CSV file
    public List<Appointment> loadAppointmentsFromFile(String filePath) {
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
    public void saveAppointmentsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(AppointmentsFilePath))) {
            for (Appointment appointment : getAppointments()) {
                String line = appointment.getPatientId() + "," + appointment.getDoctorId() + ","
                        + appointment.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAppointments(){
        if(appointments.isEmpty() ||
                FileModificationChecker.isFileModified("appointments.csv",
                        FileModificationChecker.loadedLastModifiedInfo.get("appointments.csv")))
        {
            appointments = loadAppointmentsFromFile(AppointmentsFilePath);
        }
        return  appointments;
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId){
        List<Appointment> appointmentList = getAppointments();
        List<Appointment> patientAppointments = appointmentList.stream()
                .filter(appointment -> appointment.getPatientId() == patientId)
                .collect(Collectors.toList());
        return  patientAppointments;
    }

    public List<Appointment> getAppointmentsByDoctorId(int doctorId){
        List<Appointment> appointmentList = getAppointments();
        List<Appointment> doctorAppointments = appointmentList.stream()
                .filter(appointment -> appointment.getDoctorId() == doctorId)
                .collect(Collectors.toList());
        return  doctorAppointments;
    }



    public void addAppointmentEntry(Appointment appointment){
        appointments = getAppointments();
        appointments.add(appointment);
        saveAppointmentsToFile();
    }

}
