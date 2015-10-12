package org.dohrm.crud.operations;

import org.dohrm.crud.operations.interfaces.IsAsyncCreateOperation;
import org.dohrm.crud.operations.interfaces.IsAsyncValidatedCreateUpdateOperation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public class AsyncCreateOperationBuilder<T> extends AbstractCreateUpdateOperationBuilder<T> implements IsAsyncCreateOperation<T>,
        IsAsyncValidatedCreateUpdateOperation<T> {

    public AsyncCreateOperationBuilder(T request, Function<T, CompletableFuture<T>> asyncSaveFunction) {
        super(request, null, asyncSaveFunction);
    }

    @Override
    public IsAsyncValidatedCreateUpdateOperation<T> withValidationCheck(Predicate<T> validationCheck) {
        setAsyncValidationPredicate(r -> CompletableFuture.completedFuture(validationCheck.test(r)));
        return this;
    }

    @Override
    public IsAsyncValidatedCreateUpdateOperation<T> withValidationCheck(Function<T, CompletableFuture<Boolean>> validationCheck) {
        setAsyncValidationPredicate(validationCheck);
        return this;
    }
}
