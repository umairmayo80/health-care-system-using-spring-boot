package server.service;
import server.domain.Appointment;
import server.domain.Slot;
import server.domain.User;
import server.utilities.ScheduleCreationException;

import java.util.List;

public interface DoctorService {
    List<Appointment> viewAppointments(int userID) ;
    boolean createNewSlot(int doctorId, String date, String startTime, String endTime ) throws ScheduleCreationException;
    void viewSlots (int userID);
}
