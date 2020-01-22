package pl.connectis.cinemareservationsapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class ClientExistsException extends RuntimeException {

    public ClientExistsException(String message) {
        super(message);
    }
}
