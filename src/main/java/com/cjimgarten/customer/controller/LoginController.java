package com.cjimgarten.customer.controller;

import com.cjimgarten.customer.model.User;
import com.cjimgarten.customer.model.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "/login/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(HttpServletRequest req, Model model) {
        LOGGER.info("Processing login form POST");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        LOGGER.info("Username: {}", username);
        LOGGER.debug("Password: {}", password);
        LOGGER.debug("{}", u);

        // TODO log the user in

        LOGGER.info("Redirecting to app-ctrl");
        return "redirect:/app-ctrl";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {
        LOGGER.info("GET register page");
        model.addAttribute("title", "Register");
        return "/login/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(HttpServletRequest req, Model model) {
        LOGGER.info("Processing register form POST");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm-password");

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        LOGGER.info("Username: {}", username);
        LOGGER.debug("Password: {}", password);
        LOGGER.debug("Confirm Password: {}", confirmPassword);
        LOGGER.debug("{}", u);

        // TODO register new user

        LOGGER.info("Redirecting to app-ctrl");
        return "redirect:/app-ctrl";
    }
}
