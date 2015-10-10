package org.dohrm.crud;

import org.dohrm.crud.exceptions.ResourceNotFoundException;
import org.dohrm.crud.exceptions.UnauthorisedAccessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * @author michaeldohr
 * @since 10/10/15
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ReadOperationSyncTest {

    private final String value = "TOTO";
    private final Supplier<Optional<String>> valueSupplier = () -> Optional.of(value);
    private final Predicate<String> truePredicate = (t) -> true;
    private final Function<String, CompletableFuture<Boolean>> asyncTruePredicate = __ -> CompletableFuture.completedFuture(true);
    private final Predicate<String> falsePredicate = (t) -> false;
    private final Function<String, CompletableFuture<Boolean>> asyncFalsePredicate = __ -> CompletableFuture.completedFuture(false);
    private final Supplier<CompletableFuture<Optional<String>>> asyncValueSupplier = () -> CompletableFuture.completedFuture(Optional.of(value));
    private final Supplier<CompletableFuture<Optional<String>>> asyncValueEmptySupplier = () -> CompletableFuture.completedFuture(Optional.<String>empty());


    @Test
    public void testRead() {
        assertEquals(value,
                CrudOperations.readOf(valueSupplier)
                        .read());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadEmpty() {
        CrudOperations.readOf(Optional::empty)
                .read();
    }

    @Test
    public void testReadWithSecurityCheck() {
        assertEquals(value,
                CrudOperations.readOf(valueSupplier)
                        .withSecurityCheck(truePredicate)
                        .read());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testReadWithInvalidSecurityCheck() {
        CrudOperations.readOf(valueSupplier)
                .withSecurityCheck(falsePredicate)
                .read();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadEmptyWithInvalidSecurityCheck() {
        CrudOperations.readOf(Optional::<String>empty)
                .withSecurityCheck(falsePredicate)
                .read();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadEmptyWithValidSecurityCheck() {
        CrudOperations.readOf(Optional::<String>empty)
                .withSecurityCheck(truePredicate)
                .read();
    }

    @Test
    public void testReadWithAsyncSecurityCheck() throws Exception {
        assertEquals(value,
                checkAsync(
                        CrudOperations.readOf(valueSupplier)
                                .withSecurityCheck(asyncTruePredicate)
                                .readAsync()));
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testReadWithInvalidAsyncSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readOf(valueSupplier)
                        .withSecurityCheck(asyncFalsePredicate)
                        .readAsync());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadEmptyWithInvalidAsyncSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readOf(Optional::<String>empty)
                        .withSecurityCheck(asyncFalsePredicate)
                        .readAsync());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadEmptyWithValidAsyncSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readOf(Optional::<String>empty)
                        .withSecurityCheck(asyncTruePredicate)
                        .readAsync());
    }


    @Test
    public void testReadAsync() throws Exception {
        assertEquals(value,
                checkAsync(
                        CrudOperations.readAsyncOf(asyncValueSupplier)
                                .readAsync()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadAsyncEmpty() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueEmptySupplier)
                        .readAsync());
    }

    @Test
    public void testReadAsyncWithSecurityCheck() throws Exception {
        assertEquals(value,
                checkAsync(
                        CrudOperations.readAsyncOf(asyncValueSupplier)
                                .withSecurityCheck(truePredicate)
                                .readAsync()));
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testReadAsyncWithInvalidSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueSupplier)
                        .withSecurityCheck(falsePredicate)
                        .readAsync());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadAsyncEmptyWithInvalidSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueEmptySupplier)
                        .withSecurityCheck(falsePredicate)
                        .readAsync());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadAsyncEmptyWithValidSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueEmptySupplier)
                        .withSecurityCheck(truePredicate)
                        .readAsync());
    }

    @Test
    public void testReadAsyncWithAsyncSecurityCheck() throws Exception {
        assertEquals(value,
                checkAsync(
                        CrudOperations.readAsyncOf(asyncValueSupplier)
                                .withSecurityCheck(asyncTruePredicate)
                                .readAsync()));
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testReadAsyncWithInvalidAsyncSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueSupplier)
                        .withSecurityCheck(asyncFalsePredicate)
                        .readAsync());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadAsyncEmptyWithInvalidAsyncSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueEmptySupplier)
                        .withSecurityCheck(asyncFalsePredicate)
                        .readAsync());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testReadAsyncEmptyWithValidAsyncSecurityCheck() throws Exception {
        checkAsync(
                CrudOperations.readAsyncOf(asyncValueEmptySupplier)
                        .withSecurityCheck(asyncTruePredicate)
                        .readAsync());
    }

    //------------------------------------------------------
    //
    // Internal
    //
    //-----------------------------------------------------

    private <T> T checkAsync(CompletableFuture<T> toTest) throws Exception {
        try {
            return toTest.get();
        } catch (ExecutionException e) {
            throw (Exception) e.getCause();
        }
    }
}
