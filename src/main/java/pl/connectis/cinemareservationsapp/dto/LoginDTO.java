package pl.connectis.cinemareservationsapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDTO {

    @Email
    @NotBlank(message="Email is required")
    private String username;

    @NotBlank(message="Password is required")
    @Size(min=8, max=32)
    private String password;

}
