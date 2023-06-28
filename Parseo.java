
import java.util.List;

public class Parseo {
    private final List<Token> tokens;

    private final Token clase = new Token(TipoToken.CLASS, "class");
    private final Token funcion = new Token(TipoToken.FUNCTION, "fun");
    private final Token variable = new Token(TipoToken.VARIABLE, "var");
    private final Token cicloMientras = new Token(TipoToken.WHILE_LOOP, "while");
    private final Token cicloPara = new Token(TipoToken.FOR_LOOP, "for");
    private final Token condicionSi = new Token(TipoToken.IF, "if");
    private final Token condicionDeOtraForma = new Token(TipoToken.ELSE, "else");
    private final Token condicionO = new Token(TipoToken.OR, "or");
    private final Token condicionY = new Token(TipoToken.AND, "and");
    private final Token imprime = new Token(TipoToken.PRINT, "print");
    private final Token regresa = new Token(TipoToken.RETURN, "return");
    private final Token verdadero = new Token(TipoToken.TRUE, "true");
    private final Token falso = new Token(TipoToken.FALSE, "false");
    private final Token nulo = new Token(TipoToken.NULL, "null");
    private final Token esto = new Token(TipoToken.THIS, "this");
    private final Token ssuper = new Token(TipoToken.SUPER, "super");

    private final Token parentesisIzq = new Token(TipoToken.PARENTHESIS_LEFT,
            "(");
    private final Token parentesisDer = new Token(TipoToken.PARENTHESIS_RIGHT,
            ")");
    private final Token llavesIzq = new Token(TipoToken.BRACKET_LEFT, "{");
    private final Token llavesDer = new Token(TipoToken.BRACKET_RIGHT, "}");
    private final Token puntoComa = new Token(TipoToken.SEMICOLON, ";");
    private final Token igual = new Token(TipoToken.EQUALS, "=");
    private final Token exclamacion = new Token(TipoToken.EXCLAMATION, "!");
    private final Token menorQue = new Token(TipoToken.LESS_THAN, "<");
    private final Token mayorQue = new Token(TipoToken.GREATER_THAN, ">");
    private final Token menorOIgualQue = new Token(TipoToken.LESS_OR_EQUAL_THAN,
            "<=");
    private final Token mayorOIgualQue = new Token(TipoToken.GREATER_OR_EQUAL_THAN, ">=");
    private final Token diferenteQue = new Token(TipoToken.DIFFERENT_THAN, "!=");
    private final Token igualQue = new Token(TipoToken.EQUAL_THAN, "==");
    private final Token menos = new Token(TipoToken.MINUS, "-");
    private final Token suma = new Token(TipoToken.PLUS, "+");
    private final Token barra = new Token(TipoToken.SLASH, "/");
    private final Token asterisco = new Token(TipoToken.ASTERISK, "*");
    private final Token coma = new Token(TipoToken.COMMA, ",");
    private final Token punto = new Token(TipoToken.DOT, ".");

    private final Token identificador = new Token(TipoToken.ID, "");
    private final Token numero = new Token(TipoToken.NUMBER, "");
    private final Token cadena = new Token(TipoToken.STRING, "");
    private final Token finCadena = new Token(TipoToken.EOF, "");

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parseo(List<Token> tokens) {
        this.tokens = tokens;
    }

    public boolean parseo() {
        i = 0;
        preanalisis = tokens.get(i);
        Declaration();

        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println(
                    "Error en la linea " + preanalisis.linea + ". No se esperaba el token " +
                            preanalisis.tipo);
            return false;
        } else if (!hayErrores && preanalisis.equals(finCadena)) {
            
            return true;
        }
        return false;

        /*
         * if(!preanalisis.equals(finCadena)){
         * System.out.println("Error en la posición " + preanalisis.posicion +
         * ". No se esperaba el token " + preanalisis.tipo);
         * }else if(!hayErrores){
         * System.out.println("Consulta válida");
         * }
         */

    }

    void Declaration() { // Still needs corrections
        if (preanalisis.equals(clase)) {
            classDeclaration();
            Declaration();
        } else if (preanalisis.equals(funcion)) {
            funDeclaration();
            Declaration();
        } else if (preanalisis.equals(variable)) {
            varDeclaration();
            Declaration();
        } else if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(esto) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parentesisIzq)
                || preanalisis.equals(ssuper) || preanalisis.equals(exclamacion) || preanalisis.equals(menos)
                || preanalisis.equals(cicloPara) || preanalisis.equals(condicionSi) || preanalisis.equals(imprime)
                || preanalisis.equals(regresa) || preanalisis.equals(cicloMientras) || preanalisis.equals(llavesIzq)) {
            statement();
            Declaration();
        } else if (preanalisis.equals(finCadena)) {
            return;
        } else {
            return;
        }
    }

    void classDeclaration() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(clase)) {
            coincidir(clase);
            coincidir(identificador);
            classInher();
            coincidir(llavesIzq);
            functions();
            coincidir(llavesDer);
        } else {
            hayErrores = true;
            System.out.println("Error classDeclaration");
        }
    }

    void classInher() {
        if (hayErrores)
            return;

        if (preanalisis.equals(menorQue)) {
            coincidir(menorQue);
            coincidir(identificador);
        } else {
            return;
        }
    }

    void funDeclaration() {
        if (hayErrores)
            return;

        if (preanalisis.equals(funcion)) {
            coincidir(funcion);
            function();
        } else {
            hayErrores = true;
            System.out
                    .println("Error funDeclaration");
        }
    }

    void varDeclaration() {
        if (hayErrores)
            return;

        if (preanalisis.equals(variable)) {
            coincidir(variable);
            coincidir(identificador);
            varInit();
            coincidir(puntoComa);
        } else {
            hayErrores = true;
            System.out.println("Error varDeclaration");
        }
    }

    void varInit() {
        if (hayErrores)
            return;

        if (preanalisis.equals(igual)) {
            coincidir(igual);
            expression();
        } else {
            return;
        }
    }

    void statement() {
        if (hayErrores)
            return;
        if (preanalisis.equals(condicionSi)) {
            ifSTMT();
        } else if (preanalisis.equals(cicloPara)) {
            forSTMT();
        } else if (preanalisis.equals(imprime)) {
            printSTMT();
        } else if (preanalisis.equals(regresa)) {
            returnSTMT();
        } else if (preanalisis.equals(cicloMientras)) {
            whileSTMT();
        } else if (preanalisis.equals(llavesIzq)) {
            block();
        } else {
            exprSTMT();
        }
    }

    void exprSTMT() {
        if (hayErrores) {
            return;
        }
        expression();
        coincidir(puntoComa);
    }

    void forSTMT() {
        if (hayErrores)
            return;
        if (preanalisis.equals(cicloPara)) {
            coincidir(cicloPara);
            coincidir(parentesisIzq);
            forSTMT1();
            forSTMT2();
            forSTMT3();
            coincidir(parentesisDer);
            statement();
        } else {
            hayErrores = true;
            System.out.println("Error forSTMT");
        }
    }

    void forSTMT1() {
        if (hayErrores)
            return;

        if (preanalisis.equals(puntoComa)) {
            coincidir(puntoComa);
        } else if (preanalisis.equals(variable)) {
            varDeclaration();
        } else {
            exprSTMT(); // Still needs to find their terminals
        }
    }

    void forSTMT2() {
        if (hayErrores)
            return;

        if (preanalisis.equals(puntoComa)) {
            coincidir(puntoComa);
        } else {
            expression();
            coincidir(puntoComa);
        }
    }

    void forSTMT3() {
        if (hayErrores)
            return;

        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(esto) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parentesisIzq)
                || preanalisis.equals(ssuper) || preanalisis.equals(exclamacion) || preanalisis.equals(menos)
                || preanalisis.equals(cicloPara) || preanalisis.equals(condicionSi) || preanalisis.equals(imprime)
                || preanalisis.equals(regresa) || preanalisis.equals(cicloMientras) || preanalisis.equals(llavesIzq)) {
            expression();
        } else {
            return;
        }
    }

    void ifSTMT() {
        if (hayErrores)
            return;

        if (preanalisis.equals(condicionSi)) {
            coincidir(condicionSi);
            coincidir(parentesisIzq);
            expression();
            coincidir(parentesisDer);
            statement();
            elseSTMT();
        } else {
            hayErrores = true;
            System.out.println("Error ifSTMT");
        }
    }

    void elseSTMT() {
        if (hayErrores)
            return;

        if (preanalisis.equals(condicionDeOtraForma)) {
            coincidir(condicionDeOtraForma);
            statement();
        } else {
            return;
        }
    }

    void printSTMT() {
        if (hayErrores)
            return;

        if (preanalisis.equals(imprime)) {
            coincidir(imprime);
            expression();
            coincidir(puntoComa);
        } else {
            hayErrores = true;
            System.out.println("Error printSTMT");
        }
    }

    void returnSTMT() {
        if (hayErrores)
            return;

        if (preanalisis.equals(regresa)) {
            coincidir(regresa);
            returnExpOpc();
            coincidir(puntoComa);
        } else {
            hayErrores = true;
            System.out.println("Error returnSTMT");
        }
    }

    void returnExpOpc() {
        if (hayErrores)
            return;

        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(esto) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parentesisIzq)
                || preanalisis.equals(ssuper) || preanalisis.equals(exclamacion) || preanalisis.equals(menos)
                || preanalisis.equals(cicloPara) || preanalisis.equals(condicionSi) || preanalisis.equals(imprime)
                || preanalisis.equals(regresa) || preanalisis.equals(cicloMientras) || preanalisis.equals(llavesIzq)) {
            expression();
        } else {
            return;
        }
    }

    void whileSTMT() {
        if (hayErrores)
            return;

        if (preanalisis.equals(cicloMientras)) {
            coincidir(cicloMientras);
            coincidir(parentesisIzq);
            expression();
            coincidir(parentesisDer);
            statement();
        } else {
            hayErrores = true;
        }
    }

    void block() {
        if (hayErrores)
            return;

        if (preanalisis.equals(llavesIzq)) {
            coincidir(llavesIzq);
            blockDecl();
            coincidir(llavesDer);

        } else {
            hayErrores = true;
            System.out.println("Error block");
        }
    }

    void blockDecl() {
        if (hayErrores)
            return;

        if (preanalisis.equals(llavesDer)) {
            return;
        } else {
            Declaration();
            blockDecl();
        }

    }

    void expression() {
        if (hayErrores)
            return;

        assignment();
    }

    void assignment() { // still needs improvement
        if (hayErrores) {
            return;
        }
        logicOr();
        assignmentOpc();
    }

    void assignmentOpc() {
        if (hayErrores)
            return;

        if (preanalisis.equals(igual)) {
            coincidir(igual);
            expression();
        } else {
            return;
        }
    }

    void logicOr() {
        if (hayErrores) {
            return;
        }
        logicAnd();
        logicOr2();
    }

    void logicOr2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(condicionO)) {
            coincidir(condicionO);
            logicAnd();
            logicOr2();
        } else {
            return;
        }
    }

    void logicAnd() {
        if (hayErrores) {
            return;
        }
        equality();
        logicAnd2();
    }

    void logicAnd2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(condicionY)) {
            coincidir(condicionY);
            equality();
            logicAnd2();
        } else {
            return;
        }
    }

    void equality() {
        if (hayErrores) {
            return;
        }
        comparison();
        equality2();
    }

    void equality2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(diferenteQue)) {
            coincidir(diferenteQue);
            comparison();
            equality2();
        } else if (preanalisis.equals(igualQue)) {
            coincidir(igualQue);
            comparison();
            equality2();
        } else {
            return;
        }
    }

    void comparison() {
        if (hayErrores) {
            return;
        }
        term();
        comparison2();
    }

    void comparison2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(mayorQue)) {
            coincidir(mayorQue);
            term();
            comparison2();
        } else if (preanalisis.equals(mayorOIgualQue)) {
            coincidir(mayorOIgualQue);
            term();
            comparison2();
        } else if (preanalisis.equals(menorQue)) {
            coincidir(menorQue);
            term();
            comparison2();
        } else if (preanalisis.equals(menorOIgualQue)) {
            coincidir(menorOIgualQue);
            term();
            comparison2();
        } else {
            return;
        }
    }

    void term() {
        if (hayErrores) {
            return;
        }
        factor();
        term2();
    }

    void term2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(menos)) {
            coincidir(menos);
            factor();
            term2();
        } else if (preanalisis.equals(suma)) {
            coincidir(suma);
            factor();
            term2();
        } else {
            return;
        }
    }

    void factor() {
        if (hayErrores) {
            return;
        }
        unary();
        factor2();
    }

    void factor2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(barra)) {
            coincidir(barra);
            unary();
            factor2();
        } else if (preanalisis.equals(asterisco)) {
            coincidir(asterisco);
            unary();
            factor2();
        } else {
            return;
        }
    }

    void unary() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(exclamacion)) {
            coincidir(exclamacion);
            unary();
        } else if (preanalisis.equals(menos)) {
            coincidir(menos);
            unary();
        } else {
            call();
        }
    }

    void call() {
        if (hayErrores) {
            return;
        }
        primary();
        call2();
    }

    void call2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(parentesisIzq)) {
            coincidir(parentesisIzq);
            argumentsOpc();
            coincidir(parentesisDer);
            call2();
        } else if (preanalisis.equals(punto)) {
            coincidir(punto);
            coincidir(identificador);
            call2();
        } else {
            return;
        }
    }

    void callOpc() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(esto) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parentesisIzq)
                || preanalisis.equals(ssuper)) {
            call();
            coincidir(punto);

        } else {
            return;
        }
    }

    void primary() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(verdadero)) {
            coincidir(verdadero);
        } else if (preanalisis.equals(falso)) {
            coincidir(falso);
        } else if (preanalisis.equals(nulo)) {
            coincidir(nulo);
        } else if (preanalisis.equals(esto)) {
            coincidir(esto);
        } else if (preanalisis.equals(numero)) {
            coincidir(numero);
        } else if (preanalisis.equals(cadena)) {
            coincidir(cadena);
        } else if (preanalisis.equals(identificador)) {
            coincidir(identificador);
        } else if (preanalisis.equals(parentesisIzq)) {
            coincidir(parentesisIzq);
            expression();
            coincidir(parentesisDer);
        } else if (preanalisis.equals(ssuper)) {
            coincidir(ssuper);
            coincidir(punto);
            coincidir(identificador);
        } else {
            hayErrores = true;
            System.out.println("Error primary");
        }
    }

    void function() {
        if (hayErrores)
            return;

        if (preanalisis.equals(identificador)) {
            coincidir(identificador);
            coincidir(parentesisIzq);
            parametersOpc();
            coincidir(parentesisDer);
            block();
        } else {
            hayErrores = true;
            System.out.println("Error function");
        }
    }

    void functions() {
        if (hayErrores)
            return;

        if (preanalisis.equals(identificador)) {
            function();
            functions();
        } else {
            return;
        }
    }

    void parametersOpc() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(identificador)) {
            parameters();
        } else {
            return;
        }
    }

    void parameters() {
        if (hayErrores)
            return;

        if (preanalisis.equals(identificador)) {
            coincidir(identificador);
            parameters2();
        } else {
            hayErrores = true;
            System.out.println("Error parameters");
        }
    }

    void parameters2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(coma)) {
            coincidir(coma);
            coincidir(identificador);
            parameters2();
        } else {
            return;
        }
    }

    void argumentsOpc() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(esto) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parentesisIzq)
                || preanalisis.equals(ssuper) || preanalisis.equals(exclamacion) || preanalisis.equals(menos)) {
            arguments();
        } else {
            return;
        }
    }

    void arguments() {
        if (hayErrores) {
            return;
        }
        expression();
        arguments2();
    }

    void arguments2() {
        if (hayErrores) {
            return;
        }
        if (preanalisis.equals(coma)) {
            coincidir(coma);
            expression();
            arguments2();
        } else {
            return;
        }
    }

    void coincidir(Token t) {
        if (hayErrores)
            return;

        if (preanalisis.tipo == t.tipo) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            hayErrores = true;
            System.out.println("Error en la linea " + preanalisis.linea +
                    ". Se esperaba un  " + t.tipo);

        }
    }
}