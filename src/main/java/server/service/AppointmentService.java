package server.service;
import server.domain.Appointment;
//import server.domain.version1.Appointment;
import java.util.List;

public interface AppointmentService {
    String AppointmentsFilePath = "appointments.csv";

    // Load appointments from CSV file
    List<Appointment> loadAppointmentsFromFile(String filePath);

    // Save appointments to CSV file
    void saveAppointmentsToFile();

    List<Appointment> getAppointments();

    List<Appointment> getAppointmentsByPatientId(int patientId);

    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    void addAppointmentEntry(Appointment appointment);


}
