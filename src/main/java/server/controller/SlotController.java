package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.Slot;
import server.dto.SlotDTO;
import server.mapper.SlotMapper;
import server.service.SlotService;

import java.util.List;

@RestController
@RequestMapping(path = "slots")
public class SlotController {

    private SlotService slotService;
    private SlotMapper slotMapper;

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


}
