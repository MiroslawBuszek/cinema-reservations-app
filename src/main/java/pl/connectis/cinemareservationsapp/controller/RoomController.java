package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/room/all")
    public Iterable<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/room/{id}")
    public List<Room> getRoomById(@PathVariable long id) {
        return roomService.findById(id);
    }

    @PostMapping("/room")
    public Room addRoom(@Valid @RequestBody Room room) {
        return roomService.save(room);
    }

    @PostMapping("/room/many")
    public Iterable<Room> addRoomList(@Valid @RequestBody Iterable<Room> roomList) {
        return roomService.saveAll(roomList);
    }

    @DeleteMapping("/room/{id}")
    public void deleteRoom(@PathVariable long id) {
        roomService.deleteById(id);
    }

}
