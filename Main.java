import java.util.Scanner;

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

interface BankAccount {
    void deposit(double amount) throws InvalidInputException;
    void withdraw(double amount) throws InsufficientBalanceException, InvalidInputException;
    double getBalance();
}

class SavingsAccount implements BankAccount {
    private String holderName;
    private int accountNumber;
    private double balance;

    public SavingsAccount(String holderName, int accountNumber, double initialBalance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Invalid Amount");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException, InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Invalid Amount");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Account Holder Name: ");
        String name = scanner.nextLine();

        System.out.print("Account Number: ");
        int accNum = scanner.nextInt();

        System.out.print("Initial Balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Operation (Deposit / Withdraw / Balance): ");
        String operation = scanner.nextLine();

        SavingsAccount account = new SavingsAccount(name, accNum, initialBalance);

        System.out.println("\nOutput");
        try {
            if (operation.equalsIgnoreCase("Deposit")) {
                System.out.print("Deposit Amount: ");
                double depositAmount = scanner.nextDouble();
                account.deposit(depositAmount);
                System.out.println("Successfully deposited: " + depositAmount);
                System.out.println("Updated Balance: " + account.getBalance());
            } else if (operation.equalsIgnoreCase("Withdraw")) {
                System.out.print("Withdraw Amount: ");
                double withdrawAmount = scanner.nextDouble();
                account.withdraw(withdrawAmount);
                System.out.println("Successfully withdrew: " + withdrawAmount);
                System.out.println("Updated Balance: " + account.getBalance());
            } else if (operation.equalsIgnoreCase("Balance")) {
                System.out.println("Current Balance: " + account.getBalance());
            } else {
                System.out.println("Invalid Operation Type!");
            }
        } catch (InsufficientBalanceException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
