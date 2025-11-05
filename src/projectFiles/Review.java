package projectFiles;
/**
 * Project 212 - E-Commerce System
 * Version: 2.0
 * Last Updated: 2025-01-27
 * 
 * Review class representing customer reviews for products.
 */
public class Review {
    int reviewId;
    Product product;
    Customer customer;
    String comment;
    double rating;

    // كل المراجعات تُخزن هنا
    static LinkedList<Review> allReviews = new LinkedList<Review>();

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

    // Add review to system
    public static void addReview(Review r) {
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

    // Edit an existing review
    // Time Complexity: O(R) where R is total number of reviews
    public static void editReview(int reviewId, double newRating, String newComment) {
        if (allReviews.empty()) {
            System.out.println("No reviews found.");
            return;
        }

        allReviews.findFirst();
        while (allReviews.retrieve() != null) {
            Review r = allReviews.retrieve();
            if (r.reviewId == reviewId) {
                r.rating = newRating;
                r.comment = newComment;
                System.out.println("Review updated successfully.");
                return;
            }
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        System.out.println("Review not found.");
    }

    // Get all reviews by a specific customer
    // Time Complexity: O(R) where R is total number of reviews
    public static LinkedList<Review> getReviewsByCustomer(int customerId) {
        LinkedList<Review> customerReviews = new LinkedList<Review>();
        
        if (allReviews.empty()) {
            return customerReviews;
        }

        allReviews.findFirst();
        while (allReviews.retrieve() != null) {
            Review r = allReviews.retrieve();
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
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        
        return customerReviews;
    }

    // Get common products reviewed by two customers with average rating > 4.0
    // Time Complexity: O(R + P*R_avg) where R is total reviews, P is products, R_avg is average reviews per product
    public static LinkedList<Product> getCommonHighRatedProducts(int customerId1, int customerId2) {
        LinkedList<Product> result = new LinkedList<Product>();

        // Get products reviewed by customer 1
        LinkedList<Integer> products1 = new LinkedList<Integer>();
        if (!allReviews.empty()) {
            allReviews.findFirst();
            while (allReviews.retrieve() != null) {
                Review r = allReviews.retrieve();
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
                if (allReviews.last()) break;
                allReviews.findNext();
            }
        }

        // Get products reviewed by customer 2
        LinkedList<Integer> products2 = new LinkedList<Integer>();
        if (!allReviews.empty()) {
            allReviews.findFirst();
            while (allReviews.retrieve() != null) {
                Review r = allReviews.retrieve();
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
                if (allReviews.last()) break;
                allReviews.findNext();
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

                // If common, check if average rating > 4.0
                if (inProducts2) {
                    Product p = Product.searchById(productId);
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

    public static void printAll() {
        if (allReviews.empty()) {
            System.out.println("No reviews available.");
            return;
        }

        allReviews.findFirst();
        while (allReviews.retrieve() != null) {
            System.out.println(allReviews.retrieve());
            if (allReviews.last()) break;
            allReviews.findNext();
        }
    }
}