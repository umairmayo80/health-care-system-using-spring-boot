import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public static void addAppointment(Appointment appointment){
        AppointmentService.appointments = getAppointments();
        AppointmentService.appointments.add(appointment);
        saveAppointmentsToFile();
    }

    public static void main(String[] args)
    {
        String filePath = "appointments.csv";

//        // Load appointments from CSV file
//        List<Appointment> appointments = loadAppointmentsFromFile(filePath);
////
//        List<Appointment> appointments = AppointmentService.loadAppointmentsFromFile("appointments.csv");

        System.out.println("Appointments loaded from the file:");
        for (Appointment appointment : AppointmentService.getAppointments()) {
            System.out.println(appointment);
        }


        // Create a new appointment
        Appointment newAppointment = new Appointment(-5, 4, LocalDateTime.parse("2023-06-15T09:30:00"));

        // Add the new appointment to the list
        AppointmentService.addAppointment(newAppointment);

        // Save the updated appointments to the CSV file
//        saveAppointmentsToFile();


        System.out.println("Appointments saved to the file.\n" +
                "New Appointments File:\n"
        +AppointmentService.getAppointments());

    }

}
