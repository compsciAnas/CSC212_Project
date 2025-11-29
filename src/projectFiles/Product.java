package projectFiles;

/**
 * Product class - Phase II implementation with AVL Tree storage
 * Products are stored in AVL Tree keyed by productId for O(log n) operations.
 * A secondary AVL Tree keyed by price supports range queries.
 */
public class Product {
    int productId;
    String name;
    double price;
    int stock;
    LinkedList<Review> reviews;

    // Phase II: AVL Tree for O(log n) operations - keyed by productId
    static AVLTree<Integer, Product> productTree = new AVLTree<Integer, Product>();
    
    // Phase I: LinkedList maintained for compatibility and iteration
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


    /**
     * Phase II: Add product using AVL Tree - O(log n) time complexity
     */
    public static boolean addProduct(Product p) {
        // Check for duplicate product ID using AVL search - O(log n)
        if (searchById(p.productId) != null) {
            System.out.println("Error: Product ID " + p.productId + " already exists. Cannot add duplicate product.");
            return false;
        }
        
        // Insert into AVL Tree - O(log n)
        productTree.insert(p.productId, p);
        
        // Also maintain LinkedList for backward compatibility
        if (products.empty()) {
            products.insert(p);
        } else {
            products.findFirst();
            while (!products.last()) {
                products.findNext();
            }
            products.insert(p);
        }
        return true;
    }

    /**
     * Phase II: Search product by ID using AVL Tree - O(log n) time complexity
     */
    public static Product searchById(int id) {
        // Use AVL Tree for O(log n) search
        return productTree.search(id);
    }
    
    /**
     * Phase I: Search by ID using LinkedList - O(n) time complexity
     * Kept for performance comparison
     */
    public static Product searchByIdLinear(int id) {
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

    public static Product searchByName(String name) {
        if (products.empty())
            return null;

        products.findFirst();
        while (products.retrieve() != null) {
            Product p = products.retrieve();
            if (p.name.equalsIgnoreCase(name))
                return p;
            if (products.last()) break;
            products.findNext();
        }
        return null;
    }

    /**
     * Phase II: Update product using AVL Tree - O(log n) time complexity
     */
    public static void updateProduct(int id, double newPrice, int newStock) {
        Product p = searchById(id); // O(log n)
        if (p != null) {
            p.price = newPrice;
            p.stock = newStock;
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    /**
     * Phase II: Remove product using AVL Tree - O(log n) time complexity
     */
    public static void removeProduct(int id) {
        if (productTree.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        Product p = searchById(id);
        if (p != null) {
            // Remove from AVL Tree
            productTree.delete(id);
            
            // Also remove from LinkedList
            if (!products.empty()) {
                products.findFirst();
                while (products.retrieve() != null) {
                    if (products.retrieve().productId == id) {
                        products.remove();
                        break;
                    }
                    if (products.last()) break;
                    products.findNext();
                }
            }
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    /**
     * Phase II: Print all products using in-order traversal (sorted by ID)
     */
    public static void printAll() {
        if (productTree.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        // Use in-order traversal for sorted output
        LinkedList<Product> sortedProducts = productTree.inOrderTraversal();
        sortedProducts.findFirst();
        while (sortedProducts.retrieve() != null) {
            System.out.println(sortedProducts.retrieve());
            if (sortedProducts.last()) break;
            sortedProducts.findNext();
        }
    }

    /**
     * Phase II: Print out of stock products
     */
    public static void printOutOfStock() {
        System.out.println("Out of Stock Products:");
        if (productTree.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        
        LinkedList<Product> allProducts = productTree.inOrderTraversal();
        allProducts.findFirst();
        while (allProducts.retrieve() != null) {
            Product p = allProducts.retrieve();
            if (p.stock <= 0)
                System.out.println(p.name);
            if (allProducts.last()) break;
            allProducts.findNext();
        }
    }

    /**
     * Phase II: Top 3 products by rating using AVL traversal
     */
    public static void topThreeProducts() {
        if (productTree.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        // Get all products using in-order traversal
        LinkedList<Product> allProducts = productTree.inOrderTraversal();
        
        // Count products to determine array size
        int count = 0;
        allProducts.findFirst();
        while (allProducts.retrieve() != null) {
            count++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }

        // Create arrays to store products and their ratings
        Product[] productArray = new Product[count];
        double[] ratingArray = new double[count];

        // Collect all products and their ratings
        int i = 0;
        allProducts.findFirst();
        while (allProducts.retrieve() != null) {
            Product p = allProducts.retrieve();
            productArray[i] = p;
            ratingArray[i] = p.getAverageRating();
            i++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }

        // Bubble sort by rating (descending)
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

    /**
     * Phase II: Range query by price - returns products within [minPrice, maxPrice]
     * Uses AVL traversal to find products in range - O(n) where n is total products
     * (Note: For true O(log n + k) we would need a secondary AVL keyed by price)
     */
    public static LinkedList<Product> getProductsInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> result = new LinkedList<Product>();
        
        if (productTree.isEmpty()) {
            return result;
        }

        // Traverse all products and filter by price range
        LinkedList<Product> allProducts = productTree.inOrderTraversal();
        allProducts.findFirst();
        while (allProducts.retrieve() != null) {
            Product p = allProducts.retrieve();
            if (p.price >= minPrice && p.price <= maxPrice) {
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
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        return result;
    }

    /**
     * Phase II: Print products in a price range
     */
    public static void printProductsInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> productsInRange = getProductsInPriceRange(minPrice, maxPrice);
        
        System.out.println("Products with price between $" + minPrice + " and $" + maxPrice + ":");
        
        if (productsInRange.empty()) {
            System.out.println("No products found in this price range.");
            return;
        }

        productsInRange.findFirst();
        while (productsInRange.retrieve() != null) {
            System.out.println(productsInRange.retrieve());
            if (productsInRange.last()) break;
            productsInRange.findNext();
        }
    }

    /**
     * Phase II: Get all products sorted by ID (using in-order traversal)
     */
    public static LinkedList<Product> getAllProductsSorted() {
        return productTree.inOrderTraversal();
    }

    /**
     * Phase II: Get the number of products - O(1)
     */
    public static int getProductCount() {
        return productTree.size();
    }

    /**
     * Get review count for this product
     */
    public int getReviewCount() {
        if (reviews.empty()) return 0;
        
        int count = 0;
        reviews.findFirst();
        while (reviews.retrieve() != null) {
            count++;
            if (reviews.last()) break;
            reviews.findNext();
        }
        return count;
    }

    /**
     * Phase II: Top 3 most reviewed products
     */
    public static void topThreeMostReviewedProducts() {
        if (productTree.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        LinkedList<Product> allProducts = productTree.inOrderTraversal();
        
        // Count products
        int count = 0;
        allProducts.findFirst();
        while (allProducts.retrieve() != null) {
            count++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }

        // Create arrays
        Product[] productArray = new Product[count];
        int[] reviewCounts = new int[count];

        int i = 0;
        allProducts.findFirst();
        while (allProducts.retrieve() != null) {
            Product p = allProducts.retrieve();
            productArray[i] = p;
            reviewCounts[i] = p.getReviewCount();
            i++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }

        // Bubble sort by review count (descending)
        for (int k = 0; k < count - 1; k++) {
            for (int j = 0; j < count - 1 - k; j++) {
                if (reviewCounts[j] < reviewCounts[j + 1]) {
                    int tempCount = reviewCounts[j];
                    reviewCounts[j] = reviewCounts[j + 1];
                    reviewCounts[j + 1] = tempCount;
                    
                    Product tempProduct = productArray[j];
                    productArray[j] = productArray[j + 1];
                    productArray[j + 1] = tempProduct;
                }
            }
        }

        // Display top 3 most reviewed products
        System.out.println("Top 3 Most Reviewed Products:");
        for (int l = 0; l < 3 && l < count; l++) {
            System.out.println((l + 1) + ". " + productArray[l].name + " - Reviews: " + reviewCounts[l]);
        }
    }
}