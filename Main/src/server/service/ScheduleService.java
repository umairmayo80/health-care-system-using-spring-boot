package server.service;

import server.domain.Schedule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ScheduleService {
    private static final String scheduleFilePath = "schedule.csv";
    private static List<Schedule> scheduleList = loadSchedule();


    public static void saveSchedule() {
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

    public static List<Schedule> loadSchedule() {
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
        } catch (IOException e) {
            System.out.println("Error loading schedule from file: " + e.getMessage());
        }
        return scheduleList;
    }

    public static List<Schedule> getSchedules(){
        if(ScheduleService.scheduleList.isEmpty() ||
                FileModificationChecker.isFileModified(scheduleFilePath,
                        FileModificationChecker.loadedLastModifiedInfo.get(scheduleFilePath)))
        {
            ScheduleService.scheduleList = ScheduleService.loadSchedule();
        }
        return  ScheduleService.scheduleList;
    }

    public static void addScheduleEntry(Schedule schedule){
        ScheduleService.scheduleList = getSchedules();
        ScheduleService.scheduleList.add(schedule);
        saveSchedule();
    }
    public static void main(String[] args){
        Schedule newSchedule = new Schedule(-1, "2023-01-01",
                "09:00:00","17:00:00");
        ScheduleService.addScheduleEntry(newSchedule);
    }
}
