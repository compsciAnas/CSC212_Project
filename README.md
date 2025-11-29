# Project 212 - E-Commerce System (Phase II)

A Java-based e-commerce management system that handles customers, products, orders, and reviews. This Phase II implementation uses **AVL Trees** (self-balancing Binary Search Trees) for O(log n) operations, while maintaining custom LinkedList data structures as required by the project specifications.

## How to Clone and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Git (for cloning)

### Cloning the Repository

```bash
# Clone the repository
git clone https://github.com/compsciAnas/CSC212_Project.git

# Navigate into the project directory
cd CSC212_Project
```

### Compiling the Project

```bash
# Create output directory for compiled classes
mkdir -p out

# Compile all Java files
javac -d out src/projectFiles/*.java
```

### Running the Application

```bash
# Run the application (from project root directory)
java -cp out projectFiles.SimpleECommerceTest
```

**Note:** The CSV data files (customers.csv, products.csv, orders.csv, reviews.csv) must be in the project root directory when running the application.

---

## 1. Data Structures

### Phase II: AVL Trees (Primary)
This Phase II implementation uses **AVL Trees** (self-balancing BSTs) for O(log n) operations:
- **Product.productTree**: AVL Tree keyed by productId
- **Customer.customerTreeById**: AVL Tree keyed by customerId
- **Customer.customerTreeByName**: AVL Tree keyed by customer name (for alphabetical sorting)
- **Order.orderTree**: AVL Tree keyed by orderId
- **Order.orderTreeByDate**: AVL Tree keyed by orderDate (for date range queries)
- **Review.reviewTree**: AVL Tree keyed by reviewId

### Phase I: LinkedList (Maintained for Compatibility)
Custom LinkedList implementation (projectFiles.LinkedList) is maintained for:
- Backward compatibility with Phase I operations
- Instance collections (Product reviews, Order products)
- Search result collections

---

## 2. Implemented Core Features

### Product Operations (O(log n) with AVL Tree)

- **Product.addProduct(Product p)**: Adds a new product. O(log n) duplicate check + O(log n) insertion.
- **Product.searchById(int id)**: Finds a product by ID using AVL Tree - O(log n).
- **Product.searchByIdLinear(int id)**: Phase I linear search - O(P) (kept for performance comparison).
- **Product.searchByName(String name)**: Finds by name (case-insensitive) - O(P).
- **Product.updateProduct(int id, double newPrice, int newStock)**: Updates product - O(log n) search + O(1) update.
- **Product.removeProduct(int id)**: Removes product - O(log n).
- **Product.printAll()**: Prints all products sorted by ID using in-order traversal - O(P).
- **Product.printOutOfStock()**: Prints products with stock <= 0 - O(P).
- **Product.topThreeProducts()**: Top 3 by rating using Bubble Sort - O(P² + P*R_avg).
- **Product.topThreeMostReviewedProducts()**: Top 3 by review count - O(P² + P*R_avg).
- **Product.getProductsInPriceRange(min, max)**: Range query - O(P).
- **Product.getAverageRating()**: Instance method - O(R_avg).

### Customer Operations (O(log n) with AVL Tree)

- **Customer.addCustomer(Customer c)**: Adds a new customer - O(log n).
- **Customer.searchById(int id)**: Finds customer by ID using AVL Tree - O(log n).
- **Customer.searchByIdLinear(int id)**: Phase I linear search - O(C) (kept for performance comparison).
- **Customer.searchByName(String name)**: Finds by name using AVL Tree - O(log n).
- **Customer.printAll()**: Prints all customers - O(C).
- **Customer.printAllSortedAlphabetically()**: In-order traversal of name tree - O(C).
- **Customer.placeOrder(...)**: Creates order - O(log C + log P + log M).
- **Customer.viewOrderHistory(int customerId)**: Shows customer orders - O(log C + M).
- **Customer.addReviewToProduct(...)**: Adds review - O(log C + log P + log R).
- **Customer.printCustomerReviews(int customerId)**: Shows customer reviews - O(log C + R).

### Order Operations (O(log n) with AVL Tree)

- **Order.addOrder(Order o)**: Adds order to both AVL trees - O(log n).
- **Order.searchById(int id)**: Finds order by ID using AVL Tree - O(log n).
- **Order.searchByIdLinear(int id)**: Phase I linear search - O(M) (kept for performance comparison).
- **Order.cancelOrder(int id)**: Cancels order and restores stock - O(log M + L*log P).
- **Order.updateOrderStatus(int id, String status)**: Updates status - O(log n).
- **Order.printAll()**: Prints all orders sorted by ID - O(M*L).
- **Order.printOrdersBetween(start, end)**: Date range query using AVL - O(log n + k).
- **Order.getOrdersByCustomer(int customerId)**: Gets customer orders - O(M).
- **Order.getOrdersBetweenDates(start, end)**: Returns orders in date range - O(log n + k).

### Review Operations (O(log n) with AVL Tree)

- **Review.addReview(Review r)**: Adds review - O(log n).
- **Review.searchById(int id)**: Finds review by ID using AVL Tree - O(log n).
- **Review.editReview(int reviewId, double newRating, String newComment)**: Edits review - O(log n).
- **Review.getReviewsByCustomer(int customerId)**: Gets customer reviews - O(R).
- **Review.getCommonHighRatedProducts(cust1, cust2)**: Common products rated >4.0 - O(R*R_cust + P_common*R_avg).
- **Review.printAll()**: Prints all reviews - O(R).
- **Review.printCustomersWhoReviewedProduct(int productId)**: Customers sorted by rating - O(log P + R).

---

## 3. Interactive Menu System (27 Options)

The SimpleECommerceTest provides an interactive menu with the following operations:

### View Data (Options 1-4)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 1 | View all products (sorted by ID) | Product.printAll() | O(P) |
| 2 | View all customers | Customer.printAll() | O(C) |
| 3 | View all orders (sorted by ID) | Order.printAll() | O(M*L) |
| 4 | View all reviews | Review.printAll() | O(R) |

### Product Operations (Options 5-8)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 5 | Add product | Product.addProduct() | O(log n) |
| 6 | Search product by ID | Product.searchById() | O(log n) |
| 7 | Update product | Product.updateProduct() | O(log n) |
| 8 | View product average rating | Product.getAverageRating() | O(log P + R_avg) |

### Customer Operations (Options 9-10)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 9 | Add customer | Customer.addCustomer() | O(log n) |
| 10 | View customer order history | Customer.viewOrderHistory() | O(log C + M) |

### Order Operations (Options 11-14)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 11 | Search order by ID | Order.searchById() | O(log n) |
| 12 | Place an order | Customer.placeOrder() | O(log C + log P + log M) |
| 13 | Cancel an order | Order.cancelOrder() | O(log M + L*log P) |
| 14 | Update order status | Order.updateOrderStatus() | O(log n) |

### Phase II: Advanced Queries (Options 15-20)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 15 | Products in price range | Product.printProductsInPriceRange() | O(P) |
| 16 | Orders between dates | Order.printOrdersBetween() | O(log n + k) |
| 17 | Customers sorted alphabetically | Customer.printAllSortedAlphabetically() | O(C) |
| 18 | Top 3 rated products | Product.topThreeProducts() | O(P² + P*R_avg) |
| 19 | Top 3 most reviewed products | Product.topThreeMostReviewedProducts() | O(P² + P) |
| 20 | Customers who reviewed a product | Review.printCustomersWhoReviewedProduct() | O(log P + R) |

### Reviews (Options 21-24)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 21 | Add review to product | Customer.addReviewToProduct() | O(log C + log P + log R) |
| 22 | Edit review | Review.editReview() | O(log n) |
| 23 | View customer reviews | Customer.printCustomerReviews() | O(log C + R) |
| 24 | Common high-rated products | Review.getCommonHighRatedProducts() | O(R*R_cust + P*R_avg) |

### Other (Options 25-27)
| Option | Description | Method | Complexity |
|--------|-------------|--------|------------|
| 25 | View out of stock products | Product.printOutOfStock() | O(P) |
| 26 | Performance comparison | performanceComparison() | Benchmarks Phase I vs II |
| 27 | Exit | System exit | O(1) |

---

## 4. Project Structure

```
CSC212_Project/
├── src/
│   └── projectFiles/
│       ├── AVLNode.java          # Phase II: AVL Tree node class
│       ├── AVLTree.java          # Phase II: Self-balancing BST implementation
│       ├── Customer.java         # Customer entity with AVL + LinkedList storage
│       ├── LinkedList.java       # Phase I: Custom LinkedList implementation
│       ├── List.java             # List interface
│       ├── Node.java             # LinkedList node class
│       ├── Order.java            # Order entity with AVL + LinkedList storage
│       ├── Product.java          # Product entity with AVL + LinkedList storage
│       ├── Review.java           # Review entity with AVL + LinkedList storage
│       ├── SimpleCSVReader.java  # CSV data loader
│       └── SimpleECommerceTest.java  # Main application with interactive menu
├── customers.csv                 # Sample customer data
├── products.csv                  # Sample product data
├── orders.csv                    # Sample order data
├── reviews.csv                   # Sample review data
├── ClassDiagram.puml             # UML class diagram (Mermaid format)
└── README.md                     # This file
```


---

## 5. Algorithm Complexity Analysis

### Variable Definitions
| Variable | Description |
|----------|-------------|
| P | Total number of products |
| C | Total number of customers |
| M | Total number of orders |
| R | Total number of reviews |
| R_avg | Average reviews per product (R/P) |
| R_cust | Average reviews per customer (R/C) |
| L | Number of products in a single order |
| k | Number of results in a range query |

### Phase II vs Phase I Comparison

| Operation | Phase I (LinkedList) | Phase II (AVL Tree) |
|-----------|---------------------|---------------------|
| Insert Product | O(P) | **O(log P)** |
| Search Product by ID | O(P) | **O(log P)** |
| Update Product | O(P) | **O(log P)** |
| Delete Product | O(P) | **O(log P)** |
| Insert Customer | O(C) | **O(log C)** |
| Search Customer by ID | O(C) | **O(log C)** |
| Search Customer by Name | O(C) | **O(log C)** |
| Insert Order | O(M) | **O(log M)** |
| Search Order by ID | O(M) | **O(log M)** |
| Date Range Query | O(M) | **O(log M + k)** |
| Sorted Traversal | O(n log n) | **O(n)** (in-order) |

### AVL Tree Operations (Phase II)

| Operation | Time | Space | Description |
|-----------|------|-------|-------------|
| insert(key, data) | O(log n) | O(1) | Self-balancing insertion |
| search(key) | O(log n) | O(1) | Binary search |
| delete(key) | O(log n) | O(1) | Self-balancing deletion |
| rangeQuery(min, max) | O(log n + k) | O(k) | k = results in range |
| inOrderTraversal() | O(n) | O(n) | Returns sorted elements |
| size(), isEmpty() | O(1) | O(1) | Maintained counter |

### LinkedList Operations (Phase I)

| Operation | Time | Space | Description |
|-----------|------|-------|-------------|
| insert(data) | O(1) | O(1) | Insert at current position |
| remove() | O(1) | O(1) | Remove at current |
| retrieve() | O(1) | O(1) | Get current element |
| findFirst() | O(1) | O(1) | Move to head |
| findNext() | O(1) | O(1) | Move to next |
| empty(), last() | O(1) | O(1) | Boundary checks |

### Detailed Method Complexity (Phase II)

#### Product Class Methods

| Method | Time | Space | Phase II Improvement |
|--------|------|-------|---------------------|
| addProduct(p) | O(log P) | O(1) | AVL insert vs O(P) linear |
| searchById(id) | O(log P) | O(1) | AVL search vs O(P) linear |
| searchByIdLinear(id) | O(P) | O(1) | Phase I comparison |
| searchByName(name) | O(P) | O(1) | No AVL index for name search |
| updateProduct(id, price, stock) | O(log P) | O(1) | AVL search + O(1) update |
| removeProduct(id) | O(log P) | O(1) | AVL delete |
| printAll() | O(P) | O(P) | In-order traversal |
| printOutOfStock() | O(P) | O(1) | Traverse all |
| topThreeProducts() | O(P² + P*R_avg) | O(P) | Bubble sort |
| getAverageRating() | O(R_avg) | O(1) | Instance reviews |
| getProductsInPriceRange(min, max) | O(P) | O(k) | Traversal filter |
| topThreeMostReviewedProducts() | O(P² + P) | O(P) | Bubble sort |

#### Customer Class Methods

| Method | Time | Space | Phase II Improvement |
|--------|------|-------|---------------------|
| addCustomer(c) | O(log C) | O(1) | Dual AVL insert |
| searchById(id) | O(log C) | O(1) | AVL search |
| searchByIdLinear(id) | O(C) | O(1) | Phase I comparison |
| searchByName(name) | O(log C) | O(1) | AVL search by name |
| printAll() | O(C) | O(C) | In-order traversal |
| printAllSortedAlphabetically() | O(C) | O(C) | Name tree traversal |
| updateCustomer(id, name, email) | O(log C) | O(1) | AVL search + update |
| placeOrder(...) | O(log C + log P + log M + L) | O(L) | AVL lookups |
| viewOrderHistory(custId) | O(log C + M) | O(1) | AVL + traverse orders |
| addReviewToProduct(...) | O(log C + log P + log R) | O(1) | All AVL lookups |
| printCustomerReviews(custId) | O(log C + R) | O(R_cust) | AVL + review filter |

#### Order Class Methods

| Method | Time | Space | Phase II Improvement |
|--------|------|-------|---------------------|
| addOrder(o) | O(log M) | O(1) | Dual AVL insert |
| searchById(id) | O(log M) | O(1) | AVL search |
| searchByIdLinear(id) | O(M) | O(1) | Phase I comparison |
| updateOrderStatus(id, status) | O(log M) | O(1) | AVL search + update |
| cancelOrder(id) | O(log M + L² + L*log P) | O(L) | AVL for lookups |
| printAll() | O(M*L) | O(M) | In-order traversal |
| printOrdersBetween(start, end) | O(log M + k) | O(1) | AVL range query |
| getOrdersByCustomer(custId) | O(M) | O(k) | Filter all orders |
| getOrdersBetweenDates(start, end) | O(log M + k) | O(k) | AVL range query |

#### Review Class Methods

| Method | Time | Space | Phase II Improvement |
|--------|------|-------|---------------------|
| addReview(r) | O(log R) | O(1) | AVL insert |
| searchById(id) | O(log R) | O(1) | AVL search |
| editReview(id, rating, comment) | O(log R) | O(1) | AVL search + update |
| getReviewsByCustomer(custId) | O(R) | O(R_cust) | Traverse + filter |
| getCommonHighRatedProducts(...) | O(R*R_cust + P*R_avg) | O(R_cust) | Complex query |
| printAll() | O(R) | O(R) | In-order traversal |
| printCustomersWhoReviewedProduct(pId) | O(log P + R) | O(R_prod) | AVL + filter + sort |

#### AVL Tree Class Methods

| Method | Time | Space | Description |
|--------|------|-------|-------------|
| insert(key, data) | O(log n) | O(1) | Self-balancing BST insert |
| search(key) | O(log n) | O(1) | Binary search |
| delete(key) | O(log n) | O(1) | Self-balancing delete |
| contains(key) | O(log n) | O(1) | Existence check |
| rangeQuery(min, max) | O(log n + k) | O(k) | Range retrieval |
| inOrderTraversal() | O(n) | O(n) | Sorted output |
| getMin(), getMax() | O(log n) | O(1) | Tree extremes |
| size(), isEmpty() | O(1) | O(1) | Maintained counter |
| update(key, data) | O(log n) | O(1) | Find and update |

#### SimpleCSVReader Class Methods (Startup Loading)

| Method | Time (Phase I) | Time (Phase II) | Space |
|--------|----------------|-----------------|-------|
| loadProducts(file) | O(P²) | O(P log P) | O(1)/line |
| loadCustomers(file) | O(C²) | O(C log C) | O(1)/line |
| loadOrders(file) | O(M*(C+P*L+M)) | O(M*(log C + L*log P + log M)) | O(L)/order |
| loadReviews(file) | O(R*(P+C+R)) | O(R*(log P + log C + log R)) | O(1)/line |

---

## 6. Space Complexity Summary

| Data Structure | Phase I | Phase II |
|---------------|---------|----------|
| Products | O(P) LinkedList | O(P) AVL + O(P) LinkedList |
| Customers | O(C) LinkedList | O(C) AVL by ID + O(C) AVL by Name + O(C) LinkedList |
| Orders | O(M) LinkedList | O(M) AVL by ID + O(M) AVL by Date + O(M) LinkedList |
| Reviews | O(R) LinkedList | O(R) AVL + O(R) LinkedList |
| **Total** | **O(P + C + M + R)** | **O(P + C + M + R)** (constant factor increase) |

Note: Phase II maintains both AVL Trees and LinkedLists for backward compatibility. In production, LinkedLists could be removed to reduce memory usage.

---

## 7. CSV File Formats

### customers.csv
```csv
customerId,name,email
201,Alice Johnson,alice.johnson@example.com
```

### products.csv
```csv
productId,name,price,stock
101,Laptop Pro 15,1499.99,20
```

### orders.csv
```csv
orderId,customerId,productIds,totalPrice,orderDate,status
301,201,"101;102",1529.98,2025-01-12,Pending
```

### reviews.csv
```csv
reviewId,productId,customerId,rating,comment
401,101,201,5,"Excellent laptop, super fast!"
```

---

## 8. Performance Comparison Feature

The application includes a built-in performance comparison (Menu Option 26) that benchmarks Phase I (LinkedList O(n)) operations against Phase II (AVL Tree O(log n)) operations in real-time.

Example output:
```
=== PERFORMANCE COMPARISON ===
Phase I (LinkedList) vs Phase II (AVL Tree)

--- Product Search (ID: 125) ---
Phase I (LinkedList O(n)): 45230 ns
Phase II (AVL Tree O(log n)): 1250 ns
AVL Tree is 36.18x faster
```

---

## Authors

CSC212 Data Structures Project - Phase II Implementation with AVL Trees
