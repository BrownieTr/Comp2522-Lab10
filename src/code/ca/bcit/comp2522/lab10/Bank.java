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
        for (BankAccount account : accountList)
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
        double[] totalBalance = {0.0};
        accountList.forEach(account -> totalBalance[0]+=account.getBalanceUsd());
        return totalBalance[0];
    }

}
