package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    public List<Room> findById(long id) {
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
}
