package server.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.domain.Slot;
import server.dto.SlotDTO;
import server.mapper.SlotMapper;
import server.service.SlotService;
import server.utilities.ScheduleCreationException;

import java.util.List;

@RestController
@RequestMapping(path = "slots")
public class SlotController {

    private final SlotService slotService;
    private final SlotMapper slotMapper;

    @Autowired
    public SlotController(SlotService slotService, SlotMapper slotMapper) {
        this.slotService = slotService;
        this.slotMapper = slotMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<SlotDTO>> getSlots(){
        List<Slot> slots = slotService.getSlots();
        List<SlotDTO> slotDTOList = slots.stream()
                .map(slotMapper::toDTO)
                .toList();
        return ResponseEntity.ok(slotDTOList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SlotDTO>> getSlotsByDoctorId(@PathVariable int userId){
        List<Slot> slots = slotService.getSlotsById(userId);
        List<SlotDTO> slotDTOList = slots.stream()
                .map(slotMapper::toDTO)
                .toList();
        return ResponseEntity.ok(slotDTOList);
    }

    @GetMapping("/free_slots")
    public ResponseEntity<List<SlotDTO>> getFreeSlots(){
        List<Slot> slots = slotService.getFreeSlots();
        List<SlotDTO> slotDTOList = slots.stream()
                .map(slotMapper::toDTO)
                .toList();
        return ResponseEntity.ok(slotDTOList);
    }

    @PostMapping("/")
    public ResponseEntity<String> createNewSlot(@RequestBody SlotDTO slotDTO){
        System.out.println(slotDTO);
        try {
            slotService.createNewSlot(slotDTO.getDoctorId(),
                    slotDTO.getDate().toString(),
                    slotDTO.getStartTime().toString(),
                    slotDTO.getEndTime().toString());
            return ResponseEntity.ok("Slot successfully created");
        }catch (ScheduleCreationException e){
            throw new IllegalStateException(e.getMessage());
        }
    }


    @DeleteMapping("/{slotId}")
    public ResponseEntity<String> deleteSlot(@PathVariable int slotId){
        slotService.removeSlot(slotId);
        return ResponseEntity.ok("Slot deleted successfully");
    }




}
