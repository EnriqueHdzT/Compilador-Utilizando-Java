public class Token {

    public TipoToken tipo;
    public String lexema;
    public int linea;
    public Object literal;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = linea;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = 0;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, double literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = 0;
        this.literal = literal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if (this.tipo == ((Token) o).tipo) {
            return true;
        }

        return false;
    }

    public String toString() {
        return tipo + " " + lexema + " " + literal;
    }

    public boolean esPalabraReservada() {
        switch (this.tipo) {
            case CLASS:
            case FUNCTION:
            case VARIABLE:
            case WHILE_LOOP:
            case FOR_LOOP:
            case IF:
            case PRINT:
            case RETURN:
            case ELSE:
            case TRUE:
            case FALSE:
            case NULL:
            case THIS:
            case SUPER:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperando() {
        switch (this.tipo) {
            case ID:
            case NUMBER:
            case STRING:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador() {
        switch (this.tipo) {
            case PLUS:
            case MINUS:
            case SLASH:
            case ASTERISK:
            case LESS_THAN:
            case LESS_OR_EQUAL_THAN:
            case EQUALS:
            case EQUAL_THAN:
            case GREATER_THAN:
            case GREATER_OR_EQUAL_THAN:
            case EXCLAMATION:
            case DIFFERENT_THAN:
            case AND:
            case OR:
            case PRINT:
                return true;
            default:
                return false;
        }
    }

    public int aridad() {
        switch (this.tipo) {
            case PLUS:
            case MINUS:
            case SLASH:
            case ASTERISK:
            case AND:
            case OR:
            case LESS_THAN:
            case LESS_OR_EQUAL_THAN:
            case EQUALS:
            case EQUAL_THAN:
            case GREATER_THAN:
            case GREATER_OR_EQUAL_THAN:
            case DIFFERENT_THAN:
                return 2;
            default:
                return 0;
        }
    }

    public int precedencia() {
        switch (this.tipo) {
            case ASTERISK:
            case SLASH:
                return 7;
            case PLUS:
            case MINUS:
                return 6;
            case LESS_THAN:
            case LESS_OR_EQUAL_THAN:
            case GREATER_OR_EQUAL_THAN:
            case GREATER_THAN:
                return 5;
            case EQUAL_THAN:
            case DIFFERENT_THAN:
                return 4;
            case AND:
                return 3;
            case OR:
                return 2;
            case EQUALS:
                return 1;
            default:
                return 0;
        }
    }
}
