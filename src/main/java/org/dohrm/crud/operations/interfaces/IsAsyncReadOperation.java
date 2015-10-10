package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 10/10/15
 */
public interface IsAsyncReadOperation<T> extends FinalAsyncReadOperation<T> {

    /**
     * Add security check.
     *
     * @param securityCheck security check.
     * @return next read operation.
     */
    IsAsyncSecuredReadOperation<T> withSecurityCheck(final Predicate<T> securityCheck);

    /**
     * Add async security check.
     *
     * @param securityCheck security check.
     * @return next read operation.
     */
    IsAsyncSecuredReadOperation<T> withSecurityCheck(final Function<T, CompletableFuture<Boolean>> securityCheck);
}
