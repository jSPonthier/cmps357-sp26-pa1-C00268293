# ARCHITECTURE.md

## Overview
This document describes the high-level architecture of the Recipe application.  
It focuses on separation of concerns, dependency direction, and the roles of each layer.  
Implementation details and data rules are defined in `DATA_MODEL.md` and `SPEC.md`.

---

## Architectural Style
The application follows a layered, model-centered architecture:

- Core domain models are independent of UI and persistence.
- Services coordinate domain objects and implement application-level behavior.
- Persistence is isolated behind explicit read/write components.
- The user interface depends on services, not on low-level data structures.

This structure supports incremental development, testing, and future replacement of the UI or storage mechanism.

---

## Layer Overview

```
UI Layer
  ↓
Service Layer
  ↓
Domain Model
  ↑
Persistence Layer
```

---

## Domain Model Layer

**Responsibility**
- Represent core concepts and enforce invariants.
- Contain no UI, file I/O, or framework-specific code.

**Key Classes**
- `Ingredient`
- `Recipe`

**Rules**
- Domain classes:
  - Validate their own state.
  - Do not perform searching, sorting, or aggregation across multiple recipes.
  - Do not depend on any other application layers.

---

## Service Layer

**Responsibility**
- Coordinate domain objects to implement application features.
- Provide stable APIs to the UI layer.

**Key Components**
- `RecipeBook`
  - Manages a collection of `Recipe` objects.
  - Supports add, remove, search, and retrieval operations.
- Search utilities
  - Perform read-only queries over recipe collections.
- Shopping cart aggregation
  - Builds derived views by combining ingredients from multiple recipes.

**Rules**
- Services may depend on domain model classes.
- Services do not perform file I/O directly.
- Services do not format data for display beyond what is already defined in domain rules.

---

## Persistence Layer

**Responsibility**
- Serialize and deserialize application state.
- Translate between in-memory objects and external representations.

**Key Components**
- JSON reader and writer (e.g., `RecipeJsonStore`)

**Rules**
- Persistence logic is isolated from the domain and service layers.
- Domain objects are validated on load.
- Persistence code does not contain business logic beyond structural mapping.

---

## UI Layer

**Responsibility**
- Handle user interaction and presentation.
- Invoke service-layer operations.
- Display formatted output.

**Examples**
- Console-based UI
- Future alternatives such as GUI or web front-ends

**Rules**
- UI code:
  - Does not manipulate domain objects directly.
  - Does not perform aggregation, searching, or sorting logic itself.
  - Applies sorting and formatting only when presenting results.

---

## Dependency Rules

- Dependencies flow inward toward the domain model.
- The domain model depends on nothing outside itself.
- The UI layer depends on the service layer.
- The persistence layer depends on the domain model but is not depended upon by it.

This ensures that core logic remains stable even if the UI or storage mechanism changes.

---

## Error Handling Strategy

- Domain-level validation prevents invalid state.
- Service-level operations handle missing or invalid requests gracefully.
- Persistence errors are reported to the UI layer without leaking implementation details.

---

## Extensibility Considerations

The architecture supports future extensions such as:
- Alternative persistence formats (CSV, database)
- Additional search or filter criteria
- Multiple UI implementations
- Unique identifiers for recipes
- Ingredient normalization strategies

These changes should not require modifications to the core domain model.

---

## Summary
This architecture emphasizes clarity, separation of concerns, and testability.  
By keeping responsibilities narrowly defined and dependencies unidirectional, the system remains easy to reason about, extend, and maintain as new features are added.

