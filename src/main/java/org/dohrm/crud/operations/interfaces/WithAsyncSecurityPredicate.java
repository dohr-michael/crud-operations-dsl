package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.UnauthorisedAccessException;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public interface WithAsyncSecurityPredicate<T> {

    Predicate<T> getSecurityPredicate();

    /**
     * Validate security.
     *
     * @param toTest bean to test.
     */
    default CompletableFuture<T> validateSecurity(final CompletableFuture<T> toTest) {
        return toTest.thenApplyAsync((t) -> {
            if (!getSecurityPredicate().test(t)) {
                throw new UnauthorisedAccessException();
            }
            return t;
        });
    }
}
