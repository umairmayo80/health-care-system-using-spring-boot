package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.dao.AppointmentV1Repository;
import server.dao.SlotRepository;
import server.dao.UserRepository;
import server.domain.Slot;
import server.domain.User;
import server.domain.Appointment;
import server.service.AppointmentServiceV1;

import static server.utilities.DisplayFormatting.displayAppointmentData;


import java.util.List;

@Component
public class AppointmentServiceV1HibernateImpl implements AppointmentServiceV1 {
    private final AppointmentV1Repository appointmentV1Repository;
    private final SlotRepository slotRepository;

    private final UserRepository userRepository;

    @Autowired
    public AppointmentServiceV1HibernateImpl(AppointmentV1Repository appointmentV1Repository, SlotRepository slotRepository, UserRepository userRepository) {
        this.appointmentV1Repository = appointmentV1Repository;
        this.slotRepository = slotRepository;
        this.userRepository = userRepository;
    }





    @Override
    public List<Appointment> getAppointments() {
        return appointmentV1Repository.findAll();
    }

    @Override
    public void viewAllAppointments() {
        displayAppointmentData(getAppointments());
    }

    @Override
    public void viewAppointmentsByPatientId(int patientId) {
       displayAppointmentData(appointmentV1Repository.getAppointmentsByPatient_UserId(patientId));
    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {
       displayAppointmentData(appointmentV1Repository.getAppointmentsBySlot_Doctor_UserId(doctorId));
    }


    @Transactional //it will automatically commit all the changes made to all entities or rollback if error occurs
    @Override
    public boolean addAppointment(int doctorSlotId, int patientId) {
        // check if the slot is available or not
        Slot slot = slotRepository.getSlotBySlotId(doctorSlotId);
        if(slot == null){
            System.out.println("No slot found against the provided id: "+doctorSlotId);
            throw new IllegalStateException("No slot found against the provided id: \"+doctorSlotId");
        }
        if(slot.getOccupied()){
            System.out.println("Error, slot is already occupied. Pick a different slot");
            throw new IllegalStateException("Error, slot is already occupied. Pick a different slot");
        }
        User patient = userRepository.getUserByUserId(patientId);
        Appointment newAppointment = new Appointment(patient,slot);
        //associate the appointment with the slot
        slot.setAppointmentV1(newAppointment);
        slot.setOccupied(true); //set the occupied status
        // now associate the new appointment with the parents
        patient.addAppointmentV1(newAppointment);
        appointmentV1Repository.save(newAppointment);
        return true;
    }
}
