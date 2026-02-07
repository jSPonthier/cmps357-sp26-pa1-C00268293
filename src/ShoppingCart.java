import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregates ingredients from multiple recipes into a combined shopping list.
 *
 * <p>Ingredients with the same normalized name (case-insensitive) are combined
 * by summing their amounts. Original recipes are never modified. Output uses
 * the same amount formatting rules as Recipe.
 */
public class ShoppingCart {

    /**
     * Aggregates ingredients from the given recipes into a single combined list.
     *
     * <p>Ingredients are matched by normalized name (trimmed, case-insensitive).
     * Amounts for matching ingredients are summed. The display name is taken
     * from the first occurrence. Results are returned sorted alphabetically
     * by ingredient name (case-insensitive).
     *
     * @param recipes the recipes to aggregate; may be null or empty
     * @return a new list of aggregated ingredients, each as "amount name"
     */
    public static List<String> aggregate(List<Recipe> recipes) {
        if (recipes == null || recipes.isEmpty()) {
            return new ArrayList<>();
        }

        // Key: normalized name (lowercase, trimmed). Value: display name + total amount
        Map<String, AggregatedIngredient> map = new LinkedHashMap<>();

        for (Recipe r : recipes) {
            List<String> names = r.getIngredientNames();
            List<Double> amounts = r.getIngredientAmounts();
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                if (name == null) continue;
                String trimmed = name.trim();
                if (trimmed.isEmpty()) continue;
                Double boxed = (i < amounts.size()) ? amounts.get(i) : null;
                double amount = (boxed != null) ? boxed : 0;
                if (amount <= 0) continue;

                String normalized = trimmed.toLowerCase();
                if (map.containsKey(normalized)) {
                    AggregatedIngredient agg = map.get(normalized);
                    agg.amount += amount;
                } else {
                    map.put(normalized, new AggregatedIngredient(trimmed, amount));
                }
            }
        }

        // Build sorted list for output
        List<AggregatedIngredient> aggregated = new ArrayList<>(map.values());
        Collections.sort(aggregated, (a, b) -> {
            int cmp = a.displayName.compareToIgnoreCase(b.displayName);
            return cmp != 0 ? cmp : a.displayName.compareTo(b.displayName);
        });

        List<String> result = new ArrayList<>();
        for (AggregatedIngredient agg : aggregated) {
            result.add(formatAmount(agg.amount) + " " + agg.displayName);
        }
        return result;
    }

    private static class AggregatedIngredient {
        final String displayName;
        double amount;

        AggregatedIngredient(String displayName, double amount) {
            this.displayName = displayName;
            this.amount = amount;
        }
    }

    /**
     * Formats an amount per SPEC.md: integers without decimals, otherwise up to 2 decimals.
     */
    private static String formatAmount(double x) {
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
