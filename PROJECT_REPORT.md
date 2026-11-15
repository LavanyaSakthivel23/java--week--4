# ATM Simulation: Project Report

Name: YOUR NAME
Course: Java Programming Internship, Skill Nexis (Week 4 Capstone)

## 1. Objective
To build a complete console-based ATM application in Java that combines
object-oriented programming, collections, file handling, exception handling
and the MVC pattern.

## 2. Features
- PIN-based login with a maximum of 3 attempts
- Check balance
- Deposit and withdraw money with validation
- Mini statement showing the last 5 transactions
- Change PIN
- Data stored permanently in text files

## 3. Architecture (MVC)
| Layer | Classes | Responsibility |
|-------|---------|----------------|
| Model | Account, Transaction, AccountRepository | Data and business rules, file storage |
| View | ATMView | All input and output with the user |
| Controller | ATMController | Connects view and model, controls program flow |

ATMApp creates the three parts and starts the controller. The view never
changes data and the model never prints anything, so each part can be
changed independently.

## 4. Data Storage
- `accounts.txt` : accountNumber,name,pin,balance
- `transactions.txt` : accountNumber,type,amount,balanceAfter,time
- Accounts are held in a HashMap (account number to Account) for fast lookup.
- Each account keeps its transactions in an ArrayList.
- Files are saved after every deposit, withdrawal and PIN change, and loaded
  when the program starts. Corrupted lines are skipped.

## 5. Program Logic
1. Start: load data from files (or create demo accounts on first run).
2. Login: read account number and PIN. If wrong, show one common error message
   and reduce attempts. After 3 failures the session ends.
3. Menu loop: the controller reads a choice and calls the matching method on
   the Account.
4. Rules in Account:
   - Deposit: amount must be positive and at most 50000.
   - Withdraw: positive, multiple of 100, at most 20000, not more than balance.
   - Change PIN: old PIN must match, new PIN must be 4 digits and different.
5. Every successful deposit or withdrawal adds a Transaction to the history.
6. Exit: save all data and end.

## 6. Exception Handling
A custom exception `ATMException` carries clear messages for invalid input.
The controller catches it and the view shows the message, so the program never
crashes on wrong input such as letters instead of numbers.

## 7. Testing
| Test | Result |
|------|--------|
| Correct login | Menu opens |
| 3 wrong PINs | Session blocked |
| Withdraw 250 | Rejected (not multiple of 100) |
| Withdraw more than balance | Rejected |
| Non-numeric amount | Error message, no crash |
| Deposit then restart program | Balance and history preserved |
| Change PIN with 3 digits | Rejected |

## 8. Limitations and Future Work
- PINs are stored as plain text. A real system would store a hash.
- Replace file storage with a MySQL database using JDBC.
- Add a JavaFX or Swing user interface.
- Add account creation and fund transfer.

## 9. Conclusion
The project integrates OOP, collections, file handling and exception handling
in an MVC design, and gave me practice in building and testing a complete
Java application.