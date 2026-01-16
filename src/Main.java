// src/Main.java
public class Main {
    public static void main(String[] args) {
        Recipe r = new Recipe("Pasta Aglio e Olio", 2);

        r.addIngredient("spaghetti (g)", 200);
        r.addIngredient("garlic cloves", 3);
        r.addIngredient("olive oil (cup)", 0.25);
        r.addIngredient("red pepper flakes (tsp)", 0.5);
        r.addIngredient("parsley (cup)", 0.25);

        System.out.println(r.toString());
        System.out.println();
        System.out.println("Ingredient entries: " + r.totalIngredientCount());
        System.out.println();
        System.out.println("After scaling to 5 servings:");
        System.out.println();
        r.scaleToServings(5);
        System.out.println(r.toString());
    }
}
