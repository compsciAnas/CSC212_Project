package projectFiles;

/**
 * Order class - Phase II implementation with AVL Tree storage
 * Orders are stored in AVL Tree keyed by orderId for O(log n) operations.
 * A secondary AVL Tree keyed by orderDate supports date range queries.
 */
public class Order {
    int orderId;
    Customer customer;
    LinkedList<Product> products;  // List of products in the order
    double totalPrice;
    String orderDate;
    String status;  // pending, shipped, delivered, canceled

    // Phase II: AVL Tree keyed by orderId for O(log n) operations
    static AVLTree<Integer, Order> orderTree = new AVLTree<Integer, Order>();
    
    // Phase II: AVL Tree keyed by orderDate for date range queries
    // Note: Since multiple orders can have same date, we store LinkedList<Order>
    static AVLTree<String, LinkedList<Order>> orderTreeByDate = new AVLTree<String, LinkedList<Order>>();
    
    // Phase I: LinkedList maintained for compatibility
    static LinkedList<Order> orders = new LinkedList<Order>();
    
    // Track the maximum order ID for generating new IDs
    static int maxOrderId = 0;

    public Order(int orderId, Customer customer, LinkedList<Product> products, double totalPrice, String orderDate, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(orderId).append("] ");
        sb.append(customer.name).append(" ordered ");
        
        // Build product list string
        if (products.empty()) {
            sb.append("(no products)");
        } else {
            products.findFirst();
            boolean first = true;
            while (products.retrieve() != null) {
                if (!first) sb.append(", ");
                sb.append(products.retrieve().name);
                first = false;
                if (products.last()) break;
                products.findNext();
            }
        }
        
        sb.append(" - Total: $").append(totalPrice);
        sb.append(" on ").append(orderDate);
        sb.append(" [Status: ").append(status).append("]");
        return sb.toString();
    }

    /**
     * Phase II: Get the next available order ID
     */
    public static int getNextOrderId() {
        return maxOrderId + 1;
    }

    /**
     * Phase II: Add order using AVL Tree - O(log n) time complexity
     */
    public static void addOrder(Order o) {
        // Update maxOrderId if necessary
        if (o.orderId > maxOrderId) {
            maxOrderId = o.orderId;
        }
        
        // Insert into AVL Tree by orderId - O(log n)
        orderTree.insert(o.orderId, o);
        
        // Insert into date tree for date range queries
        LinkedList<Order> ordersOnDate = orderTreeByDate.search(o.orderDate);
        if (ordersOnDate == null) {
            ordersOnDate = new LinkedList<Order>();
            ordersOnDate.insert(o);
            orderTreeByDate.insert(o.orderDate, ordersOnDate);
        } else {
            // Add to existing list
            if (ordersOnDate.empty()) {
                ordersOnDate.insert(o);
            } else {
                ordersOnDate.findFirst();
                while (!ordersOnDate.last()) {
                    ordersOnDate.findNext();
                }
                ordersOnDate.insert(o);
            }
        }
        
        // Also maintain LinkedList for backward compatibility
        if (orders.empty()) {
            orders.insert(o);
        } else {
            orders.findFirst();
            while (!orders.last()) {
                orders.findNext();
            }
            orders.insert(o);
        }
    }

    /**
     * Phase II: Print all orders using in-order traversal (sorted by ID)
     */
    public static void printAll() {
        if (orderTree.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        // Use in-order traversal for sorted output by orderId
        LinkedList<Order> sortedOrders = orderTree.inOrderTraversal();
        sortedOrders.findFirst();
        while (sortedOrders.retrieve() != null) {
            System.out.println(sortedOrders.retrieve());
            if (sortedOrders.last()) break;
            sortedOrders.findNext();
        }
    }

    /**
     * Phase II: Print orders between two dates using AVL tree date range
     * Uses in-order traversal of date tree for efficiency
     */
    public static void printOrdersBetween(String startDate, String endDate) {
        System.out.println("Orders between " + startDate + " and " + endDate + ":");

        if (orderTreeByDate.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        // Use range query on date tree
        LinkedList<LinkedList<Order>> dateRangeOrders = orderTreeByDate.rangeQuery(startDate, endDate);
        
        if (dateRangeOrders.empty()) {
            System.out.println("No orders found in this date range.");
            return;
        }

        boolean found = false;
        dateRangeOrders.findFirst();
        while (dateRangeOrders.retrieve() != null) {
            LinkedList<Order> ordersOnDate = dateRangeOrders.retrieve();
            if (!ordersOnDate.empty()) {
                ordersOnDate.findFirst();
                while (ordersOnDate.retrieve() != null) {
                    System.out.println(ordersOnDate.retrieve());
                    found = true;
                    if (ordersOnDate.last()) break;
                    ordersOnDate.findNext();
                }
            }
            if (dateRangeOrders.last()) break;
            dateRangeOrders.findNext();
        }
        
        if (!found) {
            System.out.println("No orders found in this date range.");
        }
    }

    /**
     * Phase II: Search order by ID using AVL Tree - O(log n) time complexity
     */
    public static Order searchById(int id) {
        // Use AVL Tree for O(log n) search
        return orderTree.search(id);
    }
    
    /**
     * Phase I: Search by ID using LinkedList - O(n) time complexity
     * Kept for performance comparison
     */
    public static Order searchByIdLinear(int id) {
        if (orders.empty())
            return null;

        orders.findFirst();
        while (orders.retrieve() != null) {
            Order o = orders.retrieve();
            if (o.orderId == id)
                return o;
            if (orders.last()) break;
            orders.findNext();
        }
        return null;
    }

    /**
     * Phase II: Update order status using AVL Tree - O(log n) search
     */
    public static void updateOrderStatus(int id, String newStatus) {
        Order order = searchById(id); // O(log n)
        if (order != null) {
            // Validate status
            if (newStatus.equals("pending") || newStatus.equals("shipped") || 
                newStatus.equals("delivered") || newStatus.equals("canceled")) {
                order.status = newStatus;
                System.out.println("Order status updated successfully to: " + newStatus);
            } else {
                System.out.println("Invalid status. Valid statuses are: pending, shipped, delivered, canceled");
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    /**
     * Phase II: Cancel order using AVL Tree - O(log n) search
     */
    public static void cancelOrder(int id) {
        Order order = searchById(id); // O(log n)
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        if (order.status.equals("canceled")) {
            System.out.println("Order is already canceled.");
            return;
        }

        // Restore product stock by counting occurrences of each product
        if (!order.products.empty()) {
            // Count occurrences using LinkedList
            order.products.findFirst();
            LinkedList<Integer> productIds = new LinkedList<Integer>();
            LinkedList<Integer> counts = new LinkedList<Integer>();
            
            while (order.products.retrieve() != null) {
                Product p = order.products.retrieve();
                int productId = p.productId;
                
                // Find if productId already exists in our list
                boolean found = false;
                if (!productIds.empty()) {
                    productIds.findFirst();
                    counts.findFirst();
                    while (productIds.retrieve() != null) {
                        if (productIds.retrieve() == productId) {
                            // Increment count
                            int currentCount = counts.retrieve();
                            counts.update(currentCount + 1);
                            found = true;
                            break;
                        }
                        if (productIds.last()) break;
                        productIds.findNext();
                        counts.findNext();
                    }
                }
                
                if (!found) {
                    // Add new product with count 1
                    if (productIds.empty()) {
                        productIds.insert(productId);
                        counts.insert(1);
                    } else {
                        productIds.findFirst();
                        while (!productIds.last()) {
                            productIds.findNext();
                        }
                        productIds.insert(productId);
                        
                        counts.findFirst();
                        while (!counts.last()) {
                            counts.findNext();
                        }
                        counts.insert(1);
                    }
                }
                
                if (order.products.last()) break;
                order.products.findNext();
            }

            // Restore stock for each product using AVL tree search - O(log n) per product
            if (!productIds.empty()) {
                productIds.findFirst();
                counts.findFirst();
                while (productIds.retrieve() != null) {
                    int productId = productIds.retrieve();
                    int count = counts.retrieve();
                    Product actualProduct = Product.searchById(productId); // O(log n)
                    if (actualProduct != null) {
                        actualProduct.stock += count;
                    }
                    if (productIds.last()) break;
                    productIds.findNext();
                    counts.findNext();
                }
            }
        }

        // Update order status to canceled
        order.status = "canceled";
        System.out.println("Order canceled successfully. Product stock has been restored.");
    }

    /**
     * Phase II: Get all orders for a specific customer
     */
    public static LinkedList<Order> getOrdersByCustomer(int customerId) {
        LinkedList<Order> result = new LinkedList<Order>();
        
        if (orderTree.isEmpty()) {
            return result;
        }

        // Traverse all orders and filter by customer
        LinkedList<Order> allOrders = orderTree.inOrderTraversal();
        allOrders.findFirst();
        while (allOrders.retrieve() != null) {
            Order o = allOrders.retrieve();
            if (o.customer.customerId == customerId) {
                if (result.empty()) {
                    result.insert(o);
                } else {
                    result.findFirst();
                    while (!result.last()) {
                        result.findNext();
                    }
                    result.insert(o);
                }
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return result;
    }

    /**
     * Phase II: Get orders between two dates - returns LinkedList
     */
    public static LinkedList<Order> getOrdersBetweenDates(String startDate, String endDate) {
        LinkedList<Order> result = new LinkedList<Order>();
        
        if (orderTreeByDate.isEmpty()) {
            return result;
        }

        // Use range query on date tree
        LinkedList<LinkedList<Order>> dateRangeOrders = orderTreeByDate.rangeQuery(startDate, endDate);
        
        if (dateRangeOrders.empty()) {
            return result;
        }

        dateRangeOrders.findFirst();
        while (dateRangeOrders.retrieve() != null) {
            LinkedList<Order> ordersOnDate = dateRangeOrders.retrieve();
            if (!ordersOnDate.empty()) {
                ordersOnDate.findFirst();
                while (ordersOnDate.retrieve() != null) {
                    if (result.empty()) {
                        result.insert(ordersOnDate.retrieve());
                    } else {
                        result.findFirst();
                        while (!result.last()) {
                            result.findNext();
                        }
                        result.insert(ordersOnDate.retrieve());
                    }
                    if (ordersOnDate.last()) break;
                    ordersOnDate.findNext();
                }
            }
            if (dateRangeOrders.last()) break;
            dateRangeOrders.findNext();
        }
        
        return result;
    }

    /**
     * Phase II: Get all orders sorted by ID (using in-order traversal)
     */
    public static LinkedList<Order> getAllOrdersSorted() {
        return orderTree.inOrderTraversal();
    }

    /**
     * Phase II: Get the number of orders - O(1)
     */
    public static int getOrderCount() {
        return orderTree.size();
    }
}