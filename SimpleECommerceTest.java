package projectFiles;
import java.util.Scanner;

public class SimpleECommerceTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        SimpleCSVReader.loadProducts("/Users/anas/Desktop/Project_212/data/products.csv");
        SimpleCSVReader.loadCustomers("/Users/anas/Desktop/Project_212/data/customers.csv");
        SimpleCSVReader.loadOrders("/Users/anas/Desktop/Project_212/data/orders.csv");
        SimpleCSVReader.loadReviews("/Users/anas/Desktop/Project_212/data/reviews.csv");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View all products");
            System.out.println("2. View all customers");
            System.out.println("3. View all orders");
            System.out.println("4. View out of stock products");
            System.out.println("5. View top 3 rated products");
            System.out.println("6. View orders between dates");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");

            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    Product.printAll();
                    break;
                case 2:
                    Customer.printAll();
                    break;
                case 3:
                    Order.printAll();
                    break;
                case 4:
                    Product.printOutOfStock();
                    break;
                case 5:
                    Product.topThreeProducts();
                    break;
                case 6:
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String start = scan.next();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String end = scan.next();
                    Order.printOrdersBetween(start, end);
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}