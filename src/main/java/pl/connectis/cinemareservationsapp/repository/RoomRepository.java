package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Room;

@Repository
public interface RoomRepository extends CustomJpaRepository<Room, Long> {

    @Override
    default String entityName() {
        return "room";
    }
}
