import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Console-based user interface for the Recipe application.
 *
 * <p>Depends on service classes (RecipeBook, RecipeSorter, ShoppingCart) and
 * RecipeJsonStore for persistence. Does not manipulate internal data structures directly.
 */
public class ConsoleUI {
    private final RecipeBook book;
    private final Scanner scanner;

    public ConsoleUI(RecipeBook book, Scanner scanner) {
        this.book = book;
        this.scanner = scanner;
    }

    /**
     * Runs the main menu loop until the user exits.
     */
    public void run() {
        printMenu();
        while (true) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine();
            if (input == null) input = "";
            String trimmed = input.trim();

            if (trimmed.isEmpty()) {
                System.out.println("Please enter a number (1-7).");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(trimmed);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command. Please enter a number (1-7).");
                continue;
            }

            switch (choice) {
                case 1:
                    doListRecipes();
                    break;
                case 2:
                    doSearchRecipes();
                    break;
                case 3:
                    doViewRecipeDetails();
                    break;
                case 4:
                    doBuildShoppingCart();
                    break;
                case 5:
                    doLoadRecipes();
                    break;
                case 6:
                    doSaveRecipes();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid command. Please enter a number (1-7).");
            }
        }
    }

    private void printMenu() {
        System.out.println("=== Recipe Manager ===");
        System.out.println("1. List all recipes");
        System.out.println("2. Search recipes");
        System.out.println("3. View recipe details");
        System.out.println("4. Build shopping cart");
        System.out.println("5. Load recipes from file");
        System.out.println("6. Save recipes to file");
        System.out.println("7. Exit");
    }

    private void doListRecipes() {
        List<Recipe> sorted = RecipeSorter.sortByName(book.getAllRecipes());
        System.out.println("\nAll Recipes (" + sorted.size() + "):");
        for (int i = 0; i < sorted.size(); i++) {
            Recipe r = sorted.get(i);
            System.out.println((i + 1) + ". " + r.getName() + " (" + r.getServings() + " servings)");
        }
    }

    private void doSearchRecipes() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        if (query == null) query = "";
        query = query.trim();
        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        List<Recipe> results = book.search(query);
        System.out.println("\nSearch Results (" + results.size() + "):");
        for (int i = 0; i < results.size(); i++) {
            Recipe r = results.get(i);
            System.out.println((i + 1) + ". " + r.getName() + " (" + r.getServings() + " servings)");
        }
    }

    private void doViewRecipeDetails() {
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        if (name == null) name = "";
        name = name.trim();
        if (name.isEmpty()) {
            System.out.println("Recipe name cannot be empty.");
            return;
        }

        List<Recipe> all = book.getAllRecipes();
        Recipe found = null;
        for (Recipe r : all) {
            if (r.getName().equals(name)) {
                found = r;
                break;
            }
        }

        if (found == null) {
            List<Recipe> byName = book.searchByName(name);
            if (!byName.isEmpty()) {
                found = byName.get(0);
            }
        }

        if (found == null) {
            System.out.println("Recipe not found: " + name);
            return;
        }

        System.out.println("\n" + found.toString());
    }

    private void doBuildShoppingCart() {
        System.out.print("Enter recipe names (comma-separated): ");
        String input = scanner.nextLine();
        if (input == null) input = "";
        input = input.trim();
        if (input.isEmpty()) {
            System.out.println("Please enter at least one recipe name.");
            return;
        }

        List<String> names = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        List<Recipe> selected = new ArrayList<>();
        for (String name : names) {
            for (Recipe r : book.getAllRecipes()) {
                if (r.getName().equalsIgnoreCase(name)) {
                    selected.add(r);
                    break;
                }
            }
        }

        List<String> cart = ShoppingCart.aggregate(selected);
        System.out.println("\n=== Shopping Cart ===");
        System.out.println("Aggregated Ingredients:");
        for (String line : cart) {
            System.out.println("  - " + line);
        }
    }

    private void doLoadRecipes() {
        System.out.print("Enter file path: ");
        String path = scanner.nextLine();
        if (path == null) path = "";
        path = path.trim();
        if (path.isEmpty()) {
            System.out.println("File path cannot be empty.");
            return;
        }

        try {
            RecipeBook loaded = RecipeJsonStore.load(path);
            book.clear();
            for (Recipe r : loaded.getAllRecipes()) {
                book.addRecipe(r);
            }
            System.out.println("Successfully loaded " + loaded.size() + " recipes from " + path);
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private void doSaveRecipes() {
        System.out.print("Enter file path: ");
        String path = scanner.nextLine();
        if (path == null) path = "";
        path = path.trim();
        if (path.isEmpty()) {
            System.out.println("File path cannot be empty.");
            return;
        }

        try {
            RecipeJsonStore.save(book, path);
            System.out.println("Successfully saved " + book.size() + " recipes to " + path);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RecipeBook book = new RecipeBook();
        Scanner scanner = new Scanner(System.in);
        ConsoleUI ui = new ConsoleUI(book, scanner);
        ui.run();
        scanner.close();
    }
}
