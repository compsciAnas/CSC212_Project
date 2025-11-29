package projectFiles;
import java.util.Scanner;

/**
 * E-Commerce System Test - Phase II with AVL Tree implementation
 * All data structures use AVL Trees for O(log n) operations.
 */
public class SimpleECommerceTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("=== E-Commerce System Phase II ===");
        System.out.println("Using AVL Trees for O(log n) operations\n");
        
        SimpleCSVReader.loadProducts("products.csv");
        SimpleCSVReader.loadCustomers("customers.csv");
        SimpleCSVReader.loadOrders("orders.csv");
        SimpleCSVReader.loadReviews("reviews.csv");
        
        System.out.println("\nData loaded successfully:");
        System.out.println("- Products: " + Product.getProductCount());
        System.out.println("- Customers: " + Customer.getCustomerCount());
        System.out.println("- Orders: " + Order.getOrderCount());
        System.out.println("- Reviews: " + Review.getReviewCount());

        while (true) {
            System.out.println("\n============ MENU ============");
            System.out.println("--- View Data ---");
            System.out.println("1. View all products (sorted by ID)");
            System.out.println("2. View all customers");
            System.out.println("3. View all orders (sorted by ID)");
            System.out.println("4. View all reviews");
            System.out.println("--- Product Operations ---");
            System.out.println("5. Add product [O(log n)]");
            System.out.println("6. Search product by ID [O(log n)]");
            System.out.println("7. Update product [O(log n)]");
            System.out.println("8. View product average rating");
            System.out.println("--- Customer Operations ---");
            System.out.println("9. Add customer [O(log n)]");
            System.out.println("10. Search customer by ID [O(log n)]");
            System.out.println("11. View customer order history");
            System.out.println("--- Order Operations ---");
            System.out.println("12. Search order by ID [O(log n)]");
            System.out.println("13. Place an order");
            System.out.println("14. Cancel an order [O(log n)]");
            System.out.println("15. Update order status [O(log n)]");
            System.out.println("--- Phase II: Advanced Queries ---");
            System.out.println("16. View products in price range [Range Query]");
            System.out.println("17. View orders between dates [Range Query with AVL]");
            System.out.println("18. View customers sorted alphabetically [In-order Traversal]");
            System.out.println("19. View top 3 rated products");
            System.out.println("20. View top 3 most reviewed products");
            System.out.println("21. View customers who reviewed a product (sorted by rating)");
            System.out.println("--- Reviews ---");
            System.out.println("22. Add review to product");
            System.out.println("23. Edit review [O(log n)]");
            System.out.println("24. View customer reviews");
            System.out.println("25. View common high-rated products (rating > 4.0)");
            System.out.println("--- Other ---");
            System.out.println("26. View out of stock products");
            System.out.println("27. Performance comparison (Phase I vs Phase II)");
            System.out.println("28. Exit");
            System.out.println("================================");
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
                        System.out.println("Product added successfully using AVL Tree [O(log n)].");
                    }
                    break;
                case 6:
                    System.out.print("Enter product ID: ");
                    int searchId = scan.nextInt();
                    long startTime = System.nanoTime();
                    Product foundById = Product.searchById(searchId);
                    long endTime = System.nanoTime();
                    if (foundById != null) {
                        System.out.println("Product found: " + foundById);
                        System.out.println("Search time (AVL): " + (endTime - startTime) + " ns [O(log n)]");
                    } else {
                        System.out.println("Product not found.");
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
                        System.out.println("Customer added successfully using AVL Tree [O(log n)].");
                    }
                    break;
                case 10:
                    System.out.print("Enter customer ID: ");
                    int searchCustId = scan.nextInt();
                    startTime = System.nanoTime();
                    Customer foundCustomer = Customer.searchById(searchCustId);
                    endTime = System.nanoTime();
                    if (foundCustomer != null) {
                        System.out.println("Customer found: " + foundCustomer);
                        System.out.println("Search time (AVL): " + (endTime - startTime) + " ns [O(log n)]");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 11:
                    System.out.print("Enter customer ID: ");
                    int historyCustomerId = scan.nextInt();
                    Customer.viewOrderHistory(historyCustomerId);
                    break;
                case 12:
                    System.out.print("Enter order ID: ");
                    int searchOrderId = scan.nextInt();
                    startTime = System.nanoTime();
                    Order foundOrder = Order.searchById(searchOrderId);
                    endTime = System.nanoTime();
                    if (foundOrder != null) {
                        System.out.println("Order found: " + foundOrder);
                        System.out.println("Search time (AVL): " + (endTime - startTime) + " ns [O(log n)]");
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;
                case 13:
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
                case 14:
                    System.out.print("Enter order ID to cancel: ");
                    int cancelId = scan.nextInt();
                    Order.cancelOrder(cancelId);
                    break;
                case 15:
                    System.out.print("Enter order ID to update: ");
                    int updateId = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter new status (pending/shipped/delivered/canceled): ");
                    String newStatus = scan.nextLine();
                    Order.updateOrderStatus(updateId, newStatus);
                    break;
                case 16:
                    // Phase II: Products in price range
                    System.out.print("Enter minimum price: ");
                    double minPrice = scan.nextDouble();
                    System.out.print("Enter maximum price: ");
                    double maxPrice = scan.nextDouble();
                    Product.printProductsInPriceRange(minPrice, maxPrice);
                    break;
                case 17:
                    // Phase II: Orders between dates
                    scan.nextLine(); // Clear buffer after previous input
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String start = scan.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String end = scan.nextLine();
                    Order.printOrdersBetween(start, end);
                    break;
                case 18:
                    // Phase II: Customers sorted alphabetically
                    Customer.printAllSortedAlphabetically();
                    break;
                case 19:
                    // Phase II: Top 3 rated products
                    Product.topThreeProducts();
                    break;
                case 20:
                    // Phase II: Top 3 most reviewed products
                    Product.topThreeMostReviewedProducts();
                    break;
                case 21:
                    // Phase II: Customers who reviewed a product
                    System.out.print("Enter product ID: ");
                    int reviewedProdId = scan.nextInt();
                    Review.printCustomersWhoReviewedProduct(reviewedProdId);
                    break;
                case 22:
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
                case 23:
                    System.out.print("Enter review ID to edit: ");
                    int reviewId = scan.nextInt();
                    System.out.print("Enter new rating (1-5): ");
                    double newRating = scan.nextDouble();
                    scan.nextLine(); 
                    System.out.print("Enter new comment: ");
                    String newComment = scan.nextLine();
                    Review.editReview(reviewId, newRating, newComment);
                    break;
                case 24:
                    System.out.print("Enter customer ID: ");
                    int customerId = scan.nextInt();
                    Customer.printCustomerReviews(customerId);
                    break;
                case 25:
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
                case 26:
                    Product.printOutOfStock();
                    break;
                case 27:
                    // Performance comparison
                    performanceComparison();
                    break;
                case 28:
                    System.out.println("Exiting program.");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    /**
     * Phase II: Performance comparison between Phase I (LinkedList) and Phase II (AVL Tree)
     */
    private static void performanceComparison() {
        System.out.println("\n=== PERFORMANCE COMPARISON ===");
        System.out.println("Phase I (LinkedList) vs Phase II (AVL Tree)\n");
        
        int testProductId = 125; // Mid-range product ID for fair comparison
        int testCustomerId = 215; // Mid-range customer ID
        int testOrderId = 350; // Mid-range order ID
        
        // Product Search Comparison
        System.out.println("--- Product Search (ID: " + testProductId + ") ---");
        
        // Phase I: Linear search
        long startLinear = System.nanoTime();
        Product p1 = Product.searchByIdLinear(testProductId);
        long endLinear = System.nanoTime();
        long linearTime = endLinear - startLinear;
        
        // Phase II: AVL search
        long startAVL = System.nanoTime();
        Product p2 = Product.searchById(testProductId);
        long endAVL = System.nanoTime();
        long avlTime = endAVL - startAVL;
        
        System.out.println("Phase I (LinkedList O(n)): " + linearTime + " ns");
        System.out.println("Phase II (AVL Tree O(log n)): " + avlTime + " ns");
        if (linearTime > avlTime) {
            System.out.println("AVL Tree is " + String.format("%.2f", (double)linearTime / avlTime) + "x faster");
        }
        
        // Customer Search Comparison
        System.out.println("\n--- Customer Search (ID: " + testCustomerId + ") ---");
        
        startLinear = System.nanoTime();
        Customer c1 = Customer.searchByIdLinear(testCustomerId);
        endLinear = System.nanoTime();
        linearTime = endLinear - startLinear;
        
        startAVL = System.nanoTime();
        Customer c2 = Customer.searchById(testCustomerId);
        endAVL = System.nanoTime();
        avlTime = endAVL - startAVL;
        
        System.out.println("Phase I (LinkedList O(n)): " + linearTime + " ns");
        System.out.println("Phase II (AVL Tree O(log n)): " + avlTime + " ns");
        if (linearTime > avlTime) {
            System.out.println("AVL Tree is " + String.format("%.2f", (double)linearTime / avlTime) + "x faster");
        }
        
        // Order Search Comparison
        System.out.println("\n--- Order Search (ID: " + testOrderId + ") ---");
        
        startLinear = System.nanoTime();
        Order o1 = Order.searchByIdLinear(testOrderId);
        endLinear = System.nanoTime();
        linearTime = endLinear - startLinear;
        
        startAVL = System.nanoTime();
        Order o2 = Order.searchById(testOrderId);
        endAVL = System.nanoTime();
        avlTime = endAVL - startAVL;
        
        System.out.println("Phase I (LinkedList O(n)): " + linearTime + " ns");
        System.out.println("Phase II (AVL Tree O(log n)): " + avlTime + " ns");
        if (linearTime > avlTime) {
            System.out.println("AVL Tree is " + String.format("%.2f", (double)linearTime / avlTime) + "x faster");
        }
        
        // Time Complexity Summary
        System.out.println("\n=== TIME COMPLEXITY SUMMARY ===");
        System.out.println("Operation              | Phase I (LinkedList) | Phase II (AVL Tree)");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Insert Product         | O(n)                 | O(log n)");
        System.out.println("Search Product by ID   | O(n)                 | O(log n)");
        System.out.println("Update Product         | O(n)                 | O(log n)");
        System.out.println("Delete Product         | O(n)                 | O(log n)");
        System.out.println("Insert Customer        | O(n)                 | O(log n)");
        System.out.println("Search Customer        | O(n)                 | O(log n)");
        System.out.println("Insert Order           | O(n)                 | O(log n)");
        System.out.println("Search Order           | O(n)                 | O(log n)");
        System.out.println("Price Range Query      | O(n)                 | O(n)*");
        System.out.println("Date Range Query       | O(n)                 | O(log n + k)**");
        System.out.println("Sorted Traversal       | O(n log n)           | O(n)***");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("* Price range requires traversal; could be O(log n + k) with secondary AVL");
        System.out.println("** k = number of results in range");
        System.out.println("*** In-order traversal of AVL tree gives sorted order naturally");
        
        System.out.println("\n=== SPACE COMPLEXITY ===");
        System.out.println("Phase I (LinkedList): O(n) - one node per element");
        System.out.println("Phase II (AVL Tree):  O(n) - one node per element with height info");
    }
}