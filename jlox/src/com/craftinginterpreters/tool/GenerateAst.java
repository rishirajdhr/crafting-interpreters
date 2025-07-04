package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * A CLI script to generate the abstract syntax tree classes. It is a utility
 * for automating file authoring, not a part of the interpreter itself.
 */
public class GenerateAst {
    private static String indent = "    ";

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary   : Expr left, Token operator, Expr right",
            "Grouping : Expr expression",
            "Literal  : Object value",
            "Unary    : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.craftinginterpreters.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        defineVisitor(writer, baseName, types);

        // The AST classes.
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        // The base accept() method.
        writer.println();
        writer.println(indent + "abstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println(indent + "interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println(indent + indent + "R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println(indent + "}\n");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println(indent + "static class " + className + " extends " + baseName + " {");

        //Fields
        String[] fields = fieldList.split(", ");
        
        for (String field : fields) {
            writer.println(indent + indent + "final " + field + ";");
        }
        writer.println();

        // Constructor
        writer.println(indent + indent + className + "(" + fieldList + ") {");

        // Store parameters in fields
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println(indent + indent + indent + "this." + name + " = " + name + ";");
        }

        writer.println(indent + indent + "}");

        // Visitor pattern
        writer.println();
        writer.println(indent + indent + "@Override");
        writer.println(indent + indent + "<R> R accept(Visitor<R> visitor) {");
        writer.println(indent + indent + indent + "return visitor.visit" + className + baseName + "(this);");
        writer.println(indent + indent + "}");

        writer.println(indent + "}");
        writer.println();
    }
}
