package org.dohrm.crud.operations;

import com.google.common.base.Preconditions;
import org.dohrm.crud.operations.interfaces.WithAsyncSecurityPredicate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public final class SecuredReadAsyncOperation<T> extends ReadAsyncOperation<T> implements WithAsyncSecurityPredicate<T> {

    private final Predicate<T> securityPredicate;

    /**
     * Secured read operation.
     *
     * @param supplier          supplier
     * @param securityPredicate securityPredicate.
     */
    public SecuredReadAsyncOperation(final Supplier<CompletableFuture<Optional<T>>> supplier, final Predicate<T> securityPredicate) {
        super(supplier);
        this.securityPredicate = Preconditions.checkNotNull(securityPredicate, "Security predicate must be provided.");
    }

    @Override
    public Predicate<T> getSecurityPredicate() {
        return this.securityPredicate;
    }

    @Override
    public CompletableFuture<T> read() {
        return validateSecurity(super.read());
    }
}
