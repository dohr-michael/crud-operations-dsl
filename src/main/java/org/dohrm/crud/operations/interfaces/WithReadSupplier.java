package org.dohrm.crud.operations.interfaces;

import org.dohrm.crud.exceptions.ResourceNotFoundException;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
public interface WithReadSupplier<T> extends FinalReadOperation<T> {

    Supplier<Optional<T>> getSupplier();

    default T read() {
        return getSupplier().get().orElseThrow(ResourceNotFoundException::new);
    }
}
