package royalstacks.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import royalstacks.app.model.Employee;
import royalstacks.app.service.BusinessAccountService;
import royalstacks.app.service.CustomerService;
import royalstacks.app.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(HeadBusinessController.class)
class HeadBusinessControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private BusinessAccountService businessAccountService;

    @Test
    void overviewHandlerTestThatItWorks() throws Exception {
        //ARRANGE
        int userId = 1;
        Employee employee = new Employee();
        given(employeeService.findById(userId)).willReturn(java.util.Optional.of(employee));

        //ACT & ASSERT
        mvc.perform(
                get("/headbusiness").sessionAttr("userid", userId)
        ).andExpect(status().isOk())
                .andExpect(model().attributeExists("top10BusinessAccounts"))
                .andExpect(model().attributeExists("sectorAndAverageBalances"));
    }
}