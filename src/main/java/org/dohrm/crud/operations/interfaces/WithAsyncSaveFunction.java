package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
public interface WithAsyncSaveFunction<T> {
    Function<T, CompletableFuture<T>> getAsyncSaveFunction();

    default CompletableFuture<T> saveAsync(T request) {
        return this.getAsyncSaveFunction().apply(request);
    }
}
