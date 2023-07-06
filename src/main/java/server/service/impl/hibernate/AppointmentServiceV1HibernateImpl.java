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
        Session session = ServiceContext.getSessionFactory().openSession();
        List<AppointmentV1> appointmentList = null;
        // HQL
        appointmentList = session.createQuery("from AppointmentV1").list();
        // Initialize associations before closing the session
        for (AppointmentV1 appointmentV1 : appointmentList) {
            appointmentV1.getPatient().getName(); // Initialize the patient association
            appointmentV1.getSlot().getDoctor().getName(); // Initialize the slot and doctor associations
        }
        session.close();
        return appointmentList;
    }

    @Override
    public void viewAllAppointments() {
        List<AppointmentV1> appointmentV1List = getAppointments();
        displayAppointmentData(appointmentV1List);
    }

    // Method to display appointment data with patient and doctor names
    public static void displayAppointmentData(List<AppointmentV1> appointmentV1List)  {
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
        System.out.println("| appointmentId | patientId | patientName  | slotId | doctorId | doctorName      | date      | startTime | endTime  | occupied |");
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
        for(AppointmentV1 appointmentV1 : appointmentV1List) {
            int appointmentId = appointmentV1.getAppointmentId();
            int patientId = appointmentV1.getPatient().getUserId();
            String patientName = appointmentV1.getPatient().getName();
            int slotId = appointmentV1.getSlot().getSlotId();
            int doctorId = appointmentV1.getSlot().getDoctor().getUserId();
            String doctorName = appointmentV1.getSlot().getDoctor().getName();
            String date = appointmentV1.getSlot().getDate().toString();
            String startTime = appointmentV1.getSlot().getStartTime().toString();
            String endTime = appointmentV1.getSlot().getEndTime().toString();
            boolean occupied = appointmentV1.getSlot().getOccupied();

            System.out.printf("|%14d |%10d | %12s |%7d |%9d | %15s |%10s |%9s |%9s |%10s |\n",
                    appointmentId, patientId, patientName, slotId, doctorId, doctorName, date, startTime, endTime, occupied);
        }
        System.out.println("+---------------+-----------+--------------+--------+----------+-----------------+-----------+----------+----------+-----------+");
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
        Slot slot = ServiceContext.getSlotServiceHibernate().getSlotBySlotId(appointment.getDoctorSlotId());
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
