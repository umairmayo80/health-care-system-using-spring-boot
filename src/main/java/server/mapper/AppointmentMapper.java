package server.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.Appointment;
import server.dto.AppointmentDTO;

@Component
public class AppointmentMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppointmentDTO toDTO(Appointment appointment){
        AppointmentDTO appointmentDTO = modelMapper.map(appointment,AppointmentDTO.class);
        appointmentDTO.setDoctorName(appointment.getSlot().getDoctor().getName());
        appointmentDTO.setPatientName(appointment.getPatient().getName());
        appointmentDTO.setDate(appointment.getSlot().getDate());
        appointmentDTO.setStartTime(appointment.getSlot().getStartTime());
        appointmentDTO.setEndTime(appointment.getSlot().getEndTime());
        return appointmentDTO;
    }
}
