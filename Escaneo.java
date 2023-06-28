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
<<<<<<< Updated upstream
        String cadena = "";
        int columna = 1;
        while(pos < source.length()){
            currentChar = source.charAt(pos);
            
            cadena += currentChar;

            if(currentChar == '\\'){
                linea++;
                pos++;
                columna = 1;
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
                        Interprete.error(linea,"Error en la posición: "+(columna));
                        tokens.clear();
                        return tokens;
                    }
                    cadena = "";
            }
            pos++;
            columna++;
        }
=======
        int estado = 0;
        char caracter = 0;
        String lexema = "";

        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);
            switch (estado){
                case 0:
                    if(caracter == '*'){
                        tokens.add(new Token(TipoToken.ASTERISK, "*"));
                    }
                    else if(caracter == '+'){
                        tokens.add(new Token(TipoToken.PLUS, "+"));
                    }
                    else if(caracter == '-'){
                        tokens.add(new Token(TipoToken.MINUS, "-"));
                    }
                    else if(caracter == '/'){
                        estado = 9;
                        lexema += caracter;
                    }
                    else if(caracter == '('){
                        tokens.add(new Token(TipoToken.PARENTHESIS_LEFT, "("));
                    }
                    else if(caracter == ')'){
                        tokens.add(new Token(TipoToken.PARENTHESIS_RIGHT, ")"));
                    }
                    else if(caracter == '='){
                        tokens.add(new Token(TipoToken.EQUALS, "="));
                    }
                    else if(caracter == '{'){
                        tokens.add(new Token(TipoToken.BRACKET_LEFT, "{"));
                    }
                    else if(caracter == '}'){
                        tokens.add(new Token(TipoToken.BRACKET_RIGHT, "}"));
                    }
                    else if(caracter == ';'){
                        tokens.add(new Token(TipoToken.SEMICOLON, ";"));
                    }
                    else if(Character.isAlphabetic(caracter)){
                        estado = 1;
                        lexema = lexema + caracter;
                    }
                    else if(Character.isDigit(caracter)){
                        estado = 2;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == '>'){
                        estado = 8;
                    }else if(caracter == '\"'){
                        estado = 10;
                    }
                    else if(caracter =='<'){
                        estado = 12;
                    }
                    break;

                case 1:
                    if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) ){
                        lexema += caracter;
                        if(palabrasReservadas.get(lexema) != null){
                            tokens.add(new Token(palabrasReservadas.get(lexema),lexema));
                            lexema = "";
                            estado = 0;
                        }
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(tt == null){
                            tokens.add(new Token(TipoToken.ID, lexema));
                        }
                        else{
                            tokens.add(new Token(tt, lexema));
                        }
                        i--;
                        estado = 0;
                        lexema = "";
                    }
                    break;
                case 2:
                    if(Character.isDigit(caracter)){
                        estado = 2;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == '.'){
                        estado = 3;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == 'E'){
                        estado = 5;
                        lexema = lexema + caracter;
                    }
                    else{
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 3:
                    if(Character.isDigit(caracter)){
                        estado = 4;
                        lexema = lexema + caracter;
                    }
                    else{
                        //Lanzar error
                    }
                    break;
                case 4:
                    if(Character.isDigit(caracter)){
                        estado = 4;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == 'E'){
                        estado = 5;
                        lexema = lexema + caracter;
                    }
                    else{
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 5:
                    if(caracter == '+' || caracter == '-'){
                        estado = 6;
                        lexema = lexema + caracter;
                    }
                    else if(Character.isDigit(caracter)){
                        estado = 7;
                        lexema = lexema + caracter;
                    }
                    else{
                        // Lanzar error
                    }
                    break;
                case 6:
                    if(Character.isDigit(caracter)){
                        estado = 7;
                        lexema = lexema + caracter;
                    }
                    else{
                        // Lanzar error
                    }
                    break;
                case 7:
                    if(Character.isDigit(caracter)){
                        estado = 7;
                        lexema = lexema + caracter;
                    }
                    else{
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 8:
                    if(caracter == '='){
                        tokens.add(new Token(TipoToken.GREATER_OR_EQUAL_THAN, ">="));
                    }
                    else{
                        tokens.add(new Token(TipoToken.GREATER_THAN, ">"));
                        i--;
                    }
                    estado = 0;
                    break;
                case 9:
                    if(caracter == '/'){
                        while(caracter != '\n')
                            caracter = source.charAt(i++);
                        estado = 0;
                    }else if(caracter == '*'){
                        estado = 11;
                    }else{
                        tokens.add(new Token(TipoToken.SLASH, "/"));
                        estado=0;
                    }
                    lexema = "";
                    i--;
                break;
                case 10:
                    do{
                        caracter = source.charAt(i++);
                        lexema += caracter;
                    }while(caracter != '\"');
                    lexema = '"'+lexema;
                    tokens.add(new Token(TipoToken.STRING, lexema));
                    lexema = "";
                    estado = 0;
                    i--;
                break;
                case 11:
                    while(caracter != '*')
                        caracter = source.charAt(i++);
                    if(source.charAt(i++) == '/')
                        estado = 0;
                    lexema = "";
                    break;
                case 12:
                    if(caracter == '='){
                        tokens.add(new Token(TipoToken.LESS_OR_EQUAL_THAN, "<="));
                    }
                    else{
                        tokens.add(new Token(TipoToken.LESS_THAN, "<"));
                        i--;
                    }
                    estado = 0;
                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, ""));

>>>>>>> Stashed changes
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