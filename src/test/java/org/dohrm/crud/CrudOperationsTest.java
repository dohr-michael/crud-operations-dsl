package org.dohrm.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * @author michaeldohr
 * @since 07/10/15
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CrudOperationsTest {

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<CrudOperations> constructor = CrudOperations.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
            assertTrue(false);
        } catch (InvocationTargetException ex) {
            assertTrue(ex.getCause().getMessage().contains("Private constructor"));
        }
    }
}
