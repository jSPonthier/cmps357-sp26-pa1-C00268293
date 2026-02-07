import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of recipes.
 *
 * <p>RecipeBook maintains recipes in insertion order and provides operations
 * for adding, removing, and retrieving recipes. Recipes are uniquely identified
 * by their name (case-sensitive).
 */
public class RecipeBook {
    private final List<Recipe> recipes;

    /**
     * Creates a new empty RecipeBook.
     */
    public RecipeBook() {
        this.recipes = new ArrayList<>();
    }

    /**
     * Adds a recipe to this recipe book.
     *
     * <p>The recipe is appended to the end of the list, preserving insertion order.
     *
     * @param recipe the recipe to add; must not be null
     * @throws IllegalArgumentException if recipe is null
     */
    public void addRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe must not be null");
        }
        recipes.add(recipe);
    }

    /**
     * Removes the first recipe with the specified name from this recipe book.
     *
     * <p>Name matching is case-sensitive.
     *
     * @param recipeName the name of the recipe to remove
     * @return true if a recipe was removed, false if no matching recipe was found
     */
    public boolean removeRecipe(String recipeName) {
        if (recipeName == null) {
            return false;
        }
        return recipes.removeIf(r -> r.getName().equals(recipeName));
    }

    /**
     * Returns all recipes in this recipe book.
     *
     * <p>The returned list is a copy; modifications to it will not affect
     * the internal recipe collection.
     *
     * @return a list of all recipes in insertion order
     */
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes);
    }

    /**
     * Returns the number of recipes in this recipe book.
     *
     * @return the number of recipes
     */
    public int size() {
        return recipes.size();
    }

    /**
     * Removes all recipes from this recipe book.
     */
    public void clear() {
        recipes.clear();
    }

    /**
     * Searches for recipes whose name contains the specified query string.
     *
     * <p>The search is case-insensitive and matches partial names.
     * Leading and trailing whitespace in the query is ignored.
     *
     * @param query the search string
     * @return a new list of recipes matching the query, in insertion order
     */
    public List<Recipe> searchByName(String query) {
        if (query == null) {
            return new ArrayList<>();
        }
        String trimmed = query.trim();
        if (trimmed.isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerQuery = trimmed.toLowerCase();
        List<Recipe> results = new ArrayList<>();
        
        for (Recipe r : recipes) {
            if (r.getName().toLowerCase().contains(lowerQuery)) {
                results.add(r);
            }
        }
        
        return results;
    }

    /**
     * Searches for recipes that contain an ingredient whose name matches the query.
     *
     * <p>The search is case-insensitive and matches partial ingredient names.
     * Leading and trailing whitespace in the query is ignored.
     * This operation is read-only and does not modify recipes or ingredients.
     *
     * @param query the search string
     * @return a new list of recipes matching the query, in insertion order
     */
    public List<Recipe> searchByIngredient(String query) {
        if (query == null) {
            return new ArrayList<>();
        }
        String trimmed = query.trim();
        if (trimmed.isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerQuery = trimmed.toLowerCase();
        List<Recipe> results = new ArrayList<>();
        
        for (Recipe r : recipes) {
            for (String iname : r.getIngredientNames()) {
                if (iname.toLowerCase().contains(lowerQuery)) {
                    results.add(r);
                    break;
                }
            }
        }
        
        return results;
    }

    /**
     * Searches for recipes where all tokens in the query match somewhere in the recipe.
     *
     * <p>A multi-token query like "garlic oil" is split into tokens. A recipe matches
     * if every token matches (case-insensitive, partial) in either the recipe name
     * or any ingredient name. Leading/trailing whitespace in the query is ignored.
     * This operation is read-only and returns a new list.
     *
     * @param query the search string (may contain multiple space-separated tokens)
     * @return a new list of recipes matching all tokens, in insertion order
     */
    public List<Recipe> search(String query) {
        if (query == null) {
            return new ArrayList<>();
        }
        String trimmed = query.trim();
        if (trimmed.isEmpty()) {
            return new ArrayList<>();
        }
        
        String[] tokens = trimmed.split("\\s+");
        if (tokens.length == 0) {
            return new ArrayList<>();
        }
        
        List<Recipe> results = new ArrayList<>();
        for (Recipe r : recipes) {
            if (matchesAllTokens(r, tokens)) {
                results.add(r);
            }
        }
        return results;
    }

    /**
     * Checks if a recipe matches all search tokens (in name or ingredients).
     */
    private boolean matchesAllTokens(Recipe r, String[] tokens) {
        String recipeName = r.getName().toLowerCase();
            List<String> ingredientNames = r.getIngredientNames();
            
            for (String token : tokens) {
                String lowerToken = token.toLowerCase();
                boolean tokenMatches = recipeName.contains(lowerToken);
                if (!tokenMatches) {
                    for (String iname : ingredientNames) {
                        if (iname.toLowerCase().contains(lowerToken)) {
                            tokenMatches = true;
                            break;
                        }
                    }
                }
                if (!tokenMatches) {
                    return false;
                }
            }
            return true;
    }
}
