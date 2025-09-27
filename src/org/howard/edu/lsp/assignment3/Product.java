package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Immutable domain model representing a product row in the ETL process.
 * <p>
 * Fields are final to avoid side-effects across pipeline stages.
 * </p>
 */
public class Product {
    private final int productId;
    private final String name;
    private final BigDecimal price;
    private final String category;
    private final PriceRange priceRange;

    /**
     * Constructs an immutable product.
     * 
     * @param productId  unique product id
     * @param name       product name (may already be transformed)
     * @param price      final rounded price
     * @param category   product category (may be recategorized)
     * @param priceRange derived range bucket based on final price
     */
    public Product(int productId, String name, BigDecimal price, String category, PriceRange priceRange) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.priceRange = priceRange;
    }

    /** @return product id */
    public int getProductId() {
        return productId;
    }

    /** @return name (possibly uppercased) */
    public String getName() {
        return name;
    }

    /** @return final rounded price */
    public BigDecimal getPrice() {
        return price;
    }

    /** @return final category (possibly recategorized) */
    public String getCategory() {
        return category;
    }

    /** @return final derived price range */
    public PriceRange getPriceRange() {
        return priceRange;
    }

    /**
     * Serializes this product in the target CSV column order.
     * 
     * @return CSV line as "ProductID,Name,Price,Category,PriceRange"
     */
    public String toCsvLine() {
        return String.format("%d,%s,%s,%s,%s",
                productId, name, price.toPlainString(), category, priceRange.display());
    }
}