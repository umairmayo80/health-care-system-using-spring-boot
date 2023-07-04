package server.utilities;

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

}
