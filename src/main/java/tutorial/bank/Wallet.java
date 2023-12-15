package tutorial.bank;

/** Basic Wallet: Simply stores the money that you put in */
public class Wallet implements Account
{
    // The Wallet class has a double variable to hold the balance.
    // You start out with 0.0 until you later call deposit().
    // The variable is protected.
    // You cannot simply get at the 'balance' from outside,
    // but "derived" classes like the SavingsAccount
    // will be able to read and write this variable
    protected double balance = 0.0;

    @Override
    public double getBalance()
    {
        // Simple, just return the current value of the 'balance' variable
        return balance;
    }

    @Override
    public void deposit(double amount)
    {
        // Also quite easy: Increment the balance by the deposited amount
        balance += amount;
    }

    @Override
    public double withdraw(double requested_amount)
    {
        // Is there enough money in the wallet?
        if (requested_amount < balance)
        {
            // Yes, return the full requested amount
            // after it has been removed from the balance
            balance -= requested_amount;
            return requested_amount;
        }
        else
        {
            // No! Here's what you can get,
            double all_that_available = balance;
            // and your wallet is now empty!
            balance = 0;
            return all_that_available;
        }
    }
}
