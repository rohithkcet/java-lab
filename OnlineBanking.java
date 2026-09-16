import java.sql.*;
import java.util.Scanner;
public class OnlineBanking {
    static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    static final String USER = "root";
    static final String PASSWORD = "test@123";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish Connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            int choice;
            do {
                System.out.println("\n============ Online Banking System ============");
                System.out.println("1. Create Account");
                System.out.println("2. View All Accounts");
                System.out.println("3. Check Balance");
                System.out.println("4. Deposit");
                System.out.println("5. Withdraw");
                System.out.println("6. Transfer Funds");
                System.out.println("7. Search Account");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1: {
                        System.out.print("Enter Account Number: ");
                        int accNo = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Account Holder Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Account Type (Savings/Current): ");
                        String type = sc.nextLine();
                        System.out.print("Enter Initial Deposit: ");
                        double balance = sc.nextDouble();
                        String insert = "INSERT INTO account VALUES(?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(insert);
                        ps.setInt(1, accNo);
                        ps.setString(2, name);
                        ps.setString(3, type);
                        ps.setDouble(4, balance);
                        int row = ps.executeUpdate();
                        if (row > 0) {
                            System.out.println("Account Created Successfully.");
                            logTransaction(con, accNo, "OPENING DEPOSIT", balance);
                        }
                        break;
                    }
                    case 2: {
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM account");
                        System.out.println("\n--------------------------------------------------------");
                        System.out.println("AccNo\tName\t\tType\t\tBalance");
                        System.out.println("--------------------------------------------------------");
                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("acc_no") + "\t" +
                                    rs.getString("acc_holder") + "\t\t" +
                                    rs.getString("acc_type") + "\t\t" +
                                    rs.getDouble("balance"));
                        }
                        break;
                    }
                    case 3: {
                        System.out.print("Enter Account Number: ");
                        int accNo = sc.nextInt();
                        String query = "SELECT balance FROM account WHERE acc_no=?";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setInt(1, accNo);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next())
                            System.out.println("Current Balance: " + rs.getDouble("balance"));
                        else
                            System.out.println("Account Not Found.");
                        break;
                    }
                    case 4: {
                        System.out.print("Enter Account Number: ");
                        int accNo = sc.nextInt();
                        System.out.print("Enter Deposit Amount: ");
                        double amount = sc.nextDouble();
                        if (amount <= 0) {
                            System.out.println("Deposit amount must be positive.");
                            break;
                        }
                        String update = "UPDATE account SET balance = balance + ? WHERE acc_no=?";
                        PreparedStatement ps = con.prepareStatement(update);
                        ps.setDouble(1, amount);
                        ps.setInt(2, accNo);
                        int row = ps.executeUpdate();
                        if (row > 0) {
                            System.out.println("Amount Deposited Successfully.");
                            logTransaction(con, accNo, "DEPOSIT", amount);
                        } else {
                            System.out.println("Account Not Found.");
                        }
                        break;
                    }
                    case 5: {
                        System.out.print("Enter Account Number: ");
                        int accNo = sc.nextInt();
                        System.out.print("Enter Withdrawal Amount: ");
                        double amount = sc.nextDouble();
                        double currentBalance = getBalance(con, accNo);
                        if (currentBalance < 0) {
                            System.out.println("Account Not Found.");
                            break;
                        }
                        if (amount <= 0) {
                            System.out.println("Withdrawal amount must be positive.");
                            break;
                        }
                        if (amount > currentBalance) {
                            System.out.println("Insufficient Balance.");
                            break;
                        }
                        String update = "UPDATE account SET balance = balance - ? WHERE acc_no=?";
                        PreparedStatement ps = con.prepareStatement(update);
                        ps.setDouble(1, amount);
                        ps.setInt(2, accNo);
                        int row = ps.executeUpdate();
                        if (row > 0) {
                            System.out.println("Amount Withdrawn Successfully.");
                            logTransaction(con, accNo, "WITHDRAW", amount);
                        }
                        break;
                    }
                    case 6: {
                        System.out.print("Enter Sender Account Number: ");
                        int fromAcc = sc.nextInt();
                        System.out.print("Enter Receiver Account Number: ");
                        int toAcc = sc.nextInt();
                        System.out.print("Enter Amount to Transfer: ");
                        double amount = sc.nextDouble();
                        if (fromAcc == toAcc) {
                            System.out.println("Sender and Receiver accounts cannot be the same.");
                            break;
                        }
                        if (amount <= 0) {
                            System.out.println("Transfer amount must be positive.");
                            break;
                        }

                        double fromBalance = getBalance(con, fromAcc);
                        double toBalance = getBalance(con, toAcc);
                        if (fromBalance < 0 || toBalance < 0) {
                            System.out.println("One or both accounts do not exist.");
                            break;
                        }
                        if (amount > fromBalance) {
                            System.out.println("Insufficient Balance.");
                            break;
                        }
                        try {
                            con.setAutoCommit(false);
                            PreparedStatement debit = con.prepareStatement(
                                    "UPDATE account SET balance = balance - ? WHERE acc_no=?");
                            debit.setDouble(1, amount);
                            debit.setInt(2, fromAcc);
                            debit.executeUpdate();
                            PreparedStatement credit = con.prepareStatement(
                                    "UPDATE account SET balance = balance + ? WHERE acc_no=?");
                            credit.setDouble(1, amount);
                            credit.setInt(2, toAcc);
                            credit.executeUpdate();
                            con.commit();
                            System.out.println("Transfer Successful.");
                            logTransaction(con, fromAcc, "TRANSFER OUT to " + toAcc, amount);
                            logTransaction(con, toAcc, "TRANSFER IN from " + fromAcc, amount);
                        } catch (SQLException ex) {
                            con.rollback();
                            System.out.println("Transfer Failed: " + ex.getMessage());
                        } finally {
                            con.setAutoCommit(true);
                        }
                        break;
                    }
                    case 7: {
                        System.out.println("\nSearch By:");
                        System.out.println("1. Account Number");
                        System.out.println("2. Account Holder Name");
                        System.out.print("Enter your choice: ");
                        int searchChoice = sc.nextInt();
                        sc.nextLine();
                        if (searchChoice == 1) {
                            System.out.print("Enter Account Number: ");
                            int accNo = sc.nextInt();
                            String query = "SELECT * FROM account WHERE acc_no=?";
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, accNo);
                            ResultSet rs = ps.executeQuery();
                            System.out.println("\n--------------------------------------------------------");
                            System.out.println("AccNo\tName\t\tType\t\tBalance");
                            System.out.println("--------------------------------------------------------");
                            boolean found = false;
                            while (rs.next()) {
                                found = true;
                                System.out.println(
                                        rs.getInt("acc_no") + "\t" +
                                        rs.getString("acc_holder") + "\t\t" +
                                        rs.getString("acc_type") + "\t\t" +
                                        rs.getDouble("balance"));
                            }
                            if (!found) System.out.println("No account found with this account number.");

                        } else if (searchChoice == 2) {
                            System.out.print("Enter Name (or part of it): ");
                            String name = sc.nextLine();
                            String query = "SELECT * FROM account WHERE acc_holder LIKE ?";
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setString(1, "%" + name + "%");
                            ResultSet rs = ps.executeQuery();
                            System.out.println("\n--------------------------------------------------------");
                            System.out.println("AccNo\tName\t\tType\t\tBalance");
                            System.out.println("--------------------------------------------------------");
                            boolean found = false;
                            while (rs.next()) {
                                found = true;
                                System.out.println(
                                        rs.getInt("acc_no") + "\t" +
                                        rs.getString("acc_holder") + "\t\t" +
                                        rs.getString("acc_type") + "\t\t" +
                                        rs.getDouble("balance"));
                            }
                            if (!found) System.out.println("No account found matching that name.");

                        } else {
                            System.out.println("Invalid search option.");
                        }
                        break;
                    }
                    case 8:
                        System.out.println("Thank You for Banking with Us...");
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } while (choice != 8);
            con.close();
            sc.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver Not Found.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // Returns balance for an account, or -1 if the account does not exist
    private static double getBalance(Connection con, int accNo) throws SQLException {
        String query = "SELECT balance FROM account WHERE acc_no=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, accNo);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getDouble("balance");
        return -1;
    }
    // Records a transaction into the transactions table
    private static void logTransaction(Connection con, int accNo, String type, double amount) {
        try {
            String insert = "INSERT INTO transactions(acc_no, txn_type, amount, txn_time) VALUES(?,?,?,NOW())";
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setInt(1, accNo);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not log transaction: " + e.getMessage());
        }
    }
}
