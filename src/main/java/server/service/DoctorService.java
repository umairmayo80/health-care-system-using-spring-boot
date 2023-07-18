package server.service;
import server.domain.Slot;
import server.domain.User;

public interface DoctorService {
    void viewAppointments(int userID) ;
    boolean addSlotsEntry(Slot slot, User currentUser);
    void viewSlots (int userID);
}
