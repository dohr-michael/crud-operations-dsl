package org.dohrm.crud.operations.interfaces;

import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 13/10/15
 */
public interface IsWithMergeUpdateOperation<T> extends FinalCreateUpdateOperation<T> {

    IsValidatedCreateUpdateOperation<T> withValidationPredicate(final Predicate<T> validationPredicate);
}
