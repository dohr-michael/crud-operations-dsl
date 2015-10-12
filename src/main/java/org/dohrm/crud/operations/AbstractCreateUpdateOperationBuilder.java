package org.dohrm.crud.operations;

import org.dohrm.crud.operations.interfaces.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
abstract class AbstractCreateUpdateOperationBuilder<T> implements IsAsyncValidatedCreateUpdateOperation<T>, IsValidatedCreateUpdateOperation<T>,
        WithMergeFunction<T>,
        WithAsyncSecurityPredicate<T>, WithSecurityPredicate<T>,
        WithValidationPredicate<T>, WithAsyncValidationPredicate<T>,
        WithReadSupplier<T>, WithAsyncReadSupplier<T>,
        WithAsyncSaveFunction<T>, WithSaveFunction<T> {

    private final T request;
    private final Optional<Function<T, T>> saveFunction;
    private final Optional<Function<T, CompletableFuture<T>>> asyncSaveFunction;
    private Optional<Function<T, CompletableFuture<Boolean>>> asyncValidationPredicate = Optional.empty();
    private Optional<Predicate<T>> validationPredicate = Optional.empty();
    private Optional<Supplier<CompletableFuture<Optional<T>>>> asyncSupplier = Optional.empty();
    private Optional<Supplier<Optional<T>>> supplier = Optional.empty();
    private Optional<Function<T, CompletableFuture<Boolean>>> asyncSecurityPredicate = Optional.empty();
    private Optional<Predicate<T>> securityPredicate = Optional.empty();
    private Optional<BiFunction<T, T, T>> mergeFunction = Optional.empty();

    AbstractCreateUpdateOperationBuilder(T request, Function<T, T> saveFunction, Function<T, CompletableFuture<T>> asyncSaveFunction) {
        this.request = request;
        this.saveFunction = Optional.ofNullable(saveFunction);
        this.asyncSaveFunction = Optional.ofNullable(asyncSaveFunction);
    }

    void setAsyncValidationPredicate(Function<T, CompletableFuture<Boolean>> asyncValidationPredicate) {
        this.asyncValidationPredicate = Optional.ofNullable(asyncValidationPredicate);
    }

    void setValidationPredicate(Predicate<T> validationPredicate) {
        this.validationPredicate = Optional.ofNullable(validationPredicate);
    }

    void setAsyncSupplier(Supplier<CompletableFuture<Optional<T>>> asyncSupplier) {
        this.asyncSupplier = Optional.ofNullable(asyncSupplier);
    }

    void setSupplier(Supplier<Optional<T>> supplier) {
        this.supplier = Optional.ofNullable(supplier);
    }

    void setAsyncSecurityPredicate(Function<T, CompletableFuture<Boolean>> asyncSecurityPredicate) {
        this.asyncSecurityPredicate = Optional.ofNullable(asyncSecurityPredicate);
    }

    void setSecurityPredicate(Predicate<T> securityPredicate) {
        this.securityPredicate = Optional.ofNullable(securityPredicate);
    }

    void setMergeFunction(BiFunction<T, T, T> mergeFunction) {
        this.mergeFunction = Optional.ofNullable(mergeFunction);
    }

    @Override
    public Function<T, CompletableFuture<T>> getAsyncSaveFunction() {
        if (saveFunction.isPresent()) {
            return r -> CompletableFuture.completedFuture(saveFunction.get().apply(r));
        }
        return asyncSaveFunction.orElseThrow(() -> new IllegalArgumentException("Save function is missing."));
    }

    @Override
    public Function<T, T> getSaveFunction() {
        return saveFunction.orElseThrow(() -> new IllegalArgumentException("Save function is missing."));
    }

    @Override
    public Function<T, CompletableFuture<Boolean>> getAsyncValidationPredicate() {
        return asyncValidationPredicate.orElse(__ -> CompletableFuture.completedFuture(true));
    }

    @Override
    public Predicate<T> getValidationPredicate() {
        return validationPredicate.orElse(__ -> true);
    }

    @Override
    public Supplier<CompletableFuture<Optional<T>>> getAsyncSupplier() {
        return asyncSupplier.orElse(() -> CompletableFuture.completedFuture(Optional.<T>empty()));
    }

    @Override
    public Supplier<Optional<T>> getSupplier() {
        return supplier.orElse(Optional::<T>empty);
    }

    @Override
    public Function<T, CompletableFuture<Boolean>> getAsyncSecurityPredicate() {
        return asyncSecurityPredicate.orElse(__ -> CompletableFuture.completedFuture(true));
    }

    @Override
    public Predicate<T> getSecurityPredicate() {
        return securityPredicate.orElse(__ -> true);
    }

    @Override
    public BiFunction<T, T, T> getMergeFunction() {
        return mergeFunction.orElse((a, b) -> this.request);
    }

    @Override
    public CompletableFuture<T> saveAsync() {
        CompletableFuture<T> baseBean = CompletableFuture.completedFuture(null);
        // Retrieve base bean.
        if (asyncSecurityPredicate.isPresent() || mergeFunction.isPresent()) {
            baseBean = WithAsyncReadSupplier.super.readAsync();
        }
        // Authorisation validation
        CompletableFuture<T> securityValidation = WithAsyncSecurityPredicate.super.validateAsyncSecurity(baseBean);
        // Request validation.
        CompletableFuture<T> validationValidation = WithAsyncValidationPredicate.super.validateAsyncValidation(request);

        return securityValidation.thenCombine(validationValidation, (bb, rb) -> WithMergeFunction.super.merge(bb, rb))
                .thenCompose(er -> WithAsyncSaveFunction.super.saveAsync(er));
    }

    @Override
    public T save() {
        T baseBean = null;
        // Retrieve base bean.
        if (securityPredicate.isPresent() || mergeFunction.isPresent()) {
            baseBean = WithReadSupplier.super.read();
        }
        // Authorisation validation
        WithSecurityPredicate.super.validateSecurity(baseBean);
        // Request validation.
        WithValidationPredicate.super.validateValidation(request);
        // Merge.
        T finalRequest = WithMergeFunction.super.merge(baseBean, request);
        return WithSaveFunction.super.save(finalRequest);
    }
}
