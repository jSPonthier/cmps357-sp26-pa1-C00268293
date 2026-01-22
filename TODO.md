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

## Remaining / Suggested tasks
- Address SPEC vs code inconsistency:
  - SPEC requires `toPrettyString()` but code uses `toString()`.
  - Options: add `public String toPrettyString()` delegating to `toString()`, or update `SPEC.md` to expect `toString()`.
- Add Javadoc for remaining public methods (`toString()` / `scaleToServings()`) for clarity.
- Add minimal unit tests to assert:
  - `totalIngredientCount()` returns correct count.
  - `scaleToServings()` scales amounts correctly and updates `servings`.
  - `formatAmount()` produces expected strings for representative inputs.
- Consider small API/refactor improvements:
  - Consider a consistency change for `toPrettyString()` vs. `toString()` to satisfy original SPEC. Perhaps a simple wrapper function.
  - Move ingredient pair into a small `Ingredient` class (`name`, `amount`) instead of parallel lists (optional improvement for readability/maintenance).
  - Use `System.err` for debug messages in `addIngredient` or document current behavior.

## Verification commands
To compile and run locally:

```bash
javac -d bin src\*.java
java -cp bin Main
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
