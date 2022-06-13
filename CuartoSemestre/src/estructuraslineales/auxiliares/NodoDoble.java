package estructuraslineales.auxiliares;

public class NodoDoble {
    protected NodoDoble nodoIzq;
    protected Object contenido;
    protected NodoDoble nodoDer;

    public NodoDoble(Object valor){
        this.contenido=valor;
        nodoIzq=null;
        nodoDer=null;
    }

    public NodoDoble getNodoIzq() {
        return nodoIzq;
    }

    public void setNodoIzq(NodoDoble nodoIzq) {
        this.nodoIzq = nodoIzq;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    public NodoDoble getNodoDer() {
        return nodoDer;
    }

    public void setNodoDer(NodoDoble nodoDer) {
        this.nodoDer = nodoDer;
    }

    @Override
    public String toString(){
        return contenido.toString();
    }
}
