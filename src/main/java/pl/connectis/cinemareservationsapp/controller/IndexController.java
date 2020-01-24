package pl.connectis.cinemareservationsapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String welcome(){
        return "Welcome newcomer!";
    }

    @GetMapping("index")
    public String index() {
        return "cinema-reservations-app is up!";
    }

}
