package org.dohrm.crud.operations;

import org.dohrm.crud.operations.interfaces.IsAsyncSecuredReadOperation;
import org.dohrm.crud.operations.interfaces.IsReadOperation;
import org.dohrm.crud.operations.interfaces.IsSecuredReadOperation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 10/10/15
 */
public class ReadOperationBuilder<T> extends AbstractReadOperationBuilder<T>
        implements IsReadOperation<T> {

    public ReadOperationBuilder(Supplier<Optional<T>> supplier) {
        super(supplier, null);
    }

    @Override
    public IsSecuredReadOperation<T> withSecurityCheck(Predicate<T> securityCheck) {
        setSecurityPredicate(securityCheck);
        return this;
    }

    @Override
    public IsAsyncSecuredReadOperation<T> withSecurityCheck(Function<T, CompletableFuture<Boolean>> securityCheck) {
        setAsyncSecurityPredicate(securityCheck);
        return this;
    }
}
