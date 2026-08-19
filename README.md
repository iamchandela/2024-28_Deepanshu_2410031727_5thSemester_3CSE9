# Walmart USA - Advanced Software Engineering Virtual Internship

This repository contains all the tasks and implementations completed as part of the Walmart USA Advanced Software Engineering Job Simulation on Forage.

📌 Repository Structure

* PowerOfTwoMaxHeap.java : Task 1 implementation of a modified heap with 2^x children per node.
* DataProcessorSystem.java : Task 2 implementation of a dynamically reconfigurable data processing pipeline using Strategy Pattern and SOLID principles.
* schema.sql : Task 3 implementation of a 3NF normalized relational database schema for Walmart's Pet Department.
* Data_Munger.py : Task 4 implementation of a Python script to extract, transform, and load CSV data into an SQLite database.

🔨 Tasks Summary

Task 1: Advanced Data Structures

* Implemented a novel Power of Two Max Heap data structure in Java.
* Configured dynamic child count (2^x) per node where X is defined during initialization.
* Added performant insert and popMax operations adhering to max heap properties.

Task 2: Software Architecture

* Designed a UML Class Diagram for a dynamically reconfigurable data processor.
* Implemented support for multiple execution modes: DUMP, PASSTHROUGH, and VALIDATE.
* Integrated pluggable database connections for PostgreSQL, Redis, and Elasticsearch.

Task 3: Relational Database Design

* Designed a 3NF normalized relational database schema (schema.sql) for Walmart's Pet Department.
* Created ER Diagram mapping entities for products, food, toys, apparel, customers, transactions, and shipments.
* Structured primary keys, foreign keys, and relational constraints to ensure data integrity.

Task 4: Data Munging

* Developed a Python data munging pipeline to parse and process disparate CSV spreadsheets.
* Extracted and combined shipment identifiers, product details, quantities, origins, and destinations.
* Populated the SQLite database (shipping.db) with robust error handling and type-safety mechanisms.

💻 Tech Stack

* Language: Java 17+, SQL, Python 3
* Version Control: Git & GitHub
* Architecture Tools: UML, PlantUML

Certification Link - https://www.theforage.com/completion-certificates/prBZoAihniNijyD6d/oX6f9BbCL9kJDJzfg_prBZoAihniNijyD6d_6a81679dba854243f450a1f4_1786879712477_completion_certificate.pdf