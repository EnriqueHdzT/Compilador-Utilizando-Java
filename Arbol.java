
import java.util.Deque;
import java.util.ArrayDeque;

public class Arbol {
    private final Nodo raiz;
    public Deque<Token> tokens = new ArrayDeque<Token>();

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public void recorrer() {

        SolverAritmetico resultado = new SolverAritmetico(raiz);
        resultado.resolver();

        /*
         * // Imprime arbol
         * recorrerNodo(raiz);
         *
         * while (!tokens.isEmpty())
         * System.out.print(tokens.pop().lexema + " ");
         * System.out.println();
         */
    }

    public void recorrerNodo(Nodo n) {
        if (n.getHijos() != null) {
            for (int i = 0; i < n.getHijos().size(); i++) {
                System.out.println(n.getHijos().get(i).getValue());
                tokens.addFirst(n.getHijos().get(i).getValue());
                recorrerNodo(n.getHijos().get(i));
            }
        }
    }
}
