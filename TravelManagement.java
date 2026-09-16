import java.util.ArrayList;
import java.util.Scanner;

public class TravelManagement {

    public static void main(String[] args) {

        ArrayList<String> cities = new ArrayList<>();
        Scanner s = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add City");
            System.out.println("2. Insert City");
            System.out.println("3. Search City");
            System.out.println("4. Display Cities");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = s.nextInt();
            s.nextLine(); // Consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter city name: ");
                    String city = s.nextLine();
                    cities.add(city);
                    System.out.println("City added successfully!");
                    break;

                case 2:
                    System.out.print("Enter position (0 to " + cities.size() + "): ");
                    int index = s.nextInt();
                    s.nextLine(); // Consume newline

                    if (index >= 0 && index <= cities.size()) {
                        System.out.print("Enter city name: ");
                        String newCity = s.nextLine();
                        cities.add(index, newCity);
                        System.out.println("City inserted successfully!");
                    } else {
                        System.out.println("Invalid position!");
                    }
                    break;

                case 3:
                    System.out.print("Enter city to search: ");
                    String searchCity = s.nextLine();

                    if (cities.contains(searchCity)) {
                        System.out.println(searchCity + " is found in the list.");
                    } else {
                        System.out.println(searchCity + " is not found in the list.");
                    }
                    break;

                case 4:
                    if (cities.isEmpty()) {
                        System.out.println("No cities available.");
                    } else {
                        System.out.println("\nCities List:");
                        for (String c : cities) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Thank you! Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 5);

        s.close();
    }
}