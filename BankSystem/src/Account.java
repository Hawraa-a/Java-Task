import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account {
    private String accountNumber;
    private String accountName;
    private double balances;
    private List<String> transactionHistory = new ArrayList<>();

    public Account(String accountNumber, String accountName) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balances = 0.0;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountName() {
        return accountName;
    }
    public double getBalance() {
        return balances;
    }

    public void deposit(Scanner scannerInput) {
        System.out.print("Enter deposit amount: ");
        double amount = scannerInput.nextDouble();
        if (amount > 0) {
            balances += amount;
            transactionHistory.add(java.time.LocalDate.now() + " Deposit: " + amount + " OMR");
            System.out.println("New balance: " + balances + " OMR");
        } else {
            System.out.println("Invalid amount");
        }
    }

    public void withdraw(Scanner scannerInput) {
        System.out.print("Enter withdrawal amount: ");
        double amount = scannerInput.nextDouble();
        if (amount <= balances) {
            balances -= amount;
            transactionHistory.add(java.time.LocalDate.now() + " Withdraw: " + amount + " OMR");
            System.out.println("New balance: " + balances + " OMR");
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void viewBalance() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Name: " + accountName);
        System.out.println("Current balance: " + balances + " OMR");
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setBalance(double balance) {
        this.balances = balance;
    }
}