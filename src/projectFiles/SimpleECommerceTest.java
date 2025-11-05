package projectFiles;
import java.util.Scanner;

public class SimpleECommerceTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        SimpleCSVReader.loadProducts("products.csv");
        SimpleCSVReader.loadCustomers("customers.csv");
        SimpleCSVReader.loadOrders("orders.csv");
        SimpleCSVReader.loadReviews("reviews.csv");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View all products");
            System.out.println("2. View all customers");
            System.out.println("3. View all orders");
            System.out.println("4. View all reviews");
            System.out.println("5. Add product");
            System.out.println("6. Search product (by ID or Name)");
            System.out.println("7. Update product");
            System.out.println("8. View product average rating");
            System.out.println("9. Add customer");
            System.out.println("10. View customer order history");
            System.out.println("11. Search order by ID");
            System.out.println("12. View out of stock products");
            System.out.println("13. View top 3 rated products");
            System.out.println("14. View orders between dates");
            System.out.println("15. Add review to product");
            System.out.println("16. Edit review");
            System.out.println("17. View customer reviews");
            System.out.println("18. View common high-rated products (rating > 4.0)");
            System.out.println("19. Place an order");
            System.out.println("20. Cancel an order");
            System.out.println("21. Update order status");
            System.out.println("22. Exit");
            System.out.print("Choose option: ");

            int choice = scan.nextInt();
            scan.nextLine(); 
            
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
                    Review.printAll();
                    break;
                case 5:
                    System.out.print("Enter product ID: ");
                    int newProdId = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter product name: ");
                    String newProdName = scan.nextLine();
                    System.out.print("Enter price: ");
                    double newPrice = scan.nextDouble();
                    System.out.print("Enter stock: ");
                    int newStock = scan.nextInt();
                    Product newProduct = new Product(newProdId, newProdName, newPrice, newStock);
                    if (Product.addProduct(newProduct)) {
                        System.out.println("Product added successfully.");
                    }
                    break;
                case 6:
                    System.out.print("Search by (1) ID or (2) Name? ");
                    int searchChoice = scan.nextInt();
                    scan.nextLine();
                    if (searchChoice == 1) {
                        System.out.print("Enter product ID: ");
                        int searchId = scan.nextInt();
                        Product foundById = Product.searchById(searchId);
                        if (foundById != null) {
                            System.out.println("Product found: " + foundById);
                        } else {
                            System.out.println("Product not found.");
                        }
                    } else if (searchChoice == 2) {
                        System.out.print("Enter product name: ");
                        String searchName = scan.nextLine();
                        Product foundByName = Product.searchByName(searchName);
                        if (foundByName != null) {
                            System.out.println("Product found: " + foundByName);
                        } else {
                            System.out.println("Product not found.");
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 7:
                    System.out.print("Enter product ID to update: ");
                    int updateProdId = scan.nextInt();
                    System.out.print("Enter new price: ");
                    double updatePrice = scan.nextDouble();
                    System.out.print("Enter new stock: ");
                    int updateStock = scan.nextInt();
                    Product.updateProduct(updateProdId, updatePrice, updateStock);
                    break;
                case 8:
                    System.out.print("Enter product ID: ");
                    int ratingProdId = scan.nextInt();
                    Product ratingProduct = Product.searchById(ratingProdId);
                    if (ratingProduct != null) {
                        System.out.println("Average rating for " + ratingProduct.name + ": " + ratingProduct.getAverageRating());
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 9:
                    System.out.print("Enter customer ID: ");
                    int newCustId = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter customer name: ");
                    String newCustName = scan.nextLine();
                    System.out.print("Enter customer email: ");
                    String newCustEmail = scan.nextLine();
                    Customer newCustomer = new Customer(newCustId, newCustName, newCustEmail);
                    if (Customer.addCustomer(newCustomer)) {
                        System.out.println("Customer added successfully.");
                    }
                    break;
                case 10:
                    System.out.print("Enter customer ID: ");
                    int historyCustomerId = scan.nextInt();
                    Customer.viewOrderHistory(historyCustomerId);
                    break;
                case 11:
                    System.out.print("Enter order ID: ");
                    int searchOrderId = scan.nextInt();
                    Order foundOrder = Order.searchById(searchOrderId);
                    if (foundOrder != null) {
                        System.out.println("Order found: " + foundOrder);
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;
                case 12:
                    Product.printOutOfStock();
                    break;
                case 13:
                    Product.topThreeProducts();
                    break;
                case 14:
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String start = scan.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String end = scan.nextLine();
                    Order.printOrdersBetween(start, end);
                    break;
                case 15:
                    System.out.print("Enter customer ID: ");
                    int custId = scan.nextInt();
                    System.out.print("Enter product ID: ");
                    int prodId = scan.nextInt();
                    System.out.print("Enter rating (1-5): ");
                    double rating = scan.nextDouble();
                    scan.nextLine(); 
                    System.out.print("Enter comment: ");
                    String comment = scan.nextLine();
                    Customer.addReviewToProduct(custId, prodId, rating, comment);
                    break;
                case 16:
                    System.out.print("Enter review ID to edit: ");
                    int reviewId = scan.nextInt();
                    System.out.print("Enter new rating (1-5): ");
                    double newRating = scan.nextDouble();
                    scan.nextLine(); 
                    System.out.print("Enter new comment: ");
                    String newComment = scan.nextLine();
                    Review.editReview(reviewId, newRating, newComment);
                    break;
                case 17:
                    System.out.print("Enter customer ID: ");
                    int customerId = scan.nextInt();
                    Customer.printCustomerReviews(customerId);
                    break;
                case 18:
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
                case 19:
                    System.out.print("Enter customer ID: ");
                    int placeCustomerId = scan.nextInt();
                    System.out.print("Enter product ID: ");
                    int placeProductId = scan.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantity = scan.nextInt();
                    scan.nextLine(); 
                    System.out.print("Enter order date (YYYY-MM-DD): ");
                    String orderDate = scan.nextLine();
                    Customer.placeOrder(placeCustomerId, placeProductId, quantity, orderDate);
                    break;
                case 20:
                    System.out.print("Enter order ID to cancel: ");
                    int cancelId = scan.nextInt();
                    Order.cancelOrder(cancelId);
                    break;
                case 21:
                    System.out.print("Enter order ID to update: ");
                    int updateId = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter new status (pending/shipped/delivered/canceled): ");
                    String newStatus = scan.nextLine();
                    Order.updateOrderStatus(updateId, newStatus);
                    break;
                case 22:
                    System.out.println("Exiting program.");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}