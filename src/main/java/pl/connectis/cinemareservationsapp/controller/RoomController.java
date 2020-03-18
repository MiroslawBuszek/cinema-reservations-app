package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Room;
import pl.connectis.cinemareservationsapp.service.RoomService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public Iterable<Room> getRooms(@RequestParam Map<String, String> requestParam) {

        return roomService.findRoom(requestParam);

    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room) {

        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Room> updateById(@RequestParam long id, @Valid @RequestBody Room room) {

        return new ResponseEntity<>(roomService.updateById(id, room), HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(@RequestParam long id) {

        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }





}
