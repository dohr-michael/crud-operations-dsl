package org.dohrm.crud.operations;

import org.dohrm.crud.operations.interfaces.*;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
public class UpdateOperationBuilder<T> extends AbstractCreateUpdateOperationBuilder<T> implements IsUpdateOperation<T>,
        IsSecuredUpdateOperation<T>, IsUpdateOperationWithReadSupplier<T>, IsWithMergeUpdateOperation<T> {

    public UpdateOperationBuilder(T request, Function<T, T> saveFunction) {
        super(request, saveFunction, null);
    }

    @Override
    public IsUpdateOperationWithReadSupplier<T> withReadSupplier(Supplier<Optional<T>> readSupplier) {
        setSupplier(readSupplier);
        return this;
    }

    @Override
    public IsSecuredUpdateOperation<T> withSecurityPredicate(Predicate<T> securityPredicate) {
        setSecurityPredicate(securityPredicate);
        return this;
    }

    @Override
    public IsValidatedCreateUpdateOperation<T> withValidationPredicate(Predicate<T> validationPredicate) {
        setValidationPredicate(validationPredicate);
        return this;
    }

    @Override
    public IsWithMergeUpdateOperation<T> withMergeFunction(BiFunction<T, T, T> mergeFunction) {
        setMergeFunction(mergeFunction);
        return this;
    }
}
