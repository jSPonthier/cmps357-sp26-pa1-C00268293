/**
 * Represents a single ingredient entry with a name and amount.
 *
 * <p>Ingredients are value objects that store ingredient data as part of a recipe.
 * Names are stored as-is after trimming. Amounts are stored as raw doubles without
 * rounding to maintain precision for scaling operations.
 */
public class Ingredient {
    private final String name;
    private final double amount;

    /**
     * Creates a new ingredient with the specified name and amount.
     *
     * @param name the ingredient name; must be non-null and non-blank after trimming
     * @param amount the ingredient amount; must be greater than 0
     * @throws IllegalArgumentException if name is null/blank or amount is not positive
     */
    public Ingredient(String name, double amount) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name must be non-null and non-blank");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Ingredient amount must be greater than 0");
        }
        this.name = name.trim();
        this.amount = amount;
    }

    /**
     * Returns the name of this ingredient.
     *
     * @return the ingredient name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the amount of this ingredient.
     *
     * @return the ingredient amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Creates a new Ingredient with the same name and a scaled amount.
     *
     * @param factor the scaling factor to apply to the amount
     * @return a new Ingredient with the scaled amount
     * @throws IllegalArgumentException if the resulting amount would be non-positive
     */
    public Ingredient scale(double factor) {
        double newAmount = amount * factor;
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Scaled amount must be positive");
        }
        return new Ingredient(name, newAmount);
    }

    @Override
    public String toString() {
        return amount + " " + name;
    }
}
