package com.cjimgarten.customer.controller;

import com.cjimgarten.customer.model.Customer;
import com.cjimgarten.customer.model.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * AppController.java
 * Created by chris on 1/15/18.
 */
@Controller
@RequestMapping(value = "app-ctrl")
public class AppController {

    private static final Logger LOGGER = LogManager.getLogger(AppController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String redirectToHomePage() {
        LOGGER.info("Redirecting to home page");
        return "redirect:/app-ctrl/index";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getHomePage(Model model) {
        LOGGER.info("GET home page");
        model.addAttribute("title", "Home Page");
        return "/app/index";
    }

    @RequestMapping(value = "customer-entry", method = RequestMethod.GET)
    public String getCustomerEntryForm(Customer customer, Model model) {
        LOGGER.info("GET customer entry form");
        model.addAttribute("title", "Customer Entry");
        return "/app/customer-entry";
    }

    @RequestMapping(value = "customer-entry", method = RequestMethod.POST)
    public String processCustomerEntryPost(@Valid Customer customer,
                                           Errors errors,
                                           Model model) {
        LOGGER.info("Processing customer entry POST");

        if (errors.hasErrors()) {
            LOGGER.info("Customer object has {} error(s) -- customer entry failed", errors.getErrorCount());
            model.addAttribute("title", "Customer Entry");
            return "/app/customer-entry";
        }

        LOGGER.info("Pre-save -- {}", customer);
        customerRepository.save(customer);
        LOGGER.info("Post-save -- {}", customer);

        model.addAttribute("title", "Submitted");
        return "/app/submitted";
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
        return "/app/view-customers";
    }
}
