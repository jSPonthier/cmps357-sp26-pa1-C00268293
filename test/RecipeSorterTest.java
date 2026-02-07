import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the RecipeSorter class.
 */
public class RecipeSorterTest {
    public static void main(String[] args) throws Exception {
        testSortByName();
        testSortByNameCaseInsensitive();
        testSortByNameDoesNotMutate();
        testSortByNameNullInput();
        System.out.println("All RecipeSorter tests passed.");
    }

    private static void testSortByName() throws Exception {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Zebra Cake", 8));
        recipes.add(new Recipe("Apple Pie", 6));
        recipes.add(new Recipe("Muffins", 12));
        
        List<Recipe> sorted = RecipeSorter.sortByName(recipes);
        
        assertEquals("sorted size", 3, sorted.size());
        assertEquals("first recipe", "Apple Pie", sorted.get(0).getName());
        assertEquals("second recipe", "Muffins", sorted.get(1).getName());
        assertEquals("third recipe", "Zebra Cake", sorted.get(2).getName());
    }

    private static void testSortByNameCaseInsensitive() throws Exception {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("banana bread", 1));
        recipes.add(new Recipe("Apple Tart", 1));
        recipes.add(new Recipe("Cherry Pie", 1));
        
        List<Recipe> sorted = RecipeSorter.sortByName(recipes);
        
        assertEquals("case-insensitive first", "Apple Tart", sorted.get(0).getName());
        assertEquals("case-insensitive second", "banana bread", sorted.get(1).getName());
        assertEquals("case-insensitive third", "Cherry Pie", sorted.get(2).getName());
    }

    private static void testSortByNameDoesNotMutate() throws Exception {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Zebra", 1));
        recipes.add(new Recipe("Apple", 1));
        
        String originalFirst = recipes.get(0).getName();
        
        RecipeSorter.sortByName(recipes);
        
        String afterSortFirst = recipes.get(0).getName();
        assertEquals("original not mutated", originalFirst, afterSortFirst);
    }

    private static void testSortByNameNullInput() {
        List<Recipe> sorted = RecipeSorter.sortByName(null);
        assertNotNull("null input returns empty list", sorted);
        assertEquals("null input returns empty", 0, sorted.size());
    }

    // Helper methods
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

    private static void assertNotNull(String label, Object obj) {
        if (obj == null) {
            fail(label + " expected non-null but was null");
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
