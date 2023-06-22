package server.service;
import server.domain.Schedule;
import java.util.List;

public interface ScheduleService {
    static final String scheduleFilePath = "schedule.csv";

    public void saveSchedule();

    public List<Schedule> loadSchedule();

    public List<Schedule> getSchedules();

    public List<Schedule> getSchedulesByID(int doctorID);

    public void addScheduleEntry(Schedule schedule);
}
