package ru.akhmetov.AutoRepair.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**  Контроллер Spring MVC, отвечающий за обработку запросов, связанных с аутентификацией и регистрацией пользователей  **/

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AUserValidator aUserValidator;
    private final RegistrationService registrationService;

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
