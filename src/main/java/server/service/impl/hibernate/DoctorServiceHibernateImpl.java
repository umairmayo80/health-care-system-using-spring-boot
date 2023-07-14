package server.service.impl.hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Slot;
import server.service.DoctorService;

@Component
public class DoctorServiceHibernateImpl implements DoctorService {
    private final AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate;
    private final SlotServiceHibernateImpl slotServiceHibernate;


    @Autowired
    public DoctorServiceHibernateImpl(AppointmentServiceV1HibernateImpl appointmentServiceV1Hibernate, SlotServiceHibernateImpl slotServiceHibernate) {
        this.appointmentServiceV1Hibernate = appointmentServiceV1Hibernate;
        this.slotServiceHibernate = slotServiceHibernate;
    }

    @Override
    public void viewAppointments(int userID) {
        appointmentServiceV1Hibernate.viewAppointmentsByDoctorId(userID);
    }

    @Override
    public void addSlotsEntry(Slot slot) {
        slotServiceHibernate.addSlotEntry(slot);
    }

    @Override
    public void viewSlots(int userID) {
        slotServiceHibernate.viewSlotsById(userID);
    }
}
