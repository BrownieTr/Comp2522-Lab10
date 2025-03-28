package ca.bcit.comp2522.lab10;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankApplicationTests
{
    private Bank        bank1;
    private Bank        bank2;
    private BankAccount account1;
    private BankAccount account2;

    @BeforeEach
    void setUp()
    {
        bank1    = new Bank();
        bank2    = new Bank();
        account1 = new BankAccount("12345", 1000);
        account2 = new BankAccount("67890", 500);
        bank1.addAccount(account1);
        bank2.addAccount(account2);
    }

    @Test
    void depositIncreasesBalanceAndVerify()
    {
        account1.deposit(200);
        assertEquals(1200, account1.getBalanceUsd());
        account2.deposit(300);
        assertEquals(800, account2.getBalanceUsd());
    }

    @Test
    void withdrawDecreasesBalanceAndVerify()
    {
        account1.withdraw(200);
        assertEquals(800, account1.getBalanceUsd());
        account2.withdraw(100);
        assertEquals(400, account2.getBalanceUsd());
    }

    @Test
    void cannotWithdrawMoreThanBalanceAndHandleException()
    {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1.withdraw(1200));
        assertEquals("Insufficient funds", exception1.getMessage());
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                             () -> account2.withdraw(600));
        assertEquals("Insufficient funds", exception2.getMessage());
    }

    @Test
    void addingAndRetrievingAccountFromBank()
    {
        BankAccount newAccount = new BankAccount("54321", 100);
        bank2.addAccount(newAccount);
        assertEquals(newAccount, bank2.retrieveAccount("54321"));
        BankAccount newAccount2 = new BankAccount("11111", 300);
        bank1.addAccount(newAccount2);
        assertEquals(newAccount2, bank1.retrieveAccount("11111"));
    }

    @Test
    void transferBetweenBankAccountsAndVerifyBalances()
    {
        account1.transferToBank(account2, "12345", 200);
        assertEquals(800, account1.getBalanceUsd());
        assertEquals(700, account2.getBalanceUsd());
        account2.transferToBank(account1, "67890", 100);
        assertEquals(900, account1.getBalanceUsd());
        assertEquals(600, account2.getBalanceUsd());
    }

    @Test
    void totalBalanceCalculationForBanks()
    {
        assertEquals(1000, bank1.totalBalanceUsd());
        assertEquals(500, bank2.totalBalanceUsd());
        bank1.addAccount(new BankAccount("33333", 200));
        assertEquals(1200, bank1.totalBalanceUsd());
    }

    @Test
    void handlingInvalidAccountRetrieval()
    {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class, () ->
                        bank1.retrieveAccount("99999"));
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class, () ->
                        bank2.retrieveAccount("00000"));

        final String expectedError;
        expectedError = "Account not found";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }

    @Test
    void negativeInitialBalance()
    {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1 =
                                     new BankAccount("0000", -9));
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1 =
                                     new BankAccount("1234", -25));

        final String expectedError;
        expectedError = "Amount cannot be negative";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }

    @Test
    void emptyAccountID()
    {
        IllegalArgumentException exception1
                = assertThrows(IllegalArgumentException.class,
                               () -> account1 =
                                       new BankAccount(null, 0));
        IllegalArgumentException exception2
                = assertThrows(IllegalArgumentException.class,
                               () -> account2 =
                                       new BankAccount("   ", 0));

        final String expectedError;
        expectedError = "Account ID cannot be empty";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }

    @Test
    void invalidAccountID()
    {
        IllegalArgumentException exception1
                = assertThrows(IllegalArgumentException.class,
                               () -> account2 =
                                       new BankAccount("hi mom!",
                                                       10));
        IllegalArgumentException exception2
                = assertThrows(IllegalArgumentException.class,
                               () -> account2 =
                                       new BankAccount("hello world!",
                                                       0));
        final String expectedError;
        expectedError = "Account ID can only contain numbers (0-9)";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }

    @Test
    void transferIDNotMatching()
    {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1.transferToBank(account2,
                                                           "11111",
                                                           0));
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                             () -> account2.transferToBank(account2,
                                                           "98765",
                                                           0));
        final String expectedError;
        expectedError = "Account ID does not match";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }

    @Test
    void negativeTransferAmount()
    {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1.transferToBank(account2,
                                                           "12345",
                                                           -1));
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1.transferToBank(account2,
                                                           "12345",
                                                           -100));
        final String expectedError;
        expectedError = "Amount cannot be negative";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }

    @Test
    void insufficientFundToTransfer()
    {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1.transferToBank(account2,
                                                           "12345",
                                                           2000));
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                             () -> account1.transferToBank(account2,
                                                           "12345",
                                                           1500));
        final String expectedError;
        expectedError = "Insufficient funds";
        assertEquals(expectedError, exception1.getMessage());
        assertEquals(expectedError, exception2.getMessage());
    }
}