# Walmart USA - Advanced Software Engineering Virtual Internship

This repository contains all the tasks and implementations completed as part of the Walmart USA Advanced Software Engineering Job Simulation on Forage.

## 📌 Repository Structure
- `PowerOfTwoMaxHeap.java` : Task 1 implementation of a modified heap with 2^x children per node.
- `DataProcessorSystem.java` : Task 2 implementation of a dynamically reconfigurable data processing pipeline using Strategy Pattern and SOLID principles.

## 🛠️ Tasks Summary

### Task 1: Advanced Data Structures
- Implemented a novel **Power of Two Max Heap** data structure in Java.
- Configured dynamic child count ($2^X$) per node where $X$ is defined during initialization.
- Added performant `insert` and `popMax` operations adhering to max heap properties.

### Task 2: Software Architecture
- Designed a UML Class Diagram for a dynamically reconfigurable data processor.
- Implemented support for multiple execution modes: `DUMP`, `PASSTHROUGH`, and `VALIDATE`.
- Integrated pluggable database connections for `PostgreSQL`, `Redis`, and `Elasticsearch`.

### Task 3: Relational Database Design (In Progress)
- Designing schema and queries for complex database requirements.

### Task 4: Data Munging (In Progress)
- Handling large-scale data ingestion and transformation.

## 💻 Tech Stack
- **Language:** Java 17+
- **Version Control:** Git & GitHub
- **Architecture Tools:** UML, PlantUML