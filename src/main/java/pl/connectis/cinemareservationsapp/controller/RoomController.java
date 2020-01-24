package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.ResourceExistsException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.service.RoomService;

import javax.validation.Valid;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/room/all")
    public Iterable<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/room/{id}")
    public Room getRoomById(@PathVariable long id) {
        Room room = roomService.findById(id);
        if(room == null) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }
        return room;
    }

    @PostMapping("/room")
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room) {
        validateRoom(room);
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PostMapping("/room/many")
    public ResponseEntity<Iterable> addRoomList(@Valid @RequestBody Iterable<Room> roomList) {
        for (Room room : roomList) {
            validateRoom(room);
        }
        return new ResponseEntity<>(roomService.saveAll(roomList), HttpStatus.CREATED);
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity deleteRoom(@PathVariable long id) {
        Room room = roomService.findById(id);
        if (room == null) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }
        roomService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private void validateRoom(Room room) {
        if (!roomService.validateCapacity(room)) {
            throw new ResourceExistsException("capacity does not correspond to layout");
        } else if (room.getCapacity() == 0) {
            roomService.setCapacityFromLayout(room);
        }
    }

}
