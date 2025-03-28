package ca.bcit.comp2522.lab10;

import java.util.List;
import java.util.ArrayList;

public class Bank
{
    private final List<BankAccount> accountList;

    public Bank()
    {
        accountList = new ArrayList<>();
    }

    public void addAccount(final BankAccount account)
    {
        accountList.add(account);
    }

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

    public double totalBalanceUsd()
    {
        return accountList.stream()
                          .mapToDouble(BankAccount::getBalanceUsd)
                          .sum();
    }

}
