// src/Recipe.java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Recipe {
    private final String name;
    private int servings;
    private final List<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String name, int servings) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        if (servings <= 0) {
            throw new IllegalArgumentException("servings must be positive");
        }
        this.name = name;
        this.servings = servings;
    }

    /**
     * Returns the name of this recipe.
     *
     * @return the recipe name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of servings for this recipe.
     *
     * @return the servings count
     */
    public int getServings() {
        return servings;
    }

    /**
     * Adds an ingredient and its amount to the recipe.
     *
     * <p>The ingredient is only added if both inputs are valid:
     * <ul>
     *   <li>{@code ingredientName} must be non-null and not blank</li>
     *   <li>{@code amount} must be greater than 0</li>
     * </ul>
     *
     * <p>If either input is invalid, this method leaves the recipe unchanged
     * and prints a message to standard error.
     *
     * @param ingredientName the name of the ingredient
     * @param amount the amount of the ingredient
     */
    public void addIngredient(String ingredientName, double amount) {
        if (ingredientName == null || ingredientName.trim().isEmpty()) {
            System.err.println("Invalid ingredient: name must be non-empty. Ingredient not added.");
            return;
        }
        if (amount <= 0) {
            System.err.println("Invalid ingredient: amount must be greater than 0. Ingredient not added.");
            return;
        }
        ingredients.add(new Ingredient(ingredientName.trim(), amount));
    }

    /**
     * Returns a copy of the list of ingredient names in this recipe.
     *
     * <p>The returned list is a defensive copy; modifications do not affect
     * the recipe's internal state. Use for read-only operations such as search.
     *
     * @return a new list of ingredient names in the order they were added
     */
    public List<String> getIngredientNames() {
        return ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    /**
     * Returns a copy of the list of ingredient amounts in this recipe.
     *
     * <p>The returned list parallels {@link #getIngredientNames()}; index i
     * corresponds to the amount for the ingredient at getIngredientNames().get(i).
     * Use for read-only operations such as shopping cart aggregation.
     *
     * @return a new list of ingredient amounts in the order they were added
     */
    public List<Double> getIngredientAmounts() {
        return ingredients.stream()
                .map(Ingredient::getAmount)
                .collect(Collectors.toList());
    }

    /**
     * Returns the number of ingredient entries in this recipe.
     *
     * @return the number of ingredients added to the recipe
     */
    public int totalIngredientCount() {
        return ingredients.size();
    }

    /**
     * Scales all ingredient amounts proportionally to match {@code newServings}.
     *
     * <p>Multiplies each ingredient's amount by the ratio of {@code newServings}
     * to the current servings. Stored amounts are not rounded; precision is
     * maintained for subsequent scaling operations.
     *
     * @param newServings the target number of servings; must be positive
     * @throws IllegalArgumentException if {@code newServings} is not positive
     */
    public void scaleToServings(int newServings) {
        if (newServings <= 0) {
            throw new IllegalArgumentException("newServings must be positive");
        }
        double factor = (double) newServings / this.servings;
        List<Ingredient> scaled = new ArrayList<>();
        for (Ingredient i : ingredients) {
            scaled.add(i.scale(factor));
        }
        ingredients.clear();
        ingredients.addAll(scaled);
        this.servings = newServings;
    }

    /**
     * Returns a multi-line string representation of this recipe.
     *
     * <p>Format:
     * <pre>
     * &lt;name&gt; (serves &lt;servings&gt;)
     * - &lt;amount&gt; &lt;ingredient&gt;
     * - &lt;amount&gt; &lt;ingredient&gt;
     * ...
     * </pre>
     *
     * <p>Ingredient amounts are formatted according to the formatting rules:
     * integer values display without decimals, non-integers display with up to
     * two decimals with trailing zeros trimmed.
     *
     * @return a formatted string representation of the recipe
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (serves ").append(servings).append(")\n");
        for (Ingredient i : ingredients) {
            sb.append("- ").append(formatAmount(i.getAmount())).append(" ").append(i.getName()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a prettified string representation of this recipe.
     *
     * <p>Currently delegates to {@link #toString()}.
     *
     * @return a user-friendly multi-line recipe description
     */
    public String toPrettyString() {
        return toString();
    }

    private String formatAmount(double x) {
        double rounded = Math.rint(x);
        if (Math.abs(x - rounded) < 1e-9) {
            return String.valueOf((long) rounded);
        }
        String s = String.format("%.2f", x);
        if (s.indexOf('.') >= 0) {
            s = s.replaceAll("0+$", "");
            s = s.replaceAll("\\.$", "");
        }
        return s;
    }
}
