package projectFiles;
/**
 * Project 212 - E-Commerce System
 * Version: 2.0
 * Last Updated: 2025-01-27
 * 
 * CSV file reader for loading products, customers, and orders.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimpleCSVReader {

    public static void loadProducts(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(",");
                if (fields.length < 4) continue;
                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    double price = Double.parseDouble(fields[2].trim());
                    int stock = Integer.parseInt(fields[3].trim());

                    Product.addProduct(new Product(id, name, price, stock));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing product line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public static void loadCustomers(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(",");
                if (fields.length < 3) continue;
                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    String email = fields[2].trim();

                    Customer.addCustomer(new Customer(id, name, email));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing customer line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    public static void loadOrders(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                // Use CSV parser to handle quoted fields properly
                String[] fields = parseCSVLine(line);
                if (fields.length < 6) continue;
                
                try {
                    int orderId = Integer.parseInt(fields[0].trim());
                    int customerId = Integer.parseInt(fields[1].trim());
                    
                    // Parse productIds (remove quotes and split by semicolon)
                    String productIdsStr = fields[2].trim();
                    if (productIdsStr.startsWith("\"") && productIdsStr.endsWith("\"")) {
                        productIdsStr = productIdsStr.substring(1, productIdsStr.length() - 1);
                    }
                    String[] productIdArray = productIdsStr.split(";");
                    
                    double totalPrice = Double.parseDouble(fields[3].trim());
                    String orderDate = fields[4].trim();
                    String status = fields[5].trim().toLowerCase();
                    // Handle "cancelled" vs "canceled" (both are valid)
                    if (status.equals("cancelled")) {
                        status = "canceled";
                    }

                    Customer c = Customer.searchById(customerId);
                    
                    if (c == null) {
                        System.out.println("Customer not found for order: " + orderId);
                        continue;
                    }
                    
                    // Create list of products for this order
                    LinkedList<Product> productList = new LinkedList<Product>();
                    for (String productIdStr : productIdArray) {
                        try {
                            int productId = Integer.parseInt(productIdStr.trim());
                            Product p = Product.searchById(productId);
                            
                            if (p != null) {
                                // Add product to list
                                if (productList.empty()) {
                                    productList.insert(p);
                                } else {
                                    productList.findFirst();
                                    while (!productList.last()) {
                                        productList.findNext();
                                    }
                                    productList.insert(p);
                                }
                            } else {
                                System.out.println("Product not found for order: " + orderId + ", productId: " + productId);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing product ID: " + productIdStr);
                        }
                    }
                    
                    // Create one order with all products
                    if (!productList.empty()) {
                        Order newOrder = new Order(orderId, c, productList, totalPrice, orderDate, status);
                        Order.addOrder(newOrder);
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing order line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    public static void loadReviews(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                // Parse CSV with proper handling of quoted fields
                String[] fields = parseCSVLine(line);
                if (fields.length < 5) continue;
                
                try {
                    int reviewId = Integer.parseInt(fields[0].trim());
                    int productId = Integer.parseInt(fields[1].trim());
                    int customerId = Integer.parseInt(fields[2].trim());
                    double rating = Double.parseDouble(fields[3].trim());
                    
                    // Parse comment (handle quoted strings and combine if split)
                    String comment = fields[4].trim();
                    if (comment.startsWith("\"") && comment.endsWith("\"")) {
                        comment = comment.substring(1, comment.length() - 1);
                    }
                    // If comment was split due to commas, join remaining fields
                    if (fields.length > 5) {
                        StringBuilder commentBuilder = new StringBuilder(comment);
                        for (int i = 5; i < fields.length; i++) {
                            commentBuilder.append(",").append(fields[i]);
                        }
                        comment = commentBuilder.toString().trim();
                        if (comment.startsWith("\"") && comment.endsWith("\"")) {
                            comment = comment.substring(1, comment.length() - 1);
                        }
                    }
                    
                    Product p = Product.searchById(productId);
                    Customer c = Customer.searchById(customerId);
                    
                    if (p != null && c != null) {
                        Review review = new Review(reviewId, p, c, comment, rating);
                        p.addReview(review);
                    } else {
                        if (p == null) System.out.println("Product not found for review: " + productId);
                        if (c == null) System.out.println("Customer not found for review: " + customerId);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing review line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading reviews: " + e.getMessage());
        }
    }
    
    // Helper method to parse CSV line handling quoted fields
    private static String[] parseCSVLine(String line) {
        java.util.List<String> fields = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString()); // Add last field
        
        return fields.toArray(new String[0]);
    }
}