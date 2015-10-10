package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.ResourceNotFoundException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public interface WithAsyncReadSupplier<T> extends FinalAsyncReadOperation<T> {

    Supplier<CompletableFuture<Optional<T>>> getAsyncSupplier();

    default CompletableFuture<T> readAsync() {
        return getAsyncSupplier().get().thenApply(o -> (T) o.orElseThrow(ResourceNotFoundException::new));
    }
}
