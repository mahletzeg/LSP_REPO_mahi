package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.util.*;

public class ETLPipeline {
    public static void main(String[] args) {
        // File paths
        String inputFilePath = "data/products.csv";
        String outputFilePath = "data/transformed_products.csv";

        // Start the ETL process
        List<Product> products = extract(inputFilePath);
        if (products != null) {
            List<Product> transformedProducts = transform(products);
            load(transformedProducts, outputFilePath);
        }
    }

    public static List<Product> extract(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                int productId = Integer.parseInt(columns[0].trim());
                String name = columns[1].trim();
                double price = Double.parseDouble(columns[2].trim());
                String category = columns[3].trim();
                products.add(new Product(productId, name, price, category));
            }
            System.out.println("Rows read: " + products.size());
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + filePath);
            return null;
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return null;
        }
        return products;
    }

    // Transform data
    public static List<Product> transform(List<Product> products) {
        List<Product> transformedProducts = new ArrayList<>();
        for (Product product : products) {
            // Step 1: Convert Name to UPPERCASE
            product.setName(product.getName().toUpperCase());

            // Step 2: Apply 10% discount for Electronics
            if ("Electronics".equalsIgnoreCase(product.getCategory())) {
                double discountedPrice = product.getPrice() * 0.9;
                product.setPrice(roundPrice(discountedPrice));

                // Step 3: Check if price is over $500. If yes, categorize as Premium
                // Electronics
                if (product.getPrice() > 500) {
                    product.setCategory("Premium Electronics");
                }
            }

            // Step 4: Add Price Range
            product.setPriceRange(determinePriceRange(product.getPrice()));
            transformedProducts.add(product);
        }
        System.out.println("Rows transformed: " + transformedProducts.size());
        return transformedProducts;
    }

    // Helper method for rounding price to 2 decimal places (round half up)
    public static double roundPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }

    // Helper method to determine price range
    public static String determinePriceRange(double price) {
        if (price <= 10.00) {
            return "Low";
        } else if (price <= 100.00) {
            return "Medium";
        } else if (price <= 500.00) {
            return "High";
        } else {
            return "Premium";
        }
    }

    // Load transformed data into a new CSV
    public static void load(List<Product> products, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Write header
            writer.write("ProductID,Name,Price,Category,PriceRange");
            writer.newLine();

            // Write product data
            for (Product product : products) {
                writer.write(product.getProductId() + ","
                        + product.getName() + ","
                        + product.getPrice() + ","
                        + product.getCategory() + ","
                        + product.getPriceRange());
                writer.newLine();
            }
            System.out.println("Rows loaded: " + products.size());
            System.out.println("Output written to: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error writing the output file: " + e.getMessage());
        }
    }

}
