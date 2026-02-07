import java.util.List;

public class RecipeTest {
    public static void main(String[] args) {
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

    private static void testScaleEdgeCases() {
        Recipe r = new Recipe("Edge", 3);
        r.addIngredient("sugar", 100.0);

        r.scaleToServings(1);

        List<Double> amounts = r.getIngredientAmounts();
        assertEquals("sugar amount after scaling 3->1", 100.0 / 3.0, amounts.get(0), 1e-9);

        r.scaleToServings(3);
        amounts = r.getIngredientAmounts();
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

    private static void testScaleToServings() {
        Recipe r = new Recipe("Scale", 2);
        r.addIngredient("flour", 200.0);
        r.addIngredient("salt", 0.5);

        r.scaleToServings(4);

        List<Double> amounts = r.getIngredientAmounts();
        assertEquals("amounts[0] after scale", 400.0, amounts.get(0), 1e-9);
        assertEquals("amounts[1] after scale", 1.0, amounts.get(1), 1e-9);
        assertEquals("servings after scale", 4, r.getServings());

        try {
            r.scaleToServings(0);
            fail("scaleToServings should throw on non-positive input");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    /** Tests formatAmount via toString output (amount formatting rules). */
    private static void testFormatAmount() {
        Recipe r = new Recipe("Format", 1);
        r.addIngredient("a", 200.0);
        assertContains("integer format", r.toString(), "200");

        r = new Recipe("Format2", 1);
        r.addIngredient("b", 7.5);
        assertContains("one decimal", r.toString(), "7.5");

        r = new Recipe("Format3", 1);
        r.addIngredient("c", 0.625);
        assertContains("two decimals rounded", r.toString(), "0.63");

        r = new Recipe("Format4", 1);
        r.addIngredient("d", 1.333);
        assertContains("two decimals clipped", r.toString(), "1.33");

        r = new Recipe("Format5", 1);
        r.addIngredient("e", 1.10);
        assertContains("strip trailing zero", r.toString(), "1.1");

        r = new Recipe("Format6", 1);
        r.addIngredient("f", 1.0000000001);
        assertContains("near integer tolerance", r.toString(), "- 1 f");
    }

    private static void assertContains(String label, String text, String substring) {
        if (!text.contains(substring)) {
            fail(label + " expected '" + text + "' to contain '" + substring + "'");
        }
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
