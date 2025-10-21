# AI Prompts & Excerpts — Assignment 3

## Overview

This file summarizes several prompts I asked an AI assistant while designing, implementing, and packaging Assignment 3 (object-oriented ETL pipeline). Each item includes my prompt (paraphrased) and a short excerpt from the AI’s response, plus how I applied it.

---

## 1) Refactoring A2 into an OO design

**Prompt**  
“How do I translate my A2 ETL into an optimal structural OO design without changing I/O? I want clean hierarchy, skeleton,and testability.”

**Excerpt**

> “Model three roles explicitly: `Extractor<T>`, `Transformer<T>`, `Loader<T>`. Keep `ETLApplication` as a thin orchestrator… Make `Product` immutable and use `BigDecimal`; model `PriceRange` as an enum. This is the Strategy pattern with SRP.”

**What I used**  
Created `Extractor`, `Transformer`, `Loader` interfaces; `CsvProductExtractor`, `ProductTransformer`, `CsvProductLoader`, `ETLApplication`. Switched to `BigDecimal` and `PriceRange` enum, kept relative paths.

---

## 2) VS Code classpath warning

**Prompt**  
“VS Code shows: ‘\*.java is not on the classpath… only syntax errors are reported’. How do I fix?”

**Excerpt**

> “Set Java source/output paths in `.vscode/settings.json` and reload the Java workspace…  
> `\"java.project.sourcePaths\": [\"src\"], \"java.project.outputPath\": \"bin\"`.”

**What I used**  
Updated `.vscode/settings.json`, then **Java: Clean Java Language Server Workspace**. The Run/Debug links appeared and the warning cleared.

---

## 3) File/class name mismatch error

**Prompt**  
“I get: ‘The public type CsvProductLoader must be defined in its own file’. What’s wrong?”

**Excerpt**

> “Your file is `CSVProductLoader.java` but the class is `CsvProductLoader`. Public class name must exactly match the filename (case-sensitive).”

**What I used**  
Renamed files to `CsvProductLoader.java` and `CsvProductExtractor.java`.

---

## 4) Unused header variable in extractor

**Prompt**  
“Linter says `String header` is never used. Should I keep it?”

**Excerpt**

> “Simplest: just call `br.readLine();` to skip the header and don’t store the result. Your later `items.isEmpty()` check already covers empty/header-only files.”

**What I used**  
Replaced `String header = br.readLine();` with `br.readLine();`.

---

## 5) Testing without JUnit

**Prompt**  
“I want a lightweight way to test the transformer (no JUnit). Suggestions?”

**Excerpt**

> “Create a `ProductTransformerTest` with a `main` method and Java `assert`. Run with `java -ea`… Cover boundaries (10/100/500), rounding (HALF_UP), and Electronics discount + premium recategorization.”

**What I used**  
Added `ProductTransformerTest.java` with asserts for boundary buckets and rounding cases; ran with `java -ea -cp bin …`.

---

## 6) Golden-file expectations

**Prompt**  
“What should the transformed CSV look like for the instructor sample?”

**Excerpt**

> “Ensure: uppercased names; 10% discount before bucketing; 2-decimals HALF_UP; ‘Premium Electronics’ only when discounted price > 500. Example row: `102,LAPTOP,1080.00,Premium Electronics,Premium`.”

**What I used**  
Compared `transformed_products.csv` to the golden output; matched exactly.

---

## 7) Markdown lint errors (MD022/MD032)

**Prompt**  
“My reflection MD fails: `MD022` and `MD032`. How do I fix spacing?”

**Excerpt**

> “Surround every heading with one blank line (before and after). Surround lists with blank lines too.”

**What I used**  
Inserted blank lines around all headings and lists; lint errors resolved.

---

## 8) “Multiple upstream branches” error

**Prompt**  
“`git push` says: current branch main has multiple upstream branches.”

**Excerpt**

> “Unset and set upstream explicitly:  
> `git branch --unset-upstream`  
> `git push -u origin main`.”

**What I used**  
Ran those commands; pushing worked.

---

## 9) Keep build artifacts and local test out of Git

**Prompt**  
“How do I stop committing `bin/*.class`, generated CSV, and my local test file?”

**Excerpt**

> “Add a `.gitignore`:  
> `bin/`, `*.class`, `data/transformed_products.csv`, `.vscode/`, and your test path.  
> If already tracked: `git rm -r --cached bin` then commit.”

**What I used**  
Added `.gitignore` and untracked `bin/`; kept test local.

---

## 10) VS Code Java metadata notice

**Prompt**  
“VS Code warns about project metadata files. Should I hide them?”

**Excerpt**

> “Either disable generation at project root with `java.import.generatesMetadataFilesAtProjectRoot: false`, or keep them and hide via `files.exclude`.”

**What I used**  
Disabled generation at root to keep the repo clean.

---

## Closing note

The AI guidance focused on **design clarity (Strategy + SRP)**, **correctness (`BigDecimal`, immutability)**, **tooling fixes (VS Code classpath, markdownlint)**, and **Git workflows**. I applied these recommendations to keep behavior identical to A2 while improving maintainability and testability in A3.
