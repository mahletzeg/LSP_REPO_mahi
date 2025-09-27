package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-based extractor that reads data/products.csv with four columns:
 * ProductID,Name,Price,Category
 * <p>
 * Skips malformed rows; throws if file missing or only header present.
 * </p>
 */
public class CsvProductExtractor implements Extractor<Product> {
    private final Path input;

    /**
     * @param input relative path to the input CSV
     */
    public CsvProductExtractor(Path input) {
        this.input = input;
    }

    @Override
    public List<Product> extract() throws EmptyInputException, ReadException {
        if (!Files.exists(input)) {
            throw new ReadException("ERROR: Input file not found at " + input);
        }
        List<Product> items = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            br.readLine(); // skip header (may be null)
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] c = line.split(",", -1);
                if (c.length < 4)
                    continue;
                try {
                    int id = Integer.parseInt(c[0].trim());
                    String name = c[1].trim();
                    BigDecimal price = new BigDecimal(c[2].trim());
                    String category = c[3].trim();
                    // Preliminary Product; final values will be set by transformer.
                    items.add(new Product(id, name, price, category, PriceRange.from(price)));
                } catch (Exception ignore) {
                    // skip malformed row
                }
            }
        } catch (IOException ioe) {
            throw new ReadException("ERROR reading input: " + ioe.getMessage(), ioe);
        }
        if (items.isEmpty())
            throw new EmptyInputException("Input had no data rows.");
        return items;
    }
}