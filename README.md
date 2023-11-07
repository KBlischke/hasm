# HASM

[1 Description](#1-description)  
[2 Usage](#2-usage)  
[3 Dependencies](#3-dependencies)  
[4 Implementation](#4-implementation)  
&nbsp;&nbsp;&nbsp;&nbsp;[4.1 Parser](#41-parser)  
&nbsp;&nbsp;&nbsp;&nbsp;[4.2 Coder](#42-coder)  
&nbsp;&nbsp;&nbsp;&nbsp;[4.3 SymbolTable](#43-symboltable)  
&nbsp;&nbsp;&nbsp;&nbsp;[4.4 Main](#44-main)  

## 1 Description

The HASM program is an assembler for the Hack assembly language. It was created as part of the course [nand2tetris](https://www.nand2tetris.org/course) and the book [The Elements of Computing Systems: Building a Modern Computer from First Principles](https://www.nand2tetris.org/book). The specification for the Hack assembly language can be found in [chapter 4](https://www.nand2tetris.org/_files/ugd/44046b_7ef1c00a714c46768f08c459a6cab45a.pdf) and the description for the project in [chapter 6](https://www.nand2tetris.org/_files/ugd/44046b_89a8e226476741a3b7c5204575b8a0b2.pdf) of the subsequent book.  

The program is build as a Maven project and follows its according conventions. All source files are inside the directory `src/main/java` and all test source files inside the directory `src/test/java`. The compiled class files can be found inside the directory `src/target/classes` for the main program and inside the directory `src/target/test-classes` for the test files. The compiled jar file of the program is located inside the directory `src/target`.  

## 2 Usage

The HASM assembler can be used inside the console with the command:  
`java -jar <programName>.jar <inputFileName>.asm [<outputFileName>.hack]`  

`<programName>.jar` is the name of the program. It is called `hasm-1.0.0-SNAPSHOT.jar` and positioned inside the directory `src/target` per default, but can be renamed and repositioned in any way, as long as the suffix `.jar` isn't changed.  

`<inputFileName>.asm` is the name of the Hack assembly file to be assembled. A relative path to that assembly file can be provided. The process failes if the provided file doesn't use the suffix `.asm`.  

`[<outputFileName>.hack]` is the name that should be provided to the assembled file. A relative path to the destination of that file can be provided. The process failes if the provided file doesn't use the suffix `.hack`. This argument can be omitted, which will result in the creation of a file named like the provided assembly file and inside the same location.  

If no arguments or more than two arguments are given to the program, the process failes.  

If the provided assembly file does contain syntax invalid to the Hack assembly language specification, the process will be aborted and an error message will be produced. This error message contains a warning, the line number of the detected invalid syntax and the invalid syntax itself.  

## 3 Dependencies

- [Java 17](https://www.oracle.com/de/java/technologies/downloads/#java17) or higher  
- [Maven 3.9](https://maven.apache.org/download.cgi) or higher (Optional: *only for building the project*)  
- [JUnit 5.10](https://junit.org/junit5/) or higher (Optional: *only for testing the project*)  

## 4 Implementation

The implementation of the HASM assembler follows the [API specification](https://www.nand2tetris.org/_files/ugd/44046b_89a8e226476741a3b7c5204575b8a0b2.pdf#page=10) given inside the course material. Some additions and minor changes were made for the sake of better usability and debugging.  

The API contains several modules containing routines. These are realized as classes with according methods.  

### 4.1 Parser

The `Parser` module breaks every Hack assembly instruction into its underlying fields. It's realized as follows:  

| Method | Arguments | Return | Description | Comment |
| ------ | --------- | ------ | ----------- | ------- |
| constructor | String | Parser | Returns a new parser object with argument to parse | Not in the original API |
| constructor | String[] | Parser | Returns a new parser objec with argument to parse | Not in the original API |
| constructor | File | Parser | Returns a new parser object with argument to parse | - |
| getCurrentLine | void | int | Returns the current line in the Parser object | Not in the original API |
| getCurrentInstruction | void | String | Returns the current instruction in the Parser object | Not in the original API |
| getInstructions | void | String[] | Returns all instructions passed to the constructor | Not in the original API |
| hasMoreInstructions | void | boolean | Checks if the last instruction hasn't been reached | Called "hasMoreCommands" in the original API |
| advance | void | void | Advances the current instruction to the next instruction | - |
| setCurrent | int | void | Sets the current instruction to the line passed by argument | - |
| getInstructionType | void | String | Returns the instruction type of the current instruction. The following returns are possible: <ul><li>"A_INSTRUCTION"</li><li>"C_INSTRUCTION"</li><li>"LABEL"</li><li>"EMPTY"</li><li>null</li></ul> | Called "commandType" in the original API. "A_INSTRUCTION" is called "A_COMMAND" in the original API. "C_INSTRUCTION" is called "C_COMMAND" in the original API. "EMPTY" is not in the original API. |
| getSymbol | void | String | Returns the symbol or address inside the current A-instruction or label | Called "symbol" in the original API |
| getDest | void | String | Returns the mnemmonic of the destination field inside the current C-instruction | Called "dest" in the original API |
| getComp | void | String | Returns the mnemmonic of the computation field inside the current C-instruction | Called "comp" in the original API |
| getJump | void | String | Returns the mnemmonic of the jump field inside the current C-instruction | Called "jump" in the original API |

### 4.2 Coder

The `Coder` module translates Hack assembly mnemonics into Hack machine instructions. It's realized as follows:  

| Method | Arguments | Return | Description | Comment |
| ------ | --------- | ------ | ----------- | ------- |
| constructor | void | Coder | Returns a new Coder object | Redundant because its methods are all static. Not in the original API |
| getDest | String | String | Returns the machine instruction for the mnemonic of a Hack assembly destination field | Called "dest" in the original API |
| getComp | String | String | Returns the machine instruction for the mnemonic of a Hack assembly computation field | Called "comp" in the original API |
| getJump | String | String | Returns the machine instruction for the mnemonic of a Hack assembly jump field | Called "jump" in the original API |
| getAddress | String | String | Returns the binary value of a decimal address value | Not in the original API |

### 4.3 SymbolTable

The `SymbolTable` module keeps a correspondence between symbolic labels and numeric addresses. It's realized as follows:  

| Method | Arguments | Return | Description | Comment |
| ------ | --------- | ------ | ----------- | ------- |
| constructor | void | SymbolTable | Returns a new SymbolTable object, with all built-in pointers of the Hack assembly language added per default | - |
| addEntry | String, String | void | Adds a new entry with the symbol and its address value to the SymbolTable object | The original API takes an `int` for the address value |
| deleteEntry | String | void | Deletes a specified entry in the SymbolTable object | Not in the original API |
| getTable | void | HashMap<String, String> | Returns all entries in the SymbolTable object | Not in the original API |
| contains | String | boolean | Checks if a symbol exists in the SymbolTable object | - |
| getAddress | String | String | Get the address associated with a value in the SymbolTable object, return `null` instead when the symbol doesn't exist | The original API returns an `int` for the address value |

### 4.4 Main

The `Main` module drives the entire assembling process and executes the modules and their routines. It's realized in the following way:  

1. Initialization of the provided input and output files  
2. Initialization of a SymbolTable object  
3. Adding the labels and their line numbers of the assembly file to the SymbolTable object (labels, comments and empty lines don't add up to the line number)  
4. Parsing each line of the assembly file with a Parser object  
5. Keeping track of all symbols with the SymbolTable object  
6. Translating each parsed line to Hack machine instructions with the Coder class  
7. Creating a Hack file from all parsed and translated lines  
