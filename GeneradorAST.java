
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;

public class GeneradorAST {

    private final List<Token> postfija;

    public GeneradorAST(List<Token> postfija){
        this.postfija = postfija;
    }

    public Arbol generarAST() {
        Deque<Nodo> pilaPadres = new ArrayDeque<>();
        Deque<Nodo> pilaHijos = new ArrayDeque<>();
        Nodo raiz = new Nodo(null);
        pilaPadres.push(raiz);

        

        for (Token token : postfija) {
            Nodo nodo = new Nodo(token);
            Nodo padre = raiz;
            if(token.tipo == TipoToken.SEMICOLON){
                while(!pilaHijos.isEmpty()){
                        Nodo pop = pilaHijos.pop();
                        if(pop.getValue().esOperador()){
                            padre.insertarHijo(pop);
                            padre = pop;
                        }
                        else
                            padre.insertarHijo(pop);
                }
            }
            
            if(token.esOperador()){
                int aridad = token.aridad();
                for (int i = 0; i < aridad; i++) {
                    Nodo top = pilaHijos.pop();
                    nodo.insertarHijo(top);
                }
                pilaHijos.addFirst(nodo);
            }
            else{
                if(token.tipo != TipoToken.VARIABLE && token.tipo != TipoToken.SEMICOLON)
                    pilaHijos.addFirst(nodo);
            }
        }
        // Suponiendo que en la pila sólamente queda un nodo
        // Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz);
        return programa;
    }
}
