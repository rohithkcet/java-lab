import java.util.Scanner;

interface Calculator {
    void add(double a, double b);
    void sub(double a, double b);
    void mul(double a, double b);
    void div(double a, double b);
}

class myCalculator implements Calculator {
    public void add(double a, double b) {
        double res = a + b;
        System.out.println("The Sum is " + res);
    }
    public void sub(double a, double b) {
        double res = a - b;
        System.out.println("The Subtraction is " + res);
    }
    public void mul(double a, double b) {
        double res = a * b;
        System.out.println("The Multiplication is " + res);
    }
    public void div(double a, double b) {
        if (b == 0) {
            System.out.println("Error: Division by zero");
            return;
        }
        double res = a / b;
        System.out.println("The Division is " + res);
    }
}

public class CalculatorDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        myCalculator mc = new myCalculator();
        int choice;
        
        do {
            System.out.println("====== Calculator =======");
            System.out.println("\n1) Addition \n2) Subtraction \n3) Multiplication \n4) Division \n5) Exit");
            System.out.println("Enter your choice :");
            choice = sc.nextInt();
            
            if (choice == 5) {
                System.out.println("Exiting...");
                break;
            }
            
            System.out.println("Enter number 1 : ");
            double a = sc.nextDouble();
            System.out.println("Enter number 2 : ");
            double b = sc.nextDouble();
            
            switch(choice) {
                case 1:
                    mc.add(a, b);
                    break;
                case 2: 
                    mc.sub(a, b);
                    break;
                case 3:     
                    mc.mul(a, b);
                    break;
                case 4: 
                    mc.div(a, b); 
                    break;
                default: 
                    System.out.println("Invalid Choice");
            }
        } while(choice != 5);
        
        sc.close();
    }
}