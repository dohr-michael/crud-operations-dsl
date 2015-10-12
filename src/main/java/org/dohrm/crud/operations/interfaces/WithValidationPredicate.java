package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.InvalidRequestException;

import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
public interface WithValidationPredicate<T> {

    Predicate<T> getValidationPredicate();

    default T validateValidation(final T toTest) {
        if (!getValidationPredicate().test(toTest)) {
            throw new InvalidRequestException();
        }
        return toTest;
    }
}
