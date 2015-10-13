package org.dohrm.crud.operations.interfaces;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public interface IsSecuredUpdateOperation<T> extends FinalCreateUpdateOperation<T> {

    IsValidatedCreateUpdateOperation<T> withValidationPredicate(final Predicate<T> validationPredicate);

    IsWithMergeUpdateOperation<T> withMergeFunction(BiFunction<T, T, T> mergeFunction);

    // withMergeFunction(BiFunction<T, T, T> merge);
}
