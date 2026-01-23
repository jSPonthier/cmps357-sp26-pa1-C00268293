# Specifications 

## Goal
Implement the missing behavior in `Recipe.java` so that `Main` produces the required output.

## Recipe Model
A recipe has:
- a name (String)
- servings (int)
- a list of ingredients (each ingredient has a name and an amount)

### Ingredient representation rules
- Store ingredients in the order they are added.
- The amount is a `double`.
- When printing amounts:
  - if the amount is an integer value (e.g., 2.0), print without decimals: `2`
  - otherwise print with up to 2 decimals, trimming trailing zeros (e.g., 1.5 → `1.5`, 0.25 → `0.25`, 1.333 → `1.33`)

## Required Methods (in Recipe.java)
You must implement:
- `addIngredient(String ingredientName, double amount)`
- `scaleToServings(int newServings)`
- `toString()`
- `toPrettyString()`
- `totalIngredientCount()`

### addIngredient
Adds an ingredient entry.

### totalIngredientCount
Returns how many ingredient entries exist.

### scaleToServings
Scales all ingredient amounts proportionally.
- If current servings is `s` and new servings is `n`,
  multiply every amount by `n / s` (as a double).
- Update servings to `newServings`.
- If `newServings <= 0`, throw `IllegalArgumentException`.

### toString
Returns a multi-line formatted string:

Format:
<name> (serves <servings>)
- <amount> <ingredient>
- <amount> <ingredient>
...

### toPrettyString
Returns a user-friendly multi-line string representation of the recipe.
Currently delegates to `toString()` for Day-1 simplicity.

## Recipe Collection Features
Beyond a single recipe, the application must support:
- adding recipes to a recipe list
- removing recipes from the list
- searching recipes by name (case-insensitive, partial matches)
- sorting recipes by name when presenting them
- generating a shopping cart that aggregates ingredients from multiple recipes
- reading and writing the recipe list to a file using JSON format

Details for these features are defined in `ARCHITECTURE.md`, `DATA_MODEL.md`, and `STAGES.md`.


## Previous Expected Output
When you run `Main`, output must be exactly:

```
Pasta Aglio e Olio (serves 2)
- 200 spaghetti (g)
- 3 garlic cloves
- 0.25 olive oil (cup)
- 0.5 red pepper flakes (tsp)
- 0.25 parsley (cup)

Ingredient entries: 5

After scaling to 5 servings:

Pasta Aglio e Olio (serves 5)
- 500 spaghetti (g)
- 7.5 garlic cloves
- 0.63 olive oil (cup)
- 1.25 red pepper flakes (tsp)
- 0.63 parsley (cup)
```

Notes:
- The scaled olive oil and parsley values are 0.625, which should print as `0.63` based on rounding to 2 decimals.
