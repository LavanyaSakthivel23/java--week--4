import java.util.ArrayList;
import java.util.List;

// MODEL: a bank account with its business rules
public class Account {
    public static final double MAX_WITHDRAW = 20000;
    public static final double MAX_DEPOSIT = 50000;

    private final int accountNumber;
    private final String name;
    private String pin;
    private double balance;
    private final List<Transaction> history = new ArrayList<>();

    public Account(int accountNumber, String name, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public List<Transaction> getHistory() { return history; }

    public boolean checkPin(String entered) {
        return pin.equals(entered);
    }

    public void deposit(double amount) throws ATMException {
        if (amount <= 0) {
            throw new ATMException("Amount must be greater than 0");
        }
        if (amount > MAX_DEPOSIT) {
            throw new ATMException("Maximum deposit per transaction is " + (int) MAX_DEPOSIT);
        }
        balance += amount;
        history.add(new Transaction("DEPOSIT", amount, balance));
    }

    public void withdraw(double amount) throws ATMException {
        if (amount <= 0) {
            throw new ATMException("Amount must be greater than 0");
        }
        if (amount % 100 != 0) {
            throw new ATMException("Amount must be a multiple of 100");
        }
        if (amount > MAX_WITHDRAW) {
            throw new ATMException("Maximum withdrawal per transaction is " + (int) MAX_WITHDRAW);
        }
        if (amount > balance) {
            throw new ATMException("Insufficient balance");
        }
        balance -= amount;
        history.add(new Transaction("WITHDRAW", amount, balance));
    }

    public void changePin(String oldPin, String newPin) throws ATMException {
        if (!checkPin(oldPin)) {
            throw new ATMException("Old PIN is incorrect");
        }
        if (!newPin.matches("\\d{4}")) {
            throw new ATMException("New PIN must be exactly 4 digits");
        }
        if (newPin.equals(oldPin)) {
            throw new ATMException("New PIN must be different from the old PIN");
        }
        pin = newPin;
    }

    // Used only when loading old transactions from the file
    public void addLoadedTransaction(Transaction t) {
        history.add(t);
    }
}