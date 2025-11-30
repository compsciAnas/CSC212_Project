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
    
    // Phase II: Secondary AVL Tree keyed by price for O(log n + k) range queries
    // Since multiple products can have the same price, we store a LinkedList of products per price
    static AVLTree<Double, LinkedList<Product>> productTreeByPrice = new AVLTree<Double, LinkedList<Product>>();
    
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
        
        // Insert into secondary AVL Tree keyed by price - O(log n)
        LinkedList<Product> productsAtPrice = productTreeByPrice.search(p.price);
        if (productsAtPrice == null) {
            productsAtPrice = new LinkedList<Product>();
            productsAtPrice.insert(p);
            productTreeByPrice.insert(p.price, productsAtPrice);
        } else {
            // Add to existing list at this price
            if (productsAtPrice.empty()) {
                productsAtPrice.insert(p);
            } else {
                productsAtPrice.findFirst();
                while (!productsAtPrice.last()) {
                    productsAtPrice.findNext();
                }
                productsAtPrice.insert(p);
            }
        }
        
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
            double oldPrice = p.price;
            
            // If price changed, update the productTreeByPrice
            if (oldPrice != newPrice) {
                // Remove from old price list
                LinkedList<Product> oldPriceList = productTreeByPrice.search(oldPrice);
                if (oldPriceList != null && !oldPriceList.empty()) {
                    oldPriceList.findFirst();
                    while (oldPriceList.retrieve() != null) {
                        if (oldPriceList.retrieve().productId == id) {
                            oldPriceList.remove();
                            break;
                        }
                        if (oldPriceList.last()) break;
                        oldPriceList.findNext();
                    }
                    // If list is now empty, remove the price entry from tree
                    if (oldPriceList.empty()) {
                        productTreeByPrice.delete(oldPrice);
                    }
                }
                
                // Update the product's price
                p.price = newPrice;
                
                // Add to new price list
                LinkedList<Product> newPriceList = productTreeByPrice.search(newPrice);
                if (newPriceList == null) {
                    newPriceList = new LinkedList<Product>();
                    newPriceList.insert(p);
                    productTreeByPrice.insert(newPrice, newPriceList);
                } else {
                    if (newPriceList.empty()) {
                        newPriceList.insert(p);
                    } else {
                        newPriceList.findFirst();
                        while (!newPriceList.last()) {
                            newPriceList.findNext();
                        }
                        newPriceList.insert(p);
                    }
                }
            } else {
                p.price = newPrice;
            }
            
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
            double price = p.price;
            
            // Remove from AVL Tree
            productTree.delete(id);
            
            // Remove from productTreeByPrice
            LinkedList<Product> priceList = productTreeByPrice.search(price);
            if (priceList != null && !priceList.empty()) {
                priceList.findFirst();
                while (priceList.retrieve() != null) {
                    if (priceList.retrieve().productId == id) {
                        priceList.remove();
                        break;
                    }
                    if (priceList.last()) break;
                    priceList.findNext();
                }
                // If list is now empty, remove the price entry from tree
                if (priceList.empty()) {
                    productTreeByPrice.delete(price);
                }
            }
            
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
     * Uses secondary AVL tree keyed by price for O(log n + k) time complexity,
     * where k is the number of products in the price range.
     */
    public static LinkedList<Product> getProductsInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> result = new LinkedList<Product>();
        
        if (productTreeByPrice.isEmpty()) {
            return result;
        }

        // Use range query on the secondary AVL tree keyed by price - O(log n + k)
        LinkedList<LinkedList<Product>> priceLists = productTreeByPrice.rangeQuery(minPrice, maxPrice);
        
        if (priceLists.empty()) {
            return result;
        }
        
        // Flatten the lists of products at each price point into a single result list
        priceLists.findFirst();
        while (priceLists.retrieve() != null) {
            LinkedList<Product> productsAtPrice = priceLists.retrieve();
            if (!productsAtPrice.empty()) {
                productsAtPrice.findFirst();
                while (productsAtPrice.retrieve() != null) {
                    Product p = productsAtPrice.retrieve();
                    if (result.empty()) {
                        result.insert(p);
                    } else {
                        result.findFirst();
                        while (!result.last()) {
                            result.findNext();
                        }
                        result.insert(p);
                    }
                    if (productsAtPrice.last()) break;
                    productsAtPrice.findNext();
                }
            }
            if (priceLists.last()) break;
            priceLists.findNext();
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