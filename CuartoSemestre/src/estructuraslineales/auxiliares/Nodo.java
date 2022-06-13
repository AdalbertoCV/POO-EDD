package estructuraslineales.auxiliares;

public class Nodo {
    protected Object contenido;
    protected Nodo nodoDer;

    public Nodo(Object valor){
        contenido=valor;
        nodoDer=null;
    }

    public Object getContenido() {
        return contenido;
    }

    public Nodo getNodoDer() {
        return nodoDer;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    public void setNodoDer(Nodo nodoDer) {
        this.nodoDer = nodoDer;
    }

    @Override
    public String toString(){
        return contenido.toString();
    }
}
