package org.dohrm.crud.operations;

import com.google.common.base.Preconditions;
import org.dohrm.crud.operations.interfaces.WithReadSupplier;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 07/10/15
 */
public class ReadOperation<T> implements WithReadSupplier<T> {

    private final Supplier<Optional<T>> supplier;

    public ReadOperation(final Supplier<Optional<T>> supplier) {
        this.supplier = Preconditions.checkNotNull(supplier, "Supplier must be provided.");
    }

    @Override
    public Supplier<Optional<T>> getSupplier() {
        return this.supplier;
    }

    /**
     * Add security check.
     *
     * @param securityCheck security check.
     * @return next read operation.
     */
    public ReadOperation<T> withSecurityCheck(final Predicate<T> securityCheck) {
        return ReadStrategy.addSecurityCheck(this, securityCheck);
    }

}
