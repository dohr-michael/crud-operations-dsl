# crud-operations-dsl
Based to java 8, strategy pattern. Help to create crud operation with some validations.

## Sample

### Read

* Without validation - will throws a `ResourceNotFoundException` if the result of the `Supplier` is an empty `Optional`.
  
  * Sync


          CrudOperations
              .readOf(() -> Optional.of("ResultOfYourDatabaseCall"))
              .read();
  * Async
  
  
          CrudOperations
              .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.of("ResultOfYourDatabaseCall")))
              .read();

* With security validation - will call the `Predicate` with the result of the `Supplier` (if not empty). If the validation is wrong will throws an `UnauthorisedAccessException`

  * Sync


          CrudOperations
              .readOf(() -> Optional.of("ResultOfYourDatabaseCall"))
              .withSecurityCheck(s -> true)
              .read();
  * Async


          CrudOperations
              .readAsyncOf(() -> CompletableFuture.completedFuture(Optional.of("ResultOfYourDatabaseCall")))
              .withSecurityCheck(s -> true)
              .read();
