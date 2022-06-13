package estructuraslineales.auxiliares;

public class NodoClave {
    protected Object clave;
    protected Object contenido;
    protected NodoClave nodoDer;

    public NodoClave(Object valor, Object id){
        this.clave = id;
        this.contenido = valor;
        nodoDer = null;
    }

    public Object getClave() {
        return clave;
    }

    public void setClave(Object clave) {
        this.clave = clave;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    public NodoClave getNodoDer() {
        return nodoDer;
    }

    public void setNodoDer(NodoClave nodoDer) {
        this.nodoDer = nodoDer;
    }

    @Override
    public String toString(){
        return contenido.toString();
    }
}
