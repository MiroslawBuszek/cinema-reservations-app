package pl.connectis.cinemareservationsapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;

import java.util.*;

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

        validateRoom(room);
        return roomRepository.save(room);

    }

    @Transactional
    public Room updateById(Long id, Room room) {

        validateRoomExists(id);
        validateRoom(room);

        Room existingRoom = getRoom(id);
        if (room.getLayout() != null) {
            existingRoom.setLayout(room.getLayout());
        }
        setCapacityFromLayout(existingRoom);
        return existingRoom;
    }

    public void deleteById(Long id) {

        validateRoomExists(id);
        roomRepository.deleteById(id);

    }

    private void validateRoomExists(Long id) {

        if (!roomExists(id)) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }

    }

    private void validateRoom(Room room) {

        if (!validateCapacity(room)) {
            throw new BadRequestException("capacity does not correspond to layout");
        } else if (room.getCapacity() == null) {
            setCapacityFromLayout(room);
        }

    }

    private boolean validateCapacity(Room room) {

        return getCapacityFromLayout(room) == room.getCapacity();

    }

    private int getCapacityFromLayout(Room room) {

        List<Integer> layoutIntegerList = getLayoutAsList(room.getLayout());
        return layoutIntegerList.stream().mapToInt(Integer::intValue).sum();

    }

    private void setCapacityFromLayout(Room room) {

        room.setCapacity(getCapacityFromLayout(room));

    }

    private List<Integer> getLayoutAsList(String layout) {

        List<String> layoutStringList;
        List<Integer> layoutIntegerList = new ArrayList<>();

        try {

            layoutStringList = Arrays.asList(layout.split(","));

            for (String rowCapacityString : layoutStringList) {

                layoutIntegerList.add(Integer.parseInt(rowCapacityString));

            }

        } catch (Exception exception) {

            throw new BadRequestException("inappropriate layout format");

        }

        return layoutIntegerList;

    }

    private Room getRoom(Long id) {

        Optional<Room> roomOptional = roomRepository.findById(id);

        if (!roomOptional.isPresent()) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }

        return roomOptional.get();

    }

}
