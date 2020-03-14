package pl.connectis.cinemareservationsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {

    @Email
    @NotBlank(message="Email is required")
    private String username;

    @NotBlank(message="Password is required")
    @Size(min=8, max=32)
    private CharSequence password;

    private String encodedPassword;

    @NotBlank(message="First name is required")
    private String firstName;

    @NotBlank(message="Last name is required")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

}
