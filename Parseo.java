/*
 * import java.util.List;
 * 
 * public class Parseo {
 * private final List<Token> tokens;
 * 
 * private final Token clase = new Token(TipoToken.CLASS, "class");
 * private final Token funcion = new Token(TipoToken.FUNCTION, "fun");
 * private final Token variable = new Token(TipoToken.VARIABLE, "var");
 * private final Token cicloMientras = new Token(TipoToken.WHILE_LOOP, "while");
 * private final Token cicloPara = new Token(TipoToken.FOR_LOOP, "for");
 * private final Token condicionSi = new Token(TipoToken.IF, "if");
 * private final Token condicionDeOtraForma = new Token(TipoToken.ELSE, "else");
 * private final Token condicionO = new Token(TipoToken.OR, "or");
 * private final Token condicionY = new Token(TipoToken.AND, "and");
 * private final Token imprime = new Token(TipoToken.PRINT, "print");
 * private final Token regresa = new Token(TipoToken.RETURN, "return");
 * private final Token verdadero = new Token(TipoToken.TRUE, "true");
 * private final Token falso = new Token(TipoToken.FALSE, "false");
 * private final Token nulo = new Token(TipoToken.NULL, "null");
 * private final Token esto = new Token(TipoToken.THIS, "this");
 * private final Token ssuper = new Token(TipoToken.SUPER, "super");
 * 
 * private final Token parentesisIzq = new Token(TipoToken.PARENTHESIS_LEFT,
 * "(");
 * private final Token parentesisDer = new Token(TipoToken.PARENTHESIS_RIGHT,
 * ")");
 * private final Token llavesIzq = new Token(TipoToken.BRACKET_LEFT, "{");
 * private final Token llavesDer = new Token(TipoToken.BRACKET_RIGHT, "}");
 * private final Token puntoComa = new Token(TipoToken.SEMICOLON, ";");
 * private final Token igual = new Token(TipoToken.EQUALS, "=");
 * private final Token exclamacion = new Token(TipoToken.EXCLAMATION, "!");
 * private final Token menorQue = new Token(TipoToken.LESS_THAN, "<");
 * private final Token mayorQue = new Token(TipoToken.GREATER_THAN, ">");
 * private final Token menorOIgualQue = new Token(TipoToken.LESS_OR_EQUAL_THAN,
 * "<=");
 * private final Token mayorOIgualQue = new
 * Token(TipoToken.GREATER_OR_EQUAL_THAN, ">=");
 * private final Token diferenteQue = new Token(TipoToken.DIFFERENT_THAN, "!=");
 * private final Token igualQue = new Token(TipoToken.EQUAL_THAN, "==");
 * private final Token menor = new Token(TipoToken.MINUS, "-");
 * private final Token suma = new Token(TipoToken.PLUS, "+");
 * private final Token barra = new Token(TipoToken.SLASH, "/");
 * private final Token asterisco = new Token(TipoToken.ASTERISK, "*");
 * private final Token coma = new Token(TipoToken.COMMA, ",");
 * private final Token punto = new Token(TipoToken.DOT, ".");
 * 
 * private final Token identificador = new Token(TipoToken.ID, "");
 * private final Token numero = new Token(TipoToken.NUMBER, "");
 * private final Token cadena = new Token(TipoToken.STRING, "");
 * private final Token finCadena = new Token(TipoToken.EOF, "");
 * 
 * private int i = 0;
 * private boolean hayErrores = false;
 * 
 * private Token preanalisis;
 * 
 * public Parseo(List<Token> tokens) {
 * this.tokens = tokens;
 * }
 * 
 * public void parseo() {
 * i = 0;
 * preanalisis = tokens.get(i);
 * D();
 * 
 * if (!hayErrores && !preanalisis.equals(finCadena)) {
 * System.out.println(
 * "Error en la posición " + preanalisis.linea + ". No se esperaba el token " +
 * preanalisis.tipo);
 * } else if (!hayErrores && preanalisis.equals(finCadena)) {
 * System.out.println("Consulta válida");
 * }
 * 
 * /*
 * if(!preanalisis.equals(finCadena)){
 * System.out.println("Error en la posición " + preanalisis.posicion +
 * ". No se esperaba el token " + preanalisis.tipo);
 * }else if(!hayErrores){
 * System.out.println("Consulta válida");
 * }
 * 
 * }
 * 
 * void D() {
 * if (preanalisis.equals(clase)) {
 * coincidir(clase);
 * C();
 * coincidir(parentesisIzq);
 * T();
 * } else if (preanalisis.equals(funcion)) {
 * coincidir(clase);
 * C();
 * coincidir(parentesisIzq);
 * T();
 * } else if (preanalisis.equals(variable)) {
 * coincidir(clase);
 * C();
 * coincidir(parentesisIzq);
 * T();
 * } else {
 * hayErrores = true;
 * System.out.println(
 * "Error en la posición " + preanalisis.linea +
 * ". Se esperaba la palabra reservada SELECT.");
 * }
 * }
 * 
 * void C() {
 * if (hayErrores)
 * return;
 * 
 * D();
 * 
 * if (preanalisis.equals(distinct)) {
 * coincidir(distinct);
 * P();
 * } else if (preanalisis.equals(asterisco) ||
 * preanalisis.equals(identificador)) {
 * P();
 * } else {
 * hayErrores = true;
 * System.out.println(
 * "Error en la posición " + preanalisis.linea +
 * ". Se esperaba DISTINCT, * o un identificador.");
 * }
 * }
 * 
 * void P() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(asterisco)) {
 * coincidir(asterisco);
 * } else if (preanalisis.equals(identificador)) {
 * A();
 * } else {
 * hayErrores = true;
 * System.out
 * .println("Error en la posición \" + preanalisis.posicion + \". Se esperaba * o un identificador."
 * );
 * }
 * }
 * 
 * void A() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(identificador)) {
 * A2();
 * A1();
 * } else {
 * hayErrores = true;
 * System.out.println("Error en la posición " + preanalisis.linea +
 * ". Se esperaba un identificador.");
 * }
 * }
 * 
 * void A1() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(coma)) {
 * coincidir(coma);
 * A();
 * }
 * }
 * 
 * void A2() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(identificador)) {
 * coincidir(identificador);
 * A3();
 * } else {
 * hayErrores = true;
 * System.out.println("Error en la posición " + preanalisis.linea +
 * ". Se esperaba un identificador.");
 * }
 * }
 * 
 * void A3() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(punto)) {
 * coincidir(punto);
 * coincidir(identificador);
 * }
 * }
 * 
 * void T() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(identificador)) {
 * T2();
 * T1();
 * } else {
 * hayErrores = true;
 * System.out.println("Error en la posición " + preanalisis.linea +
 * ". Se esperaba un identificador.");
 * }
 * }
 * 
 * void T1() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(coma)) {
 * coincidir(coma);
 * T();
 * }
 * }
 * 
 * void T2() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(identificador)) {
 * coincidir(identificador);
 * T3();
 * } else {
 * hayErrores = true;
 * System.out.println("Error en la posición " + preanalisis.linea +
 * ". Se esperaba un identificador.");
 * }
 * }
 * 
 * void T3() {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.equals(identificador)) {
 * coincidir(identificador);
 * }
 * }
 * 
 * void coincidir(Token t) {
 * if (hayErrores)
 * return;
 * 
 * if (preanalisis.tipo == t.tipo) {
 * i++;
 * preanalisis = tokens.get(i);
 * } else {
 * hayErrores = true;
 * System.out.println("Error en la posición " + preanalisis.linea +
 * ". Se esperaba un  " + t.tipo);
 * 
 * }
 * }
 * 
 * }
 */