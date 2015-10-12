package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
public interface IsAsyncCreateOperation<T> extends FinalAsyncCreateUpdateOperation<T> {

    IsAsyncValidatedCreateUpdateOperation<T> withValidationCheck(final Predicate<T> validationCheck);

    IsAsyncValidatedCreateUpdateOperation<T> withValidationCheck(final Function<T, CompletableFuture<Boolean>> validationCheck);
}
