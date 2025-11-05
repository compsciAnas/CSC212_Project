# Time Complexity Analysis - E-Commerce System

## Variable Definitions

- **P**: Total number of products in the system
- **C**: Total number of customers in the system  
- **M**: Total number of orders in the system
- **R**: Total number of reviews in the system
- **R_avg**: Average number of reviews per product (R / P)
- **R_cust**: Average number of reviews per customer (R / C)
- **L**: Number of items (products) in a single order

## Data Structure: LinkedList<T>

All operations are based on our custom LinkedList implementation:

| Operation | Time Complexity | Description |
|-----------|----------------|-------------|
| `add(data)` (to tail) | O(1) | Insert at current position |
| `remove(data)` | O(n) | Must search the list |
| `size()`, `isEmpty()` | O(1) | Direct property access |
| `iterator()`, `forEach` | O(n) | Iterate through all elements |

## Core Class Operations

### Product Operations

| Method | Time Complexity | Space Complexity | Analysis |
|--------|----------------|------------------|----------|
| `addProduct(p)` | O(P) | O(1) | Must traverse to end of products list |
| `searchById(id)` | O(P) | O(1) | Linear search through all products |
| `updateProduct(id, price, stock)` | O(P) | O(1) | O(P) to find product + O(1) to update |
| `removeProduct(id)` | O(P) | O(1) | O(P) to find product + O(P) to remove |
| `printAll()` | O(P) | O(1) | Iterate through all products |
| `printOutOfStock()` | O(P) | O(1) | Iterate through all products |
| `addReview(review)` | O(R_avg) | O(1) | Traverse to end of product's reviews list |
| `getAverageRating()` | O(R_avg) | O(1) | Iterate through product's reviews |

### Customer Operations

| Method | Time Complexity | Space Complexity | Analysis |
|--------|----------------|------------------|----------|
| `addCustomer(c)` | O(C) | O(1) | Must traverse to end of customers list |
| `searchById(id)` | O(C) | O(1) | Linear search through all customers |
| `updateCustomer(id, name, email)` | O(C) | O(1) | O(C) to find customer + O(1) to update |
| `placeOrder(custId, prodId, qty, date)` | O(C + P + M) | O(L) | O(C) to find customer + O(P) to find product + O(M) to generate new order ID + O(L) to create product list |
| `viewOrderHistory(custId)` | O(C + M) | O(1) | O(C) to find customer + O(M) to iterate all orders |
| `addReviewToProduct(custId, prodId, rating, comment)` | O(C + P + R) | O(1) | O(C) to find customer + O(P) to find product + O(R) to generate review ID + O(R_avg) to add review |
| `printCustomerReviews(custId)` | O(C + R) | O(R_cust) | O(C) to find customer + O(R) to filter reviews + O(R_cust) space for results |

### Order Operations

| Method | Time Complexity | Space Complexity | Analysis |
|--------|----------------|------------------|----------|
| `addOrder(o)` | O(M) | O(1) | Must traverse to end of orders list |
| `searchById(id)` | O(M) | O(1) | Linear search through all orders |
| `updateOrderStatus(id, status)` | O(M) | O(1) | O(M) to find order + O(1) to update |
| `cancelOrder(id)` | O(M + L*P) | O(L) | O(M) to find order + O(L*P) to restore stock for each product + O(L) space for tracking products |
| `printAll()` | O(M) | O(1) | Iterate through all orders |
| `printOrdersBetween(startDate, endDate)` | O(M) | O(1) | Iterate through all orders with date comparison |

### Review Operations

| Method | Time Complexity | Space Complexity | Analysis |
|--------|----------------|------------------|----------|
| `addReview(r)` | O(R) | O(1) | Must traverse to end of reviews list |
| `editReview(id, rating, comment)` | O(R) | O(1) | Linear search through all reviews |
| `getReviewsByCustomer(custId)` | O(R) | O(R_cust) | Iterate through all reviews + O(R_cust) space for results |
| `printAll()` | O(R) | O(1) | Iterate through all reviews |

## Business Query Analysis

### 1. Average Rating for All Products

**Method**: `Product.getAverageRating()` (per product)

**Time Complexity**: O(R_avg) per product, O(P * R_avg) = O(R) for all products

**Space Complexity**: O(1) per product

**Analysis**: The method iterates through that product's R_avg reviews to calculate the average. To get ratings for all products, this is called P times.

---

### 2. Top 3 Products by Rating

**Method**: `Product.topThreeProducts()`

**Time Complexity**: O(P² + P*R_avg)

**Space Complexity**: O(P)

**Analysis**: 
- Count products: O(P)
- Create arrays: O(P) space
- Collect products and ratings: O(P*R_avg) - iterates P products, calls getAverageRating() for each
- Bubble sort: O(P²) with nested loops
- Display top 3: O(1)
- **Dominant factor**: O(P²) from bubble sort

---

### 3. All Orders Between Two Dates

**Method**: `Order.printOrdersBetween(startDate, endDate)`

**Time Complexity**: O(M)

**Space Complexity**: O(1)

**Analysis**: The method iterates through all M orders exactly once, performing an O(1) string comparison for each order's date.

---

### 4. Common High-Rated Products (rating > 4.0)

**Method**: `Review.getCommonHighRatedProducts(custId1, custId2)`

**Time Complexity**: O(R + P*R_avg)

**Space Complexity**: O(R_cust)

**Analysis**:
1. **Get products reviewed by customer 1**: O(R) - iterate all reviews, build list of product IDs
2. **Get products reviewed by customer 2**: O(R) - iterate all reviews, build list of product IDs  
3. **Find intersection**: O(R_cust²) - for each product in customer 1's list, check if in customer 2's list
4. **Filter by rating > 4.0**: O(R_cust * R_avg) worst case - for each common product, calculate average rating
5. **Overall**: O(R + R_cust² + R_cust*R_avg) ≈ **O(R + P*R_avg)** in typical cases

**Space Requirements**:
- Product IDs for customer 1: O(R_cust)
- Product IDs for customer 2: O(R_cust)
- Result list: O(R_cust)
- **Total**: O(R_cust)

---

### 5. Extract Reviews from Specific Customer

**Method**: `Review.getReviewsByCustomer(custId)` 

**Time Complexity**: O(R)

**Space Complexity**: O(R_cust)

**Analysis**: The method iterates through all R reviews in the system once, filtering for reviews by the specified customer. Results stored in a new LinkedList require O(R_cust) space.

---

## CSV Loading Operations

| Method | Time Complexity | Analysis |
|--------|----------------|----------|
| `loadProducts(file)` | O(P²) | O(P) lines to read * O(P) to add each product to list |
| `loadCustomers(file)` | O(C²) | O(C) lines to read * O(C) to add each customer to list |
| `loadOrders(file)` | O(M * (L*P + M)) | For each of M orders: O(M) for ID generation + O(L*P) to find L products |
| `loadReviews(file)` | O(R * (P + C + R_avg)) | For each of R reviews: O(P) to find product + O(C) to find customer + O(R_avg) to add to product |

## Summary of Key Requirements

| Requirement | Implementation | Time Complexity |
|-------------|---------------|-----------------|
| Read CSV files | `SimpleCSVReader.loadX()` | O(P²), O(C²), O(M*(L*P+M)), O(R*(P+C+R_avg)) |
| Add/remove/update products | `Product.addProduct()`, `removeProduct()`, `updateProduct()` | O(P) |
| Search product by ID/name | `Product.searchById()` | O(P) linear search |
| Track out-of-stock | `Product.printOutOfStock()` | O(P) |
| Register customer | `Customer.addCustomer()` | O(C) |
| Place order | `Customer.placeOrder()` | O(C + P + M) |
| View order history | `Customer.viewOrderHistory()` | O(C + M) |
| Create/cancel order | `Order.addOrder()`, `cancelOrder()` | O(M), O(M + L*P) |
| Update order status | `Order.updateOrderStatus()` | O(M) |
| Add/edit review | `Customer.addReviewToProduct()`, `Review.editReview()` | O(C + P + R), O(R) |
| Get average rating | `Product.getAverageRating()` | O(R_avg) |
| **Top 3 products** | `Product.topThreeProducts()` | **O(P² + P*R_avg)** |
| **Orders between dates** | `Order.printOrdersBetween()` | **O(M)** |
| **Extract customer reviews** | `Review.getReviewsByCustomer()` | **O(R)** |
| **Common high-rated products** | `Review.getCommonHighRatedProducts()` | **O(R + P*R_avg)** |

## Optimization Notes

1. **Linear Search**: All search operations use linear search O(n) as required by using only LinkedList. Could be optimized with hash tables (not allowed) or binary search trees.

2. **Bubble Sort**: Top 3 products uses O(P²) bubble sort. Could be optimized with quicksort or heapsort to O(P log P), but bubble sort is simple and adequate for small datasets.

3. **List Traversal**: Many operations require traversing to the end of the list to insert. This is O(n) due to LinkedList implementation without a tail pointer optimization.

4. **No Java Collections**: All data structures are custom-implemented as required by project specifications. This results in higher complexity for some operations compared to using HashMap or TreeMap.
