package org.dohrm.crud.operations;

import org.dohrm.crud.CrudOperations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * @author michaeldohr
 * @since 08/10/15
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ReadStrategyTest {

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<ReadStrategy> constructor = ReadStrategy.class.getDeclaredConstructor();
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
