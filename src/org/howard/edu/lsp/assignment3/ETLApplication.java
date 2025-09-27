package org.howard.edu.lsp.assignment3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Entry point for the Assignment 3 ETL application.
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Wire together Extractor, Transformer, and Loader components.</li>
 * <li>Run the ETL flow using relative paths.</li>
 * <li>Print a simple run summary.</li>
 * </ul>
 */
public class ETLApplication {
    private static final Path INPUT = Paths.get("data/products.csv");
    private static final Path OUTPUT = Paths.get("data/transformed_products.csv");

    /**
     * Main method to execute the ETL pipeline.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Extractor<Product> extractor = new CsvProductExtractor(INPUT);
        Transformer<Product> transformer = new ProductTransformer();
        Loader<Product> loader = new CsvProductLoader(OUTPUT);

        int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0; // rowsSkipped kept for parity

        try {
            List<Product> items = extractor.extract();
            rowsRead = items.size();

            for (int i = 0; i < items.size(); i++) {
                items.set(i, transformer.apply(items.get(i)));
                rowsTransformed++;
            }

            loader.load(items);
        } catch (Extractor.EmptyInputException e) {
            // Create header-only output to keep behavior stable for an empty input file
            try {
                loader.load(List.of());
            } catch (Exception ignore) {
            }
        } catch (Extractor.ReadException | Loader.WriteException e) {
            System.err.println(e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return;
        }

        System.out.println("ETL Summary");
        System.out.println("-----------");
        System.out.println("Rows read:        " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped:     " + rowsSkipped);
        System.out.println("Output written:   " + OUTPUT);
    }
}