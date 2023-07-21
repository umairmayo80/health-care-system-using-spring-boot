package server.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.Slot;
import java.util.List;
@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
    List<Slot> getSlotsByDoctor_UserId(int userId);
    Slot getSlotBySlotId (int slotID);
    List<Slot> getSlotsByDoctor_UserIdAndOccupiedIs(int userId, boolean status);

    List<Slot> getSlotsByOccupiedIs(boolean status);

}
