package projectFiles;
/**
 * Project 212 - E-Commerce System
 * Version: 2.0
 * Last Updated: 2025-01-27
 * 
 * Customer class representing customers in the e-commerce system.
 */
public class Customer {
    int customerId;
    String name;
    String email;

    // كل العملاء تُخزّن هنا
    static LinkedList<Customer> customers = new LinkedList<Customer>();

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return "[" + customerId + "] " + name + " - " + email;
    }

    // عمليات عامة على العملاء
    public static void addCustomer(Customer c) {
        if (customers.empty()) {
            customers.insert(c);
        } else {
            customers.findFirst();
            while (!customers.last()) {
                customers.findNext();
            }
            customers.insert(c);
        }
    }

    public static Customer searchById(int id) {
        if (customers.empty())
            return null;

        customers.findFirst();
        while (customers.retrieve() != null) {
            Customer c = customers.retrieve();
            if (c.customerId == id)
                return c;
            if (customers.last()) break;
            customers.findNext();
        }
        return null;
    }

    public static void printAll() {
        if (customers.empty()) {
            System.out.println("No customers available.");
            return;
        }

        customers.findFirst();
        while (customers.retrieve() != null) {
            System.out.println(customers.retrieve());
            if (customers.last()) break;
            customers.findNext();
        }
    }

    public static void updateCustomer(int id, String newName, String newEmail) {
        Customer c = searchById(id);
        if (c != null) {
            c.name = newName;
            c.email = newEmail;
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    public static void placeOrder(int customerId, int productId, int quantity, String orderDate) {
        // Validate customer exists
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        // Validate product exists
        Product product = Product.searchById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        // Check stock availability
        if (product.stock < quantity) {
            System.out.println("Insufficient stock. Available: " + product.stock + ", Requested: " + quantity);
            return;
        }

        // Generate new order ID
        int newOrderId = 1;
        if (!Order.orders.empty()) {
            Order.orders.findFirst();
            int maxId = 0;
            while (Order.orders.retrieve() != null) {
                Order o = Order.orders.retrieve();
                if (o.orderId > maxId) {
                    maxId = o.orderId;
                }
                if (Order.orders.last()) break;
                Order.orders.findNext();
            }
            newOrderId = maxId + 1;
        }

        // Create list of products (add product quantity times)
        LinkedList<Product> productList = new LinkedList<Product>();
        for (int i = 0; i < quantity; i++) {
            if (productList.empty()) {
                productList.insert(product);
            } else {
                productList.findFirst();
                while (!productList.last()) {
                    productList.findNext();
                }
                productList.insert(product);
            }
        }

        // Calculate total price
        double totalPrice = product.price * quantity;

        // Create and add the order with status "pending"
        Order newOrder = new Order(newOrderId, customer, productList, totalPrice, orderDate, "pending");
        Order.addOrder(newOrder);

        // Update product stock
        product.stock -= quantity;

        System.out.println("Order placed successfully. Order ID: " + newOrderId);
    }

    public static void viewOrderHistory(int customerId) {
        // Validate customer exists
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (Order.orders.empty()) {
            System.out.println("No orders found for customer " + customer.name + ".");
            return;
        }

        System.out.println("Order History for " + customer.name + " (ID: " + customerId + "):");
        boolean found = false;

        Order.orders.findFirst();
        while (Order.orders.retrieve() != null) {
            Order o = Order.orders.retrieve();
            if (o.customer.customerId == customerId) {
                System.out.println(o);
                found = true;
            }
            if (Order.orders.last()) break;
            Order.orders.findNext();
        }

        if (!found) {
            System.out.println("No orders found for this customer.");
        }
    }

    // Customer adds a review to a product
    // Time Complexity: O(R) where R is total number of reviews (to find new review ID)
    public static void addReviewToProduct(int customerId, int productId, double rating, String comment) {
        // Validate customer exists
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        // Validate product exists
        Product product = Product.searchById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        // Validate rating
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Rating must be between 1 and 5.");
            return;
        }

        // Generate new review ID
        int newReviewId = 1;
        if (!Review.allReviews.empty()) {
            Review.allReviews.findFirst();
            int maxId = 0;
            while (Review.allReviews.retrieve() != null) {
                Review r = Review.allReviews.retrieve();
                if (r.reviewId > maxId) {
                    maxId = r.reviewId;
                }
                if (Review.allReviews.last()) break;
                Review.allReviews.findNext();
            }
            newReviewId = maxId + 1;
        }

        // Create and add review
        Review newReview = new Review(newReviewId, product, customer, comment, rating);
        Review.addReview(newReview);
        product.addReview(newReview);

        System.out.println("Review added successfully. Review ID: " + newReviewId);
    }

    // Get all reviews by a specific customer
    // Time Complexity: O(R) where R is total number of reviews
    public static void printCustomerReviews(int customerId) {
        // Validate customer exists
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        LinkedList<Review> customerReviews = Review.getReviewsByCustomer(customerId);
        
        if (customerReviews.empty()) {
            System.out.println("No reviews found for customer " + customer.name + ".");
            return;
        }

        System.out.println("Reviews by " + customer.name + " (ID: " + customerId + "):");
        customerReviews.findFirst();
        while (customerReviews.retrieve() != null) {
            System.out.println(customerReviews.retrieve());
            if (customerReviews.last()) break;
            customerReviews.findNext();
        }
    }
}