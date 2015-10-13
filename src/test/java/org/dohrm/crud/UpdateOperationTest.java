package org.dohrm.crud;

import org.dohrm.crud.exceptions.InvalidRequestException;
import org.dohrm.crud.exceptions.ResourceNotFoundException;
import org.dohrm.crud.exceptions.UnauthorisedAccessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * @author michaeldohr
 * @since 13/10/15
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class UpdateOperationTest {
    private final String value = "TOTO";
    private final String value2 = "TITI";
    private final Supplier<Optional<String>> readSupplierTrue = () -> Optional.of(value2);
    private final Supplier<Optional<String>> readSupplierFalse = Optional::empty;
    private final Predicate<String> predicateTrue = __ -> true;
    private final Predicate<String> predicateFalse = __ -> false;
    private final BiFunction<String, String, String> merge = (a, b) -> a;


    @Test
    public void test() throws Exception {
        assertEquals(value, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .save()));
    }

    //----------------------


    @Test
    public void testWithValidation() throws Exception {
        assertEquals(value, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withValidationPredicate(predicateTrue)
                .save()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testWithValidationError() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withValidationPredicate(predicateFalse)
                .save());
    }

    //-----------------------

    @Test
    public void testWithSupplierWithSecurity() throws Exception {
        assertEquals(value, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateTrue)
                .save()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurity() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateTrue)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateFalse)
                .save());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testWithSupplierWithSecurityFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateFalse)
                .save());
    }


    //----------------------

    @Test
    public void testWithSupplierWithSecurityWithValidation() throws Exception {
        assertEquals(value, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateTrue)
                .withValidationPredicate(predicateTrue)
                .save()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateTrue)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateTrue)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityFalseWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateFalse)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityFalseWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateFalse)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testWithSupplierWithSecurityFalseWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateFalse)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testWithSupplierWithSecurityFalseWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateFalse)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    @Test(expected = InvalidRequestException.class)
    public void testWithSupplierWithSecurityWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateTrue)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    //--------------------

    @Test
    public void testWithSupplierWithSecurityWithMerge() throws Exception {
        assertEquals(value2, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateTrue)
                .withMergeFunction(merge)
                .save()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityWithMerge() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateTrue)
                .withMergeFunction(merge)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityFalseWithMerge() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateFalse)
                .withMergeFunction(merge)
                .save());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testWithSupplierWithSecurityFalseWithMerge() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateFalse)
                .withMergeFunction(merge)
                .save());
    }

    //---------------------

    @Test
    public void testWithSupplierWithSecurityWithMergeWithValidation() throws Exception {
        assertEquals(value2, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateTrue)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateTrue)
                .save()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityWithMergeWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateTrue)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityWithMergeWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateTrue)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityFalseWithMergeWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateFalse)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithSecurityFalseWithMergeWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withSecurityPredicate(predicateFalse)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testWithSupplierWithSecurityFalseWithMergeWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateFalse)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = UnauthorisedAccessException.class)
    public void testWithSupplierWithSecurityFalseWithMergeWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateFalse)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateFalse)
                .save());
    }

    @Test(expected = InvalidRequestException.class)
    public void testWithSupplierWithSecurityWithMergeWithValidationFalse() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withSecurityPredicate(predicateTrue)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateFalse)
                .save());
    }


    //-------------------------

    @Test
    public void testWithSupplierWithMerge() throws Exception {
        assertEquals(value2, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withMergeFunction(merge)
                .save()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithMerge() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withMergeFunction(merge)
                .save());
    }


    @Test
    public void testWithSupplierWithMergeWithValidation() throws Exception {
        assertEquals(value2, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateTrue)
                .save()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithMergeWithValidation() throws Exception {
        check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateTrue)
                .save());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testWithSupplierFalseWithMergeWithValidationFalse() throws Exception {
        assertEquals(value, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierFalse)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateFalse)
                .save()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testWithSupplierWithMergeWithValidationFalse() throws Exception {
        assertEquals(value, check(CrudOperations
                .updateOf(value, Function.<String>identity())
                .withReadSupplier(readSupplierTrue)
                .withMergeFunction(merge)
                .withValidationPredicate(predicateFalse)
                .save()));
    }

    //------------------------------------------------------
    //
    // Internal
    //
    //-----------------------------------------------------

    private <T> T check(T toTest) throws Exception {
        return toTest;
    }

    private <T> T check(CompletableFuture<T> toTest) throws Exception {
        try {
            return toTest.get();
        } catch (ExecutionException e) {
            throw (Exception) e.getCause();
        }
    }
}
