package server.service;
import server.domain.Slot;

public interface DoctorService {
    void viewAppointments(int userID) ;
    void addSlotsEntry(Slot slot);
    void viewSlots (int userID);
}
