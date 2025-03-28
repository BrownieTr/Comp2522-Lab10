package ca.bcit.comp2522.lab10;

/**
 * Represents a bank account with basic operations such as deposit, withdraw,
 * and transfer. Each account is identified by a unique account ID and maintains
 * a balance.
 *
 * @author Brownie, Felix
 * @version 1
 */
public class BankAccount
{
    private       double balance;
    private final String accountID;

    /**
     * Constructs a BankAccount with a specified account ID and initial balance.
     *
     * @param accountID      the unique identifier for the account, must be
     *                       non-null,
     *                       non-empty, and contain only numeric characters.
     * @param initialBalance the initial balance of the account, must be
     *                       non-negative.
     * @throws IllegalArgumentException if the account ID is invalid or the initial
     *                                  balance is negative.
     */
    public BankAccount(final String accountID, final int initialBalance)
    {
        validateAccountID(accountID);
        validateAmount(initialBalance);
        this.accountID = accountID;
        this.balance   = initialBalance;
    }

    /**
     * Retrieves the current balance of the account in USD.
     *
     * @return the current balance of the account.
     */
    protected double getBalanceUsd()
    {
        return balance;
    }

    /**
     * Retrieves the unique account ID of the account.
     *
     * @return the account ID.
     */
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

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount the amount to deposit, must be non-negative.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void deposit(final double amount)
    {
        validateAmount(amount);
        this.balance += amount;
    }

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount the amount to withdraw, must be non-negative and less than
     *               or equal to the current balance.
     * @return the withdrawn amount.
     * @throws IllegalArgumentException if the amount is negative or exceeds
     *                                  the current balance.
     */
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

    /**
     * Transfers a specified amount from this account to another bank account.
     *
     * @param account   the target bank account to transfer funds to.
     * @param accountID the account ID of this account, must match this account's
     *                  ID.
     * @param amount    the amount to transfer, must be non-negative and less than
     *                  or equal to the current balance.
     * @throws IllegalArgumentException if the account ID does not match this
     *                                  account's ID, the amount is negative, or
     *                                  the amount exceeds the current balance.
     */
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
