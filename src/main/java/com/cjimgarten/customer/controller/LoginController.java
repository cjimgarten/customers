package com.cjimgarten.customer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {
        LOGGER.info("GET register page");
        model.addAttribute("title", "Register");
        return "/login/register";
    }
}
