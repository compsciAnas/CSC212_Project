package projectFiles;

/**
 * Customer class - Phase II implementation with AVL Tree storage
 * Customers are stored in AVL Tree keyed by customer name (alphabetically sorted).
 * A secondary AVL Tree keyed by customerId supports efficient ID lookups.
 */
public class Customer {
    int customerId;
    String name;
    String email;

    // Phase II: AVL Tree keyed by customer name for alphabetical sorting - O(log n) operations
    static AVLTree<String, Customer> customerTreeByName = new AVLTree<String, Customer>();
    
    // Phase II: AVL Tree keyed by customer ID for efficient ID lookups - O(log n) operations
    static AVLTree<Integer, Customer> customerTreeById = new AVLTree<Integer, Customer>();
    
    // Phase I: LinkedList maintained for compatibility
    static LinkedList<Customer> customers = new LinkedList<Customer>();

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }


    public String toString() {
        return "[" + customerId + "] " + name + " - " + email;
    }


    /**
     * Phase II: Add customer using AVL Tree - O(log n) time complexity
     */
    public static boolean addCustomer(Customer c) {
        // Check for duplicate customer ID using AVL search - O(log n)
        if (searchById(c.customerId) != null) {
            System.out.println("Error: Customer ID " + c.customerId + " already exists. Cannot add duplicate customer.");
            return false;
        }
        
        // Insert into AVL Trees - O(log n) each
        customerTreeById.insert(c.customerId, c);
        customerTreeByName.insert(c.name.toLowerCase(), c); // Case-insensitive key
        
        // Also maintain LinkedList for backward compatibility
        if (customers.empty()) {
            customers.insert(c);
        } else {
            customers.findFirst();
            while (!customers.last()) {
                customers.findNext();
            }
            customers.insert(c);
        }
        return true;
    }

    /**
     * Phase II: Search customer by ID using AVL Tree - O(log n) time complexity
     */
    public static Customer searchById(int id) {
        // Use AVL Tree for O(log n) search
        return customerTreeById.search(id);
    }
    
    /**
     * Phase I: Search by ID using LinkedList - O(n) time complexity
     * Kept for performance comparison
     */
    public static Customer searchByIdLinear(int id) {
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
    
    /**
     * Phase II: Search customer by name using AVL Tree - O(log n) time complexity
     */
    public static Customer searchByName(String name) {
        return customerTreeByName.search(name.toLowerCase());
    }

    /**
     * Phase II: Print all customers (unsorted - using LinkedList)
     */
    public static void printAll() {
        if (customerTreeById.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        // Use in-order traversal for output
        LinkedList<Customer> allCustomers = customerTreeById.inOrderTraversal();
        allCustomers.findFirst();
        while (allCustomers.retrieve() != null) {
            System.out.println(allCustomers.retrieve());
            if (allCustomers.last()) break;
            allCustomers.findNext();
        }
    }
    
    /**
     * Phase II: Print all customers sorted alphabetically by name
     * Uses in-order traversal of name-keyed AVL Tree - O(n)
     */
    public static void printAllSortedAlphabetically() {
        if (customerTreeByName.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        System.out.println("Customers (sorted alphabetically by name):");
        // Use in-order traversal of name tree for sorted output
        LinkedList<Customer> sortedCustomers = customerTreeByName.inOrderTraversal();
        sortedCustomers.findFirst();
        while (sortedCustomers.retrieve() != null) {
            System.out.println(sortedCustomers.retrieve());
            if (sortedCustomers.last()) break;
            sortedCustomers.findNext();
        }
    }

    /**
     * Phase II: Update customer using AVL Tree - O(log n) for search
     */
    public static void updateCustomer(int id, String newName, String newEmail) {
        Customer c = searchById(id); // O(log n)
        if (c != null) {
            // Need to update the name tree if name changed
            String oldNameKey = c.name.toLowerCase();
            String newNameKey = newName.toLowerCase();
            
            if (!oldNameKey.equals(newNameKey)) {
                // Remove from name tree and re-insert with new key
                customerTreeByName.delete(oldNameKey);
                c.name = newName;
                c.email = newEmail;
                customerTreeByName.insert(newNameKey, c);
            } else {
                c.name = newName;
                c.email = newEmail;
            }
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    /**
     * Phase II: Place an order - uses AVL trees for efficient lookups
     */
    public static void placeOrder(int customerId, int productId, int quantity, String orderDate) {
        // Validate customer exists - O(log n)
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        // Validate product exists - O(log n)
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

        // Generate new order ID using AVL tree
        int newOrderId = Order.getNextOrderId();

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

    /**
     * Phase II: View order history - uses AVL trees for efficient retrieval
     */
    public static void viewOrderHistory(int customerId) {
        // Validate customer exists - O(log n)
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        LinkedList<Order> customerOrders = Order.getOrdersByCustomer(customerId);
        
        if (customerOrders.empty()) {
            System.out.println("No orders found for customer " + customer.name + ".");
            return;
        }

        System.out.println("Order History for " + customer.name + " (ID: " + customerId + "):");
        customerOrders.findFirst();
        while (customerOrders.retrieve() != null) {
            System.out.println(customerOrders.retrieve());
            if (customerOrders.last()) break;
            customerOrders.findNext();
        }
    }



    /**
     * Phase II: Add review to product - uses AVL trees for efficient lookups
     */
    public static void addReviewToProduct(int customerId, int productId, double rating, String comment) {
        // Validate customer exists - O(log n)
        Customer customer = searchById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        // Validate product exists - O(log n)
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
        int newReviewId = Review.getNextReviewId();

        // Create and add review
        Review newReview = new Review(newReviewId, product, customer, comment, rating);
        Review.addReview(newReview);
        product.addReview(newReview);

        System.out.println("Review added successfully. Review ID: " + newReviewId);
    }


    /**
     * Phase II: Print customer reviews - uses AVL trees
     */
    public static void printCustomerReviews(int customerId) {
        // Validate customer exists - O(log n)
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
    
    /**
     * Phase II: Get all customers sorted alphabetically
     */
    public static LinkedList<Customer> getAllCustomersSortedAlphabetically() {
        return customerTreeByName.inOrderTraversal();
    }
    
    /**
     * Phase II: Get the number of customers - O(1)
     */
    public static int getCustomerCount() {
        return customerTreeById.size();
    }
}