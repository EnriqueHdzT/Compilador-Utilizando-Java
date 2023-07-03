public class variablesDeclaradas {
    public String nombre;
    public TipoToken tipoToken;
    public Object valorNumerico;
    public Object valorTexto;

    public variablesDeclaradas(String nombre, TipoToken tipoToken, Object valorNumerico, Object valorTexto) {
        this.nombre = nombre;
        this.tipoToken = tipoToken;
        this.valorNumerico = valorNumerico;
        this.valorTexto = valorTexto;
    }
}