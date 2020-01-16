package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

}
