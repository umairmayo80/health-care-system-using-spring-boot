package server.dao.impl.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.dao.AppointmentV1Repository;
import server.domain.Slot;
import server.domain.Appointment;
import java.util.List;


@Component
public class AppointmentRepoHibernate implements AppointmentV1Repository {
    SessionFactory sessionFactory;
    SlotRepoHibernateImpl slotRepoHibernate;


    @Autowired
    public AppointmentRepoHibernate(SessionFactory sessionFactory, SlotRepoHibernateImpl slotServiceHibernate) {
        this.sessionFactory = sessionFactory;
        this.slotRepoHibernate = slotServiceHibernate;
    }


    @Override
    public void saveAppointmentsToStorage(List<Appointment> appointmentList) {
        appointmentList.forEach(this::add);
    }

    @Override
    public List<Appointment> getAll() {
        Session session = sessionFactory.openSession();
        List<Appointment> appointmentList = session.createQuery("from Appointment", Appointment.class).list();
        initializeAssociations(appointmentList);
        session.close();
        return appointmentList;
    }

    private void initializeAssociations(List<Appointment> appointmentList) {
        // Initialize associations before closing the session
        for (Appointment appointmentV1 : appointmentList) {
            appointmentV1.getPatient().getName(); // Initialize the patient association
            appointmentV1.getSlot().getDoctor().getName(); // Initialize the slot and doctor associations
        }
    }


    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        Session session = sessionFactory.openSession();
        List<Appointment> appointmentList  = session.createQuery("from Appointment as a where a.patient.userId="+patientId, Appointment.class).list();
        initializeAssociations(appointmentList);
        session.close();
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        Session session = sessionFactory.openSession();
        List<Appointment> appointmentList = session.createQuery("from Appointment a where a.slot.doctor.userId="+doctorId, Appointment.class).list();
        initializeAssociations(appointmentList);
        session.close();
        return appointmentList;
    }

    @Override
    public boolean add (Appointment appointment) {
        // check if the slot is available or not
        Slot slot = slotRepoHibernate.getById(appointment.getDoctorSlotId());
        if(slot == null){
            System.out.println("No slot found against the provided id: "+appointment.getDoctorSlotId());
            return false;
        }
        if(slot.getOccupied()){
            System.out.println("Error, slot is already occupied. Pick a different slot");
            return false;
        }

        //associate the appointment with the slot
        slot.setAppointmentV1(appointment);

        // add the appointment into database
        Session session = sessionFactory.openSession();
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
