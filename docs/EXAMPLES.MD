# Examples

A short demonstration of expected program behavior. Because this is a command-line application, captured terminal input and output are shown below.

---

## Main – Recipe Display and Scaling

Running `java -cp bin Main` demonstrates the core Recipe model: creating a recipe, displaying ingredients, and scaling servings.

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

---

## ConsoleUI – Recipe Manager Session

Running `java -cp bin ConsoleUI` (or `java -cp bin Stage6Demo` with pre-loaded recipes) starts the interactive Recipe Manager. Below is a sample session showing list, search, view details, shopping cart, load, save, and exit.

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

All Recipes (2):
1. Pasta Aglio e Olio (2 servings)
2. Pancakes (4 servings)

Enter command: 2
Enter search query: garlic

Search Results (1):
1. Pasta Aglio e Olio (2 servings)

Enter command: 3
Enter recipe name: Pasta Aglio e Olio

Pasta Aglio e Olio (serves 2)
- 200 spaghetti (g)
- 3 garlic cloves
- 0.25 olive oil (cup)

Enter command: 4
Enter recipe names (comma-separated): Pancakes, Pasta Aglio e Olio

=== Shopping Cart ===
Aggregated Ingredients:
  - 2 eggs
  - 2 flour (cup)
  - 3 garlic cloves
  - 1.5 milk (cup)
  - 0.25 olive oil (cup)
  - 200 spaghetti (g)

Enter command: 5
Enter file path: recipes.json
Successfully loaded 3 recipes from recipes.json

Enter command: 6
Enter file path: my_recipes.json
Successfully saved 3 recipes to my_recipes.json

Enter command: 7
Goodbye!
```

---

## Stage5Demo – Save and Load

Running `java -cp bin Stage5Demo` demonstrates persistence: saving a RecipeBook to JSON and loading it back.

```
=== Stage 5 Demo: Persistence (JSON I/O) ===

Original RecipeBook (2 recipes):
- Pasta Aglio e Olio (serves 2)
- Pancakes (serves 4)

Saved to demo_recipes.json

Loaded from demo_recipes.json (2 recipes):
- Pasta Aglio e Olio (serves 2)
- Pancakes (serves 4)

Order preserved: true

=== Stage 5 Demo Complete ===
```
