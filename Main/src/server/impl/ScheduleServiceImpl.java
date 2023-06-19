package server.impl;
import server.domain.Schedule;
import server.service.FileModificationChecker;
import server.service.ScheduleCreationException;
import server.service.ScheduleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleServiceImpl implements ScheduleService {
    private static final String scheduleFilePath = "schedule.csv";
    private List<Schedule> scheduleList;

    public ScheduleServiceImpl(){
        this.scheduleList = new ArrayList<>();
    }


    public void saveSchedule() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scheduleFilePath))) {
            for (Schedule schedule : scheduleList) {
                String line = schedule.getUserId() + "," +
                        schedule.getDate() + "," +
                        schedule.getStartTime() + "," +
                        schedule.getEndTime();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving schedule to file: " + e.getMessage());
        }
    }

    public List<Schedule> loadSchedule() {
        List<Schedule> scheduleList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int userId = Integer.parseInt(parts[0]);
                    String date = parts[1].strip();
                    String startTime = parts[2].strip();
                    String endTime = parts[3].strip();
                    Schedule schedule = new Schedule(userId, date, startTime, endTime);
                    scheduleList.add(schedule);
                }
            }
        } catch (IOException | ScheduleCreationException e) {
            System.out.println("Error loading schedule from file: " + e.getMessage());
        }
        return scheduleList;
    }

    public List<Schedule> getSchedules(){
        if(scheduleList.isEmpty() ||
                FileModificationChecker.isFileModified(scheduleFilePath,
                        FileModificationChecker.loadedLastModifiedInfo.get(scheduleFilePath)))
        {
            scheduleList = loadSchedule();
        }
        return  scheduleList;
    }

    public List<Schedule> getSchedulesByID(int doctorID){
        List<Schedule> scheduleList = getSchedules();
        List<Schedule> doctorSheduleList = scheduleList.stream()
                .filter(schedule -> schedule.getUserId() == doctorID)
                .collect(Collectors.toList());
        return doctorSheduleList;
    }

    public void addScheduleEntry(Schedule schedule){
        scheduleList = getSchedules();
        scheduleList.add(schedule);
        saveSchedule();
    }
    public static void main(String[] args) throws ScheduleCreationException {
//        Schedule newSchedule = new Schedule(-1, "2023-01dsd-01",
//                "09:00:00","17:00:00");
//        server.service.ScheduleService.addScheduleEntry(newSchedule);
//
//        server.service.ScheduleService.getSchedulesByID(18).forEach(System.out::println);
    }
}
