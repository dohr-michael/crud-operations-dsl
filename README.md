# crud-operations-dsl
Based to java 8. Help to create crud operation with some validations.

## Sample

### Read

* Without validation - will throws a `ResourceNotFoundException` if the result of the `Supplier` is an empty `Optional`.
  
  * Start by Sync Supplier


          CrudOperations
              .readOf(() -> Optional<ResultType>)
              .read();
  * Start by Async Supplier
  
  
          CrudOperations
              .readAsyncOf(() -> CompletableFuture<Optional<ResultType>>))
              .readAsync();

* With security validation - will call the `Predicate` with the result of the `Supplier` (if not empty). If the validation is wrong will throws an `UnauthorisedAccessException`

  * Start by Sync Supplier


          CrudOperations
                .readOf(() -> Optional<ResultType>)
                .withSecurityCheck(Predicate<ResultType>)
                .read();

          ----

          CrudOperations
                .readOf(() -> Optional<ResultType>)
                .withSecurityCheck(Function<ResultType, CompletableFuture<Boolean>>)
                .readAsync();

  * Start by Async Supplier


          CrudOperations
                .readAsyncOf(() -> CompletableFuture<Optional<ResultType>>)
                .withSecurityCheck(Predicate<ResultType>)
                .readAsync();

          ----

          CrudOperations
                .readAsyncOf(() -> CompletableFuture<Optional<ResultType>>)
                .withSecurityCheck(Function<ResultType, CompletableFuture<Boolean>>)
                .readAsync();

### Creation

`Not yet implemented`

### Update

`Not yet implemented`

### Delete

`Not yet implemented`