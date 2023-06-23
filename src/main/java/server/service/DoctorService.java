package server.service;

import server.domain.Schedule;
import server.domain.Slot;

public interface DoctorService {
    public void addScheduleSlots (Schedule schedule);
    public void viewAppointments(int userID) ;

    void addSlotsEntry(Slot slot);
    void viewSlots (int userID);
}
