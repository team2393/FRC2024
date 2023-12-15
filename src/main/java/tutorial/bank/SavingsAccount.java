package tutorial.bank;

/** Savings Account:
 *  Similar to a Wallet, it stores the money that you put in.
 *  Plus you get some interest when you deposit money.
 */
public class SavingsAccount extends Wallet
{
    /** Interest that you gain on each deposit */
    private double interest = 1.0/100.0;

    @Override
    public void deposit(double amount)
    {
        // And add some extra money,
        // interest based on the current balance
        balance += balance * interest;

        // Then add the deposited amount as the Wallet did
        super.deposit(amount);
    }
}
