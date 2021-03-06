package org.dohrm.crud;

import org.dohrm.crud.operations.*;
import org.dohrm.crud.operations.interfaces.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
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
     *
     * @param read supplier.
     * @param <T>  type of return.
     * @return Operation.
     */
    public static <T> IsReadOperation<T> readOf(Supplier<Optional<T>> read) {
        return new ReadOperationBuilder<>(read);
    }

    public static <T> IsAsyncReadOperation<T> readAsyncOf(Supplier<CompletableFuture<Optional<T>>> supplier) {
        return new AsyncReadOperationBuilder<>(supplier);
    }

    public static <T> IsCreateOperation<T> createOf(T request, Function<T, T> saveFunction) {
        return new CreateOperationBuilder<>(request, saveFunction);
    }

    public static <T> IsAsyncCreateOperation<T> createAsyncOf(T request, Function<T, CompletableFuture<T>> saveFunction) {
        return new AsyncCreateOperationBuilder<>(request, saveFunction);
    }

    public static <T> IsUpdateOperation<T> updateOf(T request, Function<T, T> saveFunction) {
        return new UpdateOperationBuilder<>(request, saveFunction);
    }
}
