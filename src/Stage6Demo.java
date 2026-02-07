import java.util.Scanner;

/**
 * Demo for Stage 6 functionality - User Interface Integration.
 *
 * <p>Runs the ConsoleUI with a pre-populated RecipeBook (Pasta, Pancakes).
 * Use commands 1-7 to explore. Type 7 to exit.
 */
public class Stage6Demo {
    public static void main(String[] args) {
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

        ConsoleUI ui = new ConsoleUI(book, new Scanner(System.in));
        ui.run();
    }
}
