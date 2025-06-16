#!/bin/bash

# Set working directory to the script's location
cd "$(dirname "$0")"

# Compile the generator
javac -d target src/com/craftinginterpreters/tool/GenerateAst.java

# Run the generator to create classes in the `lox` package
java -cp target com.craftinginterpreters.tool.GenerateAst src/com/craftinginterpreters/lox