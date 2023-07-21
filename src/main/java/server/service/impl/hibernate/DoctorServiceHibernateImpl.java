package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.domain.Appointment;
import server.service.DoctorService;
import server.utilities.ScheduleCreationException;

import java.util.List;

@Service
public class DoctorServiceHibernateImpl implements DoctorService {
    private final AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate;
    private final SlotServiceHibernateImpl slotServiceHibernate;


    @Autowired
    public DoctorServiceHibernateImpl(AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate, SlotServiceHibernateImpl slotServiceHibernate) {
        this.appointmentServiceV1Hibernate = appointmentServiceV1Hibernate;
        this.slotServiceHibernate = slotServiceHibernate;
    }

    @Override
    public List<Appointment> viewAppointments(int userID) {
        return appointmentServiceV1Hibernate.getAppointmentsByDoctorId(userID);
    }


    @Override
    public boolean createNewSlot(int doctorId, String date, String startTime, String endTime) throws ScheduleCreationException {
        return  slotServiceHibernate.createNewSlot(doctorId,date,startTime,endTime);
    }

    @Override
    public void viewSlots(int userID) {
        slotServiceHibernate.viewSlotsById(userID);
    }
}
