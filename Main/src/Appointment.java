import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private int patientId;
    private int doctorId;
    private LocalDateTime dateTime;

//    private static List<Appointment> appointmentsList;
//
//    static {
//        appointmentsList = loadAppointmentsFromFile("appointments.csv");
//    }


    public Appointment(int patientId, int doctorId, LocalDateTime dateTime) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
    }

    // Getters and setters

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Helper methods

    public String formatDateTime(DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", dateTime=" + dateTime +
                '}';
    }

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
    public static void saveAppointmentsToFile(String filePath, List<Appointment> appointments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Appointment appointment : appointments) {
                String line = appointment.getPatientId() + "," + appointment.getDoctorId() + ","
                        + appointment.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Example usage in the main method
    public static void main(String[] args) {
        String filePath = "appointments.csv";

//        // Load appointments from CSV file
//        List<Appointment> appointments = loadAppointmentsFromFile(filePath);
//
        List<Appointment> appointments = Appointment.loadAppointmentsFromFile("appointments.csv");

        System.out.println("Appointments loaded from the file:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }


        // Create a new appointment
        Appointment newAppointment = new Appointment(-1, 4, LocalDateTime.parse("2023-06-15T09:30:00"));

        // Add the new appointment to the list
        appointments.add(newAppointment);

        // Save the updated appointments to the CSV file
        saveAppointmentsToFile(filePath, appointments);
        System.out.println("Appointments saved to the file.");


    }
}
