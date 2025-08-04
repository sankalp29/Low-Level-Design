import java.util.List;

abstract class Account {
    abstract void deposit(double amount);
    abstract void withdraw(double amount);
}

class CurrentAccount extends Account {
    private double balance;

    public CurrentAccount() {
        this.balance = 0;
    }

    @Override
    void deposit(double amount) {
        balance+=amount;
    }

    @Override
    void withdraw(double amount) {
        /**
         * Some current account specific code
         */
        if (amount <= balance) {
            System.out.println("Current account withdrawn for " + amount);
        } else {
            System.out.println("Not enough balance in the current account.");
            System.out.println("Balance = " + balance + " Amount requested = " + amount);
        }
    }
}

class SavingsAccount extends Account {
    private double balance;

    public SavingsAccount() {
        this.balance = 0;
    }

    @Override
    void deposit(double amount) {
        balance+=amount;
    }

    @Override
    void withdraw(double amount) {
        /**
         * Some savings account specific code
         */
        if (amount <= balance) {
            System.out.println("Current account withdrawn for " + amount);
        } else {
            System.out.println("Not enough balance in the current account.");
            System.out.println("Balance = " + balance + " Amount requested = " + amount);
        }
    }
}

/**
 * Does not adhere to Liskov substitution principle because the child class FixedDepositAccount does not support 
 * a feature supported by it's parent.
 * So FixedDepositAccount is not substitutable for it's parent
 */
class FixedDepositAccount extends Account {
    private double balance;

    public FixedDepositAccount() {
        this.balance = 0;
    }

    @Override
    void deposit(double amount) {
        balance+=amount;
    }

    @Override
    void withdraw(double amount) {
        try {
            throw new Exception("Cannot withdraw from a FD account");   
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class BankClient {
    private List<Account> accounts;

    public BankClient(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void depositMoneyToAccounts() {
        for (Account acc : accounts) {
            acc.deposit(100);
        }

        System.out.println("Successfully deposited to all accounts");
    }


    public void withdrawMoneyFromAccounts() {
        for (Account acc : accounts) {
            acc.withdraw(50);
        }
        System.out.println("Successfully withdrawn from all accounts");
    }
}

public class Main {
    public static void main(String[] args) {
        BankClient bankClient = new BankClient(List.of(new SavingsAccount(), new CurrentAccount(), new FixedDepositAccount()));
        System.out.println("********************************************************");
        bankClient.depositMoneyToAccounts();
        bankClient.withdrawMoneyFromAccounts();
    }    
}
