package org.dohrm.crud.operations.interfaces;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author michaeldohr
 * @since 13/10/15
 */
public interface IsUpdateOperationWithReadSupplier<T> {

    IsSecuredUpdateOperation<T> withSecurityPredicate(final Predicate<T> securityPredicate);

    IsWithMergeUpdateOperation<T> withMergeFunction(BiFunction<T, T, T> mergeFunction);
}
