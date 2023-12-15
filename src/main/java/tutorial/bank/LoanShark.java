package tutorial.bank;

/** Loan Shark:
 *  You get a loan, so you start with some balance that you can use.
 *  But there's also a debt.
 *  As you deposit money, you pay your dept off.
 */
public class LoanShark extends Wallet
{
    /** Interest that you have to pay on your load */
    private double interest = 25.0/100.0;
    private double debt;

    public LoanShark(double loan_amount)
    {
        // You get that loan as a balance
        balance = loan_amount;

        // .. and have to pay that plus interest
        debt = - loan_amount - loan_amount * interest;
    }

    @Override
    public void deposit(double amount)
    {
        // Deposits pay off your dept
        debt += amount;
        if (debt > 0)
        {   // If you pay your debt, you're out of the negative, then you add to balance
            balance += debt;
            debt = 0;
        }
    }
}
