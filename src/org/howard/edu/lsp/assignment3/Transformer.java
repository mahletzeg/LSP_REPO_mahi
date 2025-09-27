package org.howard.edu.lsp.assignment3;

/**
 * Pure function object for transforming a domain object into another of the
 * same type.
 * 
 * @param <T> domain type
 */
public interface Transformer<T> {
    /**
     * Applies a transformation to the input.
     * 
     * @param input input object
     * @return transformed object
     */
    T apply(T input);
}