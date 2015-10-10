package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.UnauthorisedAccessException;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public interface WithAsyncSecurityPredicate<T> {

    Function<T, CompletableFuture<Boolean>> getAsyncSecurityPredicate();

    /**
     * Validate security.
     *
     * @param toTest bean to test.
     */
    default CompletableFuture<T> validateAsyncSecurity(final CompletableFuture<T> toTest) {
        return toTest.thenCompose(v -> getAsyncSecurityPredicate().apply(v).thenApply(r -> {
            if (!r) {
                throw new UnauthorisedAccessException();
            }
            return v;
        }));
    }
}
