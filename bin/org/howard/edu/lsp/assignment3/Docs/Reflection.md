# Reflection — Assignment 2 → Assignment 3 (ETL Pipeline Refactor)

## Overview

Assignment 2 implemented the ETL pipeline as a single, procedural program: one class (`ETLPipeline`) that opened files, parsed rows, applied all business rules, and wrote the output, mutating `Product` objects along the way.  
Assignment 3 refactors that code into a **modular, object-oriented** design with clear boundaries and swappable components while preserving the exact input/output behavior and relative paths.

---

## What changed in the design?

### A2 (procedural, tightly coupled)

- One class did **everything**: extract, transform, and load.
- Transformation logic and I/O were interleaved.
- `Product` was **mutable** and used `double` for money.
- Hard to unit test in isolation—testing required running the whole program.

### A3 (OO, decoupled)

- **Three roles** modeled explicitly:
  - `Extractor<Product>` → `CsvProductExtractor`
  - `Transformer<Product>` → `ProductTransformer`
  - `Loader<Product>` → `CsvProductLoader`
- `ETLApplication` orchestrates the flow and prints a summary; it does **no business logic**.
- `Product` is **immutable** and uses **`BigDecimal`** with `RoundingMode.HALF_UP` for correct money handling.
- `PriceRange` is a **type-safe enum** (not a free-form `String`).
- Each piece has a **single responsibility**, so parts can be replaced (e.g., JSON extractor, DB loader) without code changes elsewhere.

**Why this matters:** the pipeline now follows the **Strategy** pattern (pluggable Extractor/Transformer/Loader), **SRP** (each class does one thing), and is ready for extension without modifying core logic (**Open–Closed Principle**).

---

## How is Assignment 3 more object-oriented?

1. **Abstraction & Encapsulation:** The “what” (extract/transform/load) is separated from the “how” (CSV specifics). Callers depend on **interfaces**, not implementations.

2. **Composition:** `ETLApplication` composes an `Extractor`, `Transformer`, and `Loader`. Swapping strategies requires no code changes in the app.

3. **Immutability as encapsulation:** `Product`’s state is fixed after creation, eliminating side effects during transformation.

4. **Strong domain modeling:** `PriceRange` as an enum prevents typos and centralizes bucket logic (`PriceRange.from(price)`).

5. **Correctness-oriented types:** `BigDecimal` replaces `double` to avoid floating-point drift in currency calculations.

---

## OO ideas used (and where)

- **Object & Class:** `Product`, `PriceRange`, `CsvProductExtractor`, `ProductTransformer`, `CsvProductLoader`.
- **Encapsulation:** Internal details (I/O mechanics, rounding rules) hidden behind constructors/methods.
- **Inheritance / Interface-based Polymorphism:** `Extractor<T>`, `Transformer<T>`, `Loader<T>` interfaces with CSV implementations. `ETLApplication` programs to the interfaces.
- **Polymorphism (Strategy Pattern):** At runtime, different Extractor/Loader strategies could be supplied (CSV, DB, API) with no changes to the app.
- **Single Responsibility Principle:** Each class has one reason to change (e.g., change only `ProductTransformer` if the discount rule changes).
- **Open–Closed / Dependency Inversion (lightweight):** The app depends on abstractions, so it’s open for extension and closed for modification.

---

## Parity with Assignment 2 (business rules maintained)

A3 preserves all A2 rules and their order:

1. **Name → UPPERCASE.**
2. **10% discount** if original category is “Electronics.”
3. **Round to 2 decimals (HALF_UP)** to get the final price.
4. If final price **> 500.00** _and_ original category was Electronics → recategorize to **“Premium Electronics.”**
5. Derive **`PriceRange`** from the final rounded price:
   - ≤ 10.00 → Low
   - ≤ 100.00 → Medium
   - ≤ 500.00 → High
   - > 500.00 → Premium

Paths remain **relative** (`data/products.csv` → `data/transformed_products.csv`), and the console summary matches A2.

---

## How I verified A3 works the same as A2

**1) Golden-file test (end-to-end):**  
Used a 10-row CSV covering common cases. Ran `ETLApplication` and compared `data/transformed_products.csv` to the expected output. Verified uppercasing, proper discount timing, correct `Premium Electronics` recategorization, 2-decimal HALF_UP rounding, and correct `PriceRange` buckets.

**2) Deterministic checks (no JUnit):**  
Added `ProductTransformerTest` using Java `assert` to cover boundary values and rounding:

- 10.00 → Low; 10.01 → Medium
- 100.00 → Medium; 100.01 → High
- 500.00 → High; 500.01 → Premium
- Electronics examples: 100.00 → 90.00 (Medium), 500.00 → 450.00 (High), 1200.00 → 1080.00 (Premium + recategorized)
- Rounding: 1.005 → 1.01; discounted values near thresholds

**3) Input robustness (manual):**  
Confirmed malformed lines are skipped and header-only/empty input is handled as expected (header-only output).

---

## Why A3 is a better foundation

- **Testability:** Each part can be unit tested in isolation (e.g., `ProductTransformer` without I/O).
- **Extensibility:** Swap extractor/loader (e.g., DB, API, different CSV dialect) without touching `ETLApplication`.
- **Maintainability:** Clear separation of concerns shortens the change surface when requirements evolve.
- **Correctness & safety:** `BigDecimal` eliminates floating-point surprises; immutability reduces side effects.

**Conclusion:** A3 preserves A2’s observable behavior while upgrading the design to idiomatic OO: small, focused classes; strong types; pure transformations; and strategy-based composition. It’s easier to read, change, and test—without changing what it does.
