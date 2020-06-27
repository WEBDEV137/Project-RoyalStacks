package royalstacks.app.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import royalstacks.app.model.CompanyAndTransactions;
import royalstacks.app.model.Sector;
import royalstacks.app.model.SectorAndAverageBalance;
import royalstacks.app.model.repository.BusinessAccountRepository;
import royalstacks.app.model.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BusinessAccountServiceTest {

    @Mock
    BusinessAccountRepository businessAccountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    BusinessAccountService businessAccountService;

    @Test
    public void findSectorAndAverageBalanceTest() {
        //ARRANGE
        List<Object[]> mockedList = new ArrayList<>();
        mockedList.add(new Object[]{"CULTURE_SPORTS_AND_RECREATION", 666.66});
        mockedList.add(new Object[]{"WHOLESALE", 1337.00});
        when(businessAccountRepository.findSectorAndAverageBalance()).thenReturn(mockedList);

        //ACT
        List<SectorAndAverageBalance> actual = businessAccountService.findSectorAndAverageBalance();

        //ASSERT
        //Size SectorAndAverageBalance-list
        assertEquals(2, actual.size());

        SectorAndAverageBalance firstResult = actual.get(0);
        //Check sector in first record from SectorAndAverageBalance-list
        assertEquals(Sector.CULTURE_SPORTS_AND_RECREATION, firstResult.getSector());
        //Check balance in first record from SectorAndAverageBalance-list
        assertEquals(666.66, firstResult.getTotalBalance(), 0);

        SectorAndAverageBalance secondResult = actual.get(1);
        //Check sector in first record from SectorAndAverageBalance-list
        assertEquals(Sector.WHOLESALE, secondResult.getSector());
        //Check balance in first record from SectorAndAverageBalance-list
        assertEquals(1337.00, secondResult.getTotalBalance(), 0);
    }

    @Test
    public void findTop10TransactionsOnBusinessAccountsTest() {
        //ARRANGE
        //Mock result from query findCompaniesAndBusinessAccounts in BusinessAccountRepository
        List<Object[]> mockedListCompaniesAndBusinessAccounts = new ArrayList<>();
        mockedListCompaniesAndBusinessAccounts.add(new Object[]{1, new BigDecimal(100.00), "12345678", "CompanyOne"});
        mockedListCompaniesAndBusinessAccounts.add(new Object[]{2, new BigDecimal(66666.66), "23456789", "CompanyTwo"});
        mockedListCompaniesAndBusinessAccounts.add(new Object[]{3, new BigDecimal(500000.00), "34567890", "CompanyThree"});
        mockedListCompaniesAndBusinessAccounts.add(new Object[]{4, new BigDecimal(0.25), "34567890", "Company3"});
        when(businessAccountRepository.findCompaniesAndBusinessAccounts()).thenReturn(mockedListCompaniesAndBusinessAccounts);

        //Mock result from query countByFromAccountIdOrToAccountId in TransactionRepository
        when(transactionRepository.countByFromAccountIdOrToAccountId(1, 1)).thenReturn(26);
        when(transactionRepository.countByFromAccountIdOrToAccountId(2, 2)).thenReturn(3);
        when(transactionRepository.countByFromAccountIdOrToAccountId(3, 3)).thenReturn(25);
        when(transactionRepository.countByFromAccountIdOrToAccountId(4,4)).thenReturn(1);

        //ACT
        List<CompanyAndTransactions> actualList = businessAccountService.findTop10TransactionsOnBusinessAccounts();

        //ASSERT
        //Check number of transactions and group by Kvk-number
        assertEquals(26,actualList.get(0).getNumberOfTransactions());
        assertEquals(26,actualList.get(1).getNumberOfTransactions());
        assertEquals(3,actualList.get(2).getNumberOfTransactions());

        //Check sort on number of transactions and total balance
        assertEquals("CompanyThree",actualList.get(0).getCompanyName());
        assertEquals("CompanyOne",actualList.get(1).getCompanyName());
        assertEquals("CompanyTwo",actualList.get(2).getCompanyName());
    }
}

