package org.dohrm.crud.operations.interfaces;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public interface WithMergeFunction<T> {

    BiFunction<T, T, T> getMergeFunction();

    default T merge(T original, T request) {
        return this.getMergeFunction().apply(original, request);
    }

}
