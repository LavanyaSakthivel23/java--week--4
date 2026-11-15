import java.io.*;
import java.util.HashMap;
import java.util.Map;

// MODEL (data layer): loads and saves accounts and transactions in text files
public class AccountRepository {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    private final Map<Integer, Account> accounts = new HashMap<>();

    public AccountRepository() {
        if (new File(ACCOUNTS_FILE).exists()) {
            load();
        } else {
            // First run: create demo accounts
            accounts.put(1001, new Account(1001, "Asha", "1234", 5000));
            accounts.put(1002, new Account(1002, "Ravi", "4321", 10000));
            save();
        }
    }

    public Account find(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public void save() {
        try (FileWriter fw = new FileWriter(ACCOUNTS_FILE)) {
            for (Account a : accounts.values()) {
                fw.write(a.getAccountNumber() + "," + a.getName() + "," + a.getPin() + "," + a.getBalance() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Could not save accounts: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter(TRANSACTIONS_FILE)) {
            for (Account a : accounts.values()) {
                for (Transaction t : a.getHistory()) {
                    fw.write(a.getAccountNumber() + "," + t.getType() + "," + t.getAmount() + ","
                            + t.getBalanceAfter() + "," + t.getTime() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save transactions: " + e.getMessage());
        }
    }

    private void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length != 4) continue; // skip bad lines
                try {
                    int no = Integer.parseInt(p[0].trim());
                    accounts.put(no, new Account(no, p[1], p[2].trim(), Double.parseDouble(p[3].trim())));
                } catch (NumberFormatException e) {
                    // skip bad line
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read accounts: " + e.getMessage());
        }

        File tf = new File(TRANSACTIONS_FILE);
        if (!tf.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(tf))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", 5);
                if (p.length != 5) continue;
                try {
                    Account a = accounts.get(Integer.parseInt(p[0].trim()));
                    if (a != null) {
                        a.addLoadedTransaction(new Transaction(p[1], Double.parseDouble(p[2]),
                                Double.parseDouble(p[3]), p[4]));
                    }
                } catch (NumberFormatException e) {
                    // skip bad line
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read transactions: " + e.getMessage());
        }
    }
}