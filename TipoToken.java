public enum TipoToken {

    // Palabras Reservadas:
    CLASS,
    FUNCTION,
    VARIABLE,
    WHILE_LOOP,
    FOR_LOOP,
    IF,
    ELSE,
    OR,
    AND,
    PRINT,
    RETURN,
    TRUE,
    FALSE,
    NULL,
    THIS,
    SUPER,


    // Identificador:
    ID,

    // Numero:
    NUMBER,

    // Cadena:
    STRING,
    
    // Caracteres:
    BRACKET_LEFT,               // {
    BRACKET_RIGHT,              // }
    PARENTHESIS_LEFT,           // (
    PARENTHESIS_RIGHT,          // )
    SEMICOLON,                  // ;
    EQUALS,                     // =
    EXCLAMATION,                // !
    LESS_THAN,                  // <
    GREATER_THAN,               // >
    LESS_OR_EQUAL_THAN,         // <=
    GREATER_OR_EQUAL_THAN,      // >=
    DIFFERENT_THAN,             // !=
    EQUAL_THAN,                 // ==
    MINUS,                      // -
    PLUS,                       // +
    SLASH,                      // /
    ASTERISK,                   // *
    COMMA,                      // ,
    DOT,                        // .

    // Final de Cadena
    EOF,
    SOF
}