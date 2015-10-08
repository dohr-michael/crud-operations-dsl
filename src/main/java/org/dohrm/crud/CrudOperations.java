package org.dohrm.crud;

import org.dohrm.crud.operations.ReadAsyncOperation;
import org.dohrm.crud.operations.ReadOperation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Entry point of crud operations.
 *
 * @author michaeldohr
 * @since 07/10/15
 */
public final class CrudOperations {

    /**
     * Not implementable.
     */
    private CrudOperations() throws IllegalAccessException {
        throw new IllegalAccessException("Private constructor");
    }

    /**
     * Start read process.
     * @param read supplier.
     * @param <T> type of return.
     * @return Operation.
     */
    public static <T> ReadOperation<T> readOf(Supplier<Optional<T>> read) {
        return new ReadOperation<>(read);
    }

    public static <T> ReadAsyncOperation<T> readAsyncOf(Supplier<CompletableFuture<Optional<T>>> supplier) {
        return new ReadAsyncOperation<>(supplier);
    }
}
