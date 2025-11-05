package projectFiles;

public class Product {
    int productId;
    String name;
    double price;
    int stock;
    LinkedList<Review> reviews;


    static LinkedList<Product> products = new LinkedList<Product>();

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new LinkedList<Review>();
    }

    public void addReview(Review review) {
        if (reviews.empty()) {
            reviews.insert(review);
        } else {
            reviews.findFirst();
            while (!reviews.last()) {
                reviews.findNext();
            }
            reviews.insert(review);
        }
    }

    public double getAverageRating() {
        if (reviews.empty())
            return 0;

        double sum = 0;
        int count = 0;

        reviews.findFirst();
        while (reviews.retrieve() != null) {
            sum += reviews.retrieve().rating;
            count++;
            if (reviews.last()) break;
            reviews.findNext();
        }

        return (count == 0) ? 0 : sum / count;
    }

    public String toString() {
        return "[" + productId + "] " + name + " - $" + price + " | Stock: " + stock;
    }


    public static void addProduct(Product p) {
        if (products.empty()) {
            products.insert(p);
        } else {
            products.findFirst();
            while (!products.last()) {
                products.findNext();
            }
            products.insert(p);
        }
    }

    public static Product searchById(int id) {
        if (products.empty())
            return null;

        products.findFirst();
        while (products.retrieve() != null) {
            Product p = products.retrieve();
            if (p.productId == id)
                return p;
            if (products.last()) break;
            products.findNext();
        }
        return null;
    }

    public static void updateProduct(int id, double newPrice, int newStock) {
        Product p = searchById(id);
        if (p != null) {
            p.price = newPrice;
            p.stock = newStock;
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void removeProduct(int id) {
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }

        products.findFirst();
        while (products.retrieve() != null) {
            Product p = products.retrieve();
            if (p.productId == id) {
                products.remove();
                System.out.println("Product removed successfully.");
                return;
            }
            if (products.last()) break;
            products.findNext();
        }
        
        System.out.println("Product not found.");
    }

    public static void printAll() {
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }

        products.findFirst();
        while (products.retrieve() != null) {
            System.out.println(products.retrieve());
            if (products.last()) break;
            products.findNext();
        }
    }

    public static void printOutOfStock() {
        System.out.println("Out of Stock Products:");
        products.findFirst();
        while (products.retrieve() != null) {
            Product p = products.retrieve();
            if (p.stock <= 0)
                System.out.println(p.name);
            if (products.last()) break;
            products.findNext();
        }
    }

    public static void topThreeProducts() {
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }

        // Count products to determine array size
        int count = 0;
        products.findFirst();
        while (products.retrieve() != null) {
            count++;
            if (products.last()) break;
            products.findNext();
        }

        // Create arrays to store products and their ratings
        Product[] productArray = new Product[count];
        double[] ratingArray = new double[count];

        // Collect all products and their ratings
        int i = 0;
        products.findFirst();
        while (products.retrieve() != null) {
            Product p = products.retrieve();
            productArray[i] = p;
            ratingArray[i] = p.getAverageRating();
            i++;
            if (products.last()) break;
            products.findNext();
        }

        // Bubble sort
        for (int k = 0; k < count - 1; k++) {
            for (int j = 0; j < count - 1 - k; j++) {
                if (ratingArray[j] < ratingArray[j + 1]) {
                    // Swap ratings
                    double tempRating = ratingArray[j];
                    ratingArray[j] = ratingArray[j + 1];
                    ratingArray[j + 1] = tempRating;
                    // Swap products
                    Product tempProduct = productArray[j];
                    productArray[j] = productArray[j + 1];
                    productArray[j + 1] = tempProduct;
                }
            }
        }

        // Display top 3 products
        System.out.println("Top 3 Products by Rating:");
        for (int l = 0; l < 3 && l < count; l++) {
            System.out.println((l + 1) + ". " + productArray[l].name + " - Rating: " + ratingArray[l]);
        }
    }
}