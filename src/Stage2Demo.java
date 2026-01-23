import java.util.List;

/**
 * Demo for Stage 2 functionality - RecipeBook and search/sort features.
 */
public class Stage2Demo {
    public static void main(String[] args) {
        System.out.println("=== Stage 2 Demo: RecipeBook ===\n");
        
        // Create a recipe book
        RecipeBook book = new RecipeBook();
        
        // Create some recipes
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
        
        // Add recipes to book
        book.addRecipe(pasta);
        book.addRecipe(pancakes);
        book.addRecipe(chocolateCake);
        
        System.out.println("Added " + book.size() + " recipes to the book\n");
        
        // Test search
        System.out.println("=== Search for 'cake' ===");
        List<Recipe> searchResults = book.searchByName("cake");
        for (Recipe r : searchResults) {
            System.out.println(r.toString());
        }
        
        // Test sorting
        System.out.println("=== All recipes (sorted by name) ===");
        List<Recipe> sorted = RecipeSorter.sortByName(book.getAllRecipes());
        for (Recipe r : sorted) {
            System.out.println(r.toString());
        }
        
        // Test removal
        System.out.println("=== After removing 'Pancakes' ===");
        book.removeRecipe("Pancakes");
        System.out.println("Book now has " + book.size() + " recipes\n");
        
        sorted = RecipeSorter.sortByName(book.getAllRecipes());
        for (Recipe r : sorted) {
            System.out.println(r.toString());
        }
    }
}
