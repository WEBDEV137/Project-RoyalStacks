package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.model.CustomerAndTotalBalance;
import royalstacks.app.model.CompanyAndTransactions;
import royalstacks.app.model.Employee;
import royalstacks.app.model.SectorAndAverageBalance;
import royalstacks.app.service.BusinessAccountService;
import royalstacks.app.service.CustomerService;
import royalstacks.app.service.EmployeeService;

import java.util.List;

@Controller
public class HeadBusinessController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BusinessAccountService businessAccountService;

    @GetMapping("/headbusiness")
    public ModelAndView overviewHandler(Model model, @SessionAttribute("userid") int userId ){
        List<CustomerAndTotalBalance> top10BusinessAccounts = customerService.findTop10BusinessAccounts();
        List<SectorAndAverageBalance> sectorAndAverageBalances = businessAccountService.findSectorAndAverageBalance();
        Employee employee = employeeService.findById(userId).orElseThrow();
        ModelAndView mav = new ModelAndView("headbusiness");
        model.addAttribute("employee", employee);
        model.addAttribute("top10BusinessAccounts",top10BusinessAccounts);
        model.addAttribute("sectorAndAverageBalances", sectorAndAverageBalances);
        return mav;
    }



}
