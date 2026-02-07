# STAGES

## Purpose
This document is a planning document created before implementation begins. It outlines a staged implementation plan for the Recipe application. Each stage introduces a small, coherent set of concepts and responsibilities, allowing the project to grow incrementally while remaining testable and understandable at every step.

Stages are ordered intentionally and should be completed sequentially. The checklists below are updated throughout development to indicate completed work. Both the listed tasks and their checkboxes may be revised as the program evolves.

### Stage Completion Checklist
- [x] Stage 1: Core Recipe Model
- [x] Stage 2: Recipe Collection Management
- [x] Stage 3: Searching and Sorting
- [x] Stage 4: Shopping Cart Aggregation
- [x] Stage 5: Persistence (JSON I/O)
- [x] Stage 6: User Interface Integration
- [x] Stage 7: Refinement and Extension

---

## Stage 1: Core Recipe Model

**Goal**  
Implement the foundational data model and required behavior for a single recipe.

**Focus**
- Class design
- Validation
- Numeric scaling and formatting rules

### Design Tasks Checklist
- [x] Implement `Ingredient` class
- [x] Implement `Recipe` class with `addIngredient(String, double)`
- [x] Implement `scaleToServings(int)`
- [x] Implement `totalIngredientCount()`
- [x] Implement `toString()`
- [x] Ingredients stored in insertion order
- [x] Amount formatting rules applied correctly
- [x] Scaling updates amounts proportionally without rounding stored values
- [x] Invalid inputs do not corrupt internal state

**Progress:** Complete (Stage 1 fully implemented and tested)

---

## Stage 2: Recipe Collection Management

**Goal**  
Manage multiple recipes as a collection.

**Focus**
- Aggregation of domain objects
- Basic collection operations

### Design Tasks Checklist
- [x] Create `RecipeBook` class
- [x] Add recipes to collection
- [x] Remove recipes by name
- [x] Retrieve all recipes
- [x] Recipe list is never null
- [x] Removal logic correctly identifies recipes by name
- [x] No implicit sorting enforced at data level

**Progress:** Complete (RecipeBook class created with all required operations)

---

## Stage 3: Searching and Sorting

**Goal**  
Allow users to locate, filter, and view recipes efficiently using flexible search criteria and stable presentation-time sorting.

**Focus**
- Read-only operations on recipe collections
- Separation of storage, querying, and presentation logic
- Predictable and stable search and sort behavior

### Deliverables

#### Searching
- **Case-insensitive recipe name search**
  - Partial (substring) matches
  - Leading and trailing whitespace in queries is ignored
- **Ingredient-based search**
  - Find recipes containing at least one ingredient whose name matches the query
  - Case-insensitive, partial matching
- **Multi-token search**
  - Queries containing multiple tokens (e.g., `garlic oil`)
  - A recipe matches if *all tokens* match somewhere in the recipe
    (recipe name or ingredient names)
- Search operations return a new list and never modify stored recipes.

#### Sorting
- **Front-end sorting by recipe name**
  - Case-insensitive ordering
  - Supports ascending and descending order
- **Stable and deterministic output**
  - Secondary comparison used when names compare equal ignoring case
  - Sorting does not permanently reorder stored data

### Design Tasks Checklist
- [x] Case-insensitive name search with query trimming (`RecipeBook.searchByName`)
- [x] Ingredient-based search (`RecipeBook.searchByIngredient`)
- [x] Multi-token search (`RecipeBook.search`)
- [x] Front-end sorting by name A–Z and Z–A (`RecipeSorter.sortByName`)
- [x] Secondary sort key for deterministic ordering
- [x] Search operations return new list; do not mutate stored data
- [x] Sorting applied at presentation only; stored order unchanged
- [x] Create `Stage3Demo.java`

**Progress:** Complete

---

## Stage 4: Shopping Cart Aggregation

**Goal**  
Generate a combined ingredient list from multiple recipes.

**Focus**
- Derived data views
- Normalization and aggregation rules

### Deliverables

#### Shopping Cart Logic
- **Aggregate ingredients from multiple recipes**
  - Accept a collection of recipes as input
  - Produce a single combined ingredient list
- **Name normalization**
  - Case-insensitive matching of ingredient names
  - Ingredients with matching normalized names are combined
- **Amount summation**
  - Add quantities for ingredients with the same normalized name
  - Preserve ingredient units (if applicable)
- **Non-destructive operation**
  - Original recipes remain unchanged
  - Returns a new data structure representing the aggregated list

#### Output Formatting
- **Consistent amount display**
  - Follow the same formatting rules as Recipe output
  - Handle trailing zeros appropriately
- **Sorted presentation**
  - Ingredients displayed in a consistent order (e.g., alphabetical by name)

### Design Tasks Checklist
- [x] Implement ingredient aggregation across multiple recipes (`ShoppingCart.aggregate`)
- [x] Case-insensitive name normalization
- [x] Sum amounts for matching ingredient names
- [x] Non-destructive: original recipes unchanged
- [x] Output formatting follows Recipe amount rules
- [x] Sorted presentation (alphabetical by ingredient name)
- [x] Create `Stage4Demo.java`

**Progress:** Complete

---

## Stage 5: Persistence (JSON I/O)

**Goal**  
Persist and restore application state using files.

**Focus**
- Serialization
- Validation on load
- Isolation of I/O logic

### Deliverables

#### JSON Writer
- **Serialize RecipeBook to JSON**
  - Convert recipe collection to valid JSON format
  - Preserve all recipe and ingredient data
- **Maintain structure**
  - Recipe order is preserved
  - Ingredient order within each recipe is preserved
- **Numeric precision**
  - Store ingredient amounts without formatting
  - Preserve full numeric precision for accurate restoration
- **File output**
  - Write JSON to specified file path
  - Handle I/O errors gracefully

#### JSON Reader
- **Deserialize JSON to RecipeBook**
  - Parse valid JSON files into recipe objects
  - Reconstruct recipe collection with original ordering
- **Validation**
  - Validate JSON structure before loading
  - Check for required fields (recipe name, servings, ingredients)
  - Validate data types and value ranges
- **Error handling**
  - Detect and report malformed JSON
  - Handle missing or invalid fields according to documented policy (see Validation Policy in [docs/DATA_MODEL.md](docs/DATA_MODEL.md))
  - Provide meaningful error messages for debugging

### Design Tasks Checklist
- [x] Implement JSON writer (`RecipeJsonStore.save`)
- [x] Implement JSON reader (`RecipeJsonStore.load`)
- [x] Preserve recipe and ingredient order
- [x] Store numeric values unformatted (full precision)
- [x] Validate loaded data per DATA_MODEL policy
- [x] Handle invalid data with clear error messages (all-or-nothing)
- [x] Extra/unknown fields ignored on load
- [x] Create `Stage5Demo.java`

**Progress:** Complete

---

## Stage 6: User Interface Integration

**Goal**  
Provide a usable front-end for interacting with the system.

**Focus**
- Coordinating existing components
- Clear separation between UI and model logic

### Deliverables

#### User Interface
- **Console-based interaction**
  - Command-line menu or prompt-based interface
  - Clear display of available commands
  - User-friendly input handling

#### Core Commands
- **List all recipes**
  - Display all recipes in the collection
  - Apply sorting (using RecipeSorter)
  - Show summary information (name, servings)
- **Search recipes**
  - Search by recipe name (case-insensitive, partial match)
  - Search by ingredient
  - Multi-token search support
  - Display search results with count
- **View recipe details**
  - Select a recipe by name or index
  - Display full recipe with all ingredients and amounts
  - Show formatted output using Recipe.toString()
- **Build shopping cart**
  - Select multiple recipes for aggregation
  - Generate combined ingredient list
  - Display aggregated results

#### Data Management Commands
- **Load recipes from file**
  - Specify file path
  - Load and validate JSON data
  - Report success or errors
- **Save recipes to file**
  - Specify output file path
  - Serialize current recipe collection
  - Confirm successful save

#### Input Handling
- **Robust parsing**
  - Handle invalid commands gracefully
  - Provide helpful error messages
  - Allow users to retry or exit

### Example CLI Output

```
=== Recipe Manager ===
1. List all recipes
2. Search recipes
3. View recipe details
4. Build shopping cart
5. Load recipes from file
6. Save recipes to file
7. Exit

Enter command: 1

All Recipes (3):
1. Pancakes (4 servings)
2. Garlic Bread (2 servings)
3. Tomato Soup (6 servings)

Enter command: 2
Enter search query: garlic

Search Results (1):
1. Garlic Bread (2 servings)

Enter command: 3
Enter recipe name: Garlic Bread

=== Garlic Bread ===
Servings: 2

Ingredients:
  - Bread: 1
  - Garlic: 4
  - Butter: 0.5
  - Parsley: 0.25

Enter command: 4
Enter recipe names (comma-separated): Pancakes, Garlic Bread

=== Shopping Cart ===
Aggregated Ingredients:
  - Bread: 1
  - Butter: 1.5
  - Eggs: 2
  - Flour: 2
  - Garlic: 4
  - Milk: 1.5
  - Parsley: 0.25

Enter command: 5
Enter file path: recipes.json
Successfully loaded 3 recipes from recipes.json

Enter command: 6
Enter file path: my_recipes.json
Successfully saved 3 recipes to my_recipes.json

Enter command: 7
Goodbye!
```

### Design Tasks Checklist
- [x] Create console-based UI (`ConsoleUI`)
- [x] List all recipes (sorted)
- [x] Search recipes (name and ingredient, multi-token)
- [x] View recipe details
- [x] Build shopping cart from selected recipes
- [x] Load recipes from file
- [x] Save recipes to file
- [x] UI depends on service layer; robust input handling
- [x] Create `Stage6Demo.java`

**Progress:** Complete

---

## Stage 7: Refinement and Extension

**Goal**  
Stabilize and improve the system.

**Focus**
- Code quality
- Edge cases
- Documentation

**Possible Additions**
- Improved error messages
- Additional search options
- Ingredient-level search
- Unit tests for aggregation and persistence
- Optional identifiers for recipes
- Refactor classes into package `cmps357.sp26` to align namespaces

### Design Tasks Checklist
- [x] Refactor Recipe to use `List<Ingredient>` instead of parallel lists
- [x] Improve error messages in `addIngredient`
- [x] Update RecipeTest to use public APIs (remove reflection)
- [x] Existing behavior unchanged unless explicitly extended
- [x] Documentation consistent with implementation

**Progress:** Complete

---

## Guiding Principle
At the end of every stage, the application should:
- Compile
- Run
- Demonstrate clear, correct behavior for all implemented features

No stage should rely on partially implemented functionality from a later stage.

