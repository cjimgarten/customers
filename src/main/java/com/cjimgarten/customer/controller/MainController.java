package com.cjimgarten.customer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * MainController.java
 * Created by chris on 2/10/18.
 */
@Controller
@RequestMapping(value = "")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String redirectToLoginController() {
        LOGGER.info("Redirecting to login-ctrl");
        return "redirect:/login-ctrl";
    }
}
