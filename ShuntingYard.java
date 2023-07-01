import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class ShuntingYard{
    List<Token> postfija;
    Deque<Token> pila = new ArrayDeque<Token>();
    List<Token> NuevoOrden = new ArrayList<Token>();

    public ShuntingYard(List<Token> tokens){
        this.postfija = tokens;
    }

    public List<Token> algoritmo(){
        
        for (Token token : postfija) {
            if(token.tipo == TipoToken.SEMICOLON || token.tipo == TipoToken.BRACKET_LEFT){
                semiColon();
            }
            if(token.esOperador()){
                revisarPrecedencia(token);
                pila.addFirst(token);
            }else
                if( token.tipo != TipoToken.PARENTHESIS_LEFT && token.tipo != TipoToken.PARENTHESIS_RIGHT &&
                    token.tipo != TipoToken.BRACKET_LEFT)
                    NuevoOrden.add(token);

        }
        while(!pila.isEmpty())
            NuevoOrden.add(pila.pop());
        return NuevoOrden;
    }



    public void revisarPrecedencia(Token token){
        if(!pila.isEmpty()){
            Token top = pila.getFirst();
            
            if(token.precedencia() <= top.precedencia()){
                NuevoOrden.add(pila.pop());
                revisarPrecedencia(token);
            }
        }
}

    public void semiColon(){
        Token top = null;
        do{
            if(!pila.isEmpty()){
                top = pila.getFirst();
                NuevoOrden.add(pila.pop());
            }
        }while(!pila.isEmpty() && top.tipo != TipoToken.EQUALS);
    }
}