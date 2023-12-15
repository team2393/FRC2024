package tutorial.bank;

public class AccountDemo
{
    public static void main(String[] args)
    {
        System.out.println("My Wallet:");
        Account wallet = new Wallet();

        wallet.deposit(10.0);
        System.out.println("Balance: " + wallet.getBalance());

        double got = wallet.withdraw(7.0);
        System.out.println("I got " + got + ", remaining balance: " + wallet.getBalance());

        got = wallet.withdraw(7.0);
        System.out.println("I got " + got + ", remaining balance: " + wallet.getBalance());
        System.out.println();

        // ---------------------------

        System.out.println("My Savings Account:");
        Account savings = new SavingsAccount();
        savings.deposit(10.0);
        System.out.println("Balance: " + savings.getBalance());

        savings.deposit(10.0);
        System.out.println("Balance: " + savings.getBalance());

        got = savings.withdraw(15.0);
        System.out.println("I got " + got + ", remaining balance: " + savings.getBalance());

        got = savings.withdraw(15.0);
        System.out.println("I got " + got + ", remaining balance: " + savings.getBalance());
        System.out.println();

        // ---------------------------

        System.out.println("My Loan:");
        Account loan = new LoanShark(50.0);

        got = loan.withdraw(35.0);
        System.out.println("I got " + got + ", remaining balance: " + loan.getBalance());

        got = loan.withdraw(35.0);
        System.out.println("I got " + got + ", remaining balance: " + loan.getBalance());

        // Trying to repay
        loan.deposit(25.0);
        System.out.println("I paid 25, now I have a balance of " + loan.getBalance());

        loan.deposit(25.0);
        System.out.println("I paid 25, now I have a balance of " + loan.getBalance());

        loan.deposit(25.0);
        System.out.println("I paid 25, now I have a balance of " + loan.getBalance());
    }
}
