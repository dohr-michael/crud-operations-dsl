package org.dohrm.crud.operations.interfaces;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public interface IsUpdateOperation<T> extends FinalCreateUpdateOperation<T> {

    IsUpdateOperationWithReadSupplier<T> withReadSupplier(final Supplier<Optional<T>> readSupplier);

    IsValidatedCreateUpdateOperation<T> withValidationPredicate(final Predicate<T> validationCheck);

    /*IsAsyncSecuredReadOperation<T> withSecurityPredicate(final Function<T, CompletableFuture<Boolean>> securityCheck);

    IsValidatedCreateUpdateOperation<T> withValidationPredicate(final Predicate<T> validationCheck);

    IsAsyncValidatedCreateUpdateOperation<T> withValidationPredicate(final Function<T, CompletableFuture<Boolean>> validationCheck);

    withMergeFunction(BiFunction<T, T, T> merge);*/

}
