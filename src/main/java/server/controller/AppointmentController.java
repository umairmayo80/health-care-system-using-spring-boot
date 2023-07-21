package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.Appointment;
import server.dto.AppointmentDTO;
import server.mapper.AppointmentMapper;
import server.service.AppointmentServiceV1;
import java.util.List;

@RestController
@RequestMapping(path = "appointments")
public class AppointmentController {
    private final AppointmentServiceV1 appointmentServiceV1;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentController(AppointmentServiceV1 appointmentServiceV1, AppointmentMapper appointmentMapper) {
        this.appointmentServiceV1 = appointmentServiceV1;
        this.appointmentMapper = appointmentMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(){
        List<Appointment> appointmentList = appointmentServiceV1.getAppointments();
        List<AppointmentDTO> appointmentDTOList= appointmentList.stream()
                .map(appointmentMapper::toDTO)
                .toList();
        return ResponseEntity.ok(appointmentDTOList);
    }
}
