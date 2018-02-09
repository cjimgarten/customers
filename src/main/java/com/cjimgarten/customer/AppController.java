package com.cjimgarten.customer;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * AppController.java
 * Created by chris on 1/15/18.
 */
@Controller
@RequestMapping(value = "")
public class AppController {

    private static final Logger LOGGER = LogManager.getLogger(AppController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String redirectToHomePage() {
        LOGGER.info("Redirecting to home page");
        return "redirect:/home";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String getHomePage(Model model) {
        LOGGER.info("GET home page");
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @RequestMapping(value = "customer-entry", method = RequestMethod.GET)
    public String getCustomerEntryForm(Model model) {
        LOGGER.info("GET customer entry form");
        model.addAttribute("title", "Customer Entry");
        return "customer-entry";
    }

    @RequestMapping(value = "customer-entry", method = RequestMethod.POST)
    public String processCustomerEntryPost(HttpServletRequest req, Model model) {
        LOGGER.info("Processing customer entry POST");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dob = req.getParameter("dob");
        String email = req.getParameter("email");

        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setDob(dob);
        c.setEmail(email);

        LOGGER.info("Pre-save -- {}", c);
        customerRepository.save(c);
        LOGGER.info("Post-save -- {}", c);

        model.addAttribute("title", "Submitted");
        return "submitted";
    }

    @RequestMapping(value = "view-customers", method = RequestMethod.GET)
    public String getViewCustomers(Model model) {
        LOGGER.info("GET view customers");

        Iterable<Customer> customerIterable = customerRepository.findAll();
        for (Customer customer : customerIterable) {
            LOGGER.info("{}", customer);
        }

        model.addAttribute("title", "View Customers");
        model.addAttribute("customers", customerIterable);
        return "view-customers";
    }
}
