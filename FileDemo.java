import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileDemo{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the path of the source file: ");
        String sourcePath = sc.nextLine();
        System.out.print("Enter the path of the destination file: ");
        String destinationPath = sc.nextLine();
        System.out.println("\nStarting file copy operation...");
        try (FileInputStream fis = new FileInputStream(sourcePath); 
             FileOutputStream fos = new FileOutputStream(destinationPath)) {
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data);
            }
            System.out.println("Success! File copied successfully.");

        } catch (IOException e) {
            System.err.println("Error: Could not copy file. " + e.getMessage());
        }
        sc.close();
    }
}
