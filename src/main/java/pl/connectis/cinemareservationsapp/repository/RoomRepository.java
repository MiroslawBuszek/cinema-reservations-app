package pl.connectis.cinemareservationsapp.repository;

import org.springframework.stereotype.Repository;
import pl.connectis.cinemareservationsapp.model.Room;

@Repository
public interface RoomRepository extends CustomJpaRepository<Room, Long> {

    default Room findOrThrow(Long id) {
        return findByIdOrThrow(id, "room");
    }

    default void existsOrThrow(Long id) {
        existsByIdOrThrow(id, "room");
    }

}
