package org.howard.edu.lsp.assignment3;

import java.util.List;

/**
 * Abstraction for reading domain objects from a source (CSV, DB, API, etc.).
 * 
 * @param <T> the domain type to extract
 */
public interface Extractor<T> {
    /**
     * Extracts domain objects from the underlying source.
     * 
     * @return list of extracted objects
     * @throws EmptyInputException when the source exists but contains no data rows
     * @throws ReadException       when an IO/parse error occurs or the file is
     *                             missing
     */
    List<T> extract() throws EmptyInputException, ReadException;

    /** Thrown when the input exists but has no data rows. */
    class EmptyInputException extends Exception {
        public EmptyInputException(String msg) {
            super(msg);
        }
    }

    /** Thrown for I/O or parse errors. */
    class ReadException extends Exception {
        public ReadException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public ReadException(String msg) {
            super(msg);
        }
    }
}