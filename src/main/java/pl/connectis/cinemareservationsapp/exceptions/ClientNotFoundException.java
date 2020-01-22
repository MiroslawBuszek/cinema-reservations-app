package pl.connectis.cinemareservationsapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(long id) {
        super("client {id=" + id + "} was not found");
    }
}
