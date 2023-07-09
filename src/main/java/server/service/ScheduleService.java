package server.service;
import server.domain.Schedule;
import java.util.List;

public interface ScheduleService {
    String scheduleFilePath = "schedule.csv";

    void saveSchedule();

    List<Schedule> loadSchedule();

    List<Schedule> getSchedules();

    List<Schedule> getSchedulesByID(int doctorID);

    void addScheduleEntry(Schedule schedule);
}
