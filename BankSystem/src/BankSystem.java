import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BankSystem {
    private static final String bankName = "Bank Muscat"; // constants Variables
    static Map<String, Account> accounts = new HashMap<>(); // stores all accounts in memory using their account number.
    static Account currentUser = null; // tracks which user is currently logged in.


    public static void main(String[] args) {
        Scanner scannerInput = new Scanner(System.in);
        loadAccountsFromFile();
        boolean running = true;
        // Main menu loop
        while (running){
            System.out.println("\n"+ bankName);
            System.out.println("Menu selections:");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. View Balance");
            System.out.println("6. View transactionHistory");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");
            int choice = scannerInput.nextInt(); // Read user's menu choice
            scannerInput.nextLine();

            switch (choice) { // Switch case to handle each menu option
                case 1:
                    createAccount(scannerInput);
                    break;
                case 2:
                    login(scannerInput);
                    break;
                case 3:
                    if (isLoggedIn()) currentUser.deposit(scannerInput);
                    break;
                case 4:
                    if (isLoggedIn()) currentUser.withdraw(scannerInput);
                    break;
                case 5:
                    if (isLoggedIn()) currentUser.viewBalance();
                    break;
                case 6:
                    if (isLoggedIn()) viewTransactionHistory();
                    break;
                case 7:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                case 8:
                    saveAccountsToFile();
                    running = false; // Exit the loop
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Checks if a user is logged in before performing actions.
    public static boolean isLoggedIn() {
        if (currentUser == null) {
            System.out.println("You must login first.");
            return false;
        }
        return true;
    }

    private static String generateUniqueAccountNumber() {
        int i = 1;
        while (i <= 999) {
            String generatedNumber = String.format("%03d", i); // 3-digit format: 001, 002, ...
            if (!accounts.containsKey(generatedNumber)) {
                return generatedNumber;
            }
            i++;
        }
        throw new RuntimeException("Maximum number of accounts reached.");
    }

    public static void createAccount(Scanner scannerInput){
        String accountNumber = generateUniqueAccountNumber();
        System.out.println("Your generated account number is: " + accountNumber);
        System.out.print("Enter your account name: ");
        String accountNameInput = scannerInput.nextLine();

        if (accountNameInput.isEmpty()) {
            System.out.println("Error: Account name cannot be empty!");
            return;
        }
//
        Account newAccount = new Account(accountNumber, accountNameInput);
        accounts.put(accountNumber, newAccount);
        currentUser = newAccount; // auto-login after account creation

        System.out.println("Account activated successfully.");
        System.out.println("Your balance is " + newAccount.getBalance() + " OMR");
    }

    public static void login(Scanner scannerInput) {
        System.out.print("Enter your account number to login: ");
        String accountNumberInput = scannerInput.nextLine();

        if (accounts.containsKey(accountNumberInput)) {
            currentUser = accounts.get(accountNumberInput);
            System.out.println("Login successful. Welcome, " + currentUser.getAccountName());
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void viewTransactionHistory() {
        System.out.println("\n Transaction History");
        List<String> history = currentUser.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("There are no transaction yet");
        }
        else {
            history.forEach(System.out::println); // print all transactionHistory in the list
        }
    }

    public static void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (Account account : accounts.values()) { // Loops over all saved accounts in the accounts map
                writer.write("=== The user information ===");
                writer.newLine();
                writer.write("The account number is: " + account.getAccountNumber());
                writer.newLine();
                writer.write("The account name is: " + account.getAccountName());
                writer.newLine();
                writer.write("The balance is: " + account.getBalance());
                writer.newLine();
                writer.write("=== The TransactionHistory ===");
                writer.newLine();
                for (String transaction : account.getTransactionHistory()) {
                    writer.write(transaction);
                    writer.newLine();
                }

                writer.write("-----"); // separator between accounts
                writer.newLine();
            }
            System.out.println("The account data has been saved successfully to the file 'accounts.txt'.");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage()); //Catches and prints any IOException if something goes wrong during writing.
        }
    }

    public static void loadAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            Account account = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("=== The user information ===") || line.equals("=== The TransactionHistory ===")) {
                    continue;
                }
                if (line.startsWith("The account number is: ")) {
                    String accountNumber = line.replace("The account number is: ", "").trim();
                    line = reader.readLine();
                    String accountName = line.replace("The account name is: ", "").trim();
                    line = reader.readLine();
                    double balance = Double.parseDouble(line.replace("The balance is: ", "").trim());
                    account = new Account(accountNumber, accountName);
                    account.setBalance(balance);
                } else if (line.equals("-----")) {
                    if (account != null) {
                        accounts.put(account.getAccountNumber(), account);
                    }
                } else {
                    // any other line is treated as a transaction
                    if (account != null) {
                        account.addTransaction(line);
                    }
                }
            }
            System.out.println("All accounts have been loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }
}
