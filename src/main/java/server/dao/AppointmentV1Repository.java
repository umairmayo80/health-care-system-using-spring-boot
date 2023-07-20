package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.Appointment;

import java.util.List;
@Repository
public interface AppointmentV1Repository extends JpaRepository<Appointment, Integer> {

    List<Appointment> getAppointmentsByPatient_UserId(int patientId);

    List<Appointment> getAppointmentsBySlot_Doctor_UserId(int doctorId);
}
