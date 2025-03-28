package ca.bcit.comp2522.lab10;

public class BankAccount
{
    private       double balance;
    private final String accountID;

    public BankAccount(final String accountID, final int initialBalance)
    {
        validateAccountID(accountID);
        validateAmount(initialBalance);
        this.accountID = accountID;
        this.balance   = initialBalance;
    }

    protected double getBalanceUsd()
    {
        return balance;
    }

    protected String getAccountID()
    {
        return accountID;
    }

    private void validateAmount(final double amount)
    {
        if(amount < 0)
        {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    private void validateAccountID(final String accountID)
    {
        if(accountID == null || accountID.isEmpty())
        {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }
        if(!accountID.matches("[0-9]+"))
        {
            throw new IllegalArgumentException(
                    "Account ID can only contain numbers (0-9)");
        }
    }

    public void deposit(final double amount)
    {
        validateAmount(amount);
        this.balance += amount;
    }


    public double withdraw(final double amount)
    {
        validateAmount(amount);
        if(amount > this.balance)
        {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance -= amount;
        return amount;
    }

    public void transferToBank(final BankAccount account,
                               final String accountID,
                               final int amount)
    {
        validateAccountID(accountID);
        if(!this.accountID.equals(accountID))
        {
            throw new IllegalArgumentException("Account ID does not match");
        }
        validateAmount(amount);
        account.deposit(this.withdraw(amount));
    }
}
