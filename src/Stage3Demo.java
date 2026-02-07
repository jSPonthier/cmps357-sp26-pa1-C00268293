import java.util.List;

/**
 * Demo for Stage 3 functionality - Advanced searching and sorting.
 *
 * <p>Demonstrates:
 * - Case-insensitive name search with whitespace trimming
 * - Ingredient-based search
 * - Multi-token search (all tokens must match in name or ingredients)
 * - Sort by name A–Z and Z–A
 * - Secondary sort key for deterministic output
 * - Read-only operations (no storage mutation)
 */
public class Stage3Demo {
    public static void main(String[] args) {
        System.out.println("=== Stage 3 Demo: Advanced Searching and Sorting ===\n");

        // Build recipe book
        RecipeBook book = new RecipeBook();

        Recipe pasta = new Recipe("Pasta Aglio e Olio", 2);
        pasta.addIngredient("spaghetti (g)", 200);
        pasta.addIngredient("garlic cloves", 3);
        pasta.addIngredient("olive oil (cup)", 0.25);
        pasta.addIngredient("red pepper flakes (tsp)", 0.5);

        Recipe pancakes = new Recipe("Pancakes", 4);
        pancakes.addIngredient("flour (cup)", 2);
        pancakes.addIngredient("eggs", 2);
        pancakes.addIngredient("milk (cup)", 1.5);

        Recipe garlicBread = new Recipe("Garlic Bread", 4);
        garlicBread.addIngredient("bread", 1);
        garlicBread.addIngredient("garlic cloves", 4);
        garlicBread.addIngredient("olive oil (tbsp)", 2);

        Recipe chocolateCake = new Recipe("Chocolate Cake", 8);
        chocolateCake.addIngredient("flour (cup)", 2);
        chocolateCake.addIngredient("cocoa powder (cup)", 0.75);
        chocolateCake.addIngredient("sugar (cup)", 2);
        chocolateCake.addIngredient("eggs", 3);

        book.addRecipe(pasta);
        book.addRecipe(pancakes);
        book.addRecipe(garlicBread);
        book.addRecipe(chocolateCake);

        System.out.println("Added " + book.size() + " recipes.\n");

        // 1. Case-insensitive name search (with trim)
        System.out.println("=== 1. Search by name: '  cake  ' (trimmed) ===");
        List<Recipe> results = book.searchByName("  cake  ");
        for (Recipe r : results) {
            System.out.println("- " + r.toString().split("\n")[0]);
        }

        // 2. Ingredient-based search
        System.out.println("\n=== 2. Search by ingredient: 'garlic' ===");
        results = book.searchByIngredient("garlic");
        for (Recipe r : results) {
            System.out.println("- " + r.toString().split("\n")[0]);
        }

        // 3. Multi-token search: "garlic oil" (both must match)
        System.out.println("\n=== 3. Multi-token search: 'garlic oil' ===");
        results = book.search("garlic oil");
        for (Recipe r : results) {
            System.out.println("- " + r.toString().split("\n")[0]);
        }

        // 4. Sort A–Z
        System.out.println("\n=== 4. All recipes sorted A–Z ===");
        List<Recipe> sorted = RecipeSorter.sortByName(book.getAllRecipes());
        for (Recipe r : sorted) {
            System.out.println("- " + r.toString().split("\n")[0]);
        }

        // 5. Sort Z–A
        System.out.println("\n=== 5. All recipes sorted Z–A ===");
        sorted = RecipeSorter.sortByName(book.getAllRecipes(), false);
        for (Recipe r : sorted) {
            System.out.println("- " + r.toString().split("\n")[0]);
        }

        // 6. Search result stability: stored order unchanged
        System.out.println("\n=== 6. Stored order (no mutation) ===");
        List<Recipe> all = book.getAllRecipes();
        for (int i = 0; i < all.size(); i++) {
            System.out.println((i + 1) + ". " + all.get(i).toString().split("\n")[0]);
        }

        System.out.println("\n=== Stage 3 Demo Complete ===");
    }
}
