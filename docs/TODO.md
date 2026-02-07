# TODO: cmps357-sp26-pa1-template

## Work Completed

### Stage 1: Core Recipe Model (COMPLETE)
- [x] Implemented `totalIngredientCount()` with Javadoc
- [x] Implemented `scaleToServings(int)` with validation, scaling, and Javadoc
- [x] Implemented `toString()` with Javadoc (prints name, servings, and ingredient lines)
- [x] Implemented `formatAmount(double)` to print integers without decimals and otherwise up to 2 decimals
- [x] Verified `Main` runs and output matches `SPEC.md` expected output
- [x] Added minimal tests in test/RecipeTest.java covering count, scaling, and amount formatting
- [x] Added `toPrettyString()` delegating to `toString()`
- [x] Switched debug prints in `addIngredient` from `System.out` to `System.err`
- [x] Expanded tests in test/RecipeTest.java to cover `toPrettyString()`, additional scaling edge-cases
- [x] All tests pass locally via the existing build tasks
- [x] Updated docs/SPEC.md to document `toPrettyString()` method

### Stage 2: Recipe Collection Management (COMPLETE)
- [x] Created `Ingredient` class (src/Ingredient.java)
  - Value object with name and amount fields
  - Validation for non-null/non-blank names and positive amounts
  - Immutable with scale() method for creating scaled copies
- [x] Created `RecipeBook` class (src/RecipeBook.java)
  - Add recipes to collection
  - Remove recipes by name (case-sensitive)
  - Retrieve all recipes
  - Search by name (case-insensitive, partial matches)
- [x] Added comprehensive tests (test/IngredientTest.java, test/RecipeBookTest.java)
- [x] All Stage 2 acceptance criteria met

### Stage 3: Searching and Sorting (COMPLETE)
- [x] Implemented case-insensitive name search in `RecipeBook`
- [x] Created `RecipeSorter` utility (`src/RecipeSorter.java`) for sorting recipes by name
- [x] Added tests (test/RecipeSorterTest.java)
- [x] Created `Stage2Demo.java` and `Stage3Demo.java` to demonstrate functionality
- [x] Implement ingredient-based search (`searchByIngredient`)
- [x] Implement multi-token search (`search`)
- [x] Add stable secondary sort key for deterministic ordering
- [x] Ensure search operations return a new list and never mutate stored recipes

### Documentation Updates
- [x] Updated docs/STAGES.md to reflect progress on Stages 1, 2, and 3
- [x] Updated docs/SPEC.md with current API

## Next Steps (Future Stages)

### Stage 4: Shopping Cart Aggregation (COMPLETE)
- [x] Implement ingredient aggregation across multiple recipes (`ShoppingCart.aggregate`)
- [x] Handle ingredient normalization (case-insensitive matching)
- [x] Sum amounts for matching ingredients
- [x] Apply formatting rules to aggregated amounts

### Stage 5: Persistence (JSON I/O) (COMPLETE)
- [x] Implement JSON writer for recipe lists (`RecipeJsonStore.save`)
- [x] Implement JSON reader for recipe lists (`RecipeJsonStore.load`)
- [x] Preserve recipe and ingredient order
- [x] Validate loaded data (per DATA_MODEL Validation Policy)
- [x] Handle errors gracefully (IOException with descriptive messages)

### Stage 6: User Interface Integration (COMPLETE)
- [x] Create console-based UI (`ConsoleUI`)
- [x] Commands for listing, searching, viewing recipes
- [x] Shopping cart generation
- [x] Load/save functionality

### Stage 7: Refinement and Extension (COMPLETE)
- [x] Refactored Recipe to use Ingredient class (List<Ingredient>)
- [x] Improved error messages in addIngredient
- [x] RecipeTest updated to use public APIs (no reflection)
- [x] Code quality: aligned Recipe with DATA_MODEL (ingredients as List<Ingredient>)

## Notes
- Recipe class now uses List<Ingredient> per DATA_MODEL
- Ingredient class is available for future refactoring if needed
- All existing functionality remains working and tested
- Stage 2 scaffolding complete with RecipeBook, Ingredient, and search/sort utilities

## Verification commands
To compile and run locally:

```bash
javac -d bin src\*.java
java -cp bin Main

# compile and run tests (no external deps)
javac -d bin -cp bin src\*.java test\RecipeTest.java
java -cp bin RecipeTest
```

## File references
- Implementation: [src/Recipe.java](src/Recipe.java)
- Runner: [src/Main.java](src/Main.java)
- Spec: [SPEC.md](SPEC.md)
- README: [README.md](README.md)


---

If you want, I can (pick one):
- add `toPrettyString()` delegating to `toString()` now,
- update `SPEC.md` to match the current implementation,
- add unit tests under a `test/` folder, or
- add Javadoc for `toString()` and other public methods.
