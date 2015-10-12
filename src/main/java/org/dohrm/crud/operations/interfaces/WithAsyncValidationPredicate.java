package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.InvalidRequestException;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
public interface WithAsyncValidationPredicate<T> {

    Function<T, CompletableFuture<Boolean>> getAsyncValidationPredicate();

    default CompletableFuture<T> validateAsyncValidation(final T toTest) {
        return getAsyncValidationPredicate().apply(toTest).thenApply(r -> {
            if (!r) {
                throw new InvalidRequestException();
            }
            return toTest;
        });
    }
}
