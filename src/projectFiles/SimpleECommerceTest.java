package projectFiles;
import java.util.Scanner;

public class SimpleECommerceTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // Load CSV files from repository root
        SimpleCSVReader.loadProducts("products.csv");
        SimpleCSVReader.loadCustomers("customers.csv");
        SimpleCSVReader.loadOrders("orders.csv");
        SimpleCSVReader.loadReviews("reviews.csv");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View all products");
            System.out.println("2. View all customers");
            System.out.println("3. View all orders");
            System.out.println("4. View out of stock products");
            System.out.println("5. View top 3 rated products");
            System.out.println("6. View orders between dates");
            System.out.println("7. View all reviews");
            System.out.println("8. Add review to product");
            System.out.println("9. View customer reviews");
            System.out.println("10. View common high-rated products (rating > 4.0)");
            System.out.println("11. Exit");
            System.out.print("Choose option: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline
            
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
                    String start = scan.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String end = scan.nextLine();
                    Order.printOrdersBetween(start, end);
                    break;
                case 7:
                    Review.printAll();
                    break;
                case 8:
                    System.out.print("Enter customer ID: ");
                    int custId = scan.nextInt();
                    System.out.print("Enter product ID: ");
                    int prodId = scan.nextInt();
                    System.out.print("Enter rating (1-5): ");
                    double rating = scan.nextDouble();
                    scan.nextLine(); // Consume newline
                    System.out.print("Enter comment: ");
                    String comment = scan.nextLine();
                    Customer.addReviewToProduct(custId, prodId, rating, comment);
                    break;
                case 9:
                    System.out.print("Enter customer ID: ");
                    int customerId = scan.nextInt();
                    Customer.printCustomerReviews(customerId);
                    break;
                case 10:
                    System.out.print("Enter first customer ID: ");
                    int cust1 = scan.nextInt();
                    System.out.print("Enter second customer ID: ");
                    int cust2 = scan.nextInt();
                    LinkedList<Product> commonProducts = Review.getCommonHighRatedProducts(cust1, cust2);
                    if (commonProducts.empty()) {
                        System.out.println("No common high-rated products found.");
                    } else {
                        System.out.println("Common high-rated products (rating > 4.0):");
                        commonProducts.findFirst();
                        while (commonProducts.retrieve() != null) {
                            Product p = commonProducts.retrieve();
                            System.out.println(p.name + " - Rating: " + p.getAverageRating());
                            if (commonProducts.last()) break;
                            commonProducts.findNext();
                        }
                    }
                    break;
                case 11:
                    System.out.println("Exiting program.");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}