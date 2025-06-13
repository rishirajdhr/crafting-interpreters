# crafting-interpreters
Implementation of Crafting Interpreters by Robert Nystrom (https://craftinginterpreters.com/contents.html)


## Syntactic Grammer

```
expression  -> literal
            |  unary
            |  binary
            |  grouping ;

literal     -> NUMBER | STRING | "true" | "false" | "nil" ;
grouping    -> "(" expression ")" ;
unary       -> ( "-" | "!" ) expression ;
binary      -> expression operator expression ;
operator    -> "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" ;
```