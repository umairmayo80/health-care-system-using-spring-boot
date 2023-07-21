package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.dao.SlotRepository;
import server.dao.UserRepository;
import server.domain.Slot;
import server.domain.User;
import server.service.SlotService;
import server.utilities.ScheduleCreationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class SlotServiceHibernateImpl implements SlotService {

    private final SlotRepository slotRepository;
    private final UserRepository userRepository;


    @Autowired
    public SlotServiceHibernateImpl(SlotRepository slotRepository, UserRepository userRepository) {
        this.slotRepository = slotRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Slot> getSlots() {
        return slotRepository.findAll();
    }

    @Override
    public Slot getSlotBySlotId(int slotID) {
       return slotRepository.getSlotBySlotId(slotID);
    }


    @Override
    public void viewAllSlots() {
        List<Slot> slotList = getSlots();
        displaySlots(slotList);
    }

    public static void displaySlots(List<Slot> slotList){
        // Display the data in a table format
        System.out.println("+--------+----------+------------+-----------+----------+----------+");
        System.out.println("| slotId | doctorId |    date    | startTime |  endTime | occupied |");
        System.out.println("+--------+----------+------------+-----------+----------+----------+");

        for(Slot slot : slotList){
            System.out.format("|%-7d |%9d |%11s |%10s |%9s |%9s |\n", slot.getSlotId(), slot.getDoctor().getUserId(), slot.getDate(), slot.getStartTime(), slot.getEndTime(), slot.getOccupied());
        }
        System.out.println("+--------+----------+------------+-----------+----------+----------+");

    }

    @Override
    public void viewSlotsById(int userId) {
        displaySlots(getSlotsById(userId));
    }


    @Override
    public List<Slot> getSlotsById(int userId) {
       return slotRepository.getSlotsByDoctor_UserId(userId);
    }

    @Override
    public void viewBookedSlotsById(int userId) {
        displaySlots(slotRepository.getSlotsByDoctor_UserIdAndOccupiedIs(userId,true));
    }

    public List<Slot> getFreeSlots() {
        return slotRepository.getSlotsByOccupiedIs(false);
    }

    @Override
    public void viewFreeSlotsById(int userId) {
       displaySlots(slotRepository.getSlotsByDoctor_UserIdAndOccupiedIs(userId,false));
    }

    @Override
    public boolean addSlot(Slot newSlot, User currentUser) {
        //associate the slot with the parent
        currentUser.addSlot(newSlot);
        slotRepository.save(newSlot);
        return true;
    }

    @Transactional
    @Override
    public boolean removeSlot(int slotId) {
        Slot slot = slotRepository.getSlotBySlotId(slotId);
        if(slot==null){
            throw new IllegalStateException("No slot found against the slot-id:"+slotId);
        }
//        //First Remove the Association between user and slot
//        slot.getDoctor().removeSlot(slot);
//        //Remove the associated appointment
//        slot.removeAppointmentV1();


        // for not applying dirty solution
        // It seems we had to delete the association from both parents then cascade worked
        slot.getDoctor().removeSlot(slot);

        slot.getAppointmentV1().getPatient().removeAppointment(slot.getAppointmentV1());
        slot.removeAppointmentV1();

        slotRepository.delete(slot);
        return true;
    }

    @Override
    public boolean createNewSlot(int doctorId, String date, String startTime, String endTime) throws ScheduleCreationException {
        User doctor = userRepository.getUserByUserId(doctorId);
        if(doctor==null){
            throw new IllegalStateException("No user found against provided userId:"+doctorId);
        }

        LocalDate dateParsed;
        LocalTime startTimeParsed;
        LocalTime endTimeParsed;

        try {
            dateParsed = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ScheduleCreationException("Invalid date format. Please enter date in the format YYYY-MM-DD.", e);
        }

        try {
            startTimeParsed = LocalTime.parse(startTime);
        } catch (DateTimeParseException e) {
            throw new ScheduleCreationException("Invalid start time format. Please enter time in the format HH:MM:SS.", e);
        }

        try {
            endTimeParsed = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            throw new ScheduleCreationException("Invalid end time format. Please enter time in the format HH:MM:SS.", e);
        }

        Slot newSlot = new Slot(doctor,dateParsed,startTimeParsed,endTimeParsed);
        return addSlot(newSlot,doctor);

    }
}
