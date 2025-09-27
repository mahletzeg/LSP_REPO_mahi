package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV-based loader that writes transformed rows to
 * data/transformed_products.csv.
 * <p>
 * Always writes a header row first.
 * </p>
 */
public class CsvProductLoader implements Loader<Product> {
    private final Path output;

    /**
     * @param output relative path to the output CSV file
     */
    public CsvProductLoader(Path output) {
        this.output = output;
    }

    @Override
    public void load(List<Product> items) throws WriteException {
        try {
            if (output.getParent() != null)
                Files.createDirectories(output.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();
                for (Product p : items) {
                    bw.write(p.toCsvLine());
                    bw.newLine();
                }
            }
        } catch (IOException ioe) {
            throw new WriteException("ERROR writing output: " + ioe.getMessage(), ioe);
        }
    }
}