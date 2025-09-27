package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Enumeration of price range buckets derived from a final rounded price.
 */
public enum PriceRange {
    LOW("Low"), MEDIUM("Medium"), HIGH("High"), PREMIUM("Premium");

    private final String label;

    PriceRange(String label) {
        this.label = label;
    }

    /**
     * @return the display label used in the CSV
     */
    public String display() {
        return label;
    }

    /**
     * Computes the range bucket from a final, rounded price.
     * 
     * @param price final rounded price
     * @return the PriceRange bucket
     */
    public static PriceRange from(BigDecimal price) {
        if (price.compareTo(new BigDecimal("10.00")) <= 0)
            return LOW;
        if (price.compareTo(new BigDecimal("100.00")) <= 0)
            return MEDIUM;
        if (price.compareTo(new BigDecimal("500.00")) <= 0)
            return HIGH;
        return PREMIUM;
    }
}