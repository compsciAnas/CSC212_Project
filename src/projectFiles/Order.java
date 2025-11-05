package projectFiles;
/**
 * Project 212 - E-Commerce System
 * Version: 2.0
 * Last Updated: 2025-01-27
 * 
 * Order class representing orders in the e-commerce system.
 */
public class Order {
    int orderId;
    Customer customer;
    LinkedList<Product> products;  // List of products in the order
    double totalPrice;
    String orderDate;
    String status;  // pending, shipped, delivered, canceled

    // كل الطلبات تُخزن هنا
    static LinkedList<Order> orders = new LinkedList<Order>();

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

    // عمليات عامة على الطلبات
    public static void addOrder(Order o) {
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

    public static void printAll() {
        if (orders.empty()) {
            System.out.println("No orders available.");
            return;
        }

        orders.findFirst();
        while (orders.retrieve() != null) {
            System.out.println(orders.retrieve());
            if (orders.last()) break;
            orders.findNext();
        }
    }

    public static void printOrdersBetween(String startDate, String endDate) {
        System.out.println("Orders between " + startDate + " and " + endDate + ":");

        orders.findFirst();
        while (orders.retrieve() != null) {
            Order o = orders.retrieve();
            if (o.orderDate.compareTo(startDate) >= 0 && o.orderDate.compareTo(endDate) <= 0)
                System.out.println(o);
            if (orders.last()) break;
            orders.findNext();
        }
    }

    public static Order searchById(int id) {
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

    public static void updateOrderStatus(int id, String newStatus) {
        Order order = searchById(id);
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

    public static void cancelOrder(int id) {
        Order order = searchById(id);
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
            // Count occurrences using LinkedList (no Java Collections)
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

            // Restore stock for each product
            if (!productIds.empty()) {
                productIds.findFirst();
                counts.findFirst();
                while (productIds.retrieve() != null) {
                    int productId = productIds.retrieve();
                    int count = counts.retrieve();
                    Product actualProduct = Product.searchById(productId);
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
}