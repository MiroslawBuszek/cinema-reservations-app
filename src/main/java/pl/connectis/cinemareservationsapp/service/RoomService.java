package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findRoom(Map<String, String> requestParam) {
        Room room = new Room();
        if (requestParam.containsKey("id")) {
            room.setId(Long.parseLong(requestParam.get("id")));
        }
        if (requestParam.containsKey("capacity")) {
            room.setCapacity(Integer.parseInt(requestParam.get("capacity")));
        }
        Example<Room> roomExample = Example.of(room);
        return roomRepository.findAll(roomExample);
    }

    public boolean roomExists(Long id) {
        return roomRepository.findById(id).isPresent();
    }

    public Room save(Room room) {
        validateRoomLayout(room);
        Room savedRoom = roomRepository.save(room);
        log.info("room {id=" + savedRoom.getId() + "} was added: " + room);
        return savedRoom;
    }

    public Room updateById(Room room) {
        validateRoomExists(room.getId());
        validateRoomLayout(room);
        Room savedRoom = roomRepository.save(room);
        log.info("room {id=" + savedRoom.getId() + "} was updated :" + savedRoom.toString());
        return savedRoom;
    }

    public void deleteById(Long id) {
        validateRoomExists(id);
        roomRepository.deleteById(id);
        log.info("movie {id=" + id + "} was deleted");
    }


    private void validateRoomExists(Long id) {
        if (!roomExists(id)) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }
    }

    private void validateRoomLayout(Room room) {
        String[] layoutStringArray = room.getLayout().split(",");
        int roomCapacity = 0;
        try {
            int [] layoutIntArray = Stream.of(layoutStringArray).mapToInt(Integer::parseInt).toArray();
            roomCapacity = Arrays.stream(layoutIntArray).sum();
        } catch (Exception exception) {
            throw new BadRequestException("inappropriate layout format");
        }
        if (roomCapacity != room.getCapacity()) {
            throw new BadRequestException("capacity does not correspond to layout");
        }
    }

}
