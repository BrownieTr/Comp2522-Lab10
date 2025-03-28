package ca.bcit.comp2522.lab10;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a bank that manages a list of bank accounts.
 * Provides functionality to add accounts, retrieve accounts by ID,
 * and calculate the total balance of all accounts in USD.
 *
 * @author Brownie, Felix
 * @version 1
 */
public class Bank
{
    private final List<BankAccount> accountList;

    /**
     * Constructs a new Bank object.
     * Initializes an empty list to store accounts.
     */
    public Bank()
    {
        accountList = new ArrayList<>();
    }

    /**
     * Adds a new bank account to the bank.
     *
     * @param account the BankAccount object to be added
     */
    public void addAccount(final BankAccount account)
    {
        accountList.add(account);
    }

    /**
     * Retrieves a bank account by its account ID.
     *
     * @param accountID the ID of the account to retrieve
     * @return the BankAccount object with the specified ID
     * @throws IllegalArgumentException if the account is not found
     */
    public BankAccount retrieveAccount(final String accountID)
    {
        for(final BankAccount account : accountList)
        {
            if(account.getAccountID().equals(accountID))
            {
                return account;
            }
        }
        throw new IllegalArgumentException("Account not found");
    }

    /**
     * Calculates the total balance of all accounts in USD.
     *
     * @return the total balance in USD
     */
    public double totalBalanceUsd()
    {
        return accountList.stream()
                          .mapToDouble(BankAccount::getBalanceUsd)
                          .sum();
    }
}
