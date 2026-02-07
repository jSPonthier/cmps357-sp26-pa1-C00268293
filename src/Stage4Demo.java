import java.util.List;

/**
 * Demo for Stage 4 functionality - Shopping Cart Aggregation.
 *
 * <p>Demonstrates:
 * - Combining ingredients from multiple recipes
 * - Case-insensitive name normalization (Flour + flour summed)
 * - Amount summation for matching ingredients
 * - Sorted output (alphabetical)
 * - Non-destructive: original recipes unchanged
 * - Same formatting rules as Recipe (amounts)
 */
public class Stage4Demo {
    public static void main(String[] args) {
        System.out.println("=== Stage 4 Demo: Shopping Cart Aggregation ===\n");

        RecipeBook book = new RecipeBook();

        Recipe pasta = new Recipe("Pasta Aglio e Olio", 2);
        pasta.addIngredient("spaghetti (g)", 200);
        pasta.addIngredient("garlic cloves", 3);
        pasta.addIngredient("olive oil (cup)", 0.25);

        Recipe pancakes = new Recipe("Pancakes", 4);
        pancakes.addIngredient("flour (cup)", 2);
        pancakes.addIngredient("eggs", 2);
        pancakes.addIngredient("milk (cup)", 1.5);

        Recipe chocolateCake = new Recipe("Chocolate Cake", 8);
        chocolateCake.addIngredient("flour (cup)", 2);
        chocolateCake.addIngredient("cocoa powder (cup)", 0.75);
        chocolateCake.addIngredient("sugar (cup)", 2);
        chocolateCake.addIngredient("eggs", 3);

        book.addRecipe(pasta);
        book.addRecipe(pancakes);
        book.addRecipe(chocolateCake);

        // Aggregate ingredients from Pancakes + Chocolate Cake (both have flour, eggs)
        List<Recipe> selected = List.of(pancakes, chocolateCake);
        List<String> cart = ShoppingCart.aggregate(selected);

        System.out.println("Recipes selected: Pancakes, Chocolate Cake\n");
        System.out.println("=== Shopping Cart (aggregated) ===");
        for (String line : cart) {
            System.out.println("- " + line);
        }

        // Verify: flour 2+2=4, eggs 2+3=5, milk 1.5, cocoa 0.75, sugar 2
        System.out.println("\n(Expected: flour 4, eggs 5, milk 1.5, cocoa 0.75, sugar 2)");

        // Verify originals unchanged
        System.out.println("\n=== Original recipes unchanged ===");
        System.out.println(pancakes.toString().split("\n")[0] + " still has " + pancakes.totalIngredientCount() + " ingredients.");

        System.out.println("\n=== Stage 4 Demo Complete ===");
    }
}
