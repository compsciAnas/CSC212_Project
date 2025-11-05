Project 212 - E-Commerce System Report

A Java-based e-commerce management system that handles customers, products, orders, and reviews using a custom-built LinkedList data structure, as required by the project specifications.

1. Data Structure

This project uses a custom LinkedList implementation (projectFiles.LinkedList) as the sole data structure for managing all collections. This fulfills the project requirement of not using any built-in Java collections.

System-Wide Collections: static LinkedList fields in Product, Customer, Order, and Review classes hold all data.

Instance Collections: Product instances hold a LinkedList of their reviews. Customer instances hold a LinkedList of their orders.

Search Results: Methods return new LinkedList instances containing results.

2. Implemented Core Features

Product Operations

Product.addProduct(Product p): Adds a new product to the system.

Product.searchById(int id): Finds a product by its ID (linear search) - Time: O(P).

Product.updateProduct(int id, double newPrice, int newStock): Updates a product's price and stock.

Product.removeProduct(int id): Removes a product from the system - Time: O(P).

Product.printAll(): Prints all products in the system.

Product.printOutOfStock(): Prints all products with stock <= 0 - Time: O(P).

Product.topThreeProducts(): Displays top 3 products by rating using Bubble Sort - Time: O(P² + P*R_avg).

Product.getAverageRating(): (Instance method) Calculates the average rating for one product - Time: O(R_avg).

Customer Operations

Customer.addCustomer(Customer c): Registers a new customer in the system.

Customer.searchById(int id): Finds a customer by their ID - Time: O(C).

Customer.placeOrder(int customerId, int productId, int quantity, String orderDate): Creates a new order for a specific product, validates stock, calculates total price, reduces product stock, and assigns "pending" status - Time: O(C + P + M).

Customer.viewOrderHistory(int customerId): Prints all orders for a specific customer - Time: O(M).

Customer.addReviewToProduct(int customerId, int productId, double rating, String comment): Allows a customer to add a review for a product - Time: O(C + P + R).

Customer.printCustomerReviews(int customerId): Prints all reviews written by a specific customer - Time: O(C + R).

Customer.updateCustomer(int id, String newName, String newEmail): Updates customer information.

Customer.printAll(): Prints all customers in the system.

Order Operations

Order.addOrder(Order o): Adds a new order to the system (called by Customer.placeOrder).

Order.searchById(int id): Finds an order by its ID - Time: O(M).

Order.cancelOrder(int id): Cancels an order, restores product stock for all products in the order, and updates status to "canceled" - Time: O(M + L*P + L²).

Order.updateOrderStatus(int id, String status): Updates an order's status (pending/shipped/delivered/canceled) - Time: O(M).

Order.printAll(): Prints all orders in the system.

Order.printOrdersBetween(String startDate, String endDate): Prints orders within a date range - Time: O(M).

Review Operations

Review.addReview(Review r): Adds a new review to the system and links it to the corresponding product.

Review.editReview(int reviewId, double newRating, String newComment): Edits an existing review's rating and comment - Time: O(R).

Review.getReviewsByCustomer(int customerId): Returns all reviews written by a specific customer - Time: O(R).

Review.getCommonHighRatedProducts(int custId1, int custId2): Returns products that both customers reviewed with average rating > 4.0 - Time: O(R * R_cust + P * R_avg).

Review.printAll(): Prints all reviews in the system.

3. Implemented Business Queries & Interactive Features

The following advanced business queries and interactive features are fully implemented in the SimpleECommerceTest menu system:

View All Data:
- View all products (Option 1)
- View all customers (Option 2)
- View all orders (Option 3)
- View all reviews (Option 4)

Product Queries:
- **Out of Stock Products** (Option 5): Product.printOutOfStock() - Displays all products with stock <= 0
- **Top 3 Products by Rating** (Option 6): Product.topThreeProducts() - Displays the 3 highest-rated products using Bubble Sort (O(P² + P*R_avg))

Order Queries:
- **Orders Between Two Dates** (Option 7): Order.printOrdersBetween(startDate, endDate) - Displays orders within a specified date range (O(M))

Review Operations:
- **Add Review to Product** (Option 8): Customer.addReviewToProduct() - Allows customers to submit product reviews with ratings 1-5
- **Edit Review** (Option 9): Review.editReview() - Allows editing an existing review's rating and comment
- **View Customer Reviews** (Option 10): Customer.printCustomerReviews() - Displays all reviews written by a specific customer

Advanced Query:
- **Common High-Rated Products** (Option 11): Review.getCommonHighRatedProducts(custId1, custId2) - Returns products that both customers reviewed with overall average rating > 4.0 (O(R * R_cust + P * R_avg))

Order Management:
- **Place an Order** (Option 12): Customer.placeOrder() - Creates new orders with stock validation, automatic order ID generation, and "pending" status
- **Cancel an Order** (Option 13): Order.cancelOrder() - Cancels orders and automatically restores product stock
- **Update Order Status** (Option 14): Order.updateOrderStatus() - Updates order status (pending/shipped/delivered/canceled)

4. Project Structure

CSC212_Project/
├── src/
│   └── projectFiles/
│       ├── Customer.java
│       ├── LinkedList.java
│       ├── List.java
│       ├── Node.java
│       ├── Order.java
│       ├── Product.java
│       ├── Review.java
│       ├── SimpleCSVReader.java
│       └── SimpleECommerceTest.java
├── customers.csv
├── products.csv
├── orders.csv
├── reviews.csv
└── README.md

How to Run:

1. Ensure all CSV files (customers.csv, products.csv, orders.csv, reviews.csv) are in the project root directory
2. Compile all Java files in the projectFiles package
3. Run SimpleECommerceTest.java
4. Use the interactive menu to perform operations:
   - View data (products, customers, orders, reviews)
   - Execute business queries (top products, date ranges, common reviews)
   - Manage orders (place, cancel, update status)
   - Add and view reviews


5. Algorithm Complexity Analysis

Variable Definitions

P: Total number of products in the system.

C: Total number of customers in the system.

M: Total number of orders in the system.

R: Total number of reviews in the system.

R_avg: The average number of reviews per product (R / P).

R_cust: The average number of reviews per customer (R / C).

L: The number of items (products) in a single order.

Data Structure (LinkedList<T>)

add(data) (to tail): Time: O(1)

remove(data): Time: O(n) (must search the list)

size(), isEmpty(): Time: O(1)

iterator(), forEach loop: Time: O(n)

Complete Method-by-Method Time Complexity Analysis

Product Class Methods:

Product.addProduct(Product p):
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Traverses to the end of the products LinkedList to insert at tail position. No additional data structures created.

Product.searchById(int id):
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Linear search through all P products to find matching ID. Uses only a few local variables.

Product.updateProduct(int id, double newPrice, int newStock):
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Calls searchById(O(P)) then updates fields in O(1). No additional data structures.

Product.removeProduct(int id):
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Linear search through products list to find and remove the matching product. In-place removal.

Product.printAll():
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Iterates through all P products once and prints each. No additional storage needed.

Product.printOutOfStock():
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Iterates through all P products, checking stock field for each. Prints directly without storing results.

Product.topThreeProducts():
Time Complexity: O(P² + P*R_avg)
Space Complexity: O(P)
Analysis: O(P) to count products, O(P*R_avg) to collect ratings (calls getAverageRating() for each product), O(P²) for Bubble Sort. Creates two temporary arrays of size P (productArray and ratingArray).

Product.getAverageRating():
Time Complexity: O(R_avg)
Space Complexity: O(1)
Analysis: Iterates through all reviews for this specific product (average R_avg reviews per product). Uses only sum and count variables.

Product.addReview(Review r):
Time Complexity: O(R_prod)
Space Complexity: O(1)
Analysis: Traverses to the end of this product's reviews list (R_prod reviews for this product) to insert at tail. No additional structures.

Product.toString():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Constructs string from instance fields only. String creation is O(1) for fixed number of fields.

Customer Class Methods:

Customer.addCustomer(Customer c):
Time Complexity: O(C)
Space Complexity: O(1)
Analysis: Traverses to the end of the customers LinkedList to insert at tail position. No additional data structures created.

Customer.searchById(int id):
Time Complexity: O(C)
Space Complexity: O(1)
Analysis: Linear search through all C customers to find matching ID. Uses only local variables.

Customer.printAll():
Time Complexity: O(C)
Space Complexity: O(1)
Analysis: Iterates through all C customers once and prints each. No additional storage.

Customer.updateCustomer(int id, String newName, String newEmail):
Time Complexity: O(C)
Space Complexity: O(1)
Analysis: Calls searchById(O(C)) then updates fields in O(1). No additional data structures.

Customer.placeOrder(int customerId, int productId, int quantity, String orderDate):
Time Complexity: O(C + P + M + L)
Space Complexity: O(L)
Analysis: O(C) to find the customer + O(P) to find the product + O(M) to generate a new order ID by iterating through all orders + O(L) to create a LinkedList with L copies of the product. Creates new Order object with productList containing L nodes.

Customer.viewOrderHistory(int customerId):
Time Complexity: O(C + M)
Space Complexity: O(1)
Analysis: O(C) to find and validate the customer + O(M) to iterate through all orders and filter by customer ID. Prints directly without storing.

Customer.addReviewToProduct(int customerId, int productId, double rating, String comment):
Time Complexity: O(C + P + R + R_prod)
Space Complexity: O(1)
Analysis: O(C) to find the customer + O(P) to find the product + O(R) to generate a new Review ID + O(R_prod) to add the review to the product's review list. Creates one new Review object (constant space).

Customer.printCustomerReviews(int customerId):
Time Complexity: O(C + R)
Space Complexity: O(R_cust)
Analysis: O(C) to find and validate the customer + O(R) to call getReviewsByCustomer(), which iterates through all reviews to find matches. Creates new LinkedList containing R_cust reviews for this customer.

Customer.toString():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Constructs string from instance fields only. Fixed number of fields.

Order Class Methods:

Order.addOrder(Order o):
Time Complexity: O(M)
Space Complexity: O(1)
Analysis: Traverses to the end of the orders LinkedList to insert at tail position. No additional data structures created.

Order.printAll():
Time Complexity: O(M * L)
Space Complexity: O(1)
Analysis: Iterates through all M orders. For each order, toString() iterates through L products in the order to build the product list string. Prints directly without storing.

Order.printOrdersBetween(String startDate, String endDate):
Time Complexity: O(M * L)
Space Complexity: O(1)
Analysis: Iterates through all M orders, performs O(1) date comparison for each, and calls toString() (O(L)) for matching orders. No additional storage.

Order.searchById(int id):
Time Complexity: O(M)
Space Complexity: O(1)
Analysis: Linear search through all M orders to find matching ID. Uses only local variables.

Order.updateOrderStatus(int id, String newStatus):
Time Complexity: O(M)
Space Complexity: O(1)
Analysis: Calls searchById(O(M)) then updates status field in O(1) after validating the status string (O(1)). No additional structures.

Order.cancelOrder(int id):
Time Complexity: O(M + L² + L*P)
Space Complexity: O(L)
Analysis: O(M) to find the order + O(L²) for counting occurrences of each product using nested LinkedList traversals (for each of L products, search through previously counted products) + O(L*P) to restore stock by calling Product.searchById(O(P)) for each unique product. Creates two temporary LinkedLists: productIds and counts, each with up to L elements.

Order.toString():
Time Complexity: O(L)
Space Complexity: O(L)
Analysis: Iterates through all L products in the order to build product names string. StringBuilder internally allocates space proportional to the number of product names.

Review Class Methods:

Review.addReview(Review r):
Time Complexity: O(R)
Space Complexity: O(1)
Analysis: Traverses to the end of the allReviews LinkedList to insert at tail position. No additional data structures created.

Review.editReview(int reviewId, double newRating, String newComment):
Time Complexity: O(R)
Space Complexity: O(1)
Analysis: Linear search through all R reviews to find the review with matching ID. Once found, updates rating and comment fields in O(1). Uses only local variables.

Review.printAll():
Time Complexity: O(R)
Space Complexity: O(1)
Analysis: Iterates through all R reviews once and prints each. Prints directly without storing.

Review.getReviewsByCustomer(int customerId):
Time Complexity: O(R)
Space Complexity: O(R_cust)
Analysis: Iterates through all R reviews, checking customer ID for each, and adds matches to a new LinkedList. Creates new LinkedList containing R_cust reviews for this customer.

Review.getCommonHighRatedProducts(int cust1, int cust2):
Time Complexity: O(R*R_cust + R_cust² + P*R_avg)
Space Complexity: O(R_cust + P_common)
Analysis: O(R*R_cust) to get products reviewed by customer 1 (iterates all R reviews, for each match performs O(R_cust) duplicate check), O(R*R_cust) for customer 2, O(R_cust²) to find intersection of two lists, O(P*R_avg) in worst case to filter by rating > 4.0 (calls getAverageRating() for each common product). Creates LinkedLists for products reviewed by customer 1 (R_cust space), customer 2 (R_cust space), and common products (P_common space).

Review.toString():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Constructs string from instance fields only. Fixed number of fields.

SimpleCSVReader Class Methods:

SimpleCSVReader.loadProducts(String filename):
Time Complexity: O(P_file * P)
Space Complexity: O(1) per iteration
Analysis: Reads P_file lines from file and calls Product.addProduct() for each (O(P) per call because it traverses to end of list). At startup when list is initially empty, this is O(1) + O(2) + O(3) + ... + O(P) = O(P²). Each line is processed with temporary String variables only.

SimpleCSVReader.loadCustomers(String filename):
Time Complexity: O(C_file * C)
Space Complexity: O(1) per iteration
Analysis: Reads C_file lines from file and calls Customer.addCustomer() for each (O(C) per call). Similar to loadProducts, total is O(C²) when building list from empty. Uses temporary String variables per line.

SimpleCSVReader.loadOrders(String filename):
Time Complexity: O(M_file * (C + P*L + M))
Space Complexity: O(L) per order
Analysis: Reads M_file lines from file. For each order: O(C) to find customer, O(L*P) to find L products and build product list, O(M) to add order. Creates LinkedList of L products for each order.

SimpleCSVReader.loadReviews(String filename):
Time Complexity: O(R_file * (P + C + R))
Space Complexity: O(1) per iteration
Analysis: Reads R_file lines from file. For each review: O(P) to find product, O(C) to find customer, O(R) to add review, O(R_prod) to add to product's review list. Creates one Review object per line (constant space per iteration).

LinkedList<T> Data Structure Operations:

LinkedList.insert(T data):
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Inserts at current position in constant time. Creates one new Node object.

LinkedList.remove():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Removes element at current position in constant time. Updates pointers only.

LinkedList.retrieve():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Returns element at current position. No allocation, just returns reference.

LinkedList.update(T data):
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Updates element at current position. Replaces data reference only.

LinkedList.findFirst():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Moves current pointer to head. Updates one pointer variable.

LinkedList.findNext():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Moves current pointer to next node. Updates one pointer variable.

LinkedList.last():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Checks if current pointer is at tail. Compares two pointers.

LinkedList.empty():
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Checks if list is empty by comparing head to null. Single comparison.

Interactive Menu Operations Time Complexity Analysis

Option 1 - View All Products (Product.printAll):
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Single pass through all products to print each one.

Option 2 - View All Customers (Customer.printAll):
Time Complexity: O(C)
Space Complexity: O(1)
Analysis: Single pass through all customers to print each one.

Option 3 - View All Orders (Order.printAll):
Time Complexity: O(M * L)
Space Complexity: O(1)
Analysis: Iterates through all M orders, each requiring O(L) to format product list in toString().

Option 4 - View All Reviews (Review.printAll):
Time Complexity: O(R)
Space Complexity: O(1)
Analysis: Single pass through all reviews to print each one.

Option 5 - View Out of Stock Products (Product.printOutOfStock):
Time Complexity: O(P)
Space Complexity: O(1)
Analysis: Single pass through all products, checking stock <= 0 for each.

Option 6 - View Top 3 Rated Products (Product.topThreeProducts):
Time Complexity: O(P² + P*R_avg)
Space Complexity: O(P)
Analysis: O(P) to count, O(P*R_avg) to collect ratings (getAverageRating() called P times), O(P²) for Bubble Sort. Creates temporary arrays of size P.

Option 7 - View Orders Between Dates (Order.printOrdersBetween):
Time Complexity: O(M * L)
Space Complexity: O(1)
Analysis: Single pass through M orders with O(1) date comparison, O(L) toString() for matches.

Option 8 - Add Review to Product (Customer.addReviewToProduct):
Time Complexity: O(C + P + R + R_prod)
Space Complexity: O(1)
Analysis: O(C) find customer, O(P) find product, O(R) generate review ID, O(R_prod) add to product's review list.

Option 9 - Edit Review (Review.editReview):
Time Complexity: O(R)
Space Complexity: O(1)
Analysis: O(R) to search through all reviews to find the review by ID, O(1) to update rating and comment fields.

Option 10 - View Customer Reviews (Customer.printCustomerReviews):
Time Complexity: O(C + R)
Space Complexity: O(R_cust)
Analysis: O(C) find customer, O(R) iterate all reviews to find matches. Creates new list with R_cust reviews.

Option 11 - View Common High-Rated Products (Review.getCommonHighRatedProducts):
Time Complexity: O(R*R_cust + R_cust² + P*R_avg)
Space Complexity: O(R_cust + P_common)
Analysis: Most complex query. O(R*R_cust) to get products reviewed by each customer (with duplicate checking), O(R_cust²) to find intersection, O(P_common*R_avg) to filter by rating > 4.0. Creates lists for products reviewed by each customer and common products.

Option 12 - Place an Order (Customer.placeOrder):
Time Complexity: O(C + P + M + L)
Space Complexity: O(L)
Analysis: O(C) find customer, O(P) find product, O(M) generate order ID, O(L) create product list with quantity L copies. Creates new Order object with LinkedList of L products.

Option 13 - Cancel an Order (Order.cancelOrder):
Time Complexity: O(M + L² + L*P)
Space Complexity: O(L)
Analysis: O(M) find order, O(L²) count product occurrences with nested list traversals, O(L*P) restore stock for unique products. Creates temporary lists for product IDs and counts.

Option 14 - Update Order Status (Order.updateOrderStatus):
Time Complexity: O(M)
Space Complexity: O(1)
Analysis: O(M) to find order by ID, O(1) to validate and update status field.

Option 15 - Exit:
Time Complexity: O(1)
Space Complexity: O(1)
Analysis: Closes scanner and returns from main method.

6. CSV File Formats

customers.csv

customerId,name,email
201,Alice Johnson,alice.johnson@example.com


products.csv (or prodcuts.csv)

productId,name,price,stock
101,Laptop Pro 15,1499.99,20


orders.csv

orderId,customerId,productIds,totalPrice,orderDate,status
301,201,"101;102",1529.98,2025-01-12,Pending


reviews.csv

reviewId,productId,customerId,rating,comment
401,101,201,5,"Excellent laptop, super fast!"
