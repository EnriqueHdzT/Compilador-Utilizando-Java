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
        int linea = 0;
        int estado = 0;
        char caracter = 0;
        String lexema = "";
        boolean foundNumber = false;
        boolean foundDot = false;
        boolean shouldBeID = false;
        int inicioLexema = 0;
        int i = 0;

        while (i < source.length()) {
            caracter = source.charAt(i);
            if (source.charAt(i) == '\n') {
                linea++;
                estado = 0;
                lexema = "";
                i++;
            } else {
                switch (estado) {
                    case 0:
                        if (caracter == '{') {
                            tokens.add(new Token(TipoToken.BRACKET_LEFT, "{", linea));
                            i++;
                        } else if (caracter == '}') {
                            tokens.add(new Token(TipoToken.BRACKET_RIGHT, "}", linea));
                            i++;
                        } else if (caracter == '(') {
                            tokens.add(new Token(TipoToken.PARENTHESIS_LEFT, "(", linea));
                            i++;
                        } else if (caracter == ')') {
                            tokens.add(new Token(TipoToken.PARENTHESIS_RIGHT, ")", linea));
                            i++;
                        } else if (caracter == ';') {
                            tokens.add(new Token(TipoToken.SEMICOLON, ";", linea));
                            i++;
                        } else if (caracter == '=') {
                            if (source.charAt(i + 1) == '=') {
                                tokens.add(new Token(TipoToken.EQUAL_THAN, "==", linea));
                                i = i + 2;
                            } else {
                                tokens.add(new Token(TipoToken.EQUALS, "=", linea));
                                i++;
                            }
                        } else if (caracter == '!') {
                            if (source.charAt(i + 1) == '=') {
                                tokens.add(new Token(TipoToken.DIFFERENT_THAN, "!=", linea));
                                i = i + 2;
                            }
                            tokens.add(new Token(TipoToken.EXCLAMATION, "!", linea));
                            i++;
                        } else if (caracter == '<') {
                            if (source.charAt(i + 1) == '=') {
                                tokens.add(new Token(TipoToken.LESS_OR_EQUAL_THAN, "<=", linea));
                                i = i + 2;
                            } else {
                                tokens.add(new Token(TipoToken.LESS_THAN, "<", linea));
                                i++;
                            }
                        } else if (caracter == '>') {
                            if (source.charAt(i + 1) == '=') {
                                tokens.add(new Token(TipoToken.GREATER_OR_EQUAL_THAN, ">=", linea));
                                i = i + 2;
                            }
                            tokens.add(new Token(TipoToken.GREATER_THAN, ">", linea));
                            i++;
                        } else if (caracter == '-') {
                            tokens.add(new Token(TipoToken.MINUS, "-", linea));
                            i++;
                        } else if (caracter == '+') {
                            tokens.add(new Token(TipoToken.PLUS, "-", linea));
                            i++;
                        } else if (caracter == '/') {
                            tokens.add(new Token(TipoToken.SLASH, "/", linea));
                            i++;
                        } else if (caracter == '*') {
                            tokens.add(new Token(TipoToken.ASTERISK, "*", linea));
                            i++;
                        } else if (caracter == ',') {
                            tokens.add(new Token(TipoToken.COMMA, ",", linea));
                            i++;
                        } else if (caracter == '.') {
                            if (foundNumber && !foundDot) {
                                foundDot = true;
                                lexema = lexema + caracter;
                                estado = 1;
                                i++;
                            } else {
                                tokens.add(new Token(TipoToken.DOT, ".", linea));
                                i++;
                            }
                        } else if (Character.isAlphabetic(caracter) || Character.isDigit(caracter)) {
                            if (Character.isDigit(caracter) && !foundNumber) {
                                foundNumber = !foundNumber;
                            }
                            estado = 1;
                            inicioLexema = i;
                        }
                        break;

                    case 1:
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if (tt != null) {
                            tokens.add(new Token(tt, lexema, linea));
                            if (tt == TipoToken.CLASS ||
                                    tt == TipoToken.VARIABLE ||
                                    tt == TipoToken.FUNCTION ||
                                    tt == TipoToken.SUPER) {
                                shouldBeID = true;
                            } else {
                                shouldBeID = false;
                            }
                            lexema = "";
                            estado = 0;
                            break;
                        }
                        if (Character.isAlphabetic(caracter) || Character.isDigit(caracter)) {
                            if (Character.isAlphabetic(caracter) && foundNumber) {
                                tokens.add(new Token(TipoToken.NUMBER, lexema));
                                lexema = "" + caracter;
                                inicioLexema = i;
                                foundNumber = false;
                                foundDot = false;
                                i++;
                            } else {
                                lexema = lexema + caracter;
                                i++;
                            }
                        } else if (foundNumber) {
                            tokens.add(new Token(TipoToken.NUMBER, lexema, linea));
                            lexema = "";
                            estado = 0;
                            foundNumber = false;
                            foundDot = false;
                        } else if (shouldBeID) {
                            tokens.add(new Token(TipoToken.ID, lexema, linea));
                            lexema = "";
                            estado = 0;
                            shouldBeID = false;
                        } else {
                            tokens.add(new Token(TipoToken.STRING, lexema, linea));
                            lexema = "";
                            estado = 0;

                        }
                }
            }

        }

        tokens.add(new Token(TipoToken.EOF, "EOF", linea));
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