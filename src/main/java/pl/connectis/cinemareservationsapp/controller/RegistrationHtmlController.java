package pl.connectis.cinemareservationsapp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.connectis.cinemareservationsapp.dto.UserDTO;

import javax.validation.Valid;

@Controller
@RequestMapping
public class RegistrationHtmlController extends RegistrationBaseController {


    @GetMapping("signup")
    public ModelAndView signUpForm(UserDTO userDTO) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-client");
        return modelAndView;

    }

    @PostMapping(value = "add/client", produces = MediaType.TEXT_HTML_VALUE)
    public String addClient(@Valid UserDTO userDTO, BindingResult bindingResult) {

        encodePassword(userDTO);

        if (bindingResult.hasErrors()) {
            return "add-client";
        }

        return "login";

    }

}
