package com.nttdatabc.mscuentabancaria;

import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.CustomerExt;
import com.nttdatabc.mscuentabancaria.model.TypeAccountBank;
import com.nttdatabc.mscuentabancaria.model.TypeCustomer;
import com.nttdatabc.mscuentabancaria.repository.AccountRepository;
import com.nttdatabc.mscuentabancaria.service.AccountServiceImpl;
import com.nttdatabc.mscuentabancaria.service.CustomerApiExtImpl;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class TestAccountService {
  @Mock
  private AccountRepository accountRepository;

  @Mock
  private CustomerApiExtImpl customerApiExtImpl;

  @InjectMocks
  private AccountServiceImpl accountService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void getAllAccountsServiceTest() {
    // Arrange
    when(accountRepository.findAll()).thenReturn(Collections.singletonList(new Account()));

    // Act
    List<Account> result = accountService.getAllAccountsService();

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  void createAccountServiceTest() throws ErrorResponseException {
    // Arrange
    Account accountMock = spy(new Account());
    accountMock.setTypeAccount(TypeAccountBank.CORRIENTE.toString());
    accountMock.setCustomerId("64363456346");
    accountMock.setMaintenanceFee(BigDecimal.valueOf(13.5));
    accountMock.setDateMovement("2020-01-01");
    accountMock.setLimitMaxMovements(5);
    accountMock.setCurrentBalance(BigDecimal.valueOf(30.3));
    when(accountMock.getTypeAccount()).thenReturn(TypeAccountBank.AHORRO.toString());
    when(accountMock.getCustomerId()).thenReturn("testCustomerId");

    CustomerExt customerFound = new CustomerExt();
    customerFound.setType(TypeCustomer.PERSONA.toString());

    List<Account> listAccountByCustomer = Collections.emptyList();

    // Mockear el comportamiento de tus dependencias
    when(customerApiExtImpl.getCustomerById("testCustomerId")).thenReturn(Optional.of(customerFound));
    when(accountRepository.findByCustomerId("testCustomerId")).thenReturn(listAccountByCustomer);

    // Act
    assertDoesNotThrow(() -> accountService.createAccountService(accountMock));

    // Assert
    verify(accountRepository, times(1)).save(accountMock);
  }
  @Test
  void updateAccountServiceTest() throws ErrorResponseException {
    // Arrange
    Account accountToUpdate = new Account();
    accountToUpdate.setId("testAccountId");
    accountToUpdate.setTypeAccount(TypeAccountBank.PLAZO_FIJO.toString());
    accountToUpdate.setHolders(new ArrayList<>());
    accountToUpdate.setCustomerId("customer");
    accountToUpdate.setMaintenanceFee(BigDecimal.valueOf(13.5));
    accountToUpdate.setDateMovement("2020-01-01");
    accountToUpdate.setLimitMaxMovements(5);
    accountToUpdate.setCurrentBalance(BigDecimal.valueOf(30.3));

    Optional<Account> existingAccount = Optional.of(accountToUpdate);

    when(accountRepository.findById("testAccountId")).thenReturn(existingAccount);

    // Act
    assertDoesNotThrow(() -> accountService.updateAccountServide(accountToUpdate));

    // Assert
    verify(accountRepository, times(1)).findById("testAccountId");
    verify(accountRepository, times(1)).save(accountToUpdate);
  }

  @Test
  void deleteAccountByIdServiceTest() throws ErrorResponseException {
    // Arrange
    String testAccountId = "testAccountId";
    Account existingAccount = new Account();
    existingAccount.setId(testAccountId);

    Optional<Account> accountFindByIdOptional = Optional.of(existingAccount);

    when(accountRepository.findById(testAccountId)).thenReturn(accountFindByIdOptional);

    // Act
    assertDoesNotThrow(() -> accountService.deleteAccountByIdService(testAccountId));

    // Assert
    verify(accountRepository, times(1)).findById(testAccountId);
    verify(accountRepository, times(1)).delete(existingAccount);
  }

  @Test
  void getAccountByIdServiceTest() {
    // Arrange
    String testAccountId = "testAccountId";
    Account existingAccount = new Account();
    existingAccount.setId(testAccountId);

    Optional<Account> accountOptional = Optional.of(existingAccount);

    when(accountRepository.findById(testAccountId)).thenReturn(accountOptional);

    // Act
    Account result = assertDoesNotThrow(() -> accountService.getAccountByIdService(testAccountId));

    // Assert
    assertNotNull(result);
    assertEquals(existingAccount, result);
    verify(accountRepository, times(1)).findById(testAccountId);
  }

  @Test
  void getAccountsByCustomerIdServiceTest() throws ErrorResponseException {
    // Arrange
    String testCustomerId = "testCustomerId";
    CustomerExt customerFound = new CustomerExt();
    customerFound.setType(TypeCustomer.PERSONA.toString());

    List<Account> accountsList = Arrays.asList(
        new Account(), new Account(), new Account()
    );

    when(customerApiExtImpl.getCustomerById(testCustomerId)).thenReturn(Optional.of(customerFound));
    when(accountRepository.findByCustomerId(testCustomerId)).thenReturn(accountsList);

    // Act
    List<Account> result = assertDoesNotThrow(() -> accountService.getAccountsByCustomerIdService(testCustomerId));

    // Assert
    assertNotNull(result);
    assertEquals(accountsList, result);
    verify(customerApiExtImpl, times(1)).getCustomerById(testCustomerId);
    verify(accountRepository, times(1)).findByCustomerId(testCustomerId);
  }



}
