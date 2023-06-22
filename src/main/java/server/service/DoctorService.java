package server.service;

import server.domain.Schedule;

public interface DoctorService {
    public void addSchedule(Schedule schedule);
    public void viewAppointments(int userID) ;
    void viewSchedule(int userID);
}
