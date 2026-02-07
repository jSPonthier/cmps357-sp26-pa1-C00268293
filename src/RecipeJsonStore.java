import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistence layer for RecipeBook: saves and loads recipe collections as JSON.
 *
 * <p>File I/O is isolated here. The domain model and services do not perform
 * file operations. Validation follows the policy in docs/DATA_MODEL.md.
 */
public class RecipeJsonStore {

    /**
     * Saves a RecipeBook to a JSON file.
     *
     * <p>Preserves recipe and ingredient order. Amounts are stored with full
     * numeric precision (no display formatting).
     *
     * @param book the recipe book to save; must not be null
     * @param filePath the path to write; must not be null
     * @throws IOException if the file cannot be written
     */
    public static void save(RecipeBook book, String filePath) throws IOException {
        if (book == null) {
            throw new IllegalArgumentException("RecipeBook must not be null");
        }
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or blank");
        }
        Path path = Paths.get(filePath);
        String json = toJson(book);
        Files.writeString(path, json, StandardCharsets.UTF_8);
    }

    /**
     * Loads a RecipeBook from a JSON file.
     *
     * <p>Validates structure and data per DATA_MODEL.md. On any validation
     * failure, throws with a descriptive message (all-or-nothing; no partial load).
     *
     * @param filePath the path to read; must not be null
     * @return a new RecipeBook with the loaded recipes
     * @throws IOException if the file cannot be read or JSON is malformed
     */
    public static RecipeBook load(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or blank");
        }
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }
        String json = Files.readString(path, StandardCharsets.UTF_8);
        return fromJson(json);
    }

    // --- JSON Serialization ---

    private static String toJson(RecipeBook book) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"recipes\":[");
        List<Recipe> recipes = book.getAllRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJsonRecipe(recipes.get(i)));
        }
        sb.append("]}");
        return sb.toString();
    }

    private static String toJsonRecipe(Recipe r) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"name\":").append(escapeJson(r.getName()));
        sb.append(",\"servings\":").append(r.getServings());
        sb.append(",\"ingredients\":[");
        List<String> names = r.getIngredientNames();
        List<Double> amounts = r.getIngredientAmounts();
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("{\"name\":").append(escapeJson(names.get(i)));
            sb.append(",\"amount\":").append(amounts.get(i));
            sb.append("}");
        }
        sb.append("]}");
        return sb.toString();
    }

    private static String escapeJson(String s) {
        if (s == null) return "\"\"";
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"') sb.append("\\\"");
            else if (c == '\\') sb.append("\\\\");
            else if (c == '\n') sb.append("\\n");
            else if (c == '\r') sb.append("\\r");
            else if (c == '\t') sb.append("\\t");
            else sb.append(c);
        }
        sb.append('"');
        return sb.toString();
    }

    // --- JSON Deserialization ---

    private static RecipeBook fromJson(String json) throws IOException {
        Parser p = new Parser(json);
        try {
            p.skipWhitespace();
            if (!p.expect('{')) {
                throw new IOException("Expected '{' at start of JSON");
            }
            p.skipWhitespace();

            RecipeBook book = null;
            while (p.peek() != '}') {
                String key = p.parseString();
                p.skipWhitespace();
                if (!p.expect(':')) {
                    throw new IOException("Expected ':' after key '" + key + "'");
                }
                p.skipWhitespace();
                if ("recipes".equals(key)) {
                    if (book != null) {
                        throw new IOException("Duplicate 'recipes' key");
                    }
                    if (!p.expect('[')) {
                        throw new IOException("Expected '[' for recipes array");
                    }
                    p.skipWhitespace();
                    book = new RecipeBook();
                    if (p.peek() != ']') {
                        int idx = 0;
                        do {
                            Recipe recipe = parseRecipe(p, idx);
                            book.addRecipe(recipe);
                            idx++;
                            p.skipWhitespace();
                        } while (p.expect(','));
                    }
                    if (!p.expect(']')) {
                        throw new IOException("Expected ']' to close recipes array");
                    }
                } else {
                    p.skipValue();
                }
                p.skipWhitespace();
                if (p.peek() == ',') {
                    p.expect(',');
                    p.skipWhitespace();
                }
            }
            if (book == null) {
                throw new IOException("Missing required 'recipes' key");
            }
            if (!p.expect('}')) {
                throw new IOException("Expected '}' at end of JSON");
            }
            p.skipWhitespace();
            if (p.pos < json.length()) {
                throw new IOException("Unexpected content after JSON");
            }
            return book;
        } catch (ParseException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private static Recipe parseRecipe(Parser p, int recipeIndex) throws IOException, ParseException {
        if (!p.expect('{')) {
            throw new IOException("Recipe " + recipeIndex + ": expected '{'");
        }
        p.skipWhitespace();

        String name = null;
        Integer servings = null;
        List<String> ingNames = null;
        List<Double> ingAmounts = null;

        boolean first = true;
        while (p.peek() != '}') {
            if (!first) {
                if (!p.expect(',')) {
                    throw new IOException("Recipe " + recipeIndex + ": expected ',' between fields");
                }
                p.skipWhitespace();
            }
            first = false;

            String key = p.parseString();
            p.skipWhitespace();
            if (!p.expect(':')) {
                throw new IOException("Recipe " + recipeIndex + ": expected ':' after field name");
            }
            p.skipWhitespace();

            if ("name".equals(key)) {
                name = p.parseString();
            } else if ("servings".equals(key)) {
                servings = p.parseInt();
            } else if ("ingredients".equals(key)) {
                if (!p.expect('[')) {
                    throw new IOException("Recipe " + recipeIndex + ": expected '[' for ingredients");
                }
                p.skipWhitespace();
                ingNames = new ArrayList<>();
                ingAmounts = new ArrayList<>();
                while (p.peek() != ']') {
                    if (!ingNames.isEmpty()) {
                        if (!p.expect(',')) {
                            throw new IOException("Recipe " + recipeIndex + ", ingredient: expected ','");
                        }
                        p.skipWhitespace();
                    }
                    String inName = null;
                    Double inAmt = null;
                    if (!p.expect('{')) {
                        throw new IOException("Recipe " + recipeIndex + ", ingredient: expected '{'");
                    }
                    p.skipWhitespace();
                    boolean firstIng = true;
                    while (p.peek() != '}') {
                        if (!firstIng) {
                            if (!p.expect(',')) throw new IOException("Recipe " + recipeIndex + ", ingredient: expected ','");
                            p.skipWhitespace();
                        }
                        firstIng = false;
                        String k = p.parseString();
                        p.skipWhitespace();
                        if (!p.expect(':')) throw new IOException("Recipe " + recipeIndex + ", ingredient: expected ':'");
                        p.skipWhitespace();
                        if ("name".equals(k)) {
                            inName = p.parseString();
                        } else if ("amount".equals(k)) {
                            inAmt = p.parseDouble();
                        } else {
                            p.skipValue();
                        }
                    }
                    if (!p.expect('}')) throw new IOException("Recipe " + recipeIndex + ", ingredient: expected '}'");
                    p.skipWhitespace();

                    // Validate ingredient
                    if (inName == null) {
                        throw new IOException("Recipe " + recipeIndex + ", ingredient " + ingNames.size() + ": missing 'name'");
                    }
                    String trimmed = inName.trim();
                    if (trimmed.isEmpty()) {
                        throw new IOException("Recipe " + recipeIndex + ", ingredient " + ingNames.size() + ": name must be non-blank");
                    }
                    if (inAmt == null) {
                        throw new IOException("Recipe " + recipeIndex + ", ingredient " + ingNames.size() + ": missing 'amount'");
                    }
                    if (inAmt <= 0) {
                        throw new IOException("Recipe " + recipeIndex + ", ingredient " + ingNames.size() + ": amount must be > 0");
                    }
                    ingNames.add(trimmed);
                    ingAmounts.add(inAmt);
                }
                if (!p.expect(']')) {
                    throw new IOException("Recipe " + recipeIndex + ": expected ']' to close ingredients");
                }
            } else {
                p.skipValue();
            }
            p.skipWhitespace();
        }

        if (!p.expect('}')) {
            throw new IOException("Recipe " + recipeIndex + ": expected '}'");
        }
        p.skipWhitespace();

        // Validate recipe
        if (name == null) {
            throw new IOException("Recipe " + recipeIndex + ": missing 'name'");
        }
        String nameTrimmed = name.trim();
        if (nameTrimmed.isEmpty()) {
            throw new IOException("Recipe " + recipeIndex + ": name must be non-blank");
        }
        if (servings == null) {
            throw new IOException("Recipe " + recipeIndex + ": missing 'servings'");
        }
        if (servings <= 0) {
            throw new IOException("Recipe " + recipeIndex + ": servings must be > 0");
        }
        if (ingNames == null) {
            ingNames = new ArrayList<>();
            ingAmounts = new ArrayList<>();
        }

        Recipe recipe = new Recipe(nameTrimmed, servings);
        for (int i = 0; i < ingNames.size(); i++) {
            recipe.addIngredient(ingNames.get(i), ingAmounts.get(i));
        }
        return recipe;
    }

    private static class Parser {
        final String json;
        int pos = 0;

        Parser(String json) {
            this.json = json;
        }

        void skipWhitespace() {
            while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
                pos++;
            }
        }

        char peek() {
            skipWhitespace();
            return pos < json.length() ? json.charAt(pos) : '\0';
        }

        boolean expect(char c) {
            skipWhitespace();
            if (pos < json.length() && json.charAt(pos) == c) {
                pos++;
                return true;
            }
            return false;
        }

        boolean expectString(String s) throws ParseException {
            skipWhitespace();
            if (pos < json.length() && json.charAt(pos) == '"') {
                String parsed = parseString();
                return s.equals(parsed);
            }
            return false;
        }

        String parseString() throws ParseException {
            skipWhitespace();
            if (pos >= json.length() || json.charAt(pos) != '"') {
                throw new ParseException("Expected string at position " + pos);
            }
            pos++;
            StringBuilder sb = new StringBuilder();
            while (pos < json.length()) {
                char c = json.charAt(pos++);
                if (c == '"') break;
                if (c == '\\') {
                    if (pos >= json.length()) throw new ParseException("Unterminated escape");
                    c = json.charAt(pos++);
                    if (c == 'n') sb.append('\n');
                    else if (c == 'r') sb.append('\r');
                    else if (c == 't') sb.append('\t');
                    else if (c == '"' || c == '\\') sb.append(c);
                    else throw new ParseException("Invalid escape \\" + c);
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        int parseInt() throws ParseException, IOException {
            skipWhitespace();
            int start = pos;
            if (pos < json.length() && json.charAt(pos) == '-') pos++;
            while (pos < json.length() && Character.isDigit(json.charAt(pos))) pos++;
            if (pos == start) {
                throw new IOException("Expected number at position " + pos);
            }
            try {
                return Integer.parseInt(json.substring(start, pos));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid integer at position " + start);
            }
        }

        void skipValue() throws ParseException, IOException {
            skipWhitespace();
            if (pos >= json.length()) return;
            char c = json.charAt(pos);
            if (c == '"') {
                parseString();
            } else if (c == '{') {
                pos++;
                while (peek() != '}') {
                    parseString();
                    skipWhitespace();
                    if (!expect(':')) break;
                    skipValue();
                    skipWhitespace();
                    if (peek() == ',') pos++;
                }
                expect('}');
            } else if (c == '[') {
                pos++;
                while (peek() != ']') {
                    skipValue();
                    skipWhitespace();
                    if (peek() == ',') pos++;
                }
                expect(']');
            } else if (c == 't' && json.startsWith("true", pos)) {
                pos += 4;
            } else if (c == 'f' && json.startsWith("false", pos)) {
                pos += 5;
            } else if (c == 'n' && json.startsWith("null", pos)) {
                pos += 4;
            } else if (c == '-' || Character.isDigit(c)) {
                parseDouble();
            }
        }

        double parseDouble() throws ParseException, IOException {
            skipWhitespace();
            int start = pos;
            if (pos < json.length() && json.charAt(pos) == '-') pos++;
            while (pos < json.length() && Character.isDigit(json.charAt(pos))) pos++;
            if (pos < json.length() && json.charAt(pos) == '.') {
                pos++;
                while (pos < json.length() && Character.isDigit(json.charAt(pos))) pos++;
            }
            if (pos < json.length() && (json.charAt(pos) == 'e' || json.charAt(pos) == 'E')) {
                pos++;
                if (pos < json.length() && (json.charAt(pos) == '+' || json.charAt(pos) == '-')) pos++;
                while (pos < json.length() && Character.isDigit(json.charAt(pos))) pos++;
            }
            if (pos == start) {
                throw new IOException("Expected number at position " + pos);
            }
            try {
                return Double.parseDouble(json.substring(start, pos));
            } catch (NumberFormatException e) {
                throw new IOException("Invalid number at position " + start);
            }
        }
    }

    private static class ParseException extends Exception {
        ParseException(String msg) {
            super(msg);
        }
    }
}
