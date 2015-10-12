package org.dohrm.crud;

import org.dohrm.crud.exceptions.InvalidRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * @author michaeldohr
 * @since 12/10/15
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CreateOperationTest {

    private final String value = "TOTO";

    @Test
    public void testCreate() {
        assertEquals(value, CrudOperations.createOf(value, Function.<String>identity())
                .save());
    }

    @Test
    public void testCreateWithValidationOk() {
        assertEquals(value, CrudOperations
                .createOf(value, Function.<String>identity())
                .withValidationCheck((Predicate<String>) __ -> true)
                .save());
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateWithValidationKo() {
        CrudOperations
                .createOf(value, Function.<String>identity())
                .withValidationCheck((Predicate<String>) __ -> false)
                .save();
    }

    @Test
    public void testCreateWithAsyncValidationOk() throws Exception {
        assertEquals(value, checkAsync(CrudOperations
                .createOf(value, Function.<String>identity())
                .withValidationCheck((Function<String, CompletableFuture<Boolean>>) __ -> CompletableFuture.completedFuture(true))
                .saveAsync()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateWithAsyncValidationKo() throws Exception {
        checkAsync(CrudOperations
                .createOf(value, Function.<String>identity())
                .withValidationCheck((Function<String, CompletableFuture<Boolean>>) __ -> CompletableFuture.completedFuture(false))
                .saveAsync());
    }


    @Test
    public void testCreateAsync() throws Exception {
        assertEquals(value, checkAsync(
                CrudOperations
                        .createAsyncOf(value, CompletableFuture::completedFuture)
                        .saveAsync()));
    }

    @Test
    public void testCreateAsyncWithValidationOk() throws Exception {
        assertEquals(value, checkAsync(
                CrudOperations
                        .createAsyncOf(value, CompletableFuture::completedFuture)
                        .withValidationCheck((Predicate<String>) __ -> true)
                        .saveAsync()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateAsyncWithValidationKo() throws Exception {
        checkAsync(
                CrudOperations
                        .createAsyncOf(value, CompletableFuture::completedFuture)
                        .withValidationCheck((Predicate<String>) __ -> false)
                        .saveAsync());
    }

    @Test
    public void testCreateAsyncWithAsyncValidationOk() throws Exception {
        assertEquals(value, checkAsync(
                CrudOperations
                        .createAsyncOf(value, CompletableFuture::completedFuture)
                        .withValidationCheck((Function<String, CompletableFuture<Boolean>>) __ -> CompletableFuture.completedFuture(true))
                        .saveAsync()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateAsyncWithAsyncValidationKo() throws Exception {
        checkAsync(
                CrudOperations
                        .createAsyncOf(value, CompletableFuture::completedFuture)
                        .withValidationCheck((Function<String, CompletableFuture<Boolean>>) __ -> CompletableFuture.completedFuture(false))
                        .saveAsync());
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
