# cmps357-sp26-pa1-template

## Project Requirements
 - Java IDE such as
   - **IntelliJ Community Edition** [Windows](https://download.jetbrains.com/idea/ideaIC-2025.2.6.1.exe?_cl=MTsxOzE7c2tod09qdG1hNXpQbERWcGpJWGlZUUZ6Ym0zUW0yVTdpSGxRWHE3RU85RVJPaGtGd2tQSmNRTW13NGpSb0lXbTs=&_gl=1*1uc1dra*_gcl_au*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*FPAU*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*_ga*MTk5NDUxODMxNy4xNzY4NTgwNjIy*_ga_9J976DJZ68*czE3Njg1ODA2MTkkbzEkZzEkdDE3Njg1ODA2NjkkajEwJGwwJGgw) [Linux](https://download.jetbrains.com/idea/ideaIC-2025.2.6.1-aarch64.tar.gz?_gl=1*njar6m*_gcl_au*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*FPAU*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*_ga*MTk5NDUxODMxNy4xNzY4NTgwNjIy*_ga_9J976DJZ68*czE3Njg1ODA2MTkkbzEkZzEkdDE3Njg1ODA2NjkkajEwJGwwJGgw) [macOS (Intel)](https://download.jetbrains.com/idea/ideaIC-2025.2.6.1.dmg?_gl=1*ue2abp*_gcl_au*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*FPAU*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*_ga*MTk5NDUxODMxNy4xNzY4NTgwNjIy*_ga_9J976DJZ68*czE3Njg1ODA2MTkkbzEkZzEkdDE3Njg1ODA2NjkkajEwJGwwJGgw) [macOS (Apple Silicone)](https://download.jetbrains.com/idea/idea-2025.3.1.1-aarch64.dmg?_gl=1*ue2abp*_gcl_au*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*FPAU*MTI0NzgwMjk5NS4xNzY4NTgwNjIy*_ga*MTk5NDUxODMxNy4xNzY4NTgwNjIy*_ga_9J976DJZ68*czE3Njg1ODA2MTkkbzEkZzEkdDE3Njg1ODA2NjkkajEwJGwwJGgw), or
   - **VS Code** [Windows](https://code.visualstudio.com/sha/download?build=stable&os=win32-x64) [Linux](https://code.visualstudio.com/sha/download?build=stable&os=linux-x64) [macOS (Intel)](https://code.visualstudio.com/sha/download?build=stable&os=darwin) [macOS (Apple Silicone)](https://code.visualstudio.com/sha/download?build=stable&os=darwin-arm64)
 - Java SDK such as Azul Zulu
    - Available to download from with IntelliJ, or
    - Available [here](https://www.azul.com/downloads/?version=java-17-lts&package=jdk#zulu)
 - Git [Windows](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/Git-2.52.0-64-bit.exe) [Linux](https://git-scm.com/install/linux) [macOS](https://git-scm.com/install/mac)

## Setup and Execution

Combined steps to create a working environment in Visual Studio Code, configure it, compile the program, and run it after cloning.

### Create a Working Environment (VS Code)

This project uses `.vscode/tasks.json` and `.vscode/launch.json` for build and run configuration (analogous to a Makefile or `requirements.txt` in other projects).

1. **Clone the repository**  
   ```bash
   git clone <your-repository-url>
   cd cmps357-sp26-pa1-C00268293
   ```

2. **Open in VS Code**  
   - File → Open Folder, then select the cloned directory.

3. **Install Extension Pack for Java** (Microsoft)  
   - Extensions view (Ctrl+Shift+X) → search "Extension Pack for Java" → Install.

4. **Configure JDK**  
   - Ensure `JAVA_HOME` points to a Java 17+ JDK, or set it in VS Code: File → Preferences → Settings → search `java.configuration.runtimes`.

5. **Accept workspace configuration**  
   - When prompted, allow VS Code to use the `.vscode` tasks and launch configurations.

### Compile the Program

- **In VS Code:** Terminal → Run Build Task (or Ctrl+Shift+B / Cmd+Shift+B).  
- **Command line:**  
  ```bash
  javac -d bin -cp bin src/*.java
  ```  
  With tests:  
  ```bash
  javac -d bin -cp bin src/*.java test/*.java
  ```

### Run the Program

- **In VS Code:** Run and Debug panel (Ctrl+Shift+D). Choose a configuration such as Run Main, Run ConsoleUI, Run Stage2Demo, or Run RecipeTest.  
- **Command line:**  
  ```bash
  java -cp bin Main
  java -cp bin ConsoleUI
  java -cp bin RecipeTest
  ```

## Syllabus README Note

This `README.md` should be edited to match the syllabus README description: include concise setup and execution instructions for this project. Below are Java-specific details you should keep or expand when editing this file:

- **VS Code setup**: Install the *Extension Pack for Java* (recommended) and the *Language Support for Java(TM) by Red Hat* extension. Open the project folder in VS Code and allow the Java extensions to configure the workspace.
- **Java runtime**: Install a Java 17+ JDK (e.g., Azul Zulu or OpenJDK) and ensure `JAVA_HOME` is set or configured in VS Code settings.
- **Run in VS Code**: Use the Java Extension Pack to run or debug the `Main` class directly (click the Run icon or use the Run | Debug options). Configure launch configurations in `.vscode/launch.json` if desired.
- **Notes for students**: After cloning, follow these steps to configure VS Code and the JDK, then use the commands above to compile and run. Update this README with any project-specific setup steps (build tools, additional environment variables, or required extensions).

Suggested workflow (starting at Stage 3):

1. Run **Stage 1** by executing `Main.java` to verify that Java, the JDK, and your IDE are configured correctly.
2. Run the **Stage 2** demo file to confirm that the provided example behavior works as expected.
3. Read **Stage 3** in [STAGES.md](docs/STAGES.md) carefully, noting which requirements are already implemented and which are still incomplete.
4. Treat Stage 3 as a continuation stage:
   - Identify gaps between the existing code and the Stage 3 acceptance criteria.
   - Plan the remaining work before writing code.
5. Use AI tools intentionally and transparently:
   - Use AI to help plan implementations, outline algorithms, and reason about edge cases.
   - Use AI to generate or refine method stubs, comments, and documentation.
   - Review, adapt, and understand all AI-generated output before incorporating it.
6. Implement the remaining Stage 3 features incrementally:
   - Make small, focused changes.
   - Commit frequently with messages that describe what changed and why.
7. After completing Stage 3, proceed sequentially through **Stages 4–6**:
   - Read each stage fully before starting.
   - Use AI for design planning, refactoring guidance, and documentation updates.
   - Avoid implementing features from later stages early unless explicitly instructed.
8. Push your work regularly to your GitHub Classroom repository to preserve progress and demonstrate steady development.


**Note**: Each student will work in their own repository created through GitHub Classroom. When you accept the assignment, GitHub automatically generates a private repository under your account where you have full write access. You should clone that repository locally, make changes, Commit them, and Push updates normally. Unlike a shared example repository, you are already a contributor to your own Classroom repository, so no Fork is required.

The original files and workflow in this repository (Day 1) were created with the help of a [ChatGPT Session](https://chatgpt.com/share/696a5966-6000-8011-a070-388192adf348).

## Day 2+ From Single Class to Application

Once the `Recipe` class is complete and behaving correctly, we will begin moving beyond a single-class exercise and toward a small but complete application.

At this stage, the focus shifts from:
- implementing isolated methods and
- writing driver code

To:
- designing interactions between classes
- managing collections of objects
- separating responsibilities across files

You should expect the project to grow incrementally rather than all at once.

### What changes conceptually
- `Recipe` becomes a *domain object*, not the whole program.
- New classes will manage collections of recipes, searching, sorting, and aggregation.
- Output formatting and user interaction will move out of `Recipe`.

### What stays the same
-  [SPEC.md](docs/SPEC.md) remains the source of truth for required behavior.
- Formatting rules for ingredients do not change.
- Small, testable steps remain the goal.

### Upcoming focus areas
Over the next several days, we will introduce:
- managing multiple recipes at once
- searching and sorting recipes
- combining ingredients across recipes into a shopping list
- reading and writing data to files using JSON
- thinking in terms of architecture instead of individual methods

Each of these additions will be guided by the documents in the `docs/` directory:
-  [ARCHITECTURE.md](docs/ARCHITECTURE.md) for overall structure
-  [DATA_MODEL.md](docs/DATA_MODEL.md) for how data is represented
-  [STAGES.md](docs/STAGES.md) for incremental milestones

By the end of this sequence, you should be comfortable reading a spec, using an LLM to assist with implementation, and reasoning about how small classes fit into a larger design.




## Repository Structure

```
cmps357-sp26-first-example/
├─ AUTHORS.md
├─ README.md
├─ docs/
│  ├─ SPEC.md
│  ├─ ARCHITECTURE.md
│  ├─ DATA_MODEL.md
│  ├─ STAGES.md
│  ├─ TRANSCRIPT.md
│  ├─ REFLECTION.md
│  └─ EXAMPLES.md
└─ src/
   └─ cmps357/
      └─ sp26/
         ├─ Main.java
         ├─ model/
         │  ├─ Recipe.java
         │  └─ Ingredient.java
         ├─ service/
         │  ├─ RecipeBook.java
         │  ├─ ShoppingCart.java
         │  ├─ Search.java
         │  └─ Sorts.java
         ├─ io/
         │  └─ RecipeJsonStore.java
         └─ ui/
            └─ ConsoleUI.java
```
> Note: The `ui/` directory is structured to allow future alternatives such as JavaFX or web-based front ends.

The updated files and workflow in this repository (Day 2+) were created with the help of a [ChatGPT Session](https://chatgpt.com/share/6972a438-2e10-8011-9a61-eef40179e2c6).
