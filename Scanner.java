package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(String source){
        // cadea es la variable donde se guardaran las palabras, cuenta se usará solamente para las cadenas.
        String cadena = "";
        int cuenta = 0;
        for (int i = 0; i < source.length(); i++) {
            
            // Se obtiene el char de la posición actual.
            char currentChar = source.charAt(i);
            
            // Se verifica si empieza una cadena y se suma la cuenta.
            if (currentChar == '"')
                cuenta++;

            // Si no es un espacio en blanco se añade a la cadena.
            if(!Character.isWhitespace(currentChar))
                cadena += currentChar;

            // Se compara si es un espacio en blanco o el final de la cadena.
            if(Character.isWhitespace(currentChar) || i == source.length()-1){
            
                // Se revisa si es una comilla de cierre.
                if(currentChar == '"' && cuenta == 2){
                    
                    // Se limpia la cuenta, se agrega el token sin la primera comilla.
                    cuenta = 0;
                    tokens.add(new Token(TipoToken.CADENA, cadena.substring(1), null, i));
                }

                
                // Se compara si es un número.
                if(esNumeroEntero(cadena))
                    // Se guarda el número y se vacía la cadena.
                    tokens.add(new Token(TipoToken.NUMERO, cadena, null, i));
                
                // Se verifica si es una de las unas palabras clave.
                else
                    switch(cadena){
                    case "Y":
                        tokens.add(new Token(TipoToken.Y, cadena, null, i));
                        break;
                    case "CLASE":
                        tokens.add(new Token(TipoToken.CLASE, cadena, null, i));
                        break;
                    case "ADEMAS":
                        tokens.add(new Token(TipoToken.ADEMAS, cadena, null, i));
                        break;
                    case "FALSO":
                        tokens.add(new Token(TipoToken.FALSO, cadena, null, i));
                        break;
                    case "PARA":
                        tokens.add(new Token(TipoToken.PARA, cadena, null, i));
                        break;
                    case "FUN":
                        tokens.add(new Token(TipoToken.FUN, cadena, null, i));
                        break;
                    case "SI":
                        tokens.add(new Token(TipoToken.SI, cadena, null, i));
                        break;
                    case "NULO":
                        tokens.add(new Token(TipoToken.NULO, cadena, null, i));
                        break;
                    case "O":
                        tokens.add(new Token(TipoToken.O, cadena, null, i));
                        break;
                    case "IMPRIMIR":
                        tokens.add(new Token(TipoToken.IMPRIMIR, cadena, null, i));
                        break;
                    case "RETORNAR":
                        tokens.add(new Token(TipoToken.RETORNAR, cadena, null, i));
                        break;
                    case "SUPER":
                        tokens.add(new Token(TipoToken.SUPER, cadena, null, i));
                        break;
                    case "ESTE":
                        tokens.add(new Token(TipoToken.ESTE, cadena, null, i));
                        break;
                    case "VERDADERO":
                        tokens.add(new Token(TipoToken.VERDADERO, cadena, null, i));
                        break;
                    case "VAR":
                        tokens.add(new Token(TipoToken.VAR, cadena, null, i));
                        break;
                    case "MIENTRAS":
                        tokens.add(new Token(TipoToken.MIENTRAS, cadena, null, i));
                        break;
                    case "(":
                        tokens.add(new Token(TipoToken.PAR_IZ, cadena, null, i));
                        break;
                    case ")":
                        tokens.add(new Token(TipoToken.PAR_DER, cadena, null, i));
                        break;
                    case "{":
                        tokens.add(new Token(TipoToken.LLAVE_IZ, cadena, null, i));
                        break;
                    case "}":
                        tokens.add(new Token(TipoToken.PAR_DER, cadena, null, i));
                        break;
                    case ",":
                        tokens.add(new Token(TipoToken.COMA, cadena, null, i));
                        break;
                    case ".":
                        tokens.add(new Token(TipoToken.PUNTO, cadena, null, i));
                        break;
                    case ";":
                        tokens.add(new Token(TipoToken.PUNTO_COMA, cadena, null, i));
                        break;
                    case "-":
                        tokens.add(new Token(TipoToken.MENOS, cadena, null, i));
                        break;
                    case "+":
                        tokens.add(new Token(TipoToken.MAS, cadena, null, i));
                        break;
                    case "*":
                        tokens.add(new Token(TipoToken.POR, cadena, null, i));
                        break;
                    case "/":
                        tokens.add(new Token(TipoToken.BARRA, cadena, null, i));
                        break;
                    case "!":
                        tokens.add(new Token(TipoToken.NIEGA, cadena, null, i));
                        break;
                    case "!=":
                        tokens.add(new Token(TipoToken.DIFERENTE_DE, cadena, null, i));
                        break;
                    case "=":
                        tokens.add(new Token(TipoToken.ASIGNA, cadena, null, i));
                        break;
                    case "==":
                        tokens.add(new Token(TipoToken.IGUAL_QUE, cadena, null, i));
                        break;
                    case "<":
                        tokens.add(new Token(TipoToken.MENOR_QUE, cadena, null, i));
                        break;
                    case "<=":
                        tokens.add(new Token(TipoToken.MENOR_O_IGUAL_QUE, cadena, null, i));
                        break;
                    case ">":
                        tokens.add(new Token(TipoToken.MAYOR_QUE, cadena, null, i));
                        break;
                    case ">=":
                        tokens.add(new Token(TipoToken.MAYOR_O_IGUAL_QUE, cadena, null, i));
                        break;
                    default:
                        tokens.add(new Token(TipoToken.IDENTIFICADOR, cadena, cadena, i));
                        break;

                }

                // Se limpia la cadena.
                cadena = "";
            }
            
        }

        tokens.add(new Token(TipoToken.EOF, null , null, source.length()));

        return tokens;
    }

    public static boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

