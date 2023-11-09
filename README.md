# MicroJava Compiler

## Overview

This repository hosts the source code and documentation for the **MicroJava Compiler**, a Java-based compiler for the **MicroJava** programming language. MicroJava is a small, educational programming language, and this compiler is designed to convert MicroJava source code into bytecode that can be executed on the MicroJava Runtime Machine.

## Navigation

- [Overview](#overview)
- [Project Components](#project-components)
- [Syntax Analysis in General](#syntax-analysis-in-general)
- [Usage](#usage)
- [Contributions](#contributions)
- [License](#license)

## Project Components

The MicroJava Compiler consists of four primary components:

1. **Lexer**:
   - Implemented using [jFlex](https://jflex.de/), the lexer analyzes the source code and tokenizes it based on the MicroJava language specification. It identifies and classifies language constructs, such as keywords, operators, and identifiers. This lexer is a crucial initial step in the compilation process.

2. **Parser**:
   - Implemented using [CUP](http://www2.cs.tum.edu/projects/cup/), the parser takes the tokenized output from the lexer and constructs an Abstract Syntax Tree (AST) following the MicroJava language specification. It enforces the language's syntax rules, ensuring that the source code adheres to the correct structure and order of statements and expressions.

3. **Syntax Analysis**:
   - In this phase, the AST generated by the parser is analyzed to enforce the constraints of the MicroJava language. Syntax analysis ensures that the code follows the correct grammar, handles operator precedence, type checking, and validates the program's structure.

4. **Code Generation**:
   - The final phase of the compiler is responsible for generating executable bytecode for the MicroJava Runtime Machine. This process translates the validated AST into instructions that can be executed on the MicroJava virtual machine, enabling the execution of MicroJava programs.

## Syntax Analysis in General

Syntax analysis, also known as parsing, is an essential part of any compiler. It ensures that the source code adheres to the rules and grammar of the target programming language. During this phase, the compiler constructs a structured representation of the code (such as an AST) and performs various checks to ensure correctness. Common tasks in syntax analysis include identifying and reporting syntax errors, handling operator precedence, and building a representation of the program that can be used for further processing and code generation.

## Usage

The MicroJava Compiler can be used by students and educators as a learning tool for understanding the principles of compiler construction. To build and use the compiler, please refer to the instructions provided in the repository's documentation.

## Contributions

We welcome contributions to enhance the MicroJava Compiler. If you'd like to contribute, please follow our guidelines as outlined in the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## License

This project is open source and available under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code, respecting the terms and conditions of the license.

For any questions or feedback, please don't hesitate to [contact us](mailto:your-email@example.com).

Thank you for your interest in the MicroJava Compiler project. We hope it serves as a valuable resource for understanding compiler construction and the MicroJava programming language.
