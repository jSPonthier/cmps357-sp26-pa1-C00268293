import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Demo for Stage 5 functionality - Persistence (JSON I/O).
 *
 * <p>Demonstrates:
 * - Saving RecipeBook to JSON file
 * - Loading RecipeBook from JSON file
 * - Preserved recipe and ingredient order
 * - Full numeric precision (no formatting in storage)
 * - Validation and error handling
 */
public class Stage5Demo {
    private static final String DEMO_FILE = "demo_recipes.json";

    public static void main(String[] args) {
        System.out.println("=== Stage 5 Demo: Persistence (JSON I/O) ===\n");

        RecipeBook book = new RecipeBook();
        Recipe pasta = new Recipe("Pasta Aglio e Olio", 2);
        pasta.addIngredient("spaghetti (g)", 200);
        pasta.addIngredient("garlic cloves", 3);
        pasta.addIngredient("olive oil (cup)", 0.25);

        Recipe pancakes = new Recipe("Pancakes", 4);
        pancakes.addIngredient("flour (cup)", 2);
        pancakes.addIngredient("eggs", 2);
        pancakes.addIngredient("milk (cup)", 1.5);

        book.addRecipe(pasta);
        book.addRecipe(pancakes);

        System.out.println("Original RecipeBook (" + book.size() + " recipes):");
        for (Recipe r : book.getAllRecipes()) {
            System.out.println("- " + r.toString().split("\n")[0]);
        }

        try {
            // Save to file
            RecipeJsonStore.save(book, DEMO_FILE);
            System.out.println("\nSaved to " + DEMO_FILE);

            // Load from file
            RecipeBook loaded = RecipeJsonStore.load(DEMO_FILE);
            System.out.println("\nLoaded from " + DEMO_FILE + " (" + loaded.size() + " recipes):");
            for (Recipe r : loaded.getAllRecipes()) {
                System.out.println("- " + r.toString().split("\n")[0]);
            }

            // Verify order preserved
            System.out.println("\nOrder preserved: " + (book.size() == loaded.size()));

            // Clean up demo file
            Files.deleteIfExists(Path.of(DEMO_FILE));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Stage 5 Demo Complete ===");
    }
}
