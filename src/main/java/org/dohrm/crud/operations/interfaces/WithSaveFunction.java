package org.dohrm.crud.operations.interfaces;

import java.util.function.Function;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
public interface WithSaveFunction<T> {

    Function<T, T> getSaveFunction();

    default T save(T request) {
        return this.getSaveFunction().apply(request);
    }

}
