package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.UnauthorisedAccessException;

import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public interface WithSecurityPredicate<T> {

    Predicate<T> getSecurityPredicate();

    /**
     * Validate security.
     * @param toTest bean to test.
     */
    default void validateSecurity(final T toTest) {
        if (!getSecurityPredicate().test(toTest)) {
            throw new UnauthorisedAccessException();
        }
    }
}
