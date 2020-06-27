package royalstacks.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import royalstacks.app.backingBean.OpenAccountBackingBean;
import royalstacks.app.model.repository.AccountRepository;
import royalstacks.app.model.repository.EmployeeRepository;
import royalstacks.app.service.AccountService;
import royalstacks.app.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OpenAccountController.class)
class OpenAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private UserService userService;
    @MockBean
    private EmployeeRepository employeeRepository;



    @Test
    void createAccountHandler() throws Exception {
        int id = 10;
        OpenAccountBackingBean bb = new OpenAccountBackingBean("McDonalds","McDonalds","13767676","13767676", "NL858805315B01","NL858805315B01", "Retail", "Business");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/openaccount")
                        .requestAttr("account", bb)
                        .sessionAttr("userid", id)
        ).andExpect(status().isOk()).andExpect(view().name("openaccount"));
    }
}