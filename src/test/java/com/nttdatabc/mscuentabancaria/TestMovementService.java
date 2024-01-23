package com.nttdatabc.mscuentabancaria;
import com.nttdatabc.mscuentabancaria.model.Account;
import com.nttdatabc.mscuentabancaria.model.Movement;
import com.nttdatabc.mscuentabancaria.repository.MovementRepository;
import com.nttdatabc.mscuentabancaria.service.AccountServiceImpl;
import com.nttdatabc.mscuentabancaria.service.MovementServiceImpl;
import com.nttdatabc.mscuentabancaria.utils.exceptions.errors.ErrorResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestMovementService {

  @Mock
  private MovementRepository movementRepository;

  @Mock
  private AccountServiceImpl accountServiceImpl;

  @InjectMocks
  private MovementServiceImpl movementService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  void createMovementDepositServiceTest() throws ErrorResponseException {
    // Arrange
    Movement movementMock = new Movement();
    movementMock.setAccountId("testAccountId");
    movementMock.setMount(BigDecimal.valueOf(100));
    movementMock.setFecha("2020-01-01");
    movementMock.setTypeMovement("RETIRO");
    movementMock.setId("cudtom");

    List<Movement> listMovementByAccount = Collections.emptyList();

    Account accountFound = new Account();
    accountFound.setCurrentBalance(BigDecimal.valueOf(500));
    accountFound.setTypeAccount("AHORRO");

    when(accountServiceImpl.getAccountByIdService("testAccountId")).thenReturn(accountFound);
    when(movementRepository.findByAccountId("testAccountId")).thenReturn(listMovementByAccount);

    // Act
    assertDoesNotThrow(() -> movementService.createMovementDepositService(movementMock));

    // Assert
    verify(movementRepository, times(1)).save(movementMock);
    verify(accountServiceImpl, times(1)).updateAccountServide(accountFound);
  }

  @Test
  void createWithDrawServiceTest() throws ErrorResponseException {
    // Arrange
    Movement movementMock = new Movement();
    movementMock.setAccountId("testAccountId");
    movementMock.setMount(BigDecimal.valueOf(100));
    movementMock.setFecha("2020-01-01");
    movementMock.setTypeMovement("RETIRO");
    movementMock.setId("cudtom");

    List<Movement> listMovementByAccount = Collections.emptyList();

    Account accountFound = new Account();
    accountFound.setCurrentBalance(BigDecimal.valueOf(100));
    accountFound.setTypeAccount("ahorro");
    accountFound.setId("id");

    when(accountServiceImpl.getAccountByIdService("testAccountId")).thenReturn(accountFound);
    when(movementRepository.findByAccountId("testAccountId")).thenReturn(listMovementByAccount);

    // Act
    assertDoesNotThrow(() -> movementService.createWithDrawService(movementMock));

    // Assert
    verify(movementRepository, times(1)).save(movementMock);
    verify(accountServiceImpl, times(1)).updateAccountServide(accountFound);
  }

  @Test
  void createWithDrawServiceInsufficientBalanceTest() throws ErrorResponseException {
    // Arrange
    Movement movementMock = new Movement();
    movementMock.setAccountId("testAccountId");
    movementMock.setMount(BigDecimal.valueOf(100));
    movementMock.setFecha("2020-01-01");
    movementMock.setTypeMovement("RETIRO");
    movementMock.setId("cudtom");

    List<Movement> listMovementByAccount = Collections.emptyList();

    Account accountFound = new Account();
    accountFound.setCurrentBalance(BigDecimal.valueOf(100));
    accountFound.setTypeAccount("ahorro");
    accountFound.setId("id");

    when(accountServiceImpl.getAccountByIdService("testAccountId")).thenReturn(accountFound);
    when(movementRepository.findByAccountId("testAccountId")).thenReturn(listMovementByAccount);

    // Act
    assertDoesNotThrow(() -> movementService.createWithDrawService(movementMock));

    // Assert
    verify(movementRepository, times(1)).save(movementMock);
    verify(accountServiceImpl, times(1)).updateAccountServide(accountFound);
  }

  @Test
  void getMovementsByAccountIdServiceTest() throws ErrorResponseException {
    // Arrange
    String testAccountId = "testAccountId";

    Account accountFound = new Account();
    accountFound.setId(testAccountId);

    List<Movement> movementsList = Collections.singletonList(new Movement());

    when(accountServiceImpl.getAccountByIdService(testAccountId)).thenReturn(accountFound);
    when(movementRepository.findByAccountId(testAccountId)).thenReturn(movementsList);

    // Act
    List<Movement> result = assertDoesNotThrow(() -> movementService.getMovementsByAccountIdService(testAccountId));

    // Assert
    verify(accountServiceImpl, times(1)).getAccountByIdService(testAccountId);
    verify(movementRepository, times(1)).findByAccountId(testAccountId);
  }
}
