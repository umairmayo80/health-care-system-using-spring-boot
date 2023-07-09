package server.service;

import server.domain.Schedule;
import server.domain.Slot;

public interface DoctorService {
    void addScheduleSlots (Schedule schedule);
    void viewAppointments(int userID) ;

    void addSlotsEntry(Slot slot);
    void viewSlots (int userID);
}
