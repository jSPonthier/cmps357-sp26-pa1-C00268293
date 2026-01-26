/**
 * Tests for the Ingredient class.
 */
public class IngredientTest {
    public static void main(String[] args) {
        testConstructorValid();
        testConstructorInvalidName();
        testConstructorInvalidAmount();
        testGetters();
        testScale();
        testToString();
        System.out.println("All Ingredient tests passed.");
    }

    private static void testConstructorValid() {
        Ingredient ing = new Ingredient("flour", 2.5);
        assertEquals("name", "flour", ing.getName());
        assertEquals("amount", 2.5, ing.getAmount(), 1e-9);
    }

    private static void testConstructorInvalidName() {
        try {
            new Ingredient(null, 1.0);
            fail("Constructor should throw on null name");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            new Ingredient("   ", 1.0);
            fail("Constructor should throw on blank name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testConstructorInvalidAmount() {
        try {
            new Ingredient("sugar", 0);
            fail("Constructor should throw on zero amount");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            new Ingredient("salt", -1.5);
            fail("Constructor should throw on negative amount");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testGetters() {
        Ingredient ing = new Ingredient("  olive oil  ", 0.25);
        assertEquals("getName trims whitespace", "olive oil", ing.getName());
        assertEquals("getAmount", 0.25, ing.getAmount(), 1e-9);
    }

    private static void testScale() {
        Ingredient ing = new Ingredient("butter", 100.0);
        Ingredient scaled = ing.scale(2.5);
        
        assertEquals("original unchanged", 100.0, ing.getAmount(), 1e-9);
        assertEquals("scaled amount", 250.0, scaled.getAmount(), 1e-9);
        assertEquals("scaled name unchanged", "butter", scaled.getName());

        // Test fractional scaling
        Ingredient scaled2 = ing.scale(0.5);
        assertEquals("fractional scale", 50.0, scaled2.getAmount(), 1e-9);
    }

    private static void testToString() {
        Ingredient ing = new Ingredient("eggs", 3.0);
        String str = ing.toString();
        assertTrue("toString contains amount", str.contains("3"));
        assertTrue("toString contains name", str.contains("eggs"));
    }

    // Helper methods
    private static void assertEquals(String label, String expected, String actual) {
        if (!expected.equals(actual)) {
            fail(label + " expected '" + expected + "' but was '" + actual + "'");
        }
    }

    private static void assertEquals(String label, double expected, double actual, double eps) {
        if (Math.abs(expected - actual) > eps) {
            fail(label + " expected " + expected + " but was " + actual);
        }
    }

    private static void assertTrue(String label, boolean condition) {
        if (!condition) {
            fail(label + " expected true but was false");
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
