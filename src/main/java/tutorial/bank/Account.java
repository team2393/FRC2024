package tutorial.bank;

/**
 * An 'interface' describes what we expect to be
 * able to do with a class, i.e. how we would
 * 'interface' with it, which methods we want to be there.
 * 
 * For a bank account, we expect to be able to somehow
 * get the current balance,
 * make a deposit,
 * and withdraw money.
 */
public interface Account
{
    /** A method for getting the current balance.
     *  Does not require any arguments.
     *  @return Current account balance
     */
    public double getBalance();

    /** A method for making a deposit.
     *  Takes one argument, doesn't return anything.
     *  @param amount The amount of money we put into the account
     */
    public void deposit(double amount);

    /** A method to make a withdrawal.
     *  Needs one argument and returns something.
     *  @param requested_amount Amount that I'd like to withdraw
     *  @return What I actually got
     */
    public double withdraw(double requested_amount);
}
