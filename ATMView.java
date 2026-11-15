import java.util.List;
import java.util.Scanner;

// VIEW: everything the user sees and types. No business logic here.
public class ATMView {
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("=================================");
        System.out.println("      WELCOME TO SKILL NEXIS ATM  ");
        System.out.println("=================================");
    }

    public void showMenu(String name) {
        System.out.println("\n--- Hello, " + name + " ---");
        System.out.println("1. Check balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Mini statement (last 5)");
        System.out.println("5. Change PIN");
        System.out.println("6. Exit");
        System.out.print("Choose: ");
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showBalance(double balance) {
        System.out.printf("Current balance: %.2f%n", balance);
    }

    public void showHistory(List<Transaction> list) {
        if (list.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.printf("%-20s %-10s %10s %12s%n", "Time", "Type", "Amount", "Balance");
        for (Transaction t : list) System.out.println(t);
    }
}