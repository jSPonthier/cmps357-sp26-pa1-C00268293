# TODO: cmps357-sp26-first-example

## Implementation Plan (from initial outline)
- Create implementation plan for `src/Recipe.java`.
- Inspect `src/Recipe.java` to identify TODOs and missing methods.
- Design ingredient storage (parallel lists used for Day-1 simplicity).
- Implement methods one at a time:
  - `addIngredient(String, double)` (already present)
  - `totalIngredientCount()`
  - `scaleToServings(int)`
  - `toString()` / `toPrettyString()` (print formatted recipe)
  - `formatAmount(double)` (helper for printing amounts)
- Verify by compiling and running `Main` and matching `SPEC.md` expected output.

## Work completed
- Implemented `totalIngredientCount()` with Javadoc.
- Implemented `scaleToServings(int)` with validation and scaling.
- Implemented `toString()` (prints name, servings, and ingredient lines).
- Implemented `formatAmount(double)` to print integers without decimals and otherwise up to 2 decimals (rounding as specified).
- Verified `Main` runs and output matches `SPEC.md` expected output.
- Added minimal tests in [test/RecipeTest.java](test/RecipeTest.java) covering count, scaling, and amount formatting.

## Remaining / Suggested tasks
- Close SPEC/API gap: add `public String toPrettyString()` delegating to `toString()` and document both in code and [`docs/SPEC.md`](docs/SPEC.md) if needed.
- Add Javadoc for the remaining public methods in [src/Recipe.java](src/Recipe.java) (`toString()`, `scaleToServings(int)`) for clarity.
- Tidy `addIngredient`: drop or move debug prints to `System.err`; optionally return a boolean to signal rejection without throwing.
- Optional future cleanup: replace parallel lists with a small `Ingredient` value object; stub upcoming classes (`RecipeBook`, search/sort helpers, shopping cart aggregator, JSON store, console UI) with signatures only to align with [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

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
