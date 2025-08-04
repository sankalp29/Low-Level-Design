import java.util.List;

abstract class NonWithdrawableAccount {
    abstract void deposit(double amount);
}

abstract class WithdrawAbleAccount extends NonWithdrawableAccount {
    abstract void withdraw(double amount);
}

class CurrentAccount extends WithdrawAbleAccount {
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

class SavingsAccount extends WithdrawAbleAccount {
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
class FixedDepositAccount extends NonWithdrawableAccount {
    private double balance;

    public FixedDepositAccount() {
        this.balance = 0;
    }

    @Override
    void deposit(double amount) {
        balance+=amount;
    }
}

class BankClient {
    private List<NonWithdrawableAccount> nonWithdrawableAccounts;
    private List<WithdrawAbleAccount> withdrawableAccounts;

    public BankClient(List<NonWithdrawableAccount> nonWithdrawableAccounts, List<WithdrawAbleAccount> withdrawableAccounts) {
        this.nonWithdrawableAccounts = nonWithdrawableAccounts;
        this.withdrawableAccounts = withdrawableAccounts;
    }

    public void depositMoneyToAccounts() {
        for (NonWithdrawableAccount acc : nonWithdrawableAccounts) {
            acc.deposit(100);
        }

        for (WithdrawAbleAccount acc : withdrawableAccounts) {
            acc.deposit(100);
        }

        System.out.println("Successfully deposited to all accounts");
    }


    public void withdrawMoneyFromAccounts() {
        for (WithdrawAbleAccount acc : withdrawableAccounts) {
            acc.deposit(100);
        }

        System.out.println("Successfully withdrawn from all accounts");
    }
}

public class Main {
    public static void main(String[] args) {
        List<NonWithdrawableAccount> nonWithdrawableAccounts = List.of(new FixedDepositAccount());
        List<WithdrawAbleAccount> withdrawableAccounts = List.of(new SavingsAccount(), new CurrentAccount());
        BankClient bankClient = new BankClient(nonWithdrawableAccounts, withdrawableAccounts);
        System.out.println("********************************************************");
        bankClient.depositMoneyToAccounts();
        bankClient.withdrawMoneyFromAccounts();
    }    
}
