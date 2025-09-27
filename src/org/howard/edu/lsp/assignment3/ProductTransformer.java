package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Applies the Assignment 2 business rules in order:
 * <ol>
 * <li>Name -> UPPERCASE</li>
 * <li>10% discount for original category "Electronics"</li>
 * <li>After discount & rounding, if price &gt; 500 and original category was
 * "Electronics",
 * recategorize to "Premium Electronics"</li>
 * <li>Derive PriceRange from the final rounded price</li>
 * </ol>
 */
public class ProductTransformer implements Transformer<Product> {
    @Override
    public Product apply(Product in) {
        String originalCategory = in.getCategory();

        // 1) uppercase
        String name = in.getName().toUpperCase();

        // 2) discount electronics
        BigDecimal price = in.getPrice();
        if (equalsIgnoreCaseTrim(originalCategory, "Electronics")) {
            price = price.multiply(new BigDecimal("0.90"));
        }

        // Round HALF_UP to 2 decimals
        BigDecimal finalPrice = price.setScale(2, RoundingMode.HALF_UP);

        // 3) recategorize if > 500 and originally Electronics
        String category = originalCategory;
        if (equalsIgnoreCaseTrim(originalCategory, "Electronics")
                && finalPrice.compareTo(new BigDecimal("500.00")) > 0) {
            category = "Premium Electronics";
        }

        // 4) derive price range
        PriceRange range = PriceRange.from(finalPrice);

        return new Product(in.getProductId(), name, finalPrice, category, range);
    }

    private static boolean equalsIgnoreCaseTrim(String a, String b) {
        return a != null && a.trim().equalsIgnoreCase(b);
    }
}