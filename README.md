# MicroJava Compiler
![Untitled](https://github.com/zarkobabic/Microjava-compiler/assets/92127059/5a4ec5ab-2712-49d6-a213-c5104a8039b9)

## Navigation

- [Overview](#overview)
- [Project Components](#project-components)
- [Lexing in General](#lexing-in-general)
- [Parsing in General](#parsing-in-general)
- [Syntax Analysis in General](#syntax-analysis-in-general)
- [Code Generation in General](#code-generation-in-general)
- [Usage](#usage)
- [License](#license)

## Overview

This repository hosts the source code and documentation for the **MicroJava Compiler**, a Java-based compiler for the **MicroJava** programming language. MicroJava is a small, educational programming language, and this compiler is designed to convert MicroJava source code into bytecode that can be executed on the MicroJava Runtime Machine.

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

## Lexing in General

Lexing, also known as tokenization, is the process of breaking down the source code into a stream of tokens. Tokens are the smallest units of a programming language and include keywords, identifiers, operators, and literals. The lexer's role is to recognize and classify these tokens based on the language's grammar and rules.

## Parsing in General

Parsing is a crucial step in the compilation process that follows lexing. It involves the analysis of the tokenized source code to determine its structure and relationships between different elements. In this phase, a parser constructs an Abstract Syntax Tree (AST) that represents the program's syntax according to the language's grammar. It enforces the correct order of statements, expressions, and the overall program structure.


## Syntax Analysis in General

Syntax analysis, also known as parsing, is an essential part of any compiler. It ensures that the source code adheres to the rules and grammar of the target programming language. During this phase, the compiler constructs a structured representation of the code (such as an AST) and performs various checks to ensure correctness. Common tasks in syntax analysis include identifying and reporting syntax errors, handling operator precedence, and building a representation of the program that can be used for further processing and code generation.

## Code Generation in General

Code generation is the final phase of a compiler, responsible for producing executable code from the parsed and analyzed source code. During this phase, the compiler generates code that can be executed on the target platform, such as machine code or bytecode. The generated code should adhere to the rules and specifications of the target platform and be semantically equivalent to the original source code.

## Usage

The MicroJava Compiler can be used by students and educators as a learning tool for understanding the principles of compiler construction. To build and use the compiler, please refer to the instructions provided in the repository's documentation.

## Documentation

## Appendix A: MicroJava Programming Language

This appendix describes the MicroJava programming language. Microjava is similar to Java but much simpler.

## A.1 General Features of the Language

- A MikroJava program starts with a keyword `program` and has static fields, static methods, and inner classes that can be used as (user) data types.
- The main method of a MikroJava program is always called `main()`. When a MikroJava program is called, that method is executed.
- Since:
  - Integer, sign, and logical constants (`int`, `char`, `bool`).
  - Basic types: `int`, `bool`, `char` (ASCII).
  - Variables: global (static), local, class (fields).
    - Variables of basic types contain values.
    - Structured/reference types: one-dimensional arrays as in Java and inner classes with fields and methods.
  - Variables of reference types represent references (they contain addresses that cannot be changed explicitly).
- Static methods in the program.
- There is no garbage collector (allocated objects are only deallocated after the end of the program).
- There is class inheritance and polymorphism.
- There is a redefinition of methods.
- Methods of inner classes are bound to the instance and have an implicit parameter `this` (a reference to the instance of the class for which the method was called).
  - The reference "this" is implicitly declared in the methods of inner classes as the first formal argument of the reference type to the class to which the method belongs.
  - Within instance methods, the field name refers to the instance field of the current object, assuming the field is not hidden by a method parameter. If it is hidden, we can access the instance field via `this.fieldName`.
- Pre-declared procedures: `ord`, `chr`, `len`.
- Method `print` prints the values of all basic types.
- Control structures include conditional branching (`if-else`) and cycle (`do-while`).

## A.2 Syntax
```cup
## Program Structure
Program = "program" ident {ConstDecl | VarDecl | ClassDecl } "{" {MethodDecl} "}".

## Constant Declaration
ConstDecl = "const" Type ident "=" (numConst | charConst | boolConst) {, ident "=" (numConst | charConst | boolConst)} ";".

## Variable Declaration
VarDecl = Type ident ["[" "]"] {"," ident ["[" "]"]} ";".

## Class Declaration
ClassDecl = "class" ident ["extends" Type] "{" {VarDecl} ["{" {ConstructorDecl} {MethodDecl} "}"] "}".

## Constructor Declaration
ConstructorDecl = ident "(" [FormPars] ")" {VarDecl} "{" {Statement} "}. * za C nivo

## Method Declaration
MethodDecl = (Type | "void") ident "(" [FormPars] ")" {VarDecl} "{" {Statement} "}".

## Formal Parameters
FormPars = Type ident ["[" "]"] {"," Type ident ["[" "]"]}.

## Type Definition
Type = ident.

## Statements
Statement = DesignatorStatement ";" | "if" "(" Condition ")" Statement ["else" Statement] | "while" "(" Condition ")" Statement | "break" ";" | "continue" ";" | "return" [Expr] ";" | "read" "(" Designator ")" ";" | "print" "(" Expr ["," numConst] ")" ";" | Designator "." "foreach" "(" ident "=>" Statement ")" ";" | "{" {Statement} "}".

## Designator Statement
DesignatorStatement = Designator (Assignop Expr | "(" [ActPars] ")" | "++" | "‐‐") | "[" [Designator] {"," [Designator]}"]" "=" Designator.

## Actual Parameters
ActPars = Expr {"," Expr}.

## Conditions
Condition = CondTerm {"||" CondTerm}.

## Conditional Term
CondTerm = CondFact {"&&" CondFact}.

## Conditional Factor
CondFact = Expr [Relop Expr].

## Expressions
Expr = ["‐"] Term {Addop Term}.

## Terms
Term = Factor {Mulop Factor}.

## Factors
Factor = Designator ["(" [ActPars] ")"] | numConst | charConst | boolConst | "new" Type ( "[" Expr "]" | "(" [ActPars] ")" ) | "(" Expr ")".

## Designators
Designator = ident {"." ident | "[" Expr]"}.

## Labels
Label = ident.

## Assignment Operator
Assignop = "=".

## Relational Operators
Relop = "==" | "!=" | ">" | ">=" | "<" | "<=".

## Addition Operators
Addop = "+" | "‐".

## Multiplication Operators
Mulop = "*" | "/" | "%".
```





## License

This project is open source and available under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code, respecting the terms and conditions of the license.

For any questions or feedback, please don't hesitate to [contact us](mailto:your-email@example.com).

Thank you for your interest in the MicroJava Compiler project. We hope it serves as a valuable resource for understanding compiler construction and the MicroJava programming language.
