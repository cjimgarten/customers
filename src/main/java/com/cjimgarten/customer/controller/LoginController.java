package com.cjimgarten.customer.controller;

import com.cjimgarten.customer.model.User;
import com.cjimgarten.customer.model.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
    public String getLoginPage(Model model) {
        LOGGER.info("GET login page");
        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());
        return "/login/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(@Valid User user, BindingResult bindingResult,
                                   Model model) {

        LOGGER.info("Processing login form POST");

        if (bindingResult.hasErrors()) {
            LOGGER.info("Login failed -- User object has {} error(s)", bindingResult.getErrorCount());
            model.addAttribute("title", "Login");
            return "/login/login";
        }

        LOGGER.info("Username: {}", user.getUsername());
        LOGGER.debug("Password: {}", user.getPassword());
        LOGGER.debug("{}", user);

        // TODO hash password

        // validate username and password combination
        for (User u : userRepository.findAll()) {
            if ( u.getUsername().equals(user.getUsername()) &&
                    u.getPassword().equals(user.getPassword()) ) {

                // successful validation
                LOGGER.info("Login successful -- Redirecting to app-ctrl");
                return "redirect:/app-ctrl";
            }
        }

        // failed validation
        String usernamePasswordError = "Invalid username or password";
        LOGGER.info("Registration failed -- {}", usernamePasswordError);
        model.addAttribute("title", "Login");
        model.addAttribute("usernamePasswordError", usernamePasswordError);
        return "/login/login";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {
        LOGGER.info("GET register page");
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User());
        return "/login/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@Valid User user, Errors errors,
                                      Model model, HttpServletRequest request) {

        LOGGER.info("Processing register form POST");

        if (errors.hasErrors()) {
            LOGGER.info("Registration failed -- User object has {} error(s)", errors.getErrorCount());
            model.addAttribute("title", "Register");
            return "/login/register";
        }

        String confirmPassword = request.getParameter("confirm-password");

        LOGGER.info("Username: {}", user.getUsername());
        LOGGER.debug("Password: {}", user.getPassword());
        LOGGER.debug("Confirm Password: {}", confirmPassword);
        LOGGER.debug("{}", user);

        if ( !(confirmPassword.equals(user.getPassword())) ) {
            String confirmPasswordError = "Password does not match confirmation";
            LOGGER.info("Registration failed -- {}", confirmPasswordError);
            model.addAttribute("title", "Register");
            model.addAttribute("confirmPasswordError", confirmPasswordError);
            return "/login/register";
        }

        // ensure username does not exist
        for (User u : userRepository.findAll()) {
            if (u.getUsername().equals(user.getUsername())) {
                String usernameExistsError = "Username already exists";
                LOGGER.info("Registration failed -- {}", usernameExistsError);
                model.addAttribute("title", "Register");
                model.addAttribute("usernameExistsError", usernameExistsError);
                return "/login/register";
            }
        }

        // TODO hash password

        // save new user
        LOGGER.info("Pre-save -- {}: ", user);
        userRepository.save(user);
        LOGGER.info("Post-save -- {}: ", user);

        LOGGER.info("Registration successful -- Redirecting to app-ctrl");
        return "redirect:/app-ctrl";
    }
}
