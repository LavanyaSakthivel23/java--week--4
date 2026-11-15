import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// MODEL: one entry in the transaction history
public class Transaction {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final String time;

    public Transaction(String type, double amount, double balanceAfter) {
        this(type, amount, balanceAfter, LocalDateTime.now().format(FORMAT));
    }

    public Transaction(String type, double amount, double balanceAfter, String time) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.time = time;
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %10.2f %12.2f", time, type, amount, balanceAfter);
    }
}