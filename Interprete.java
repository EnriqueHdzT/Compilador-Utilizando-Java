
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Interprete {

    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");

            // Convención defininida en el archivo "system.h" de UNIX
            System.exit(64);
        } else if (args.length == 1) {
            ejecutarArchivo(args[0]);
        } else {
            ejecutarPrompt();
        }
    }

    private static void ejecutarArchivo(String path) throws IOException {
        File arch_entrada = new File(path);
        Scanner texto = new Scanner(arch_entrada);
        String data = "";
        while (texto.hasNextLine()) {
            data += texto.nextLine() + "\n";
        }
        texto.close();
        data = data.replace(" ", "");
        data = data.replace("\t", "");
        ejecutar(data);

        // Se indica que existe un error
        if (existenErrores)
            System.exit(65);
    }

    private static void ejecutarPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print(">>> ");
            String linea = reader.readLine();
            linea = linea.replace(" ", "");
            linea = linea.replace("\t", "");
            if (linea == null)
                break; // Presionar Ctrl + D
            ejecutar(linea);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source) {
        Escaneo scanner = new Escaneo(source);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }

        Parseo parseo = new Parseo(tokens);
        parseo.parseo();

    }

    /*
     * El método error se puede usar desde las distintas clases
     * para reportar los errores:
     * Interprete.error(....);
     */
    static void error(int linea, String mensaje) {
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje) {
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje);
        existenErrores = true;
    }
}
