package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.connectis.cinemareservationsapp.model.Place;
import pl.connectis.cinemareservationsapp.repository.PlaceRepository;

@RestController
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping("/place/all")
    public Iterable<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
}
