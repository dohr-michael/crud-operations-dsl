package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 09/10/15
 */
public interface IsReadOperation<T> extends FinalReadOperation<T> {

    /**
     * Add security check.
     *
     * @param securityCheck security check.
     * @return next read operation.
     */
    IsSecuredReadOperation<T> withSecurityCheck(final Predicate<T> securityCheck);

    /**
     * Add async security check.
     *
     * @param securityCheck security check.
     * @return
     */
    IsAsyncSecuredReadOperation<T> withSecurityCheck(final Function<T, CompletableFuture<Boolean>> securityCheck);
}
