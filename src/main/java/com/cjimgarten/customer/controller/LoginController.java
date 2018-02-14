package com.cjimgarten.customer.controller;

import com.cjimgarten.customer.model.User;
import com.cjimgarten.customer.model.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * LoginController.java
 * Created by chris on 2/10/18.
 */
@Controller
@RequestMapping(value = "login-ctrl")
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String redirectToLoginPage() {
        LOGGER.info("Redirecting to login page");
        return "redirect:/login-ctrl/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLoginPage(User user, Model model) {
        LOGGER.info("GET login page");
        model.addAttribute("title", "Login");
        return "/login/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(@Valid User user,
                                   BindingResult bindingResult,
                                   Model model) {
        LOGGER.info("Processing login form POST");

        if (bindingResult.hasErrors()) {
            LOGGER.info("User object has {} error(s) -- login failed", bindingResult.getErrorCount());
            model.addAttribute("title", "Login");
            return "/login/login";
        }

        LOGGER.info("Username: {}", user.getUsername());
        LOGGER.debug("Password: {}", user.getPassword());
        LOGGER.debug("{}", user);

        // TODO log the user in

        LOGGER.info("Redirecting to app-ctrl");
        return "redirect:/app-ctrl";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegisterPage(User user, Model model) {
        LOGGER.info("GET register page");
        model.addAttribute("title", "Register");
        return "/login/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@Valid User user,
                                      Errors errors,
                                      Model model) {
        LOGGER.info("Processing register form POST");

        if (errors.hasErrors()) {
            LOGGER.info("User object has {} error(s) -- registration failed", errors.getErrorCount());
            model.addAttribute("title", "Register");
            return "/login/register";
        }

        LOGGER.info("Username: {}", user.getUsername());
        LOGGER.debug("Password: {}", user.getPassword());
        LOGGER.debug("{}", user);

        // TODO register new user

        LOGGER.info("Redirecting to app-ctrl");
        return "redirect:/app-ctrl";
    }
}
