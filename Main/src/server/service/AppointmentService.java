package server.service;
import server.domain.Appointment;
import java.util.List;

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


}
