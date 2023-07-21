package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.Appointment;
import server.dto.AppointmentDTO;
import server.mapper.AppointmentMapper;
import server.service.AppointmentServiceV1;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByPatientId(@PathVariable int patientId){
        List<Appointment> appointmentList = appointmentServiceV1.getAppointmentsByPatientId(patientId);
        List<AppointmentDTO> appointmentDTOList= appointmentList.stream()
                .map(appointmentMapper::toDTO)
                .toList();
        return ResponseEntity.ok(appointmentDTOList);
    }
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByDoctorId(@PathVariable int doctorId){
        List<Appointment> appointmentList = appointmentServiceV1.getAppointmentsByDoctorId(doctorId);
        List<AppointmentDTO> appointmentDTOList= appointmentList.stream()
                .map(appointmentMapper::toDTO)
                .toList();
        return ResponseEntity.ok(appointmentDTOList);
    }


    @PostMapping("/")
    public ResponseEntity<String> createNewAppointment(@RequestBody Map<String, Integer> request){
        int slotId = request.get("slotId");
        int patientId = request.get("patientId");
        appointmentServiceV1.addAppointment(slotId,patientId);
        return ResponseEntity.ok("Appointment SuccessFully created");
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int appointmentId){
        appointmentServiceV1.deleteAppointment(appointmentId);
        return ResponseEntity.ok("Appointment deleted successfully");
    }


}
