package royalstacks.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import royalstacks.app.model.Customer;
import royalstacks.app.model.Employee;
import royalstacks.app.model.User;
import royalstacks.app.service.LogInService;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(DoLoginController.class)
class DoLoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogInService service;

    @Test
    void doLoginHandlerUsernameIsEmptyString() throws Exception {
        //ARRANGE
        String username = "";
        String password = "password";
        Customer customer = new Customer();
        customer.setUsername("username");
        customer.setPassword("correct");
        given(service.findByUsername(username)).willReturn(customer);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isOk()).andExpect(view().name("homepage"));
    }

    @Test
    void doLoginHandlerPasswordIsEmptyString() throws Exception {
        //ARRANGE
        String username = "username";
        String password = "";
        Customer customer = new Customer();
        customer.setUsername("username");
        customer.setPassword("correct");
        given(service.findByUsername(username)).willReturn(customer);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isOk()).andExpect(view().name("homepage"));
    }

    @Test
    void doLoginHandlerUsernameDoesNotExist() throws Exception {
        //ARRANGE
        String username = "nonexistent";
        String password = "password";
        given(service.findByUsername(username)).willReturn(null);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isOk()).andExpect(view().name("homepage"));
    }

    @Test
    void doLoginHandlerPasswordNotCorrect() throws Exception {
        //ARRANGE
        String username = "user";
        String password = "notCorrect";
        Customer customer = new Customer();
        customer.setPassword("correct");
        given(service.findByUsername(username)).willReturn(customer);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isOk()).andExpect(view().name("homepage"));
    }

    @Test
    void doLoginHandlerHeadBusinessLoginCorrect() throws Exception {
        //ARRANGE
        String username = "headbusiness";
        String password = "correct";
        Employee employee = new Employee();
        employee.setPosition("headbusiness");
        employee.setPassword("correct");
        given(service.findByUsername(username)).willReturn(employee);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isMovedTemporarily()).andExpect(view().name("redirect:/headbusiness"));
    }

    @Test
    void doLoginHandlerHeadPrivateLoginCorrect() throws Exception {
        //ARRANGE
        String username = "headprivate";
        String password = "correct";
        Employee employee = new Employee();
        employee.setPosition("headprivate");
        employee.setPassword("correct");
        given(service.findByUsername(username)).willReturn(employee);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isMovedTemporarily()).andExpect(view().name("redirect:/headprivate"));
    }

    @Test
    void doLoginHandlerCustomerLoginCorrect() throws Exception {
        //ARRANGE
        String username = "customer";
        String password = "correct";
        Customer customer = new Customer();
        customer.setPassword("correct");
        given(service.findByUsername(username)).willReturn(customer);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isMovedTemporarily()).andExpect(view().name("redirect:/accountOverview"));
    }

    @Test
    void doLoginHandlerNonExistingUserType() throws Exception {
        //ARRANGE
        String username = "nonExistingUserType";
        String password = "correct";

        class Supervisor extends User {
        }

        Supervisor supervisor = new Supervisor();
        supervisor.setPassword("correct");
        given(service.findByUsername(username)).willReturn(supervisor);

        //ACT & ASSERT
        mvc.perform(
                post("/doLogin")
                        .param("inputUsername", username)
                        .param("inputPassword", password)
        ).andExpect(status().isOk()).andExpect(view().name("homepage"));
    }
}