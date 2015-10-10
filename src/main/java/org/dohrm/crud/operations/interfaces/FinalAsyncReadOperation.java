package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;

/**
 * @author michaeldohr
 * @since 10/10/15
 */
public interface FinalAsyncReadOperation<T> {

    CompletableFuture<T> readAsync();
}
