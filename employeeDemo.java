import java.util.Scanner;
class Employee {
    String empName, address, mail, designation;
    int empId, mobileNum;
    double basicPay, da, hra, pf, staffClub, grossSalary, netSalary;
    void calculateSalary(double basic, double daPercent, double hraPercent, double pfPercent, double scPercent) {
        this.basicPay = basic;
        this.da = (daPercent / 100.0) * basic;
        this.hra = (hraPercent / 100.0) * basic;
        this.pf = (pfPercent / 100.0) * basic;
        this.staffClub = (scPercent / 100.0) * basic;
        this.grossSalary = this.basicPay + this.da + this.hra;
        this.netSalary = this.grossSalary - (this.pf + this.staffClub);
    }
    void displayPaySlip() {
        System.out.println("Designation:  " + designation);
        System.out.println("Employee ID:  " + empId);
        System.out.println("Name:         " + empName);
        System.out.println("Email:        " + mail);
        System.out.printf("Basic Pay:    Rs. %.2f\n", basicPay);
        System.out.printf("DA:           Rs. %.2f\n", da);
        System.out.printf("HRA:          Rs. %.2f\n", hra);
        System.out.printf("PF:           Rs. %.2f\n", pf);
        System.out.printf("Staff Club:   Rs. %.2f\n", staffClub);
        System.out.printf("GROSS SALARY: Rs. %.2f\n", grossSalary);
        System.out.printf("NET SALARY:   Rs. %.2f\n", netSalary);

    }
}
class Programmer extends Employee {
    Programmer(String name, int id, String addr, String email) {
        this.empName = name;
        this.empId = id;
        this.address = addr;
        this.mail = email;
        this.designation = "Programmer";
    }
}

class AssistantProfessor extends Employee {
    AssistantProfessor(String name, int id, String addr, String email) {
        this.empName = name;
        this.empId = id;
        this.address = addr;
        this.mail = email;
        this.designation = "Assistant Professor";
    }
}

class AssociateProfessor extends Employee {
    AssociateProfessor(String name, int id, String addr, String email) {
        this.empName = name;
        this.empId = id;
        this.address = addr;
        this.mail = email;
        this.designation = "Associate Professor";
    }
}

class Professor extends Employee {
    Professor(String name, int id, String addr, String email) {
        this.empName = name;
        this.empId = id;
        this.address = addr;
        this.mail = email;
        this.designation = "Professor";
    }
}
public class EmployeeDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline leftover
        System.out.print("Enter Address: ");
        String addr = sc.nextLine();
        System.out.print("Enter Mail ID: ");
        String email = sc.next();
        System.out.println("\nSelect Designation:");
        System.out.println("1. Programmer");
        System.out.println("2. Assistant Professor");
        System.out.println("3. Associate Professor");
        System.out.println("4. Professor");
        System.out.print("Enter choice (1-4): ");
        int choice = sc.nextInt();

        System.out.print("Enter Basic Pay: Rs. ");
        double basic = sc.nextDouble();

        Employee emp = null;
        switch (choice) {
            case 1:
                emp = new Programmer(name, id, addr, email);
                emp.calculateSalary(basic, 97, 10, 12, 0.1); 
                break;
            case 2:
                emp = new AssistantProfessor(name, id, addr, email);
                emp.calculateSalary(basic, 97, 10, 12, 0.1);
                break;
            case 3:
                emp = new AssociateProfessor(name, id, addr, email);
                emp.calculateSalary(basic, 110, 12, 12, 0.2);
                break;
            case 4:
                emp = new Professor(name, id, addr, email);
                emp.calculateSalary(basic, 120, 15, 12, 0.5);
                break;
            default:
                System.out.println("Invalid Choice! Exiting.");
                sc.close();
                return;
        }

        emp.displayPaySlip();
    }
}
