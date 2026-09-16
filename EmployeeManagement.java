import java.sql.*;
import java.util.Scanner;
public class EmployeeManagement {
    static final String URL = "jdbc:mysql://localhost:3306/company";
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

                System.out.println("\n========== Employee Management ==========");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                switch(choice) {
                    case 1:
                        System.out.print("Enter Employee ID: ");
                        int id = sc.next;
                        sc.nextLine();
                        System.out.print("Enter Employee Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = sc.nextDouble();
                        String insert = "INSERT INTO employee VALUES(?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(insert);
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setString(3, dept);
                        ps.setDouble(4, salary);

                        int row = ps.executeUpdate();
                        if(row > 0)
                            System.out.println("Employee Added Successfully.");
                        break;
                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM employee");
                        System.out.println("\n--------------------------------------------");
                        System.out.println("ID\tName\tDepartment\tSalary");
                        System.out.println("--------------------------------------------");
                        while(rs.next()) {
                            System.out.println(
                                    rs.getInt("emp_id") + "\t" +
                                    rs.getString("emp_name") + "\t" +
                                    rs.getString("department") + "\t\t" +
                                    rs.getDouble("salary"));
                        }
                        break;
                    case 3:
                        System.out.print("Enter Employee ID: ");
                        int eid = sc.nextInt();
                        System.out.print("Enter New Salary: ");
                        double newsalary = sc.nextDouble();

                        String update = "UPDATE employee SET salary=? WHERE emp_id=?";
                        PreparedStatement ps2 = con.prepareStatement(update);
                        ps2.setDouble(1, newsalary);
                        ps2.setInt(2, eid);
                        int updateRow = ps2.executeUpdate();
                        if(updateRow > 0)
                            System.out.println("Employee Updated Successfully.");
                        else
                            System.out.println("Employee Not Found.");
                        break;
                    case 4:
                        System.out.print("Enter Employee ID: ");
                        int did = sc.nextInt();
                        String delete = "DELETE FROM employee WHERE emp_id=?";
                        PreparedStatement ps3 = con.prepareStatement(delete);
                        ps3.setInt(1, did);
                        int deleteRow = ps3.executeUpdate();
                        if(deleteRow > 0)
                            System.out.println("Employee Deleted Successfully.");
                        else
                            System.out.println("Employee Not Found.");
                        break;
                    case 5:
                        System.out.println("Thank You...");
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } while(choice != 5);
            con.close();
            sc.close();
        }
        catch(ClassNotFoundException e) {
            System.out.println("MySQL Driver Not Found.");
        }
        catch(SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
