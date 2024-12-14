# Library Management Application

This project is a **Library Management Application** developed in **Java** using **Gradle** as the build tool. It provides functionalities to manage library items such as books and magazines, handle user loans, and ensure proper error handling through custom exceptions.

## Features

- **Library Catalog Management**:
  - Add and manage books and magazines.
  - Retrieve library items by their attributes.

- **Loan Management**:
  - Loan and return items to/from users.
  - Ensure availability checks when loaning items.

- **Error Handling**:
  - Includes custom exception handling for scenarios like:
    - `ItemNotFoundException`: When an item is not found in the catalog.
    - `NoCopyAvailableException`: When no copies of an item are available.
    - `ItemAlreadyReturnedException`: When attempting to return an item that has already been returned.

- **Unit Testing**:
  - Test classes are provided to ensure the functionality of the system.

## Project Structure

- **Source Code**:
  - Located in `src/main/java`.
  - Organized into packages:
    - `catalogue`: Handles library catalog management.
    - `exceptions`: Contains custom exception classes.
    - Root classes for application logic.

- **Resources**:
  - Additional resources like `.bib` files are located in `src/main/resources`.

- **Tests**:
  - Located in `src/test/java` for unit testing.

## Requirements

- **Java**: JDK 8 or higher.
- **Gradle**: Installed and configured.
- **IDE**: IntelliJ IDEA, VS Code, or any Java-compatible IDE.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/mudo9/Library-Management-Application.git

2. Navigate to the project directory:
   ```bash
   cd Library-Management-Application

3. Build the project:
   ```bash
   gradle build

4. Run the application:
   ```bash
   gradle run

## How to Test

- Run the unit tests using Gradle:
  ```bash
  gradle test


## Author
Developed by Michael Udo
