# INSTRUCTIONS.MD stages 3-7
_Exported on 2/7/2026 at 02:59:12 CST from Cursor (2.4.28)_

---

**User**

We're gonna be working within this repository, using the 'INSTRUCTIONS.MD' file we will work through stages 3-7. Starting with Stage 3. @INSTRUCTIONS.md (17-63) 

---

**Cursor**

Reviewing the instructions and codebase for Stage 3.


Checking Stage2Demo format and how Recipe exposes ingredients:


Checking the docs and specs for the architecture:


Implementing Stage 3 requirements.


Implementing Stage 3: adding `getIngredientNames()` to `Recipe`, then extending search and sort capabilities.


Updating RecipeSorter to add Z–A sort and a secondary sort key.


Updating docs/STAGES.md to mark Stage 3 complete and ensure tests still pass.


Stage 3 is implemented. Summary of what was done:

## Stage 3: Advanced Searching and Sorting

### Changes

**1. Recipe.java**
- Added `getIngredientNames()` to return a copy of ingredient names for search, without exposing internal state.

**2. RecipeBook.java**
- **searchByName**: Trims leading/trailing whitespace from the query.
- **searchByIngredient** (new): Finds recipes with at least one ingredient that matches the query (case-insensitive partial match).
- **search** (new): Multi-token search (e.g. `garlic oil`) where every token must appear in the recipe name or any ingredient name.

**3. RecipeSorter.java**
- **sortByName(List)**: Still sorts A–Z (delegates to the new overload).
- **sortByName(List, boolean)**: New overload; `ascending = true` for A–Z, `false` for Z–A.
- Secondary sort: When names compare equal ignoring case, uses case-sensitive name to keep order deterministic.

**4. Stage3Demo.java**
- Demonstrates name search, ingredient search, multi-token search, A–Z and Z–A sort, and that stored order is unchanged after search/sort.

**5. docs/STAGES.md**
- Updated to mark Stage 3 as complete.

**6. .vscode/launch.json**
- Added a “Run Stage3Demo” configuration.

### Requirements satisfied

| Requirement | Implementation |
|------------|----------------|
| Case-insensitive name search, partial match | `searchByName` |
| Trim leading/trailing whitespace | `searchByName`, `searchByIngredient`, `search` |
| Ingredient-based search | `searchByIngredient` |
| Multi-token queries (all tokens must match) | `search` |
| Search is read-only, returns new list | All search methods |
| Sort A–Z / Z–A | `sortByName(recipes, true/false)` |
| Secondary sort key | Case-sensitive name when case-insensitive names are equal |
| No storage mutation | Sorting returns a new list |

You can run **Stage3Demo** from the debug/run menu to exercise these features. If you want, we can move on to Stage 4 (Shopping Cart Aggregation) next.

---

**User**

@c:\Users\Administrator\.cursor\projects\c-Users-Administrator-CursorProjects-CMPS-357-cmps357-sp26-pa1-C00268293\terminals\2.txt:23-36 

---

**Cursor**

`javac` not being recognized means the JDK either isn’t installed or its `bin` directory isn’t on your PATH.

### What’s going on
`javac` is the Java compiler. If it’s not found, your shell can’t run it, so the build task fails.

### What to do

1. **Check if Java is installed**
   - Open a new PowerShell and run:
   - `java -version`
   - `where.exe java`
   - If both fail, the JDK isn’t installed or isn’t on PATH.

2. **Install a JDK** (if needed)
   - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/
   - Use a JDK (not only JRE) and install it.

3. **Add Java to PATH**
   - Find the install location, e.g.:
     - `C:\Program Files\Eclipse Adoptium\jdk-17.x.x\bin`
     - `C:\Program Files\Java\jdk-21\bin`
   - Add that `bin` folder to the system PATH:
     - Search Windows for “Environment Variables”
     - Edit “Path” under System variables
     - Add the `bin` folder above
   - Restart VS Code/Cursor and open a new terminal.

4. **Check Cursor/VS Code Java setup**
   - Install the “Extension Pack for Java” (or equivalent) if you use it to build/run.
   - If it finds a JDK, it can manage compilation; otherwise it still needs a working JDK on the system.

Once `javac` is on PATH (or the IDE’s configured JDK includes it), the build command should work. If you share whether Java appears when you run `java -version` and `where.exe java`, I can suggest more specific next steps.

---

**User**

thank you Ive got everything working now we can move onto stage 4:
@INSTRUCTIONS.md (67-76) @TODO.md (47-51) 

---

**Cursor**


Implementing Stage 4: Shopping Cart Aggregation. Reviewing requirements and current structure:



Implementing Stage 4: adding `getIngredientAmounts()` to Recipe, creating `ShoppingCart`, and adding `Stage4Demo`.



Summary of what was implemented for Stage 4:

## Stage 4: Shopping Cart Aggregation

### Changes made

**1. Recipe.java**
- `getIngredientAmounts()` – returns a defensive copy of the amounts list for aggregation.

**2. ShoppingCart.java**
- Static `aggregate(List<Recipe> recipes)`:
  - Merges ingredients from multiple recipes
  - Normalizes names (trim + case-insensitive)
  - Sums amounts for matching names
  - Uses display name from the first occurrence
  - Applies SPEC.md formatting (integers without decimals; otherwise up to 2 decimals)
  - Returns a list of strings in alphabetical order by ingredient name

**3. Stage4Demo.java**
- Demonstrates:
  - Aggregating Pancakes and Chocolate Cake (shared flour and eggs)
  - Summing amounts (e.g. flour 2+2=4, eggs 2+3=5)
  - Alphabetic output
  - Original recipes left unchanged

**4. Docs**
- `docs/STAGES.md` – Stage 4 marked complete
- `TODO.md` – Stages 3 and 4 checkboxes updated
- `.vscode/launch.json` – added Run Stage4Demo configuration

### Usage example

```java
List<Recipe> selected = List.of(pancakes, chocolateCake);
List<String> cart = ShoppingCart.aggregate(selected);
for (String line : cart) {
    System.out.println("- " + line);  // e.g. "- 4 flour (cup)", "- 5 eggs", etc.
}
```

Run **Stage4Demo** from the Run/Debug menu to exercise the feature. Should we continue with Stage 5 (JSON persistence)?

---

**User**

Next we will start work on stage 5: Persistence:
@INSTRUCTIONS.md (80-88) 
@TODO.md (53-58) .

---

**Cursor**


Reviewing the full Stage 5 requirements and project structure:



Implementing Stage 5: creating `RecipeJsonStore` with save/load functionality and a minimal JSON implementation (no external dependencies).



Updating the parser to skip unknown top-level keys per the Validation Policy and fixing potential issues.



Stage 5 is finished. Here’s a concise summary:

## Stage 5: Persistence (JSON I/O)

### Implemented

**1. RecipeJsonStore.java**
- **`save(RecipeBook book, String filePath)`** – writes the recipe book to a JSON file
  - Keeps recipe and ingredient order
  - Stores amounts without formatting
  - Handles strings with proper escaping
- **`load(String filePath)`** – reads a JSON file into a `RecipeBook`
  - Validates per `DATA_MODEL.md` (required fields, types, constraints)
  - Ignores unknown fields
  - Fails atomically (no partial load) with clear error messages

**2. JSON format**
- Matches `DATA_MODEL.md`:
```json
{
  "recipes": [
    {
      "name": "Recipe Name",
      "servings": 4,
      "ingredients": [
        { "name": "Flour", "amount": 2.5 },
        { "name": "Sugar", "amount": 1.0 }
      ]
    }
  ]
}
```

**3. Stage5Demo.java**
- Demonstrates save and load
- Verifies order is preserved

**4. Docs and tooling**
- `docs/STAGES.md` – Stage 5 marked complete
- `TODO.md` – checkboxes updated
- `.vscode/launch.json` – “Run Stage5Demo” configuration added

### Design choices

- No external JSON libraries; custom parser for this format
- File I/O confined to `RecipeJsonStore`
- Validation and error handling follow the Validation Policy (all-or-nothing, clear errors)
- Uses reflection to read `Recipe`’s private `name` and `servings` (same pattern as `RecipeBook`)

Run **Stage5Demo** from the Run menu to verify. If you’d like, we can move on to Stage 6 (User Interface Integration).

---

**User**

Great work. Now we move onto stage 6: User Interface Integration:
 @INSTRUCTIONS.md (91-101) 
@TODO.md (60-65) 

---

**Cursor**


Reviewing Stage 6 requirements and architecture:



Implementing Stage 6: adding `getName()` and `getServings()` to Recipe, then creating ConsoleUI.



Adding a `clear()` method to RecipeBook; `getAllRecipes()` returns a copy, so we can't clear the internal list.



Summary of the Stage 6 implementation:

## Stage 6: User Interface Integration

### Changes

**1. Recipe.java**
- `getName()` – returns the recipe name
- `getServings()` – returns the servings count

**2. RecipeBook.java**
- `clear()` – removes all recipes (used when loading from file)
- Refactored to use `r.getName()` instead of reflection

**3. RecipeSorter.java & RecipeJsonStore.java**
- Switched to `Recipe.getName()` / `Recipe.getServings()` instead of reflection

**4. ConsoleUI.java**
- Menu-driven console interface with 7 commands:
  1. **List all recipes** – sorted A–Z via RecipeSorter
  2. **Search recipes** – multi-token search via RecipeBook.search()
  3. **View recipe details** – by name, full output via Recipe.toString()
  4. **Build shopping cart** – comma-separated recipe names → ShoppingCart.aggregate()
  5. **Load recipes from file** – RecipeJsonStore.load()
  6. **Save recipes to file** – RecipeJsonStore.save()
  7. **Exit**

**5. Stage6Demo.java**
- Starts the UI with a pre-populated RecipeBook (Pasta, Pancakes)

**6. Docs & launch config**
- `docs/STAGES.md` – Stage 6 marked complete
- `TODO.md` – checkboxes updated
- `.vscode/launch.json` – added Run ConsoleUI and Run Stage6Demo

### Architecture

- UI uses service classes: RecipeBook, RecipeSorter, ShoppingCart
- File I/O via RecipeJsonStore
- No direct access to internal data structures
- Invalid input produces clear messages without crashing

### How to run

- **Interactive UI:** Run `ConsoleUI` (or “Run ConsoleUI” in the debug menu)
- **Demo (pre-loaded recipes):** Run `Stage6Demo`

---

**User**

Great work again.
Moving onto Step 7
@TODO.md (66-71) 

---

**Cursor**


Reviewing Stage 7 requirements in detail:



Checking files that depend on Recipe's ingredient API:



Implementing Stage 7: refactoring Recipe to use Ingredient, improving error messages, and applying code-quality updates.



Summary of the Stage 7 implementation:

## Stage 7: Refinement and Extension

### Changes made

**1. Recipe.java – use `Ingredient` class**
- Replaced parallel lists (`ingredientNames`, `ingredientAmounts`) with `List<Ingredient>`
- `addIngredient(String, double)` creates `new Ingredient(name, amount)` and adds to the list
- `getIngredientNames()` / `getIngredientAmounts()` now iterate over `ingredients`
- `scaleToServings()` uses `Ingredient.scale(factor)` to create scaled copies
- Implementation matches `DATA_MODEL.md` (`ingredients : List<Ingredient>`)

**2. Error messages in `addIngredient`**
- Replaced `"[DEBUG] Invalid ingredient name..."` with `"Invalid ingredient: name must be non-empty. Ingredient not added."`
- Replaced `"[DEBUG] Invalid ingredient amount..."` with `"Invalid ingredient: amount must be greater than 0. Ingredient not added."`

**3. RecipeTest.java – use public APIs**
- Removed reflection usage (`ingredientAmounts`, `servings`, `formatAmount`)
- `getIngredientAmounts(r)` replaced with `r.getIngredientAmounts()`
- `getServings(r)` replaced with `r.getServings()`
- `formatAmount` tested via `toString()` output instead of reflection

**4. Docs**
- `docs/STAGES.md` – Stage 7 marked complete
- `TODO.md` – notes updated (Recipe now uses `List<Ingredient>`)

### Verification

- All tests pass (RecipeTest, RecipeBookTest, RecipeSorterTest)
- Main output matches `SPEC.md`
- Stage5Demo save/load works
- Public API unchanged; `RecipeBook`, `ShoppingCart`, `RecipeJsonStore`, `ConsoleUI` work without code changes

---

**User**

Now we're gonna focus on the documentation. I'll give a rubric for how each individual file should look. starting with STAGES.MD, look over the file once more to ensure it is compliant with the documentation standards outlined here:
A planning document created before implementation begins. It must include a checklist of design tasks and be updated throughout development to indicate completed work. Both the listed tasks and their checkboxes may be revised as the program evolves.

---

**Cursor**


Converting STAGES.md into a planning document with explicit checkbox checklists. It currently uses prose and ✓ for progress and lacks the `- [ ]` / `- [x]` checklist format.



Updates to `STAGES.md` to match the rubric:

## Changes made

1. **Purpose section**
   - Stated it is a planning document created before implementation.
   - Clarified that checklists are updated during development.
   - Noted that tasks and checkboxes may change as the program evolves.

2. **Stage Completion Checklist**
   - Added a top-level checklist with all 7 stages marked complete.

3. **Design Tasks Checklists per stage**
   - Replaced Acceptance Criteria and Progress sections with:
     - Checkbox-style design tasks (`- [x]`).
     - Items that can be checked off during development.
     - A short **Progress:** line summarizing status.

4. **Checkbox format**
   - Used `- [x]` for completed tasks and `- [ ]` for any remaining tasks.
   - Tasks are written so they can be checked off as work is done.

5. **Task revision**
   - Kept the existing deliverables as reference, with the new checklists as the main tracking structure.

The document now:
- Is described as a planning document used from before implementation.
- Includes clear design task checklists.
- Shows completed work with checkboxes.
- Allows revising tasks and checkboxes as the program evolves.

---

**User**

next update the README.MD as necessary along these lines:
README.md: Combined setup and execution instructions. Provide steps to create a working environment in Visual Studio Code (for example, a requirements.txt for Python or a Makefile or CMakeLists.txt for C++), then list the commands or procedures needed to configure the environment, compile the program, and run it after cloning.

---

**Cursor**


Updated the README to match the rubric. Summary:

## README.md updates

**1. Setup and execution (top of file)**  
- Combined setup and execution in a single section near the top.

**2. Step-by-step environment setup**  
- **Prerequisites** – Java JDK 17+, VS Code, Git.  
- **Clone** – `git clone` and `cd`.  
- **Configure VS Code** – Open folder, install Extension Pack for Java, set up `JAVA_HOME` or JDK in settings, use workspace tasks/launch configs.

**3. Build configuration**  
- Documented `.vscode/tasks.json` and `.vscode/launch.json` as the build and run setup for this Java project (instead of `requirements.txt` or a Makefile).

**4. Compile instructions**  
- VS Code: **Run Build Task** (`Ctrl+Shift+B`).  
- Command line:  
  - `javac -d bin -cp bin src/*.java`  
  - `javac -d bin -cp bin src/*.java test/*.java` for tests.

**5. Run instructions**  
- VS Code: **Run and Debug** panel with listed configurations.  
- Command line: `java -cp bin Main`, `java -cp bin ConsoleUI`, `java -cp bin RecipeTest`, etc.

**6. Project structure**  
- Updated repo structure to match the current layout (`src/`, `test/`, `.vscode/`).

**7. Other content**  
- Kept prerequisites, workflow, and documentation links; reduced duplication and moved details into the setup section.

---

**User**

can you try that again while focusing more on adding to the @README.md than editing what's already there

---

**Cursor**


Added a new **"Setup and Execution"** section between Project Requirements and Syllabus README Note. Existing sections were left as-is. The new section covers:

- **Create a Working Environment (VS Code)** – clone, open in VS Code, install Extension Pack for Java, configure JDK, and use `.vscode/tasks.json` and `.vscode/launch.json`
- **Compile** – VS Code build task and `javac` commands
- **Run** – VS Code Run and Debug options and `java` commands

---

**User**

next we're gonna worko on EXAMPLES.md: A short demonstration of expected program behavior. For graphical output, include screenshots stored in the repository and reference them using relative paths. For command-line programs, captured terminal input and output may be pasted in place of screenshots.

---

**Cursor**


Added `docs/EXAMPLES.MD` with:

1. **Main** – Sample output for recipe display and scaling  
2. **ConsoleUI** – Example session showing list, search, view details, shopping cart, load, save, and exit  
3. **Stage5Demo** – Example output for JSON save and load  

Terminal output is pasted as specified for command-line programs, and there are no screenshots since the app is not graphical.

