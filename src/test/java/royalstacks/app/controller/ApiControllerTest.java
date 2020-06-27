package royalstacks.app.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import royalstacks.app.model.AccountHolderTransaction;
import royalstacks.app.model.Customer;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
@WebAppConfiguration
class ApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @MockBean
    private BusinessAccountService businessAccountService;

    @MockBean
    private PosService posService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    void UsernameIsUnique() throws Exception {

        String url = "/api/username?username=" + "Wesley";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(Boolean.parseBoolean(content));

    }

    @Test
    void UsernameIsNotUnique() throws Exception {

        String username = "JanJansen";

        Customer customer = new Customer();
        customer.setUsername(username);

        given(userService.findByUsername(username)).willReturn(Optional.of(customer));
        String url = "/api/username?username=" + username;

        MvcResult mvcResult = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        assertFalse(Boolean.parseBoolean(content));
    }

    @Test
    void BSNisUnique() throws Exception {

        String BSN = "663046129";
        String url = "/api/bsn?bsn=" + BSN;
        MvcResult mvcResult = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(Boolean.parseBoolean(content));
    }

    @Test
    void BSNisNotUnique() throws Exception {

        String BSN = "663046129";
        String url = "/api/bsn?bsn=" + BSN;

        Customer customer = new Customer();
        customer.setBSN(BSN);


        given(customerService.findCustomerByBSN(BSN)).willReturn(Optional.of(customer));
        MvcResult mvcResult = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertFalse(Boolean.parseBoolean(content));
    }

    @Test
    void ApiDoesNotExist404() throws Exception {

        String BSN = "663046129";
        String url = "/api/bsm?BSN=" + BSN;

        Customer customer = new Customer();
        customer.setBSN(BSN);

        given(customerService.findCustomerByBSN(BSN)).willReturn(Optional.of(customer));
        MvcResult mvcResult = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(404, status);

    }

    @Test
    void lastTenTransactionList() throws Exception {

        List<AccountHolderTransaction> accountHolderTransaction = new ArrayList<>();
        String accountNumber ="NL79ROYA1111111111";
        PrivateAccount account = new PrivateAccount();

        given(accountService.getAccountFromAccountNumber(accountNumber)).willReturn(account);
        given(transactionService.getTenLastTransaction(account.getAccountId())).willReturn(accountHolderTransaction);

        String url = "/api/transactions?accountNumber=" + accountNumber;

        MvcResult mvcResult = mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        assertFalse(Boolean.parseBoolean(content));


    }
}