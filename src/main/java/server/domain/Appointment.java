package server.domain;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private int patientId;
//    slot Obj;
    private int doctorId;
    private LocalDateTime dateTime;

//    private static List<server.domain.Appointment> appointmentsList;
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
        return "server.domain.Appointment{" +
                "patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", dateTime=" + dateTime +
                '}';
    }


    // Example usage in the main method
    public static void main(String[] args) {


    }
}
