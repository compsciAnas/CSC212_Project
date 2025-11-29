package projectFiles;

/**
 * Review class - Phase II implementation with AVL Tree storage
 * Reviews are stored in AVL Tree keyed by reviewId for O(log n) operations.
 */
public class Review {
    int reviewId;
    Product product;
    Customer customer;
    String comment;
    double rating;

    // Phase II: AVL Tree keyed by reviewId for O(log n) operations
    static AVLTree<Integer, Review> reviewTree = new AVLTree<Integer, Review>();
    
    // Phase I: LinkedList maintained for compatibility
    static LinkedList<Review> allReviews = new LinkedList<Review>();
    
    // Track the maximum review ID for generating new IDs
    static int maxReviewId = 0;

    public Review(int reviewId, Product product, Customer customer, String comment, double rating) {
        this.reviewId = reviewId;
        this.product = product;
        this.customer = customer;
        this.comment = comment;
        this.rating = rating;
    }

    public String toString() {
        return "[" + reviewId + "] " +
               customer.name + " reviewed " +
               product.name + " - Rating: " + rating +
               " | Comment: " + comment;
    }

    /**
     * Phase II: Get the next available review ID
     */
    public static int getNextReviewId() {
        return maxReviewId + 1;
    }

    /**
     * Phase II: Add review using AVL Tree - O(log n) time complexity
     */
    public static void addReview(Review r) {
        // Update maxReviewId if necessary
        if (r.reviewId > maxReviewId) {
            maxReviewId = r.reviewId;
        }
        
        // Insert into AVL Tree - O(log n)
        reviewTree.insert(r.reviewId, r);
        
        // Also maintain LinkedList for backward compatibility
        if (allReviews.empty()) {
            allReviews.insert(r);
        } else {
            allReviews.findFirst();
            while (!allReviews.last()) {
                allReviews.findNext();
            }
            allReviews.insert(r);
        }
    }

    /**
     * Phase II: Search review by ID using AVL Tree - O(log n)
     */
    public static Review searchById(int id) {
        return reviewTree.search(id);
    }

    /**
     * Phase II: Edit review using AVL Tree - O(log n) search
     */
    public static void editReview(int reviewId, double newRating, String newComment) {
        Review r = searchById(reviewId); // O(log n)
        if (r != null) {
            r.rating = newRating;
            r.comment = newComment;
            System.out.println("Review updated successfully.");
        } else {
            System.out.println("Review not found.");
        }
    }


    public static LinkedList<Review> getReviewsByCustomer(int customerId) {
        LinkedList<Review> customerReviews = new LinkedList<Review>();
        
        if (reviewTree.isEmpty()) {
            return customerReviews;
        }

        // Traverse all reviews using in-order traversal
        LinkedList<Review> allReviewsSorted = reviewTree.inOrderTraversal();
        allReviewsSorted.findFirst();
        while (allReviewsSorted.retrieve() != null) {
            Review r = allReviewsSorted.retrieve();
            if (r.customer.customerId == customerId) {
                if (customerReviews.empty()) {
                    customerReviews.insert(r);
                } else {
                    customerReviews.findFirst();
                    while (!customerReviews.last()) {
                        customerReviews.findNext();
                    }
                    customerReviews.insert(r);
                }
            }
            if (allReviewsSorted.last()) break;
            allReviewsSorted.findNext();
        }
        
        return customerReviews;
    }


    public static LinkedList<Product> getCommonHighRatedProducts(int customerId1, int customerId2) {
        LinkedList<Product> result = new LinkedList<Product>();

        // Get products reviewed by customer 1
        LinkedList<Integer> products1 = new LinkedList<Integer>();
        LinkedList<Review> allReviewsSorted = reviewTree.inOrderTraversal();
        if (!allReviewsSorted.empty()) {
            allReviewsSorted.findFirst();
            while (allReviewsSorted.retrieve() != null) {
                Review r = allReviewsSorted.retrieve();
                if (r.customer.customerId == customerId1) {
                    int productId = r.product.productId;
                    // Check if product already in list
                    boolean found = false;
                    if (!products1.empty()) {
                        products1.findFirst();
                        while (products1.retrieve() != null) {
                            if (products1.retrieve() == productId) {
                                found = true;
                                break;
                            }
                            if (products1.last()) break;
                            products1.findNext();
                        }
                    }
                    if (!found) {
                        if (products1.empty()) {
                            products1.insert(productId);
                        } else {
                            products1.findFirst();
                            while (!products1.last()) {
                                products1.findNext();
                            }
                            products1.insert(productId);
                        }
                    }
                }
                if (allReviewsSorted.last()) break;
                allReviewsSorted.findNext();
            }
        }

        // Get products reviewed by customer 2
        LinkedList<Integer> products2 = new LinkedList<Integer>();
        allReviewsSorted = reviewTree.inOrderTraversal();
        if (!allReviewsSorted.empty()) {
            allReviewsSorted.findFirst();
            while (allReviewsSorted.retrieve() != null) {
                Review r = allReviewsSorted.retrieve();
                if (r.customer.customerId == customerId2) {
                    int productId = r.product.productId;
                    // Check if product already in list
                    boolean found = false;
                    if (!products2.empty()) {
                        products2.findFirst();
                        while (products2.retrieve() != null) {
                            if (products2.retrieve() == productId) {
                                found = true;
                                break;
                            }
                            if (products2.last()) break;
                            products2.findNext();
                        }
                    }
                    if (!found) {
                        if (products2.empty()) {
                            products2.insert(productId);
                        } else {
                            products2.findFirst();
                            while (!products2.last()) {
                                products2.findNext();
                            }
                            products2.insert(productId);
                        }
                    }
                }
                if (allReviewsSorted.last()) break;
                allReviewsSorted.findNext();
            }
        }

        // Find common products with rating > 4.0
        if (!products1.empty()) {
            products1.findFirst();
            while (products1.retrieve() != null) {
                int productId = products1.retrieve();
                
                // Check if this product is also in products2
                boolean inProducts2 = false;
                if (!products2.empty()) {
                    products2.findFirst();
                    while (products2.retrieve() != null) {
                        if (products2.retrieve() == productId) {
                            inProducts2 = true;
                            break;
                        }
                        if (products2.last()) break;
                        products2.findNext();
                    }
                }

                // If common, check if average rating > 4.0 using AVL search
                if (inProducts2) {
                    Product p = Product.searchById(productId); // O(log n)
                    if (p != null && p.getAverageRating() > 4.0) {
                        if (result.empty()) {
                            result.insert(p);
                        } else {
                            result.findFirst();
                            while (!result.last()) {
                                result.findNext();
                            }
                            result.insert(p);
                        }
                    }
                }

                if (products1.last()) break;
                products1.findNext();
            }
        }

        return result;
    }

    /**
     * Phase II: Print all reviews using in-order traversal
     */
    public static void printAll() {
        if (reviewTree.isEmpty()) {
            System.out.println("No reviews available.");
            return;
        }

        LinkedList<Review> sortedReviews = reviewTree.inOrderTraversal();
        sortedReviews.findFirst();
        while (sortedReviews.retrieve() != null) {
            System.out.println(sortedReviews.retrieve());
            if (sortedReviews.last()) break;
            sortedReviews.findNext();
        }
    }

    /**
     * Phase II: Get all customers who reviewed a specific product (sorted by rating)
     */
    public static void printCustomersWhoReviewedProduct(int productId) {
        Product product = Product.searchById(productId); // O(log n)
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        // Collect all reviews for this product
        LinkedList<Review> productReviews = new LinkedList<Review>();
        LinkedList<Review> allReviewsSorted = reviewTree.inOrderTraversal();
        
        if (allReviewsSorted.empty()) {
            System.out.println("No reviews found for this product.");
            return;
        }

        allReviewsSorted.findFirst();
        while (allReviewsSorted.retrieve() != null) {
            Review r = allReviewsSorted.retrieve();
            if (r.product.productId == productId) {
                if (productReviews.empty()) {
                    productReviews.insert(r);
                } else {
                    productReviews.findFirst();
                    while (!productReviews.last()) {
                        productReviews.findNext();
                    }
                    productReviews.insert(r);
                }
            }
            if (allReviewsSorted.last()) break;
            allReviewsSorted.findNext();
        }

        if (productReviews.empty()) {
            System.out.println("No customers have reviewed this product.");
            return;
        }

        // Count reviews and create arrays for sorting
        int count = 0;
        productReviews.findFirst();
        while (productReviews.retrieve() != null) {
            count++;
            if (productReviews.last()) break;
            productReviews.findNext();
        }

        Review[] reviewArray = new Review[count];
        int i = 0;
        productReviews.findFirst();
        while (productReviews.retrieve() != null) {
            reviewArray[i++] = productReviews.retrieve();
            if (productReviews.last()) break;
            productReviews.findNext();
        }

        // Sort by rating (descending) using insertion sort
        // Note: For a small number of reviews per product (typically < 100),
        // simple sorting algorithms are acceptable. The main Phase II optimization
        // is the O(log n) lookup of the product itself.
        for (int k = 1; k < count; k++) {
            Review key = reviewArray[k];
            int j = k - 1;
            while (j >= 0 && reviewArray[j].rating < key.rating) {
                reviewArray[j + 1] = reviewArray[j];
                j--;
            }
            reviewArray[j + 1] = key;
        }

        System.out.println("Customers who reviewed " + product.name + " (sorted by rating):");
        for (int l = 0; l < count; l++) {
            System.out.println("  " + reviewArray[l].customer.name + " - Rating: " + reviewArray[l].rating);
        }
    }

    /**
     * Phase II: Get the number of reviews - O(1)
     */
    public static int getReviewCount() {
        return reviewTree.size();
    }
}