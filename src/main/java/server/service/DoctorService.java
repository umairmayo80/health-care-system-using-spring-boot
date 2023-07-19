package server.service;
import server.domain.Slot;
import server.domain.User;
import server.utilities.ScheduleCreationException;

public interface DoctorService {
    void viewAppointments(int userID) ;
    boolean createNewSlot(int doctorId, String date, String startTime, String endTime ) throws ScheduleCreationException;
    void viewSlots (int userID);
}
