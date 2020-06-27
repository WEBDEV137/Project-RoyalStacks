package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.model.CustomerAndTotalBalance;
import royalstacks.app.model.Employee;
import royalstacks.app.service.CustomerService;
import royalstacks.app.service.EmployeeService;

import java.util.List;

@Controller
public class HeadPrivateController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/headprivate")
    public ModelAndView overviewHandler(Model model, @SessionAttribute("userid") int userId ){

        List<CustomerAndTotalBalance> top10PrivateAccounts = customerService.findTop10PrivateAccounts();

        Employee employee = employeeService.findById(userId).orElseThrow();

        ModelAndView mav = new ModelAndView("headprivate");
        model.addAttribute("employee", employee);
        model.addAttribute("top10PrivateAccounts",top10PrivateAccounts);

        return mav;
    }
}
