package server.utilities;

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

}
