package server.service.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.context.ServiceContext;
import server.domain.Slot;
import server.domain.version1.AppointmentV1;
import server.service.version1.AppointmentServiceV1;


import java.util.List;

public class AppointmentServiceV1HibernateImpl implements AppointmentServiceV1 {
    @Override
    public void saveAppointmentsToStorage(List<AppointmentV1> appointmentList) {

    }

    @Override
    public List<AppointmentV1> getAppointments() {
        return null;
    }

    @Override
    public void viewAllAppointments() {

    }

    @Override
    public void viewAppointmentsByPatientId(int patientId) {

    }

    @Override
    public void viewAppointmentsByDoctorId(int doctorId) {

    }

    @Override
    public boolean addAppointmentEntry(AppointmentV1 appointment) {
        // check if the slot is available or not
        Slot slot = ServiceContext.getSlotService().getSlotBySlotId(appointment.getDoctorSlotId());
        if(slot == null){
            System.out.println("No slot found against the provided id: "+appointment.getDoctorSlotId());
            return false;
        }
        if(slot.getOccupied()){
            System.out.println("Error, slot is already occupied. Pick a different slot");
            return false;
        }

        // add the appointment into database
        Session session = ServiceContext.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            //save the appointment
            session.save(appointment);

            // update the selected slot occupied status to true
            String updateSlotOccupiedStatusQuery = "UPDATE Slot as s\n" +
                    "SET s.occupied = TRUE\n" +
                    "WHERE s.slotId = "+appointment.getDoctorSlotId();
            session.createQuery(updateSlotOccupiedStatusQuery).executeUpdate();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error: Unable to add appointment entry to the database using hibernate: " + e.getMessage());
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
                session.close();
            }
            return false;
        }

        return true;
    }
}
