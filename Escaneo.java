import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Escaneo {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 0;

    private char currentChar;

    private int pos = 0;


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

    Escaneo(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        String cadena = "";
        while(pos < source.length()){
            currentChar = source.charAt(pos);
            
            cadena += currentChar;

            if(currentChar == '\\'){
                linea++;
                pos++;
                cadena = "";
                continue;
            }
            if(palabrasReservadas.get(cadena) instanceof TipoToken){
                tokens.add(new Token(palabrasReservadas.get(cadena), cadena, cadena, linea));
                cadena="";
            }

            if(!Character.isLetterOrDigit(currentChar)){
                if(cadena.length()>=2){
                    if(esNumero(cadena.substring(0,cadena.length()-1)))
                        tokens.add(new Token(TipoToken.NUMERO, cadena.substring(0,cadena.length()-1), cadena.substring(0,cadena.length()-1), linea));
                    else    
                        tokens.add(new Token(TipoToken.IDENTIFICADOR, cadena.substring(0, cadena.length()-1), null, linea));
                    pos--;
                }else
                    switch(currentChar){
                        case '(':
                            tokens.add(new Token(TipoToken.PAR_IZ, cadena, null, linea));
                            break;
                        case ')':
                            tokens.add(new Token(TipoToken.PAR_DER, cadena, null, linea));
                            break;
                        case '{':
                            tokens.add(new Token(TipoToken.LLAVE_IZ, cadena, null, linea));
                            break;
                        case '}':
                            tokens.add(new Token(TipoToken.PAR_DER, cadena, null, linea));
                            break;
                        case ',':
                            tokens.add(new Token(TipoToken.COMA, cadena, null, linea));
                            break;
                        case '.':
                            tokens.add(new Token(TipoToken.PUNTO, cadena, null, linea));
                            break;
                        case ';':
                            tokens.add(new Token(TipoToken.PUNTO_COMA, cadena, null, linea));
                            break;
                        case '-':
                            tokens.add(new Token(TipoToken.MENOS, cadena, null, linea));
                            break;
                        case '+':
                            tokens.add(new Token(TipoToken.MAS, cadena, null, linea));
                            break;
                        case '*':
                            currentChar = source.charAt(pos+1);
                            if(currentChar == '/')
                                tokens.add(new Token(TipoToken.COMENTARIO_LARGO, "*/", null, linea));
                            else
                                tokens.add(new Token(TipoToken.POR, cadena, null, linea));
                            break;
                        case '/':
                            currentChar = source.charAt(pos+1);
                            switch (currentChar) {
                                case '/':
                                    tokens.add(new Token(TipoToken.LINEA_COMENTARIO, "//", null, linea));
                                    pos++;
                                    break;
                                case '*':
                                    tokens.add(new Token(TipoToken.COMENTARIO_LARGO, "/*", null, linea));
                                    pos++;
                                    break;
                                default:
                                    tokens.add(new Token(TipoToken.BARRA, "/", null, linea));
                                    break;
                            }
                        break;
                    case '!':
                        currentChar = source.charAt(pos+1);
                        if(currentChar == '='){
                                tokens.add(new Token(TipoToken.DIFERENTE_DE, "!=", null, linea));
                                pos++;
                            }else
                                tokens.add(new Token(TipoToken.NIEGA, "!", null, linea));
                        break;
                    case '=':
                        currentChar = source.charAt(pos+1);
                        switch(currentChar){
                            case '=':
                                tokens.add(new Token(TipoToken.IGUAL_QUE, "==", null, linea));
                                pos++;
                                break;
                            default:
                                tokens.add(new Token(TipoToken.ASIGNA, "=", null, linea));
                                break;
                        }
                        break;
                    case '<':
                        currentChar = source.charAt(pos+1);
                        switch (currentChar) {
                            case '=':
                                tokens.add(new Token(TipoToken.MENOR_O_IGUAL_QUE, "<=", null, linea));
                                pos++;
                                break;
                            default:
                                tokens.add(new Token(TipoToken.MENOR_QUE, "<", null, linea));
                                break;
                        }
                        break;
                    case '>':
                        currentChar = source.charAt(pos + 1);
                        switch (currentChar) {
                            case '=':
                                tokens.add(new Token(TipoToken.MENOR_O_IGUAL_QUE, ">=", null, linea));
                                pos++;
                                break;
                            default:
                                tokens.add(new Token(TipoToken.MENOR_QUE, null, null, linea));
                                break;
                        }
                        break;
                        
                    default:
                        Interprete.error(linea,"Error en la posición: "+(pos+1));
                        tokens.clear();
                        return tokens;
                    }
                    cadena = "";
            }
            pos++;
        }
        return tokens;
    }   
 
    public static boolean esNumero(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}