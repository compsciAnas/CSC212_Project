package projectFiles;
public class Review {
    int reviewId;
    Product product;
    Customer customer;
    String comment;
    double rating;

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
}