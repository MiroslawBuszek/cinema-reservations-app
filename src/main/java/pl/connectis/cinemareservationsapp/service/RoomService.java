package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(long id) {
        return roomRepository.findById(id);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Iterable<Room> saveAll(Iterable<Room> roomList) {
        return roomRepository.saveAll(roomList);
    }

    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }

    public boolean validateCapacity(Room room) {
        return getCapacityFromLayout(room) == room.getCapacity();
    }

    public int getCapacityFromLayout(Room room) {
        int capacity = 0;
        int[] layout = room.getLayout();
        for (int i = 0; i < layout.length; i++) {
            capacity += layout[i];
        }
        return capacity;
    }

    public void setCapacityFromLayout(Room room) {
        room.setCapacity(getCapacityFromLayout(room));
    }
}
