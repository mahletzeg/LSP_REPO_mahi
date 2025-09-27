package org.howard.edu.lsp.assignment3;

import java.util.List;

/**
 * Abstraction for writing domain objects to a sink (CSV, DB, API, etc.).
 * 
 * @param <T> the domain type to load
 */
public interface Loader<T> {
    /**
     * Writes the given items to the underlying sink.
     * 
     * @param items transformed items to write
     * @throws WriteException if an I/O error occurs
     */
    void load(List<T> items) throws WriteException;

    /** Thrown when writing fails. */
    class WriteException extends Exception {
        public WriteException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public WriteException(String msg) {
            super(msg);
        }
    }
}