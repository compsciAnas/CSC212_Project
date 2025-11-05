Project 212 - E-Commerce System Report

A Java-based e-commerce management system that handles customers, products, orders, and reviews using a custom-built LinkedList data structure, as required by the project specifications.

1. Class Diagram

This diagram describes the classes and their relationships. The system is built on a static data model, where each core class (Product, Customer, Order, Review) holds a static LinkedList of all instances of that object.

classDiagram
    class LinkedList~T~ {
        -Node~T~ head
        -Node~T~ tail
        -int size
        +add(T data) O(1)
        +remove(T data) O(n)
        +size() O(1)
        +isEmpty() O(1)
        +iterator() O(1)
    }
    class Node~T~ {
        -T data
        -Node~T~ next
    }
    
    class Product {
        -int productId
        -String name
        -double price
        -int stock
        -LinkedList~Review~ reviews
        -static LinkedList~Product~ allProducts
        +addReview(Review r) O(1)
        +getAverageRating() O(R_avg)
        +static addOrUpdateProduct(Product p) O(P)
        +static removeProduct(int id) O(P)
        +static findById(int id) O(P)
        +static findByName(String name) O(P)
        +static getOutOfStockProducts() O(P)
        +static getAverageRatingReport() O(P*R_avg)
        +static getTop3ProductsByRating() O(P^2 + P*R_avg)
    }
    
    class Customer {
        -int customerId
        -String name
        -String email
        -LinkedList~Order~ orders
        -static LinkedList~Customer~ allCustomers
        +addOrder(Order o) O(1)
        +addReview(int pId, int r, String c) O(P+R)
        +placeNewOrder(LinkedList~Integer~ pIds) O(M + L*P)
        +printOrderHistory() O(O_cust)
        +static addCustomer(Customer c) O(C)
        +static findById(int id) O(C)
    }
    
    class Order {
        -int orderId
        -int customerId
        -LinkedList~Integer~ productIds
        -double totalPrice
        -String orderDate
        -String status
        -static LinkedList~Order~ allOrders
        +static createOrder(Order o) O(1)
        +static cancelOrder(int id) O(M+P*L)
        +static updateOrderStatus(int id, String s) O(M)
        +static findById(int id) O(M)
        +static getOrdersBetweenDates(String d1, String d2) O(M)
    }
    
    class Review {
        -int reviewId
        -int productId
        -int customerId
        -int rating
        -String comment
        -static LinkedList~Review~ allReviews
        +static addReview(Review r) O(1+P)
        +static editReview(int id, int r, String c) O(R)
        +static getCommonHighRatedProducts(int c1, int c2) O(R*R_cust + P*R_avg)
        -static getProductsReviewedBy(int cId) O(R*R_cust)
    }

    class SimpleCSVReader {
        +static loadProducts(String f)
        +static loadCustomers(String f)
        +static loadOrders(String f)
        +static loadReviews(String f)
        +static loadAllData()
    }
    
    class SimpleECommerceTest {
        +static main(String[] args)
    }

    LinkedList --o "1" Node : (head)
    LinkedList --o "1" Node : (tail)
    
    Product "1" --o "*" Review : (reviews)
    Customer "1" --o "*" Order : (orders)
    
    Product ..> "static" LinkedList : allProducts
    Customer ..> "static" LinkedList : allCustomers
    Order ..> "static" LinkedList : allOrders
    Review ..> "static" LinkedList : allReviews
    
    SimpleCSVReader ..> Product : (uses static methods)
    SimpleCSVReader ..> Customer : (uses static methods)
    SimpleCSVReader ..> Order : (uses static methods)
    SimpleCSVReader ..> Review : (uses static methods)
    
    SimpleECommerceTest ..> SimpleCSVReader : (calls)
    SimpleECommerceTest ..> Product : (uses static methods)
    SimpleECommerceTest ..> Customer : (uses static methods)
    SimpleECommerceTest ..> Order : (uses static methods)
    SimpleECommerceTest ..> Review : (uses static methods)


2. Data Structure

This project uses a custom LinkedList implementation (projectFiles.LinkedList) as the sole data structure for managing all collections. This fulfills the project requirement of not using any built-in Java collections.

System-Wide Collections: static LinkedList fields in Product, Customer, Order, and Review classes hold all data.

Instance Collections: Product instances hold a LinkedList of their reviews. Customer instances hold a LinkedList of their orders.

Search Results: Methods return new LinkedList instances containing results.

3. Implemented Core Features

Product Operations

Product.addOrUpdateProduct(Product p): Adds a new product or updates an existing one.

Product.removeProduct(int id): Removes a product from the system.

Product.findById(int id): Finds a product by its ID (linear search).

Product.findByName(String name): Finds a product by its name (linear search).

Product.getOutOfStockProducts(): Returns a list of products with stock <= 0.

Customer Operations

Customer.addCustomer(Customer c): Registers a new customer.

Customer.findById(int id): Finds a customer by their ID.

Customer.placeNewOrder(LinkedList<Integer> pIds): Creates a new order for the customer, calculates the price, and reduces product stock.

Customer.printOrderHistory(): Prints all orders for a specific customer.

Order Operations

Order.createOrder(Order o): Adds a new order to the system (called by placeNewOrder).

Order.cancelOrder(int id): Cancels an order and restores product stock.

Order.updateOrderStatus(int id, String status): Updates an order's status.

Order.findById(int id): Finds an order by its ID.

Review Operations

Review.addReview(Review r): Adds a new review to the system and links it to the corresponding product.

Review.editReview(int id, int rating, String comment): Modifies an existing review.

Product.getAverageRating(): (Instance method) Calculates the average rating for one product.

4. Implemented Business Queries

The following four advanced business queries from the project requirements are fully implemented:

Average Rating for All Products:

Product.getAverageRatingReport(): Returns a LinkedList<String> with the average rating for every product.

Top 3 Products by Rating:

Product.getTop3ProductsByRating(): Returns a LinkedList<Product> containing the 3 highest-rated products, sorted using Bubble Sort.

All Orders Between Two Dates:

Order.getOrdersBetweenDates(String startDate, String endDate): Returns a LinkedList<Order> where the order date is between the two given dates (inclusive).

Common High-Rated Products:

Review.getCommonHighRatedProducts(int custId1, int custId2): Returns a LinkedList<Product> of products that both customers have reviewed and that have an overall average rating greater than 4.0.

5. Project Structure

Project_212/
└── src/
    └── projectFiles/
        ├── Customer.java
        ├── LinkedList.java
        ├── Order.java
        ├── Product.java
        ├── Review.java
        ├── SimpleCSVReader.java
        └── SimpleECommerceTest.java


6. Algorithm Complexity Analysis

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

Standard Operations

Product.findById(id): Time: O(P)

Customer.findById(id): Time: O(C)

Order.findById(id): Time: O(M)

Product.findByName(name): Time: O(P)

Customer.placeNewOrder(pIds): Time: O(M + L*P)

Analysis: O(M) to find a new Order ID + O(L*P) to iterate L product IDs and call Product.findById(O(P)) for each one.

Customer.addReview(...): Time: O(P + R)

Analysis: O(P) to find the product + O(R) to find a new Review ID.

Order.cancelOrder(id): Time: O(M + L*P)

Analysis: O(M) to find the order + O(L*P) to iterate L products in the order and call Product.findById(O(P)) for each to restore stock.

Business Query Analysis

Average Rating for All Products (Product.getAverageRatingReport)

Time Complexity: O(P * R_avg) (or O(P + R))

Analysis: The method iterates through all P products (O(P)). For each product, it calls getAverageRating(), which iterates through that product's R_avg reviews (O(R_avg)).

Space Complexity: O(P) (to store the new LinkedList<String> for the report).

Top 3 Products by Rating (Product.getTop3ProductsByRating)

Time Complexity: O(P² + P*R_avg)

Analysis: The dominant factor is the O(P²) Bubble Sort. Inside the sort's nested loops, getAverageRating() (O(R_avg)) is called O(P) times.

Space Complexity: O(P) (to create the temporary array used for sorting).

All Orders Between Two Dates (Order.getOrdersBetweenDates)

Time Complexity: O(M)

Analysis: The method iterates through the allOrders list (size M) exactly once, performing an O(1) string comparison for each order.

Space Complexity: O(M) (in the worst case, all orders match and are added to the results list).

Common High-Rated Products (Review.getCommonHighRatedProducts)

Time Complexity: O(R * R_cust + P * R_avg)

Analysis: This is the most complex query.

getProductsReviewedBy(c1): O(R * R_cust). It iterates all R reviews, and for each of c1's R_cust reviews, it calls listContains (O(R_cust)).

getProductsReviewedBy(c2): O(R * R_cust).

Find Intersection: O(R_cust * R_cust).

Filter by Rating: O(P * R_avg) in the worst case (if all products are common).

The dominant term is O(R * R_cust).

Space Complexity: O(R_cust) (to store the lists of product IDs for both customers).

7. CSV File Formats

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
