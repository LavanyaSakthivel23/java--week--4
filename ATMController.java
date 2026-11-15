import java.util.List;

// CONTROLLER: connects View and Model, decides what happens next
public class ATMController {
    private static final int MAX_ATTEMPTS = 3;

    private final AccountRepository repository;
    private final ATMView view;

    public ATMController(AccountRepository repository, ATMView view) {
        this.repository = repository;
        this.view = view;
    }

    public void start() {
        view.showWelcome();
        Account account = login();
        if (account == null) {
            view.showMessage("Too many failed attempts. Card blocked for this session.");
            return;
        }
        menuLoop(account);
    }

    private Account login() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String accText = view.readLine("Enter account number: ");
            String pin = view.readLine("Enter PIN: ");
            try {
                Account a = repository.find(Integer.parseInt(accText));
                if (a != null && a.checkPin(pin)) {
                    return a;
                }
            } catch (NumberFormatException e) {
                // fall through to the common error message
            }
            // Same message for both cases so nobody learns which part was wrong
            view.showError("Invalid account number or PIN. Attempts left: " + (MAX_ATTEMPTS - attempt));
        }
        return null;
    }

    private void menuLoop(Account account) {
        while (true) {
            view.showMenu(account.getName());
            String choice = view.readLine("");
            try {
                switch (choice) {
                    case "1":
                        view.showBalance(account.getBalance());
                        break;
                    case "2":
                        account.deposit(readAmount("Enter deposit amount: "));
                        repository.save();
                        view.showMessage("Deposit successful.");
                        view.showBalance(account.getBalance());
                        break;
                    case "3":
                        account.withdraw(readAmount("Enter withdrawal amount: "));
                        repository.save();
                        view.showMessage("Please collect your cash.");
                        view.showBalance(account.getBalance());
                        break;
                    case "4":
                        List<Transaction> all = account.getHistory();
                        int from = Math.max(0, all.size() - 5);
                        view.showHistory(all.subList(from, all.size()));
                        break;
                    case "5":
                        String oldPin = view.readLine("Enter old PIN: ");
                        String newPin = view.readLine("Enter new 4-digit PIN: ");
                        account.changePin(oldPin, newPin);
                        repository.save();
                        view.showMessage("PIN changed successfully.");
                        break;
                    case "6":
                        repository.save();
                        view.showMessage("Thank you for banking with us. Goodbye!");
                        return;
                    default:
                        view.showError("Invalid choice. Enter 1-6.");
                }
            } catch (ATMException e) {
                view.showError(e.getMessage());
            }
        }
    }

    private double readAmount(String prompt) throws ATMException {
        String text = view.readLine(prompt);
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new ATMException("Please enter a valid amount");
        }
    }
}