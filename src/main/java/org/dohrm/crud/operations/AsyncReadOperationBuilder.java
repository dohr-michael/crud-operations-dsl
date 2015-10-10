package org.dohrm.crud.operations;

import org.dohrm.crud.operations.interfaces.IsAsyncReadOperation;
import org.dohrm.crud.operations.interfaces.IsAsyncSecuredReadOperation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 10/10/15
 */
public class AsyncReadOperationBuilder<T> extends AbstractReadOperationBuilder<T>
        implements IsAsyncReadOperation<T> {

    public AsyncReadOperationBuilder(Supplier<CompletableFuture<Optional<T>>> asyncSupplier) {
        super(null, asyncSupplier);
    }

    @Override
    public IsAsyncSecuredReadOperation<T> withSecurityCheck(Predicate<T> securityCheck) {
        setAsyncSecurityPredicate(t -> CompletableFuture.completedFuture(securityCheck.test(t)));
        return this;
    }

    @Override
    public IsAsyncSecuredReadOperation<T> withSecurityCheck(Function<T, CompletableFuture<Boolean>> securityCheck) {
        setAsyncSecurityPredicate(securityCheck);
        return this;
    }
}
