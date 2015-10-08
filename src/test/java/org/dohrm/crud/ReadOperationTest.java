package org.dohrm.crud;

import org.dohrm.crud.exceptions.InvalidOperationConfigurationException;
import org.dohrm.crud.exceptions.ResourceNotFoundException;
import org.dohrm.crud.exceptions.UnauthorisedAccessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author michaeldohr
 * @since 07/10/15
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ReadOperationTest {

    @Test
    public void testBasicRead() throws Exception {
        assertEquals("Test", CrudOperations
                .readOf(() -> Optional.of("Test"))
                .read());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testBasicReadWithEmpty() throws Exception {
        CrudOperations
                .readOf(Optional::empty)
                .read();
    }

    @Test
    public void testSecuredReadOperation() throws Exception {
        assertEquals("Test", CrudOperations
                .readOf(() -> Optional.of("Test"))
                .withSecurityCheck(s -> true)
                .read());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testSecuredReadWithEmpty() throws Exception {
        CrudOperations
                .readOf(Optional::empty)
                .withSecurityCheck(s -> true)
                .read();
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testSecuredReadWithInvalidRight() throws Exception {
        CrudOperations
                .readOf(() -> Optional.of("Test"))
                .withSecurityCheck(s -> false)
                .read();
    }

    @Test(expected = InvalidOperationConfigurationException.class)
    public void testSecuredReadWithTwoCheck() throws Exception {
        CrudOperations
                .readOf(() -> Optional.of("Test"))
                .withSecurityCheck(s -> false)
                .withSecurityCheck(s -> false)
                .read();
    }

    @Test
    public void testBasicReadAsync() throws Exception {
        assertEquals("Test", CrudOperations
                .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.of("Test")))
                .read().get());
    }

    @Test
    public void testBasicReadAsyncWithEmpty() throws Exception {
        try {
            CrudOperations
                    .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.empty()))
                    .read().get();
            assertTrue(false);
        } catch (ExecutionException ex) {
            assertTrue(ex.getCause() instanceof ResourceNotFoundException);
        }
    }

    @Test
    public void testSecuredReadAsyncOperation() throws Exception {
        assertEquals("Test", CrudOperations
                .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.of("Test")))
                .withSecurityCheck(s -> true)
                .read().get());
    }


    @Test
    public void testSecuredReadAsyncWithEmpty() throws Exception {
        try {
            CrudOperations
                    .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.empty()))
                    .withSecurityCheck(s -> true)
                    .read().get();
        } catch (ExecutionException ex) {
            assertTrue(ex.getCause() instanceof ResourceNotFoundException);
        }
    }

    @Test
    public void testSecuredReadAsyncWithInvalidRight() throws Exception {
        try {
            CrudOperations
                    .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.of("Test")))
                    .withSecurityCheck(s -> false)
                    .read().get();
        } catch (ExecutionException ex) {
            assertTrue(ex.getCause() instanceof UnauthorisedAccessException);
        }
    }


    @Test(expected = InvalidOperationConfigurationException.class)
    public void testSecuredReadAsyncWithTwoCheck() throws Exception {
        CrudOperations
                .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.of("Test")))
                .withSecurityCheck(s -> false)
                .withSecurityCheck(s -> false)
                .read();
    }
}
