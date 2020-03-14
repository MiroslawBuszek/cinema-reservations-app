package pl.connectis.cinemareservationsapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.connectis.cinemareservationsapp.dto.UserDTO;
import pl.connectis.cinemareservationsapp.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping
public class RegistrationHTMLController extends RegistrationBaseController {

    @Autowired
    UserService userService;

    @GetMapping("signup")
    public ModelAndView signUpForm(UserDTO userDTO) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;

    }

    @PostMapping(value = "signup", produces = MediaType.TEXT_HTML_VALUE)
    public String addClient(@Valid UserDTO userDTO, BindingResult bindingResult) {

        encodePassword(userDTO);
        log.info(userDTO.toString());

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        addUser(userDTO);

        return "login";

    }

}
