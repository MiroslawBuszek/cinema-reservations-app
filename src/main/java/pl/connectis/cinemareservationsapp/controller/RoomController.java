package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.service.RoomService;

import javax.validation.Valid;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public Iterable<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping
    public Room getRoomById(@RequestParam long id) {
        Room room = roomService.findById(id);
        if (room == null) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }
        return room;
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room) {
        validateRoom(room);
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    public ResponseEntity<?> addRoomList(@Valid @RequestBody Iterable<Room> roomList) {
        for (Room room : roomList) {
            validateRoom(room);
        }
        return new ResponseEntity<>(roomService.saveAll(roomList), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Room> updateById(@RequestParam long id, @Valid @RequestBody Room room) {
        Room existingRoom = roomService.findById(id);
        if (existingRoom == null) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }
        validateRoom(room);
        return new ResponseEntity<>(roomService.updateById(id, room), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(@RequestParam long id) {
        Room room = roomService.findById(id);
        if (room == null) {
            throw new ResourceNotFoundException("room {id=" + id + "} was not found");
        }
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validateRoom(Room room) {
        if (!roomService.validateCapacity(room)) {
            throw new BadRequestException("capacity does not correspond to layout");
        } else if (room.getCapacity() == 0) {
            roomService.setCapacityFromLayout(room);
        }
    }

}
