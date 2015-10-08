package org.dohrm.crud.operations;

import com.google.common.base.Preconditions;
import org.dohrm.crud.operations.interfaces.WithReadSupplier;
import org.dohrm.crud.operations.interfaces.WithSecurityPredicate;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public final class SecuredReadOperation<T> extends ReadOperation<T> implements WithSecurityPredicate<T> {

    private final Predicate<T> securityPredicate;

    /**
     * Secured read operation.
     *
     * @param supplier          supplier
     * @param securityPredicate securityPredicate.
     */
    public SecuredReadOperation(final Supplier<Optional<T>> supplier, final Predicate<T> securityPredicate) {
        super(supplier);
        this.securityPredicate = Preconditions.checkNotNull(securityPredicate, "Security predicate must be provided.");
    }

    @Override
    public Predicate<T> getSecurityPredicate() {
        return this.securityPredicate;
    }

    @Override
    public T read() {
        final T result = super.read();
        validateSecurity(result);
        return result;
    }
}
