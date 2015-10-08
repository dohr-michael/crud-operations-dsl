package org.dohrm.crud.operations;

import org.dohrm.crud.exceptions.InvalidOperationConfigurationException;
import org.dohrm.crud.operations.interfaces.WithAsyncSecurityPredicate;
import org.dohrm.crud.operations.interfaces.WithSecurityPredicate;

import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
final class ReadStrategy {

    /**
     * Not implementable.
     */
    private ReadStrategy() throws IllegalAccessException {
        throw new IllegalAccessException("Private constructor");
    }


    static <T> ReadAsyncOperation<T> addSecurityCheck(final ReadAsyncOperation<T> base,
                                                      final Predicate<T> securityCheck) {
        if (base instanceof WithAsyncSecurityPredicate) {
            throw new InvalidOperationConfigurationException("Secured trait already present");
        }
        return new SecuredReadAsyncOperation<>(base.getSupplier(), securityCheck);
    }

    static <T> ReadOperation<T> addSecurityCheck(final ReadOperation<T> base,
                                                 final Predicate<T> securityCheck) {
        if (base instanceof WithSecurityPredicate) {
            throw new InvalidOperationConfigurationException("Secured trait already present");
        }
        return new SecuredReadOperation<>(base.getSupplier(), securityCheck);
    }
}
