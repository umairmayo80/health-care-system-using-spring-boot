package server.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import server.domain.Slot;
import server.dto.SlotDTO;

@Component
public class SlotMapper {
    private final ModelMapper modelMapper;

    public SlotMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SlotDTO toDTO(Slot slot) {
        SlotDTO slotDTO = modelMapper.map(slot, SlotDTO.class);
        if (slot.getDoctor() != null) {
            slotDTO.setDoctorId(slot.getDoctor().getUserId());
        }
        return slotDTO;
    }

    public Slot toEntity(SlotDTO slotDTO) {
        return modelMapper.map(slotDTO, Slot.class);
    }

}