import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Escaneo {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("fun", TipoToken.FUNCTION);
        palabrasReservadas.put("var", TipoToken.VARIABLE);
        palabrasReservadas.put("while", TipoToken.WHILE_LOOP);
        palabrasReservadas.put("for", TipoToken.FOR_LOOP);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("super", TipoToken.SUPER);
    }

    Escaneo(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        int estado = 0;
        char caracter = 0;
        String lexema = "";

        for (int i = 0; i < source.length(); i++) {
            caracter = source.charAt(i);
            switch (estado) {
                case 0:
                    if (caracter == '*') {
                        tokens.add(new Token(TipoToken.ASTERISK, "*"));
                    } else if (caracter == '+') {
                        tokens.add(new Token(TipoToken.PLUS, "+"));
                    } else if (caracter == '-') {
                        tokens.add(new Token(TipoToken.MINUS, "-"));
                    } else if (caracter == '/') {
                        estado = 9;
                        lexema += caracter;
                    } else if (caracter == '(') {
                        tokens.add(new Token(TipoToken.PARENTHESIS_LEFT, "("));
                    } else if (caracter == ')') {
                        tokens.add(new Token(TipoToken.PARENTHESIS_RIGHT, ")"));
                    } else if (caracter == '=') {
                        tokens.add(new Token(TipoToken.EQUALS, "="));
                    } else if (caracter == '{') {
                        tokens.add(new Token(TipoToken.BRACKET_LEFT, "{"));
                    } else if (caracter == '}') {
                        tokens.add(new Token(TipoToken.BRACKET_RIGHT, "}"));
                    } else if (caracter == ';') {
                        tokens.add(new Token(TipoToken.SEMICOLON, ";"));
                    } else if (Character.isAlphabetic(caracter)) {
                        estado = 1;
                        lexema = lexema + caracter;
                    } else if (Character.isDigit(caracter)) {
                        estado = 2;
                        lexema = lexema + caracter;
                    } else if (caracter == '>') {
                        estado = 8;
                    } else if (caracter == '\"') {
                        estado = 10;
                    } else if (caracter == '<') {
                        estado = 12;
                    }
                    break;

                case 1:
                    if (Character.isAlphabetic(caracter) || Character.isDigit(caracter)) {
                        lexema += caracter;
                        if (palabrasReservadas.get(lexema) != null) {
                            tokens.add(new Token(palabrasReservadas.get(lexema), lexema));
                            lexema = "";
                            estado = 0;
                        }
                    } else {
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if (tt == null) {
                            tokens.add(new Token(TipoToken.ID, lexema));
                        } else {
                            tokens.add(new Token(tt, lexema));
                        }
                        i--;
                        estado = 0;
                        lexema = "";
                    }
                    break;
                case 2:
                    if (Character.isDigit(caracter)) {
                        estado = 2;
                        lexema = lexema + caracter;
                    } else if (caracter == '.') {
                        estado = 3;
                        lexema = lexema + caracter;
                    } else if (caracter == 'E') {
                        estado = 5;
                        lexema = lexema + caracter;
                    } else {
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 3:
                    if (Character.isDigit(caracter)) {
                        estado = 4;
                        lexema = lexema + caracter;
                    } else {
                        // Lanzar error
                    }
                    break;
                case 4:
                    if (Character.isDigit(caracter)) {
                        estado = 4;
                        lexema = lexema + caracter;
                    } else if (caracter == 'E') {
                        estado = 5;
                        lexema = lexema + caracter;
                    } else {
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 5:
                    if (caracter == '+' || caracter == '-') {
                        estado = 6;
                        lexema = lexema + caracter;
                    } else if (Character.isDigit(caracter)) {
                        estado = 7;
                        lexema = lexema + caracter;
                    } else {
                        // Lanzar error
                    }
                    break;
                case 6:
                    if (Character.isDigit(caracter)) {
                        estado = 7;
                        lexema = lexema + caracter;
                    } else {
                        // Lanzar error
                    }
                    break;
                case 7:
                    if (Character.isDigit(caracter)) {
                        estado = 7;
                        lexema = lexema + caracter;
                    } else {
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 8:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.GREATER_OR_EQUAL_THAN, ">="));
                    } else {
                        tokens.add(new Token(TipoToken.GREATER_THAN, ">"));
                        i--;
                    }
                    estado = 0;
                    break;
                case 9:
                    if (caracter == '/') {
                        while (caracter != '\n')
                            caracter = source.charAt(i++);
                        estado = 0;
                    } else if (caracter == '*') {
                        estado = 11;
                    } else {
                        tokens.add(new Token(TipoToken.SLASH, "/"));
                        estado = 0;
                    }
                    lexema = "";
                    i--;
                    break;
                case 10:
                    do {
                        caracter = source.charAt(i++);
                        lexema += caracter;
                    } while (caracter != '\"');
                    lexema = '"' + lexema;
                    tokens.add(new Token(TipoToken.STRING, lexema));
                    lexema = "";
                    estado = 0;
                    i--;
                    break;
                case 11:
                    while (caracter != '*')
                        caracter = source.charAt(i++);
                    if (source.charAt(i++) == '/')
                        estado = 0;
                    lexema = "";
                    break;
                case 12:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.LESS_OR_EQUAL_THAN, "<="));
                    } else {
                        tokens.add(new Token(TipoToken.LESS_THAN, "<"));
                        i--;
                    }
                    estado = 0;
                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, ""));

        return tokens;
    }

    public static boolean esNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}