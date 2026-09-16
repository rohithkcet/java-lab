import java.util.Scanner;

abstract class shape {
    int val1, val2;

    shape(int val1, int val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    abstract void calcArea();
}

class rectangle extends shape {
    rectangle(int val1, int val2) {
        super(val1, val2);
    }

    void calcArea() {
        int area = val1 * val2;
        System.out.println("The area of Rectangle : " + area);
    }
}

class circle extends shape {
    circle(int radius) {
        super(radius, 0);
    }

    void calcArea() {
        double area = 3.14 * val1 * val1;
        System.out.println("The area of circle : " + area);
    }
}

class triangle extends shape {
    triangle(int val1, int val2) {
        super(val1, val2);
    }

    void calcArea() {
        double area = 0.5 * val1 * val2;
        System.out.println("The area of triangle : " + area);
    }
}

public class shapeDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the shape (rectangle, circle, triangle): ");
        String shapeType = sc.next();

        if (shapeType.equalsIgnoreCase("rectangle")) {
            System.out.println("Enter length: ");
            int l = sc.nextInt();
            System.out.println("Enter breadth: ");
            int b = sc.nextInt();
            rectangle r = new rectangle(l, b);
            r.calcArea();
        } else if (shapeType.equalsIgnoreCase("circle")) {
            System.out.println("Enter radius : ");
            int rad = sc.nextInt();
            circle c = new circle(rad);
            c.calcArea();
        } else if (shapeType.equalsIgnoreCase("triangle")) {
            System.out.println("Enter base/length: ");
            int l = sc.nextInt();
            System.out.println("Enter height/breadth: ");
            int b = sc.nextInt();
            triangle t = new triangle(l, b);
            t.calcArea();
        } else {
            System.out.println("Invalid shape");
        }
    
    }
}
