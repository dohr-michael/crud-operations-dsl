package org.dohrm.crud.operations;

import com.google.common.base.Preconditions;
import org.dohrm.crud.operations.interfaces.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author michaeldohr
 * @since 10/10/15
 */
abstract class AbstractReadOperationBuilder<T> implements
        IsAsyncSecuredReadOperation<T>, IsSecuredReadOperation<T>,
        WithReadSupplier<T>, WithAsyncReadSupplier<T>, WithSecurityPredicate<T>,
        WithAsyncSecurityPredicate<T> {

    private Optional<Supplier<Optional<T>>> supplier = Optional.empty();
    private Optional<Supplier<CompletableFuture<Optional<T>>>> asyncSupplier = Optional.empty();
    private Optional<Predicate<T>> securityPredicate = Optional.empty();
    private Optional<Function<T, CompletableFuture<Boolean>>> asyncSecurityPredicate = Optional.empty();


    AbstractReadOperationBuilder(Supplier<Optional<T>> supplier, Supplier<CompletableFuture<Optional<T>>> asyncSupplier) {
        Preconditions.checkArgument(supplier != null || asyncSupplier != null, "One of suppliers must be populated");
        this.supplier = Optional.ofNullable(supplier);
        this.asyncSupplier = Optional.ofNullable(asyncSupplier);
    }

    void setSecurityPredicate(Predicate<T> securityPredicate) {
        this.securityPredicate = Optional.ofNullable(securityPredicate);
    }

    void setAsyncSecurityPredicate(Function<T, CompletableFuture<Boolean>> asyncSecurityPredicate) {
        this.asyncSecurityPredicate = Optional.ofNullable(asyncSecurityPredicate);
    }

    @Override
    public Supplier<Optional<T>> getSupplier() {
        return this.supplier.orElseThrow(() -> new IllegalArgumentException("Supplier is missing"));
    }

    @Override
    public Supplier<CompletableFuture<Optional<T>>> getAsyncSupplier() {
        final Supplier<CompletableFuture<Optional<T>>> supplier;
        if (this.supplier.isPresent()) {
            supplier = () -> CompletableFuture.completedFuture(this.supplier.get().get());
        } else {
            supplier = this.asyncSupplier.orElseThrow(() -> new IllegalArgumentException("Supplier is missing"));
        }
        return supplier;
    }

    @Override
    public Function<T, CompletableFuture<Boolean>> getAsyncSecurityPredicate() {
        return this.asyncSecurityPredicate.orElse(v -> CompletableFuture.completedFuture(Boolean.TRUE));
    }

    @Override
    public Predicate<T> getSecurityPredicate() {
        return this.securityPredicate.orElse(o -> true);
    }

    @Override
    public CompletableFuture<T> readAsync() {
        return WithAsyncSecurityPredicate.super.validateAsyncSecurity(WithAsyncReadSupplier.super.readAsync());
    }

    @Override
    public T read() {
        return WithSecurityPredicate.super.validateSecurity(WithReadSupplier.super.read());
    }
}
