package org.dohrm.crud.operations;

import com.google.common.base.Preconditions;
import org.dohrm.crud.operations.interfaces.WithAsyncReadSupplier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 07/10/15
 */
public class ReadAsyncOperation<T> implements WithAsyncReadSupplier<T> {


    private final Supplier<CompletableFuture<Optional<T>>> supplier;

    public ReadAsyncOperation(final Supplier<CompletableFuture<Optional<T>>> supplier) {
        this.supplier = Preconditions.checkNotNull(supplier, "Supplier is mandatory");
    }

    @Override
    public Supplier<CompletableFuture<Optional<T>>> getSupplier() {
        return this.supplier;
    }

    /**
     * Add security check.
     *
     * @param securityCheck security check.
     * @return next read operation.
     */
    public ReadAsyncOperation<T> withSecurityCheck(final Predicate<T> securityCheck) {
        return ReadStrategy.addSecurityCheck(this, securityCheck);
    }
}
