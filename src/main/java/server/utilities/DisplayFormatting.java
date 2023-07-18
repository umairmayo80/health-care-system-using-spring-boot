package server.utilities;

import server.domain.Appointment;
import server.domain.Slot;
import server.domain.User;

import java.util.List;

public class DisplayFormatting {
    public static void displayUsers(List<User> usersList){
        // Print table header
        System.out.println("+-----+---------------+----------+---------------+---------------+-----------+");
        System.out.format("%-5s %-15s %-10s %-15s %-15s %-10s%n", "ID", "Name", "Role", "Username", "Password", "Account Locked");
        System.out.println("+-----+---------------+----------+---------------+---------------+-----------+");

        // Print table rows
        for (User user : usersList) {
            System.out.format("%-5d %-15s %-10s %-15s %-15s %-10s%n",
                    user.getUserId(), user.getName(), user.getRole(), user.getUsername(), user.getPassword(), user.getAccountStatus());
        }
        System.out.println("+-----+---------------+----------+---------------+---------------+-----------+");
    }


    public static void displaySlots(List<Slot> slotList){
        // Display the data in a table format
        System.out.println("+--------+----------+------------+-----------+----------+----------+");
        System.out.println("| slotId | doctorId |    date    | startTime |  endTime | occupied |");
        System.out.println("+--------+----------+------------+-----------+----------+----------+");

        for(Slot slot : slotList){
            System.out.format("|%-7d |%9d |%11s |%10s |%9s |%9s |\n", slot.getSlotId(), slot.getDoctorId(), slot.getDate(), slot.getStartTime(), slot.getEndTime(), slot.getOccupied());
        }
        System.out.println("+--------+----------+------------+-----------+----------+----------+");


    }


    // Method to display appointment data with patient and doctor names
    public static void displayAppointmentData(List<Appointment> appointmentV1List)  {
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
        System.out.println("| appointmentId | patientId | patientName  | slotId | doctorId | doctorName      | date      | startTime | endTime  | occupied |");
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
        for(Appointment appointmentV1 : appointmentV1List) {
            int appointmentId = appointmentV1.getAppointmentId();
            int patientId = appointmentV1.getPatient().getUserId();
            String patientName = appointmentV1.getPatient().getName();
            int slotId = appointmentV1.getSlot().getSlotId();
            int doctorId = appointmentV1.getSlot().getDoctor().getUserId();
            String doctorName = appointmentV1.getSlot().getDoctor().getName();
            String date = appointmentV1.getSlot().getDate().toString();
            String startTime = appointmentV1.getSlot().getStartTime().toString();
            String endTime = appointmentV1.getSlot().getEndTime().toString();
            boolean occupied = appointmentV1.getSlot().getOccupied();

            System.out.printf("|%14d |%10d | %12s |%7d |%9d | %15s |%10s |%9s |%9s |%10s |\n",
                    appointmentId, patientId, patientName, slotId, doctorId, doctorName, date, startTime, endTime, occupied);
        }
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
    }

}
