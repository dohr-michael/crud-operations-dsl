package org.dohrm.crud.operations;

import org.dohrm.crud.operations.interfaces.IsAsyncValidatedCreateUpdateOperation;
import org.dohrm.crud.operations.interfaces.IsCreateOperation;
import org.dohrm.crud.operations.interfaces.IsValidatedCreateUpdateOperation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public class CreateOperationBuilder<T> extends AbstractCreateUpdateOperationBuilder<T> implements IsCreateOperation<T>,
        IsValidatedCreateUpdateOperation<T>, IsAsyncValidatedCreateUpdateOperation<T> {

    public CreateOperationBuilder(T request, Function<T, T> saveFunction) {
        super(request, saveFunction, null);
    }

    @Override
    public IsValidatedCreateUpdateOperation<T> withValidationCheck(Predicate<T> validationCheck) {
        setValidationPredicate(validationCheck);
        return this;
    }

    @Override
    public IsAsyncValidatedCreateUpdateOperation<T> withValidationCheck(Function<T, CompletableFuture<Boolean>> validationCheck) {
        setAsyncValidationPredicate(validationCheck);
        return this;
    }

}
