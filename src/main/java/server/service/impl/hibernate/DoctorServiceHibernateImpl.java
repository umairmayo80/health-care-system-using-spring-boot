package server.service.impl.hibernate;

import server.context.ServiceContext;
import server.domain.Schedule;
import server.domain.Slot;
import server.service.DoctorService;

public class DoctorServiceHibernateImpl implements DoctorService {
    @Override
    public void addScheduleSlots(Schedule schedule) {
    }

    @Override
    public void viewAppointments(int userID) {
        ServiceContext.getAppointmentServiceV1Hibernate().viewAppointmentsByDoctorId(userID);
    }

    @Override
    public void addSlotsEntry(Slot slot) {
        ServiceContext.getSlotServiceHibernate().addSlotEntry(slot);
    }

    @Override
    public void viewSlots(int userID) {
        ServiceContext.getSlotServiceHibernate().viewSlotsById(userID);
    }
}
