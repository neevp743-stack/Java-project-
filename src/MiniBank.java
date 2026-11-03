import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * MiniBank - a small console-based banking simulation for Java coursework.
 * Educational demo only: it is not suitable for real banking or real customer data.
 */
public class MiniBank {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Map<Integer, Account> ACCOUNTS = new LinkedHashMap<>();
    private static final List<String> HISTORY = new ArrayList<>();
    private static int nextAccountNumber = 1001;
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private static class Account {
        private final int number;
        private final String holder;
        private BigDecimal balance;

        Account(int number, String holder, BigDecimal openingBalance) {
            this.number = number;
            this.holder = holder;
            this.balance = openingBalance;
        }
        int getNumber() { return number; }
        String getHolder() { return holder; }
        BigDecimal getBalance() { return balance; }
        void credit(BigDecimal amount) { balance = balance.add(amount).setScale(2, RoundingMode.HALF_UP); }
        void debit(BigDecimal amount) { balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP); }
    }

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("       MINIBANK - JAVA PROJECT      ");
        System.out.println("====================================");
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> showAccounts();
                case 6 -> showStatement();
                case 7 -> { System.out.println("Thank you for using MiniBank."); running = false; }
                default -> System.out.println("Invalid option. Please choose 1 to 7.");
            }
        }
        INPUT.close();
    }

    private static void showMenu() {
        System.out.println("\n----------- MAIN MENU --------------");
        System.out.println("1. Create account");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Transfer money");
        System.out.println("5. View all accounts");
        System.out.println("6. View transaction history");
        System.out.println("7. Exit");
    }

    private static void createAccount() {
        System.out.print("Account holder name: ");
        String name = INPUT.nextLine().trim();
        if (name.isEmpty() || name.length() > 60) {
            System.out.println("Name must contain 1 to 60 characters.");
            return;
        }
        BigDecimal opening = readAmount("Opening deposit (enter 0 if none): ", true);
        if (opening == null) return;
        int number = nextAccountNumber++;
        Account account = new Account(number, name, opening);
        ACCOUNTS.put(number, account);
        record("Account created: " + number + " for " + name + ", opening balance Rs. " + money(opening));
        System.out.println("Account created successfully. Account number: " + number);
    }

    private static void deposit() {
        Account account = findAccount(readInt("Account number: "));
        if (account == null) return;
        BigDecimal amount = readAmount("Deposit amount (Rs.): ", false);
        if (amount == null) return;
        account.credit(amount);
        record("Deposit Rs. " + money(amount) + " into account " + account.getNumber());
        System.out.println("Deposit successful. New balance: Rs. " + money(account.getBalance()));
    }

    private static void withdraw() {
        Account account = findAccount(readInt("Account number: "));
        if (account == null) return;
        BigDecimal amount = readAmount("Withdrawal amount (Rs.): ", false);
        if (amount == null) return;
        if (account.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance. Withdrawal cancelled.");
            record("FAILED withdrawal of Rs. " + money(amount) + " from account " + account.getNumber());
            return;
        }
        account.debit(amount);
        record("Withdrawal Rs. " + money(amount) + " from account " + account.getNumber());
        System.out.println("Withdrawal successful. New balance: Rs. " + money(account.getBalance()));
    }

    private static void transfer() {
        int fromNumber = readInt("From account number: ");
        Account from = findAccount(fromNumber);
        if (from == null) return;
        int toNumber = readInt("To account number: ");
        Account to = findAccount(toNumber);
        if (to == null) return;
        if (fromNumber == toNumber) {
            System.out.println("Source and destination accounts must be different.");
            return;
        }
        BigDecimal amount = readAmount("Transfer amount (Rs.): ", false);
        if (amount == null) return;
        if (from.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance. Transfer cancelled.");
            record("FAILED transfer Rs. " + money(amount) + " from " + fromNumber + " to " + toNumber);
            return;
        }
        // Both balance changes occur only after all validations have passed.
        from.debit(amount);
        to.credit(amount);
        record("Transfer Rs. " + money(amount) + " from " + fromNumber + " to " + toNumber);
        System.out.println("Transfer successful.");
        System.out.println("Source balance: Rs. " + money(from.getBalance()));
    }

    private static void showAccounts() {
        if (ACCOUNTS.isEmpty()) { System.out.println("No accounts created yet."); return; }
        System.out.println("\nACCOUNT DIRECTORY");
        System.out.printf("%-12s %-25s %s%n", "Account No.", "Holder", "Balance (Rs.)");
        for (Account a : ACCOUNTS.values())
            System.out.printf("%-12d %-25s %s%n", a.getNumber(), a.getHolder(), money(a.getBalance()));
    }

    private static void showStatement() {
        if (HISTORY.isEmpty()) { System.out.println("No transactions recorded yet."); return; }
        System.out.println("\nTRANSACTION HISTORY");
        for (String entry : HISTORY) System.out.println(entry);
    }

    private static Account findAccount(int number) {
        Account account = ACCOUNTS.get(number);
        if (account == null) System.out.println("Account " + number + " was not found.");
        return account;
    }

    private static BigDecimal readAmount(String prompt, boolean allowZero) {
        System.out.print(prompt);
        String raw = INPUT.nextLine().trim();
        try {
            BigDecimal amount = new BigDecimal(raw).setScale(2, RoundingMode.UNNECESSARY);
            if (amount.signum() < 0 || (!allowZero && amount.signum() == 0)) {
                System.out.println(allowZero ? "Amount cannot be negative." : "Amount must be greater than zero.");
                return null;
            }
            return amount;
        } catch (NumberFormatException | ArithmeticException ex) {
            System.out.println("Enter a valid amount with at most two decimal places.");
            return null;
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String raw = INPUT.nextLine().trim();
        try { return Integer.parseInt(raw); }
        catch (NumberFormatException ex) { System.out.println("Please enter a whole number."); return -1; }
    }

    private static void record(String description) {
        HISTORY.add("[" + LocalDateTime.now().format(TIME) + "] " + description);
    }

    private static String money(BigDecimal amount) { return amount.setScale(2, RoundingMode.HALF_UP).toPlainString(); }
}
