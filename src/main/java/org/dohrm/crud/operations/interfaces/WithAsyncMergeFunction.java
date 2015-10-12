package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public interface WithAsyncMergeFunction<T> {

    BiFunction<T, T, CompletableFuture<T>> getAsyncMergeFunction();


    default CompletableFuture<T> asyncMerge(T original, T request) {
        return getAsyncMergeFunction().apply(original, request);
    }

}
