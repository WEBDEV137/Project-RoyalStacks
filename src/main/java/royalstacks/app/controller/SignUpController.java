package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.backingBean.CustomerBackingBean;
import royalstacks.app.model.Customer;
import royalstacks.app.service.CustomerService;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SignUpController {

    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getName());

    private CustomerService customerService;
    private Customer customer;



    @Autowired
    public SignUpController(CustomerService cs){
        this.customerService = cs;
    }


    @GetMapping("/signup")
    public final ModelAndView signUpHandler(){
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    public @ResponseBody boolean SignUpPost(@RequestBody CustomerBackingBean cbb){
        this.customer = cbb.customer();

        if(customerService.isAllInputValid(this.customer)){
            customerService.saveCustomer(this.customer);
            LOGGER.log(Level.INFO, "**** Customer saved");
            return true;
        } else {
            LOGGER.log(Level.SEVERE,"**** No customer saved");
            return false;
        }
    }


}