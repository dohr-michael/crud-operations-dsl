package org.dohrm.crud.operations.interfaces;

import java.util.concurrent.CompletableFuture;

/**
 * @author michaeldohr
 * @since 11/10/15
 */
public interface FinalAsyncCreateUpdateOperation<T> {

    CompletableFuture<T> saveAsync();
}
