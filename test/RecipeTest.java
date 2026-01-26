import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RecipeTest {
    public static void main(String[] args) throws Exception {
        testTotalIngredientCount();
        testScaleToServings();
        testFormatAmount();
        testToPrettyString();
        testScaleEdgeCases();
        System.out.println("All Recipe tests passed.");
    }

    private static void testToPrettyString() {
        Recipe r = new Recipe("Pretty", 2);
        r.addIngredient("egg", 2.0);
        r.addIngredient("milk", 1.5);

        String s1 = r.toString();
        String s2 = r.toPrettyString();

        assertEquals("toPrettyString equals toString", s1, s2);
    }

    private static void testScaleEdgeCases() throws Exception {
        Recipe r = new Recipe("Edge", 3);
        r.addIngredient("sugar", 100.0);

        r.scaleToServings(1);

        List<Double> amounts = getIngredientAmounts(r);
        // expect 100/3
        assertEquals("sugar amount after scaling 3->1", 100.0 / 3.0, amounts.get(0), 1e-9);

        // scaling back up should restore original
        r.scaleToServings(3);
        amounts = getIngredientAmounts(r);
        assertEquals("sugar amount after scaling back 1->3", 100.0, amounts.get(0), 1e-9);
    }

    private static void testTotalIngredientCount() {
        Recipe r = new Recipe("Test", 2);
        r.addIngredient("A", 1.0);
        r.addIngredient("B", 2.0);
        r.addIngredient(null, 3.0); // should be ignored
        r.addIngredient("C", -1.0); // should be ignored

        assertEquals("totalIngredientCount", 2, r.totalIngredientCount());
    }

    private static void testScaleToServings() throws Exception {
        Recipe r = new Recipe("Scale", 2);
        r.addIngredient("flour", 200.0);
        r.addIngredient("salt", 0.5);

        r.scaleToServings(4);

        List<Double> amounts = getIngredientAmounts(r);
        assertEquals("amounts[0] after scale", 400.0, amounts.get(0), 1e-9);
        assertEquals("amounts[1] after scale", 1.0, amounts.get(1), 1e-9);
        assertEquals("servings after scale", 4, getServings(r));

        try {
            r.scaleToServings(0);
            fail("scaleToServings should throw on non-positive input");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testFormatAmount() throws Exception {
        Recipe r = new Recipe("Format", 1);
        assertEquals("integer format", "200", formatAmount(r, 200.0));
        assertEquals("one decimal", "7.5", formatAmount(r, 7.5));
        assertEquals("two decimals rounded", "0.63", formatAmount(r, 0.625));
        assertEquals("two decimals clipped", "1.33", formatAmount(r, 1.333));
        assertEquals("strip trailing zero", "1.1", formatAmount(r, 1.10));
        assertEquals("near integer tolerance", "1", formatAmount(r, 1.0000000001));
    }

    // Helpers
    private static String formatAmount(Recipe r, double value) throws Exception {
        Method m = Recipe.class.getDeclaredMethod("formatAmount", double.class);
        m.setAccessible(true);
        return (String) m.invoke(r, value);
    }

    @SuppressWarnings("unchecked")
    private static List<Double> getIngredientAmounts(Recipe r) throws Exception {
        Field f = Recipe.class.getDeclaredField("ingredientAmounts");
        f.setAccessible(true);
        return new ArrayList<>((List<Double>) f.get(r));
    }

    private static int getServings(Recipe r) throws Exception {
        Field f = Recipe.class.getDeclaredField("servings");
        f.setAccessible(true);
        return f.getInt(r);
    }

    private static void assertEquals(String label, String expected, String actual) {
        if (!expected.equals(actual)) {
            fail(label + " expected '" + expected + "' but was '" + actual + "'");
        }
    }

    private static void assertEquals(String label, int expected, int actual) {
        if (expected != actual) {
            fail(label + " expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(String label, double expected, double actual, double eps) {
        if (Math.abs(expected - actual) > eps) {
            fail(label + " expected " + expected + " but was " + actual);
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
