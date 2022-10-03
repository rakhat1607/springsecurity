package kz.bitlab.springsecurity.controllers;

import kz.bitlab.springsecurity.dto.UserDto;
import kz.bitlab.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDto userDto;

    @GetMapping(value = "/")
    public String indexPage() {
        userDto.setEmail("Asdadssda");
        System.out.println(userDto);
        return "index";
    }

    @GetMapping(value = "/enter")
    public String enterPage() {
        return "enter";
    }

    @GetMapping(value = "/403")
    public String deniedPage() {
        return "403";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage() {
        return "profile";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String adminPage() {
        return "admin";
    }


    @GetMapping(value = "/editor")
    @PreAuthorize("hasAnyRole('ROLE_EDITOR' , 'ROLE_ADMIN')")
    public String editorPage() {
        return "editor";
    }

    @GetMapping(value = "/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String signUp(UserDto userDto) {
        if (userService.registerUser(userDto)) {
            return "redirect:/signup?success";
        }
        return "redirect:/signup?error";
    }
}
