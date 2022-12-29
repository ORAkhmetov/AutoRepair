package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akhmetov.AutoRepair.models.AUser;
import ru.akhmetov.AutoRepair.services.RegistrationService;
import ru.akhmetov.AutoRepair.util.AUserValidator;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AUserValidator aUserValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(AUserValidator aUserValidator, RegistrationService registrationService) {
        this.aUserValidator = aUserValidator;
        this.registrationService = registrationService;
    }




    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("aUser") AUser aUser) {

        return "auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("aUser") @Valid AUser aUser,
                                      BindingResult bindingResult) {
        aUserValidator.validate(aUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        registrationService.register(aUser);

        return "redirect:/auth/login";
    }
}
