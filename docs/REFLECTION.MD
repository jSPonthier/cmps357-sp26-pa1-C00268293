
## Entry 1

**1. Name of Tool:**
Gemini Pro (via Cursor IDE)

**2. Components Benefited:**
The tool was heavily utilized for the implementation of Stages 3 through 7. Specifically, it assisted with:

* **Logic Implementation:** Writing the `search` and `sort` algorithms in `RecipeBook` (Stage 3) and the ingredient aggregation logic in `ShoppingCart` (Stage 4).
* **Data Persistence:** Generating the `RecipeJsonStore` to handle custom JSON parsing without external libraries (Stage 5).
* **UI Construction:** Scaffolding the `ConsoleUI` class and the command-loop structure (Stage 6).
* **Refactoring:** Converting the `Recipe` class to utilize the `Ingredient` object instead of parallel lists (Stage 7).

**3. Review and Verification:**
I reviewed the tool's output by running the specific "Demo" classes generated for each stage (e.g., `Stage3Demo`, `Stage4Demo`) and comparing the actual console output against the expected results in the specifications. I specifically tested edge cases, such as case-insensitivity during ingredient searching and correct formatting during shopping cart aggregation, to ensure the logic held up under non-standard inputs.

**4. Workflow Reflection:**

* **Worked Immediately:** The speed of implementing feature logic was the primary benefit. The tool successfully generated complex Java syntax, such as the custom JSON string parsing and the multi-token search logic, with very few syntax errors on the first attempt.
* **Required Multiple Iterations:** Documentation generation required several attempts. For example, when updating `README.md`, the tool initially replaced existing content rather than appending to it, requiring a prompt refinement to preserve the original text. Similarly, `STAGES.md` required a specific prompt to ensure the checklist format met the specific rubric requirements.
* **Required Manual Implementation:** While the coding was handled well by the AI, environment configuration (such as fixing `javac` path issues) required manual troubleshooting. I also manually verified the final formatting of the Markdown documents to ensure strict compliance with the project rubrics.