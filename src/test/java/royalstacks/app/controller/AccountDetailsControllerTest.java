package royalstacks.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.backingBean.AccountDetailsBackingBean;
import royalstacks.app.backingBean.TransactionBackingBean;
import royalstacks.app.model.*;
import royalstacks.app.service.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountDetailsBackingBean.class)
class AccountDetailsControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;



    @MockBean
    private UserService userService;

    @MockBean
    private  TransactionService transactionService;


    @MockBean
    private AccountService accountService;


    @Test
    void getAccountdetailsbb() {
        PrivateAccount account = new PrivateAccount("NL79ROYA1111111111",new BigDecimal("1337.00"));
        account.setAccountId(1);

        AccountDetailsBackingBean bbExpected = new AccountDetailsBackingBean("NL79ROYA1111111111", new BigDecimal("1337.00"));
        bbExpected.setAccountId(1);
        bbExpected.setAccountType("Private Account");

       AccountDetailsController accountDetailsController = new AccountDetailsController();
       AccountDetailsBackingBean bbActual =  accountDetailsController.getAccountdetailsbb(account);

       assertEquals(bbExpected.getAccountNumber(),bbActual.getAccountNumber());


    }

}